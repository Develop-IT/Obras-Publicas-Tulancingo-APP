package com.example.bmxmajo.obraspublicastulancingo;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;


public class fmObras extends Fragment{

    MapView mMapView;
    private GoogleMap googleMap;
    private ProgressDialog pDialog;
    String descripcion,estatus, fecha;
    JSONParser jParser = new JSONParser();
    JSONArray all_datos = null;




    ////lista
    private static final String TAG_RESULTADOS = "resultados";
    private static final String TAG_DATOS="datos";
    private static final String TAG_DESCRIPCION = "desc_complaint";
    private static final String TAG_FECHA = "date_complaint";
    private static final String TAG_ESTATUS = "status";
    private static final String TAG_LAT = "lat_complaint";
    private static final String TAG_LON = "lon_complaint";


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
        final View root =  inflater.inflate(R.layout.activity_fm_obras, null);
        root.getContext();
        new JSONParse().execute();
        return root;


    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mMapView = (MapView) getView().findViewById(R.id.map);
        mMapView.onCreate(savedInstanceState);

        mMapView.onResume();// needed to get the map to display immediately

        try {
            MapsInitializer.initialize(getActivity().getApplicationContext());
        } catch (Exception e) {
            e.printStackTrace();
        }
        googleMap = mMapView.getMap();
        // latitude and longitude
        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(20.0853323, -98.3755154), 13f));
        googleMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);



    }

    @Override
    public void onPause() {
        super.onPause();
        mMapView.onPause();
    }
    @Override
    public void onDestroy() {
        super.onDestroy();
        mMapView.onDestroy();
    }
    @Override
    public void onSaveInstanceState(Bundle outState)
    {
        super.onSaveInstanceState(outState); mMapView.onSaveInstanceState(outState);
    }
    @Override
    public void onLowMemory()
    {
        super.onLowMemory();
        mMapView.onLowMemory();
    }
    @Override
    public void onResume() {
        super.onResume();
        mMapView.onResume();
    }
    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }



    private class JSONParse extends AsyncTask<String, String, JSONObject> {

        private ProgressDialog pDialog;
        private JSONObject json;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(getActivity());
            pDialog.setMessage("Cargando mapa...");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(true);
            pDialog.show();

        }

        @Override
        protected JSONObject doInBackground(String... args) {

            // Getting JSON from URL
            String url="http://104.43.252.17/OPT/api/mapeo.php?tipo=3";
            json = jParser.getJSONFromUrl(url);
            return json;
        }

        @Override
        protected void onPostExecute(JSONObject json) {
            pDialog.dismiss();
            try {
                float latitud,longitud;

               if(json.getInt(TAG_RESULTADOS)== 1) {
                   // Getting JSON Array from URL
                   all_datos = json.getJSONArray(TAG_DATOS);
                   //array = new String[all_datos.length()];
                   for (int i = 0; i < all_datos.length(); i++) {
                       JSONObject c = all_datos.getJSONObject(i);

                       estatus = c.getString(TAG_ESTATUS);
                       descripcion = c.getString(TAG_DESCRIPCION);
                       longitud = Float.parseFloat(c.getString(TAG_LON));
                       latitud = Float.parseFloat(c.getString(TAG_LAT));
                       fecha = c.getString(TAG_FECHA);

                       if (estatus.equals("Recibido")) {
                           googleMap.addMarker(new MarkerOptions()
                                   .position(new LatLng(latitud, longitud))
                                   .title(estatus)
                                   .snippet(descripcion)
                                   .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED)));

                       }
                       if (estatus.equals("Completado")) {
                           googleMap.addMarker(new MarkerOptions()
                                   .position(new LatLng(latitud, longitud))
                                   .title(estatus)
                                   .snippet(descripcion)
                                   .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN)));

                       }
                       if (estatus.equals("En progreso")) {

                           googleMap.addMarker(new MarkerOptions()
                                   .position(new LatLng(latitud, longitud))
                                   .title(estatus)
                                   .snippet(descripcion)
                                   .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_YELLOW)));
                       }


                   }
               }else{
                   Toast.makeText(getActivity(), "No hay resultados...", Toast.LENGTH_SHORT).show();
               }

            } catch (JSONException e) {
                Toast.makeText(getActivity(), "Error en la conexión de red", Toast.LENGTH_SHORT).show();
                e.printStackTrace();
            }
        }
    }



}
