package com.example.bmxmajo.obraspublicastulancingo;

import android.*;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.location.Address;
import android.location.Geocoder;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.adeel.library.easyFTP;
import com.android.internal.http.multipart.MultipartEntity;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import org.apache.commons.net.ftp.FTPClient;
import org.apache.http.HttpVersion;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.CoreProtocolPNames;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Random;

import me.drakeet.materialdialog.MaterialDialog;

import static android.support.v4.content.PermissionChecker.checkSelfPermission;

public class fmDenuncia extends Fragment {
    ImageButton btn_foto;
    ImageView photo;
    Spinner spin;
    Button enviar;
    EditText descripcion;
    MapView mMapView;
    String nombre,email,desc, cadena;
    double latitud,longitud;
    int id_user,id_user_type,type,new_id_complait;
    private Uri output;
    private String foto;
    int result=3;
    JSONParser jsonParser = new JSONParser();
    private File file;
    private GoogleMap googleMap;
    private ProgressDialog pDialog;
    private static final String LOGIN_URL = "http://104.43.252.17/OPT/api/upload.php";
    private static final String TAG_RESULTADO = "resultados";

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View root = inflater.inflate(R.layout.activity_fm_denuncia, null);
        root.getContext();

       btn_foto = (ImageButton) root.findViewById(R.id.btn_foto);
        enviar = (Button) root.findViewById(R.id.btn_enviar);
        descripcion = (EditText) root.findViewById(R.id.edt_descripcion);
        spin = (Spinner) root.findViewById(R.id.spinner_tipo);
        photo = (ImageView) root.findViewById(R.id.photo);
        btn_foto.setOnClickListener(
                new View.OnClickListener() {
                    public void onClick(View view) {
                        getCamara();
                    }});

        enviar.setOnClickListener(
                new View.OnClickListener() {
                    public void onClick(View view) {
                        if(descripcion.getText().length()==0){
                            Toast.makeText(getActivity(), "No puede dejar campos vacios!!!", Toast.LENGTH_SHORT).show();
                        }else {
                            if(foto==null){
                                Toast.makeText(getActivity(), "No se ha seleccionado ninguna foto...", Toast.LENGTH_SHORT).show();
                            }else {
                                    desc = descripcion.getText().toString();
                                    new Attemptpicture().execute();
                            }
                        }
                    }
                });


        spin.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                type = position + 1;

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        return root;
    }

    String getCadenaAll(int longitud){
        String cadenaAleatoria = "";
        long milis = new java.util.GregorianCalendar().getTimeInMillis();
        Random r = new Random(milis);
        int i = 0;
        while ( i < longitud){
            char c = (char)r.nextInt(255);
            if ( (c >= '0' && c <='9') || (c >='A' && c <='Z') ){
                cadenaAleatoria += c;
                i ++;
            }
        }
        return cadenaAleatoria;
    }


    private void getCamara(){
        cadena = getCadenaAll(20);
        foto = Environment.getExternalStorageDirectory() +"/" +"OPT_".toString().trim()+cadena+".jpg";
        file=new File(foto);
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        output = Uri.fromFile(file);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, output);
        startActivityForResult(intent, 1);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        ContentResolver cr=getActivity().getContentResolver();
        Bitmap bit = null;
        try {
            bit = android.provider.MediaStore.Images.Media.getBitmap(cr, output);

            //orientation
            int rotate = 0;
            try {
                ExifInterface exif = new ExifInterface(
                        file.getAbsolutePath());
                int orientation = exif.getAttributeInt(
                        ExifInterface.TAG_ORIENTATION,
                        ExifInterface.ORIENTATION_NORMAL);

                switch (orientation) {
                    case ExifInterface.ORIENTATION_ROTATE_270:
                        rotate = 270;
                        break;
                    case ExifInterface.ORIENTATION_ROTATE_180:
                        rotate = 180;
                        break;
                    case ExifInterface.ORIENTATION_ROTATE_90:
                        rotate = 90;
                        break;
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            Matrix matrix = new Matrix();
            matrix.postRotate(rotate);
            bit = Bitmap.createBitmap(bit , 0, 0, bit.getWidth(), bit.getHeight(), matrix, true);

        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

            photo.setImageBitmap(bit);



    }

    public FragmentManager getSupportFragmentManager() {
        return getSupportFragmentManager();
    }


    class Attemptpicture extends AsyncTask<String, String, String> {

        @Override
        protected void onPreExecute() {

            super.onPreExecute();
            pDialog = new ProgressDialog(getActivity());
            pDialog.setMessage("Subiendo Imagen...");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(true);
            pDialog.show();

        }

        @Override
        protected String doInBackground(String... args) {
            try {
                easyFTP ftp = new easyFTP();
                ftp.connect("104.43.252.17","opt_ftp","12345");
                ftp.uploadFile(foto);
            }catch (Exception e){
                String t = " Failure : " + e . getLocalizedMessage();
                return t;

            }
            return null;

        }

        protected void onPostExecute(String file_url) {
                pDialog.hide();
                new AttemptUpload().execute();

        }
    }


    class AttemptUpload extends AsyncTask<String, String, String> {

        @Override
        protected void onPreExecute() {

            super.onPreExecute();
            pDialog = new ProgressDialog(getActivity());
            pDialog.setMessage("Publicando Nueva denuncia...");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(true);
            pDialog.show();

        }

        @Override
        protected String doInBackground(String... args) {
            try {
            String type1= String.valueOf(type);
                String user= String.valueOf(id_user);
                String lat= String.valueOf(latitud);
                String lon= String.valueOf(longitud);


                List params = new ArrayList();
                params.add(new BasicNameValuePair("type", type1));
                params.add(new BasicNameValuePair("id_user", user));
                params.add(new BasicNameValuePair("desc",desc ));
                params.add(new BasicNameValuePair("img", "OPT_"+cadena+".jpg"));
                params.add(new BasicNameValuePair("latitude", lat));
                params.add(new BasicNameValuePair("longitude", lon));
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
            try {
                if (result == 1) {
                    pDialog.hide();
                    Toast.makeText(getActivity(), "Denuncia publicada Correctamente", Toast.LENGTH_LONG).show();
                    terminar();


                }
            }catch (Exception v) {
                Toast.makeText(getActivity(), "Error en la conexión de red", Toast.LENGTH_SHORT).show();

            }
        }
    }


public void terminar() {
    fmInicio nextFrag= new fmInicio();
    this.getFragmentManager().beginTransaction()
            .replace(R.id.miContenido, nextFrag,null)
            .addToBackStack(null)
            .commit();
}


    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        recojerextras();
        mMapView = (MapView) getView().findViewById(R.id.map);
        mMapView.onCreate(savedInstanceState);
        mMapView.onResume();
        try {
            MapsInitializer.initialize(getActivity().getApplicationContext());
        } catch (Exception e) {
            e.printStackTrace();
        }



        /* Use the LocationManager class to obtain GPS locations */
        LocationManager mlocManager = (LocationManager) getContext().getSystemService(Context.LOCATION_SERVICE);
        MyLocationListener mlocListener = new MyLocationListener();
        mlocListener.setactivity(this);
        if (checkSelfPermission(getContext(),android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && checkSelfPermission(getContext(),android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    public void requestPermissions(@NonNull String[] permissions, int requestCode)
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for Activity#requestPermissions for more details.
            return;
        }
        mlocManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 30000, 10, (LocationListener) mlocListener);

        googleMap = mMapView.getMap();
        // latitude and longitude
        googleMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);



    }


    public void recojerextras() {
        Bundle bundle = getActivity().getIntent().getExtras();
        nombre= bundle.getString("NOMBRE");
        email=bundle.getString("EMAIL");
        id_user=bundle.getInt("ID_USER");
        id_user_type=bundle.getInt("ID_USER_TYPE");
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

    public class MyLocationListener implements LocationListener {
        fmDenuncia denuncia;
        private fmDenuncia fmDenuncia;

        public fmDenuncia getactivity() {
            return denuncia;
        }

        public void setactivity(fmDenuncia denuncia) {
            this.fmDenuncia = denuncia;
        }


        @Override
        public void onLocationChanged(Location loc) {
            // Este mŽtodo se ejecuta cada vez que el GPS recibe nuevas coordenadas
            // debido a la detecci—n de un cambio de ubicacion
            latitud = loc.getLatitude();
            longitud= loc.getLongitude();
            googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(loc.getLatitude(), loc.getLongitude()), 16f));
            googleMap.clear();
            googleMap.addMarker(new MarkerOptions()
                    .position(new LatLng(loc.getLatitude(), loc.getLongitude()))
                    .draggable(true)
                    .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE)));
            googleMap.setOnMarkerDragListener(new GoogleMap.OnMarkerDragListener() {

                @Override
                public void onMarkerDragStart(Marker marker) {
                    // TODO Auto-generated method stub
                    // Here your code
                }

                @Override
                public void onMarkerDragEnd(Marker marker) {
                    // TODO Auto-generated method stub
                    Toast.makeText(
                            getActivity(), "Lat " + marker.getPosition().latitude+ " "+ "Long " + marker.getPosition().longitude, Toast.LENGTH_LONG).show();
                    latitud= marker.getPosition().latitude;
                    longitud= marker.getPosition().longitude;
                }

                @Override
                public void onMarkerDrag(Marker marker) {
                    // TODO Auto-generated method stub
                    // Toast.makeText(MainActivity.this, "Dragging",
                    // Toast.LENGTH_SHORT).show();

                }
            });

        }

        @Override
        public void onProviderDisabled(String provider) {
            Toast.makeText(getActivity(), "El GPS esta desactivado!!!", Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onProviderEnabled(String provider) {
            Toast.makeText(getActivity(), "GPS Activado", Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onStatusChanged(String provider, int status, Bundle extras) {

        }

    }


}
