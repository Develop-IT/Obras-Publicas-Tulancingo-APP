package com.smart.cruzromero.obraspublicastulancinco;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

/**
 * Created by cruz on 05/03/2016.
 */
public class login  extends AppCompatActivity {


    Button aceptar;
    Button user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);

        aceptar=(Button)findViewById(R.id.button);
        user=(Button)findViewById(R.id.user_register);

        aceptar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();

                Intent menu = new Intent(login.this, MainActivity.class);
                startActivity(menu);
                finish();
            }
        });


        user.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
             ventana("Bienvenido al Registro de Usuarios");

                Intent menu = new Intent(login.this, registro.class);
                startActivity(menu);
                finish();

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
