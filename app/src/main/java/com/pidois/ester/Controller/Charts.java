package com.pidois.ester.Controller;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.pidois.ester.R;

import java.util.ArrayList;

public class Charts extends AppCompatActivity {

    private BarChart barChart;
    private int[] colorClassArray = new int[]{Color.GREEN, Color.RED};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_charts);

        barChart = findViewById(R.id.chart1);

        BarDataSet barDataSet = new BarDataSet(dataValues(), "");
        barDataSet.setColors(colorClassArray);

        BarData barData = new BarData(barDataSet);
        barChart.setData(barData);


    }

    private ArrayList<BarEntry> dataValues(){
        ArrayList<BarEntry> dataEntry = new ArrayList<>();
        dataEntry.add(new BarEntry(0, new float[]{69, 39}));
        dataEntry.add(new BarEntry(1, new float[]{79, 14}));
        dataEntry.add(new BarEntry(2, new float[]{20, 39}));
        dataEntry.add(new BarEntry(3, new float[]{15, 10}));

        return dataEntry;
    }
}
