package com.smart.cruzromero.obraspublicastulancinco;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

/**
 * Created by cruz on 07/03/2016.
 */
public class registro extends AppCompatActivity{

    Button aceptar;
    Button cancelar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.registro);

        aceptar=(Button)findViewById(R.id.re_aceptar);
        cancelar=(Button)findViewById(R.id.re_cancelar);

        aceptar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();

                Intent menu = new Intent(registro.this, login.class);
                startActivity(menu);
                finish();
            }
        });


        cancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ventana("Registro Cancelado");
            }
        });

    }


    private void ventana(String mensaje){


        AlertDialog.Builder builder =
                new AlertDialog.Builder(this, R.style.AppCompatAlertDialogStyle);
        builder.setTitle("Dialog");
        builder.setMessage(mensaje);
        builder.setPositiveButton("OK", null);
        //builder.setNegativeButton("Cancel", null);
        builder.show();


    }


}
