package com.example.bmxmajo.obraspublicastulancingo;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.StatusLine;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import android.support.v7.app.AppCompatActivity;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONObject;
import java.util.ArrayList;
import java.util.List;

import me.drakeet.materialdialog.MaterialDialog;


public class Login extends AppCompatActivity {


    EditText pass,user;
    TextView title;
    Button entrar,registrarse;
    String name_user,password_user;
    final MaterialDialog mMaterialDialog = new MaterialDialog(Login.this);
    private ProgressDialog pDialog;
    int result=3;
    // Clase JSONParser
    JSONParser jsonParser = new JSONParser();
    //lista de tags
    private static final String LOGIN_URL = "http://104.43.252.17/OPT/api/login.php";
    private static final String TAG_RESULTADO = "resultados";
    private static final String TAG_id_user = "id_user";
    private static final String TAG_id_user_type = "id_user_type";
    private static final String TAG_name = "name_user";
    private static final String TAG_email = "email_user";
    private static final String TAG_password= "password_user";

    String nombre,email;
            int id_user,id_user_type;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        getSupportActionBar().setTitle("Login");
        String font_path = "font/Roboto-Light.ttf";
        Typeface TF = Typeface.createFromAsset(getAssets(),font_path);
        user = (EditText) findViewById(R.id.edt_user);
        pass = (EditText) findViewById(R.id.edt_password);
        entrar=(Button) findViewById(R.id.btn_entrar);
        registrarse=(Button) findViewById(R.id.btn_registrer);
        title=(TextView) findViewById(R.id.tv_title);
        title.setTypeface(TF);
        user.setTypeface(TF);
        pass.setTypeface(TF);
        entrar.setTypeface(TF);
        registrarse.setTypeface(TF);


        registrarse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent registro = new Intent(Login.this, Registro.class);
                startActivity(registro);
                finish();

            }



        });


    }

    public void entrar(View v){
        name_user=user.getText().toString();
        password_user=pass.getText().toString();

        if(user.getText().length()==0 || pass.getText().length()==0){
            mMaterialDialog.setTitle("Advertencia!!!");
            mMaterialDialog.setMessage("No puedes dejar campos vacios...");
            mMaterialDialog.setPositiveButton("ACEPTAR", new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mMaterialDialog.dismiss();
                }
            });

            mMaterialDialog.show();
        }else{

            new AttemptLogin().execute();
        }

    }



    class AttemptLogin extends AsyncTask<String, String, String> {

            @Override
            protected void onPreExecute() {

                super.onPreExecute();
                pDialog = new ProgressDialog(Login.this);
                pDialog.setMessage("Verificando Correo y Contraseña...");
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
                    params.add(new BasicNameValuePair("correo", name_user));
                    params.add(new BasicNameValuePair("clave", password_user));

                    Log.d("request!", "starting");
                    // getting product details by making HTTP request
                    JSONObject json = jsonParser.makeHttpRequest(LOGIN_URL, "POST", params);

                    // check your log for json response
                    Log.d("Login attempt", json.toString());

                    // json success tag
                    result = json.getInt(TAG_RESULTADO);
                    nombre= json.getString(TAG_name);
                    id_user=json.getInt(TAG_id_user);
                    id_user_type= json.getInt(TAG_id_user_type);
                    email=json.getString(TAG_email);


                } catch (Exception e) {
                }

                return null;

            }

            protected void onPostExecute(String file_url) {
                if (result == 1) {
                    Intent intent = new Intent(Login.this, MainActivity.class);
                    Bundle b = new Bundle();
                    b.putString("NOMBRE", String.valueOf(nombre));
                    b.putInt("ID_USER", Integer.valueOf(id_user));
                    b.putInt("ID_USER_TYPE", Integer.valueOf(id_user_type));
                    b.putString("EMAIL", String.valueOf(email));
                    intent.putExtras(b);
                    finish();
                    startActivity(intent);

                }
                pDialog.dismiss();
                if (result == 0) {

                    ventana("Advertencia", "El usuario y contraseña no son correctos,\nIntente una vez más!","ACEPTAR");

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
                mMaterialDialog.dismiss();
            }
        });

        mMaterialDialog.show();

    }



}




