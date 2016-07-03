package com.smart.cruzromero.obraspublicastulancinco;

/**
 * Created by cruz on 05/03/2016.
 */


import android.app.Activity;
import android.app.AlertDialog;
import android.os.Bundle;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.ArrayAdapter;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.Toast;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


public class fmReporte extends Fragment{

    Spinner spinner;
    Button reporte;

   // private final static String[] tipo = { "Perro", "Gato", "Caballo",
     //       "Canario", "Vaca", "Cerdo" };


    public static android.support.v4.app.Fragment newInstance(Context context) {
        fmReporte f = new fmReporte();
        return f;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
        final View root =  inflater.inflate(R.layout.option_4_reporte, null);
        root.getContext();

        reporte=(Button)root.findViewById(R.id.reporte_aceptar);
        spinner = (Spinner)root.findViewById(R.id.spinner);


        reporte.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                AlertDialog.Builder msgBox= new AlertDialog.Builder(v.getContext());
                msgBox.setTitle("MENSAJE");
                msgBox.setMessage("Reporte Enviado");
                msgBox.create();
                msgBox.show();

            }
        });


        return root;


    }



}
