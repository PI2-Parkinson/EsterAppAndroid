package com.pidois.ester;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Shader;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.pidois.ester.Adapter.ProfileAdapter;
import com.pidois.ester.Adapter.ProfilePersonalInfoAdapter;
import com.pidois.ester.Models.Person;
import com.pidois.ester.Models.Profile;
import com.pidois.ester.Models.Strap;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Transformation;


import java.util.ArrayList;
import java.util.List;

public class ProfileActivity extends AppCompatActivity implements View.OnClickListener{

    public RecyclerView vRecyclerView;
    public ProfileAdapter profileAdapter;
    public List<Profile> dataProfile = new ArrayList<>();

    public TextView profileName;
    public ImageView profileImg;

    private FirebaseAuth mAuth;

    private GoogleSignInClient mGoogleSignInClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        profileName = findViewById(R.id.profileName);
        profileImg = findViewById(R.id.profile_image);


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

        //dataProfile.add(new Profile(currentUser.getDisplayName(), currentUser.getEmail(), "24/10/2019", "", "", "", ""));
        //dataProfile.add(new Profile("", "", "", "1", "2", "3", "16/01/1993"));

        Profile profile = new Profile();
        profile.setType(1);
        profile.setName(currentUser.getDisplayName());
        profile.setEmail(currentUser.getEmail());
        profile.setBirthday("24/10/2016");
        dataProfile.add(profile);

        profile = new Profile();
        profile.setType(3);
        profile.setTotalAnswers("69");
        profile.setRightAnswers("30");
        profile.setWrongAnswers("39");
        profile.setCognitiveDate("25/10/2019");
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
        vRecyclerView.setAdapter(profileAdapter);

    }

    /*@Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
        updateUI(currentUser);
    }*/

    private void signOut() {
        // Firebase sign out
        mAuth.signOut();

        // Google sign out
        mGoogleSignInClient.signOut().addOnCompleteListener(this,
                new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        updateUI(null);
                    }
                });
    }

    @Override
    public void onClick(View view) {
        int i = view.getId();
        if (i == R.id.btn_logout2) {
            signOut();
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

}
