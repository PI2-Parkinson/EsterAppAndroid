package com.pidois.ester.Controller;

import android.app.AlertDialog;
import android.content.DialogInterface;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.Date;

public abstract class StrapUtils extends AppCompatActivity {

    private String title;
    private String message;

    public void calibrate() {
        title = "Vamos calibrar a strap?";
        message = "\nAntes de iniciar, coloque a pulseira em um lugar estável (ex: em cima da mesa). \n\nA calibração dura aproximadamente 15 segundos.";
        alertDialog(title, message);
    }

    public void alertDialog(String title, String message) {
        AlertDialog.Builder dialog=new AlertDialog.Builder(this);
        dialog.setTitle(title);
        dialog.setMessage(message);
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

    public void strapResult(String level) {
        switch (level) {
            case "GT0":
                title = "Você está na escala 0 de 4!";
                message = "De acordo com a Escala de Classificação de Doenças de Parkinson Unificada, o tremor 0 é classificado como NORMAL.";
                alertDialog(title, message);
                break;
            case "GT1":
                title = "Você está na escala 1 de 4!";
                message = "De acordo com a Escala de Classificação de Doenças de Parkinson Unificada, o tremor 1 é classificado como DISCRETO.";
                alertDialog(title, message);
                break;
            case "GT2":
                title = "Você está na escala 2 de 4!";
                message = "De acordo com a Escala de Classificação de Doenças de Parkinson Unificada, o tremor 2 é classificado como \n" +
                        "LIGEIRO.";
                alertDialog(title, message);
                break;
            case "GT3":
                title = "Você está na escala 3 de 4!";
                message = "De acordo com a Escala de Classificação de Doenças de Parkinson Unificada, o tremor 3 é classificado como MODERADO.";
                alertDialog(title, message);
                break;
            case "GT4":
                title = "Você está na escala 4 de 4!";
                message = "De acordo com a Escala de Classificação de Doenças de Parkinson Unificada, o tremor 4 é classificado como \n" +
                        "GRAVE.";
                alertDialog(title, message);
                break;

        }
    }

    public String getCurrentDate() {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        String currentDateandTime = sdf.format(new Date());

        return currentDateandTime;
    }

     public void sendStrapAsnwer(FirebaseUser currentFirebaseUser, DatabaseReference databaseReference, String tipoTremor, int value) {
         currentFirebaseUser = FirebaseAuth.getInstance().getCurrentUser();

         databaseReference = FirebaseDatabase.getInstance().getReference("strap_answers/" + currentFirebaseUser.getUid());
         DatabaseReference databaseRef = databaseReference.push();

         databaseRef.child(tipoTremor).setValue(value);
         databaseRef.child("date").setValue(getCurrentDate());

    }

}
