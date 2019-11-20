package com.pidois.ester.Controller;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlarmManager;
import android.app.AlertDialog;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Shader;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.pidois.ester.Controller.Adapter.ProfileAdapter;
import com.pidois.ester.Models.Profile;
import com.pidois.ester.R;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Transformation;


import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ProfileActivity extends AppCompatActivity implements View.OnClickListener {

    public RecyclerView vRecyclerView;
    public ProfileAdapter profileAdapter;
    public List<Profile> dataProfile = new ArrayList<>();

    public TextView profileName;
    public ImageView profileImg;
    private ProgressBar progressBar;

    private FirebaseAuth mAuth;

    private GoogleSignInClient mGoogleSignInClient;

    private DatabaseReference databaseReference;
    private FirebaseDatabase firebaseDatabase;

    private FirebaseUser currentFirebaseUser;

    private Long rightAnswers, totalAnswers, wrongAnswers;
    private String cognitiveDate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        profileName = findViewById(R.id.profileName);
        profileImg = findViewById(R.id.profile_image);
        progressBar = findViewById(R.id.profile_progressbar);


        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();

        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);

        findViewById(R.id.btn_logout2).setOnClickListener(this);

        mAuth = FirebaseAuth.getInstance();

        FirebaseUser currentUser = mAuth.getCurrentUser();
        updateUI(currentUser);

        Picasso.get()
                .load(currentUser.getPhotoUrl())
                .transform(new CircleTransform())
                .into(profileImg);


        profileName.setText(currentUser.getDisplayName());


        Profile profile = new Profile();
        profile.setType(1);
        profile.setName(currentUser.getDisplayName());
        profile.setEmail(currentUser.getEmail());
        profile.setBirthday("24/10/2016");
        dataProfile.add(profile);

        profile = new Profile();
        profile.setType(4);
        profile.setSoundLevel("5");
        profile.setSoundRightAnswers("20");
        profile.setSoundDate("25/10/2019");
        dataProfile.add(profile);

        profile = new Profile();
        profile.setType(5);
        profile.setColorLevel("2");
        profile.setColorRightanswers("12");
        profile.setColorDate("01/01/1960");
        dataProfile.add(profile);

        profile = new Profile();
        profile.setType(2);
        profile.setTremorPos1("1");
        profile.setTremorPos2("2");
        profile.setTremorPos3("3");
        profile.setDate("24/10/2019");
        dataProfile.add(profile);

        vRecyclerView = findViewById(R.id.profile_myrecycler);
        vRecyclerView.setHasFixedSize(true);
        vRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        profileAdapter = new ProfileAdapter(this, dataProfile);
        //vRecyclerView.setAdapter(profileAdapter);

        getCognitiveLastAsnwer(currentFirebaseUser, firebaseDatabase, databaseReference);

    }


    private void signOut() {
        // Firebase sign out
        mAuth.signOut();

        // Google sign out
        mGoogleSignInClient.signOut().addOnCompleteListener(this,
                new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        updateUI(null);
                        Handler handler = new Handler();
                        handler.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                restartApp();
                            }
                        }, 500);
                    }
                });
    }

    @Override
    public void onClick(View view) {
        int i = view.getId();
        if (i == R.id.btn_logout2) {
            //signOut();
            alertDialog();
        }
    }

    private void updateUI(FirebaseUser user) {
        if (user == null) {
            findViewById(R.id.btn_logout2).setVisibility(View.INVISIBLE);
        } else {
            findViewById(R.id.btn_logout2).setVisibility(View.VISIBLE);
        }
    }

    public class CircleTransform implements Transformation {
        @Override
        public Bitmap transform(Bitmap source) {
            int size = Math.min(source.getWidth(), source.getHeight());

            int x = (source.getWidth() - size) / 2;
            int y = (source.getHeight() - size) / 2;

            Bitmap squaredBitmap = Bitmap.createBitmap(source, x, y, size, size);
            if (squaredBitmap != source) {
                source.recycle();
            }

            Bitmap bitmap = Bitmap.createBitmap(size, size, source.getConfig());

            Canvas canvas = new Canvas(bitmap);
            Paint paint = new Paint();
            BitmapShader shader = new BitmapShader(squaredBitmap,
                    Shader.TileMode.CLAMP, Shader.TileMode.CLAMP);
            paint.setShader(shader);
            paint.setAntiAlias(true);

            float r = size / 2f;
            canvas.drawCircle(r, r, r, paint);

            squaredBitmap.recycle();
            return bitmap;
        }

        @Override
        public String key() {
            return "circle";
        }
    }

    public void restartApp() {
        Intent mStartActivity = new Intent(ProfileActivity.this, SplashScreen.class);
        int mPendingIntentId = 123456;
        PendingIntent mPendingIntent = PendingIntent.getActivity(ProfileActivity.this, mPendingIntentId, mStartActivity, PendingIntent.FLAG_CANCEL_CURRENT);
        AlarmManager mgr = (AlarmManager) ProfileActivity.this.getSystemService(Context.ALARM_SERVICE);
        mgr.set(AlarmManager.RTC, System.currentTimeMillis() + 100, mPendingIntent);
        System.exit(0);
    }

    private void alertDialog() {
        AlertDialog.Builder dialog = new AlertDialog.Builder(this);
        dialog.setMessage("Tem certeza que deseja sair ou trocar de usuário? Este aplicativo só funciona com alguma conta registrada.");
        dialog.setTitle("Sair");
        dialog.setPositiveButton("sim",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog,
                                        int which) {
                        signOut();
                    }
                });
        dialog.setNegativeButton("cancelar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //
            }
        });
        AlertDialog alertDialog = dialog.create();
        alertDialog.show();
    }

    private void getCognitiveLastAsnwer(FirebaseUser currentFirebaseUser, FirebaseDatabase firebaseDatabase, DatabaseReference databaseReference) {

        currentFirebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference("cognitive_answers/" + currentFirebaseUser.getUid());

        databaseReference.limitToLast(1).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                for (DataSnapshot ds : snapshot.getChildren()) {
                    rightAnswers = ds.child("rightAnswers").getValue(Long.class);
                    totalAnswers = ds.child("totalAnswers").getValue(Long.class);
                    wrongAnswers = ds.child("wrongAnswers").getValue(Long.class);
                    cognitiveDate = ds.child("date").getValue(String.class);

                    Log.d("NULLDATA", cognitiveDate);

                }

                if (cognitiveDate == null) {
                    Profile profile = new Profile();
                    profile.setType(3);
                    profile.setTotalAnswers("Sem registro");
                    profile.setRightAnswers("Sem registro");
                    profile.setWrongAnswers("Sem registro");
                    profile.setCognitiveDate("Sem registro");
                    dataProfile.add(profile);

                    vRecyclerView.setAdapter(profileAdapter);

                    progressBar.setVisibility(View.INVISIBLE);

                } else {

                    Profile profile = new Profile();
                    profile.setType(3);
                    profile.setTotalAnswers(String.valueOf(totalAnswers));
                    profile.setRightAnswers(String.valueOf(rightAnswers));
                    profile.setWrongAnswers(String.valueOf(wrongAnswers));
                    profile.setCognitiveDate(cognitiveDate);
                    dataProfile.add(profile);

                    vRecyclerView.setAdapter(profileAdapter);

                    progressBar.setVisibility(View.INVISIBLE);
                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast toast = Toast.makeText(ProfileActivity.this, "Não deu!", Toast.LENGTH_SHORT);
                toast.show();
            }
        });


    }

}
