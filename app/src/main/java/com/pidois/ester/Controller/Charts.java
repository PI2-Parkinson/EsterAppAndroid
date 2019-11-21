package com.pidois.ester.Controller;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.Chart;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.pidois.ester.Models.Profile;
import com.pidois.ester.R;

import java.util.ArrayList;

public class Charts extends ChartUtils {

    private BarChart barChart;
    private int[] colorClassArray = new int[]{Color.argb(255, 80, 240, 80), Color.argb(255, 255, 75, 75)};
    private ArrayList<BarEntry> dataEntry = new ArrayList<>(), noEntry = new ArrayList<>();
    private Long rightAnswers, wrongAnswers;
    private int i = 0;
    private String cognitiveDate;

    private FloatingActionButton floatingActionButton;

    FirebaseUser currentFirebaseUser;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_charts);

        floatingActionButton = findViewById(R.id.chart_cognitive_floatingbtn);

        barChart = findViewById(R.id.cognitive_chart);
        barChart.setPinchZoom(true);
        barChart.setNoDataText("Dados do gráfico não acessíveis.");
        barChart.setDrawBorders(true);
        barChart.getDescription().setText("");
        barChart.getAxisLeft().setAxisMinimum(0f);
        barChart.getAxisRight().setAxisMinimum(0f);


        currentFirebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference("cognitive_answers/" + currentFirebaseUser.getUid());
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot ds : snapshot.getChildren()) {

                    rightAnswers = ds.child("rightAnswers").getValue(Long.class);
                    wrongAnswers = ds.child("wrongAnswers").getValue(Long.class);
                    cognitiveDate = ds.child("date").getValue(String.class);

                    dataEntry.add(new BarEntry(i, new float[]{rightAnswers, wrongAnswers}));

                    Log.d("HOLY", "onDataChange: " + dataEntry.get(i));

                    i++;
                }

                if (cognitiveDate == null){

                    //Nada

                } else {

                    BarDataSet barDataSet = new BarDataSet(dataEntry, "Exercício Cognitivo");
                    barDataSet.setHighlightEnabled(false);
                    barDataSet.setColors(colorClassArray);
                    barDataSet.setValueTextSize(10);
                    barDataSet.setStackLabels(new String[]{"Acertos", "Erros"});

                    BarData barData = new BarData(barDataSet);

                    barChart.setData(barData);
                    barChart.animateY(2000);
                    barChart.invalidate();

                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });

        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (ContextCompat.checkSelfPermission(view.getContext(), Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
                    saveToGallery();
                } else {
                    requestStoragePermission(barChart);
                }
            }
        });

    }

    @Override
    protected void saveToGallery() {
        saveToGallery(barChart, "Cognitive Chart");
    }
}
