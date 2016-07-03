package com.example.bmxmajo.obraspublicastulancingo;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBarDrawerToggle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;


public class MainActivity extends AppCompatActivity {
    private ListView mDrawerList;
    private DrawerLayout mDrawerLayout;
    private ArrayAdapter<String> mAdapter;
    private ActionBarDrawerToggle mDrawerToggle;
    private String mActivityTitle,nombre,email;
    private int id_user,id_user_type;
    @Override
    public void onBackPressed() {

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        recojerextras();

        mDrawerList = (ListView)findViewById(R.id.navList);
        mDrawerLayout = (DrawerLayout)findViewById(R.id.drawer_layout);
        mActivityTitle = getTitle().toString();

        addDrawerItems();
        setupDrawer();

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        selectItem(0);
    }

    public void recojerextras() {
        Bundle bundle = this.getIntent().getExtras();
        nombre= bundle.getString("NOMBRE");
        email=bundle.getString("EMAIL");
        id_user=bundle.getInt("ID_USER");
        id_user_type=bundle.getInt("ID_USER_TYPE");
    }

    private void addDrawerItems() {
        String[] osArray = { "Inicio","Alumbrado","Baches","Obras en progreso","Otros","Nueva Denuncia","Cerrar sesión"};
        mAdapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1, osArray);
        mDrawerList.setAdapter(mAdapter);
        mDrawerList.setOnItemClickListener(new DrawerItemClickListener());
    }

    private void setupDrawer() {
        mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout, R.string.drawer_open, R.string.drawer_close) {

            /** Called when a drawer has settled in a completely open state. */
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                getSupportActionBar().setTitle("Menu");
                invalidateOptionsMenu(); // creates call to onPrepareOptionsMenu()
            }

            /** Called when a drawer has settled in a completely closed state. */
            public void onDrawerClosed(View view) {
                super.onDrawerClosed(view);

                invalidateOptionsMenu(); // creates call to onPrepareOptionsMenu()
            }
        };

        mDrawerToggle.setDrawerIndicatorEnabled(true);
        mDrawerLayout.setDrawerListener(mDrawerToggle);
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        // Sync the toggle state after onRestoreInstanceState has occurred.
        mDrawerToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        mDrawerToggle.onConfigurationChanged(newConfig);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        //getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement


        // Activate the navigation drawer toggle
        if (mDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void selectItem(int position) {

        Fragment mFrag = null;
        switch (position){
            case 0:
                Intent intent = new Intent(MainActivity.this, fmInicio.class);
                Bundle b = new Bundle();
                b.putString("NOMBRE", String.valueOf(nombre));
                b.putInt("ID_USER", Integer.valueOf(id_user));
                b.putInt("ID_USER_TYPE", Integer.valueOf(id_user_type));
                b.putString("EMAIL", String.valueOf(email));
                intent.putExtras(b);
                mFrag = new fmInicio();
                getSupportActionBar().setTitle("Inicio");
                break;
            case 1:
                mFrag=new fmAlumbrado();
                getSupportActionBar().setTitle("Alumbrado");

                break;
            case 2:
                mFrag = new fmBaches();
                getSupportActionBar().setTitle("Baches");
                break;
            case 3:
                mFrag=new fmObras();
                getSupportActionBar().setTitle("Obras en progreso");
                break;
            case 4:
                mFrag=new fmOtros();
                getSupportActionBar().setTitle("Otras denuncias");
                break;
            case 5:
                Intent intent1 = new Intent(MainActivity.this, fmDenuncia.class);
                Bundle b1 = new Bundle();
                b1.putString("NOMBRE", String.valueOf(nombre));
                b1.putInt("ID_USER", Integer.valueOf(id_user));
                b1.putInt("ID_USER_TYPE", Integer.valueOf(id_user_type));
                b1.putString("EMAIL", String.valueOf(email));
                intent1.putExtras(b1);
                mFrag=new fmDenuncia();
                getSupportActionBar().setTitle("Nueva denuncia");
                break;
            case 6:
                break;

        }
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.miContenido,mFrag).commit();
        mDrawerLayout.closeDrawer(mDrawerList);
    }



    private class DrawerItemClickListener implements ListView.OnItemClickListener {
        @Override
        public void onItemClick(AdapterView parent, View view, int position, long id) {
            selectItem(position);
        }
    }





}

