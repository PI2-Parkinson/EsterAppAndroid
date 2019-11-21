package com.pidois.ester.Controller;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.pidois.ester.R;


public class StrapActivity extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_strap);

        // Button listeners
        findViewById(R.id.btn_m1).setOnClickListener(this);
        findViewById(R.id.btn_m2).setOnClickListener(this);
        findViewById(R.id.btn_m3).setOnClickListener(this);
        findViewById(R.id.btn_m4).setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        int i = view.getId();
        if (i == R.id.btn_m1){
            switchScreen(StrapRestActivity.class);
        } else if (i == R.id.btn_m2){
            switchScreen(StrapPosturalActivity.class);
        } else if (i == R.id.btn_m3){
            switchScreen(StrapFingerNoseActivity.class);
        } else if (i == R.id.btn_m4){
            alertDialog();
        }

    }

    private void alertDialog() {
        AlertDialog.Builder dialog=new AlertDialog.Builder(this);
        dialog.setMessage("A medição do tremor da mão é realizada de acordo com a Escala de Classificação de Doenças de Parkinson Unificada (Unified Parkinson’s Disease Rating Scale - UPDRS).\n" +
                "Essa forma de avaliação é patrocinada pela Sociedade de Distúrbios do Movimento (Movement Disorders Society - MDS), e possui classificação dos tremores de 0 a 4, sendo eles: normal, discreto, ligeiro, moderado e grave. Para iniciar o teste é necessário colocar a pulseira no punho e escolher o modo.");
        dialog.setCancelable(false);
        dialog.setPositiveButton("ok",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog,
                                        int which) {
                    }
                });
        AlertDialog alertDialog=dialog.create();
        alertDialog.show();
    }

    private void switchScreen (Class cl){
        Intent intent = new Intent(StrapActivity.this, cl);
        StrapActivity.this.startActivity(intent);
    }

}
