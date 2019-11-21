package com.pidois.ester.Controller;

import android.app.AlertDialog;
import android.content.DialogInterface;

import androidx.appcompat.app.AppCompatActivity;

public abstract class StrapUtils extends AppCompatActivity {

    private String title;
    private String message;

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

    public void strapResult(int level) {
        switch (level) {
            case 0:
                title = "Você está na escala 0 de 4!";
                message = "De acordo com a Escala de Classificação de Doenças de Parkinson Unificada, o tremor 0 é classificado como NORMAL.";
                alertDialog(title, message);
                break;
            case 1:
                title = "Você está na escala 1 de 4!";
                message = "De acordo com a Escala de Classificação de Doenças de Parkinson Unificada, o tremor 1 é classificado como DISCRETO.";
                alertDialog(title, message);
                break;
            case 2:
                title = "Você está na escala 2 de 4!";
                message = "De acordo com a Escala de Classificação de Doenças de Parkinson Unificada, o tremor 2 é classificado como \n" +
                        "LIGEIRO.";
                alertDialog(title, message);
                break;
            case 3:
                title = "Você está na escala 3 de 4!";
                message = "De acordo com a Escala de Classificação de Doenças de Parkinson Unificada, o tremor 3 é classificado como MODERADO.";
                alertDialog(title, message);
                break;
            case 4:
                title = "Você está na escala 4 de 4!";
                message = "De acordo com a Escala de Classificação de Doenças de Parkinson Unificada, o tremor 4 é classificado como \n" +
                        "GRAVE.";
                alertDialog(title, message);
                break;

        }
    }

}
