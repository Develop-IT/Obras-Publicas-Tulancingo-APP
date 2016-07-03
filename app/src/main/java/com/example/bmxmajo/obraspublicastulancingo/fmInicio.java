package com.example.bmxmajo.obraspublicastulancingo;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

public class fmInicio extends Fragment {
    private String nombre,email;
    private int id_user,id_user_type;
    TextView txt_nombre,txt_correo,txt_title,txt_todo;
    private ProgressDialog pDialog;
    JSONParser jParser = new JSONParser();
    ArrayList<HashMap<String, String>> datosList;
    JSONArray all_datos = null;
    ListView lista;
    String array[];


    ////lista
    private static final String TAG_RESULTADOS = "resultados";
    private static final String TAG_DATOS ="datos";
    private static final String TAG_ID="id_complaint";
    private static final String TAG_DESCRIPCION = "desc_complaint";
    private static final String TAG_FECHA = "date_complaint";
    private static final String TAG_ESTATUS = "status";
    private static final String TAG_TIPO ="name_complaint_type";




    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
        final View root =  inflater.inflate(R.layout.activity_fm_inicio, null);
        root.getContext();

        return root;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onActivityCreated(savedInstanceState);
        recojerextras();
        txt_nombre=(TextView)getView().findViewById(R.id.txt_nombre_inicio);
        txt_correo=(TextView)getView().findViewById(R.id.txt_correo);
        lista = (ListView)getView().findViewById(R.id.listView_datos);
        txt_nombre.setText("Nombre: "+nombre);
        txt_correo.setText("Email: "+email);
        datosList = new ArrayList<HashMap<String, String>>();
        new JSONParse().execute();

    }
    public void recojerextras() {
        Bundle bundle = getActivity().getIntent().getExtras();
        nombre= bundle.getString("NOMBRE");
        email=bundle.getString("EMAIL");
        id_user=bundle.getInt("ID_USER");
        id_user_type=bundle.getInt("ID_USER_TYPE");
    }

    private class JSONParse extends AsyncTask<String, String, JSONObject> {

        private ProgressDialog pDialog;
        private JSONObject json;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(getActivity());
            pDialog.setMessage("Obteniendo Datos...");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(true);
            pDialog.show();

        }

        @Override
        protected JSONObject doInBackground(String... args) {

            // Getting JSON from URL
            String url="http://104.43.252.17/OPT/api/inicio.php?id_user="+id_user;
            json = jParser.getJSONFromUrl(url);
            return json;
        }

        @Override
        protected void onPostExecute(JSONObject json) {

            pDialog.dismiss();
            try {
                // Getting JSON Array from URL
                all_datos = json.getJSONArray(TAG_DATOS);

                for(int i = 0; i < all_datos.length(); i++){

                    JSONObject c = all_datos.getJSONObject(i);

                    // Storing  JSON item in a Variable
                    String id = c.getString(TAG_ID);
                    String tipo = c.getString(TAG_TIPO);
                    String estatus = c.getString(TAG_ESTATUS);
                    String descripcion = c.getString(TAG_DESCRIPCION);
                    String fecha = c.getString(TAG_FECHA);
                    String todo = "Tipo: "+tipo+"\nEstado: "+estatus+"\nDescripción: \""+descripcion+"\"\nFecha y hora: "+fecha;

                    // Adding value HashMap key => value
                    HashMap<String, String> map = new HashMap<String, String>();
                    map.put(TAG_ID, id);
                    map.put(TAG_DATOS, todo);
                    datosList.add(map);



                    ListAdapter adapter = new SimpleAdapter(getActivity(), datosList,
                            R.layout.single_post,
                            new String[]{
                                    TAG_ID,
                                    TAG_DATOS,

                            },
                            new int[]{
                                    R.id.txt_id,
                                    R.id.txt_todo,

                            });
                    lista.setAdapter(adapter);

                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }


}
