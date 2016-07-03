package com.example.bmxmajo.obraspublicastulancingo;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import org.apache.http.message.BasicNameValuePair;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import me.drakeet.materialdialog.MaterialDialog;

public class Registro extends AppCompatActivity {
    EditText password,name,lastname,email,confirm_password;
    TextView title,name1,lastname1,email1,confirm_password1,password1;
    Button registro;
    String N,LN,E,P,CP;
    int valida;
    final MaterialDialog mMaterialDialog = new MaterialDialog(Registro.this);
    private ProgressDialog pDialog;
    int result=3;
    // Clase JSONParser
    JSONParser jsonParser = new JSONParser();
    //lista de tags
    private static final String LOGIN_URL = "http://104.43.252.17/OPT/api/registro.php";
    private static final String TAG_RESULTADO = "resultados";





    @Override
    public void onBackPressed() {
        if(name.getText().length()>= 1 ||lastname.getText().length()>=1 || email.getText().length()>=1 || password.getText().length()>=1 || confirm_password.getText().length()>=1){
            valida=1;
            ventana("Advertencia!!!","Quieres salir sin ternimar el registro???","Salir");
            mMaterialDialog.show();
        }else{

            salir_login();
        }
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro);
        String font_path = "font/Roboto-Light.ttf";
        Typeface TF = Typeface.createFromAsset(getAssets(),font_path);
        password = (EditText) findViewById(R.id.etxt_password);
        name = (EditText) findViewById(R.id.etxt_name);
        email= (EditText) findViewById(R.id.etxt_email);
        lastname = (EditText) findViewById(R.id.etxt_lastname);
        confirm_password = (EditText) findViewById(R.id.etxt_confirmpassword);
        password1 = (TextView) findViewById(R.id.txt_password);
        name1 = (TextView) findViewById(R.id.txt_name);
        email1= (TextView) findViewById(R.id.txt_email);
        lastname1 = (TextView) findViewById(R.id.txt_lastname);
        confirm_password1 = (TextView) findViewById(R.id.txt_confirmpassword);
        title = (TextView) findViewById(R.id.txt_title);
        registro = (Button) findViewById(R.id.btn_registro);
        title.setTypeface(TF);
        password.setTypeface(TF);
        name.setTypeface(TF);
        lastname.setTypeface(TF);
        email.setTypeface(TF);
        confirm_password.setTypeface(TF);
        password1.setTypeface(TF);
        name1.setTypeface(TF);
        lastname1.setTypeface(TF);
        email1.setTypeface(TF);
        confirm_password1.setTypeface(TF);
        registro.setTypeface(TF);

    }



    public void nuevo_registro(View v){
     if(name.getText().length()==0 ||lastname.getText().length()==0 || email.getText().length()==0 || password.getText().length()==0 || confirm_password.getText().length()==0){
         ventana("Advertencia!!!","No puedes dejar campos vacios...","ACEPTAR");
     }else{
         P=password.getText().toString();
         CP=confirm_password.getText().toString();
         if(P.equals(CP)){
             N=name.getText().toString();
             LN=lastname.getText().toString();
             E=email.getText().toString();
             P=password.getText().toString();
             new AttemptRegistro().execute();

         }else{
             password.setText("");
             confirm_password.setText("");
             ventana("Error!!!","Las contraseñas no coiciden!!\n intenta una vez más... ","ACEPTAR");
         }
     }
    }

    class AttemptRegistro extends AsyncTask<String, String, String> {

        @Override
        protected void onPreExecute() {

            super.onPreExecute();
            pDialog = new ProgressDialog(Registro.this);
            pDialog.setMessage("Creando Nuevo Usuario...");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(true);
            pDialog.show();

        }

        @Override
        protected String doInBackground(String... args) {
            try {
                result=3;
                // Building Parameters
                List params = new ArrayList();
                params.add(new BasicNameValuePair("nombre", N+" "+LN));
                params.add(new BasicNameValuePair("email", E));
                params.add(new BasicNameValuePair("password", P));

                Log.d("request!", "starting");
                // getting product details by making HTTP request
                JSONObject json = jsonParser.makeHttpRequest(LOGIN_URL, "POST", params);

                // check your log for json response
                Log.d("Login attempt", json.toString());

                // json success tag
                result = json.getInt(TAG_RESULTADO);


            } catch (Exception e) {
            }

            return null;

        }

        protected void onPostExecute(String file_url) {
            if (result == 1) {
                ventana("Felicidades", "Usuario Agregado correctamente", "ACEPTAR");

                mMaterialDialog.show();
                salir_login();
            }
            pDialog.dismiss();
            if (result == 0) {

                ventana("Advertencia","El Correo electronico ingresado ya existe!!","ACEPTAR");

                mMaterialDialog.show();
            }
            pDialog.dismiss();
            if (result == 3) {
                ventana("Error","No tiene Conexion a internet intentelo más tarde!!!","ACEPTAR");
            }


        }
    }

    private void ventana(String title,String message,String msjbutton){
        mMaterialDialog.setTitle(title);
        mMaterialDialog.setMessage(message);
        mMaterialDialog.setPositiveButton(msjbutton, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(valida==1){
                    salir_login();
                }
                mMaterialDialog.dismiss();
            }
        });

        mMaterialDialog.show();

    }
    private void salir_login(){
        Intent menu = new Intent(Registro.this, Login.class);
        startActivity(menu);
        finish();

    }



}
