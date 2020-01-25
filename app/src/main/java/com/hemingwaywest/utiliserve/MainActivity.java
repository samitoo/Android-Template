package com.hemingwaywest.utiliserve;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.AssetManager;
import android.content.res.Configuration;
import android.net.Uri;
import android.nfc.Tag;
import android.os.Bundle;
import android.preference.PreferenceManager;
import androidx.annotation.NonNull;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;
import com.google.gson.Gson;
import com.hemingwaywest.utiliserve.Utilities.AppExecutors;
import com.hemingwaywest.utiliserve.database.AppDatabase;
import com.hemingwaywest.utiliserve.database.FormField;
import com.hemingwaywest.utiliserve.database.Forms;

import androidx.fragment.app.Fragment;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.util.Log;
import android.view.MenuItem;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.FileReader;
import java.io.InputStream;
import java.io.Reader;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity
        implements SharedPreferences.OnSharedPreferenceChangeListener,
        NavigationView.OnNavigationItemSelectedListener {

    private static final String TAG = MainActivity.class.getSimpleName();
    boolean mDebugMode = false;
    boolean mSync = false;
    DrawerLayout drawerLayout;
    ActionBarDrawerToggle toggle;
    Toolbar toolbar;
    private AppDatabase mDb;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //Swap from the launcher theme back to to the original before calling super
        setTheme(R.style.AppTheme_NoActionBar);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mDb = AppDatabase.getInstance(this);

        //Find elements
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        drawerLayout = findViewById(R.id.drawer_layout);
        NavigationView drawerNavigationView = findViewById(R.id.nav_view);
        BottomNavigationView bottomNav = findViewById(R.id.bottom_navigation);

        //Setup nav drawer button
        toggle = new ActionBarDrawerToggle(MainActivity.this, drawerLayout, toolbar,
                R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        toggle.setDrawerIndicatorEnabled(true);
        toggle.syncState();

        //Set Listeners
        bottomNav.setOnNavigationItemSelectedListener(navListener);
        drawerNavigationView.setNavigationItemSelectedListener(this);
        drawerLayout.addDrawerListener(toggle);




        //show fragment container on launch with Form Fragment
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new FormFragment()).commit();

        setupSharedPreferences();

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //Turn off the listener
        PreferenceManager.getDefaultSharedPreferences(this).unregisterOnSharedPreferenceChangeListener(this);
    }

    /**
     * Setup the settings preferences calls
     */
    private void setupSharedPreferences(){
        //TODO: Get more Items from preferences file
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        //Can attach the listener to "this" since the class implements SharedPreferences.OnSharedPreferenceChangeListener
        sharedPreferences.registerOnSharedPreferenceChangeListener(this);
        mDebugMode = sharedPreferences.getBoolean((getString(R.string.pref_show_debug_key)),
                getResources().getBoolean(R.bool.pref_show_debug_default));
    }



    //Controls bottom navigation
    private BottomNavigationView.OnNavigationItemSelectedListener navListener =
            new BottomNavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                    Fragment selectedFragment = null;

                    switch(menuItem.getItemId()){
                        case R.id.nav_forms:
                            selectedFragment = new FormFragment();
                            break;
                        case R.id.nav_queue:
                            selectedFragment = new QueueFragment();
                            break;
                        case R.id.nav_sync:
                            mSync = true;
                            break;
                    }
                    if (mSync){
                        Toast.makeText(getApplicationContext(), "Syncing Files", Toast.LENGTH_SHORT).show();
                        mSync = false;
                    }
                    else {
                        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                                selectedFragment).commit();
                    }
                    return true;
                }
            };

    //Controls / creates top menu
    /*@Override
    public boolean (Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        MenuItem itemDelDB = menu.findItem(R.id.action_delete_db);
        MenuItem itemLoadDB = menu.findItem(R.id.action_reload_db);
        if(!mDebugMode){
            itemDelDB.setVisible(false);
            itemLoadDB.setVisible(false);
        }
        else{
            itemDelDB.setVisible(true);
            itemLoadDB.setVisible(true);
        }
        return true;
    } */

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (toggle.onOptionsItemSelected(item)){
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    //Listener that updates View on preference change.
    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
        //TODO Add preferences changes to here instead of OnCreate
        if (key.equals(getString(R.string.pref_show_debug_key))){
            //Change to debug pull
            mDebugMode = sharedPreferences.getBoolean
                    (getString(R.string.pref_show_debug_key),false);
            invalidateOptionsMenu();
        }
    }

    //Create an alert to show confirmation before delete DB from settings menu
    private void createAlertForDelete(){
        AlertDialog.Builder alertbox = new AlertDialog.Builder(MainActivity.this);
        alertbox.setTitle("Are you sure?");
        alertbox.setMessage("This will wipe the DB and reload the defaults");
        alertbox.setCancelable(false);
        alertbox.setPositiveButton("yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Toast.makeText(MainActivity.this, "Selected Option: YES", Toast.LENGTH_SHORT).show();
                FormFragment formFrag = (FormFragment)getSupportFragmentManager().findFragmentById(R.id.fragment_container);
                formFrag.deleteEntireDB();
            }
        });
        alertbox.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Toast.makeText(MainActivity.this, "Selected Option: NO", Toast.LENGTH_SHORT).show();
            }
        });
        AlertDialog dialog = alertbox.create();
        dialog.show();
    }

    //Override the back action to close the nav side drawer before closing fragment / activity
    @Override
    public void onBackPressed() {
        if(drawerLayout.isDrawerOpen(GravityCompat.START)){
            drawerLayout.closeDrawer(GravityCompat.START);
        }
        else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {


        switch (menuItem.getItemId()){
            case R.id.nav_settings:
                Intent startSettingsActivity = new Intent(this, SettingsActivity.class);
                startActivity(startSettingsActivity);
                break;
            case R.id.nav_account:
                getSupportFragmentManager().beginTransaction().replace
                        (R.id.fragment_container, new AccountFragment()).commit();
                break;
            case R.id.nav_help:
                startActivity(new Intent(Intent.ACTION_VIEW).setData(Uri.parse("https://www.hemingwaywest.com/")));
                break;
            case R.id.nav_logout:
                Toast.makeText(MainActivity.this, R.string.nav_logout, Toast.LENGTH_SHORT).show();
                break;
            case R.id.nav_action_file_upload:
                Toast.makeText(MainActivity.this, R.string.nav_sync_up, Toast.LENGTH_SHORT).show();
                break;
            case R.id.nav_action_file_download:
                readGsonJson();
                Toast.makeText(MainActivity.this, R.string.nav_sync_down, Toast.LENGTH_SHORT).show();
                break;
            case R.id.nav_action_api_test:
                Toast.makeText(MainActivity.this, R.string.nav_api_test, Toast.LENGTH_SHORT).show();
                startActivity(new Intent(Intent.ACTION_VIEW).setData(Uri.parse("https://www.hemingwaywest.com/")));
                break;
            case R.id.nav_action_delete_db:
                createAlertForDelete();
                break;
            case R.id.nav_action_reload_db:
                FormFragment formFrag = (FormFragment)getSupportFragmentManager().findFragmentById(R.id.fragment_container);
                formFrag.reloadDB();
        }

        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }

    // `onPostCreate` called when activity start-up is complete after `onStart()`
    // NOTE 1: Make sure to override the method with only a single `Bundle` argument
    // Note 2: Make sure you implement the correct `onPostCreate(Bundle savedInstanceState)` method.
    // There are 2 signatures and only `onPostCreate(Bundle state)` shows the hamburger icon.
    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        // Sync the toggle state after onRestoreInstanceState has occurred.
        toggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        // Pass any configuration change to the drawer toggles
        toggle.onConfigurationChanged(newConfig);
    }



    private void readGsonJson(){
        String test = readJsonFile();
        try {
           // Reader reader = new FileReader("demoforms.json");
            final Forms form = new Gson().fromJson(test, Forms.class);
            Log.d(TAG, "Loaded from gson: " + form);

            //Add Json to DB
            AppExecutors.getInstance().diskIO().execute(new Runnable() {
                @Override
                public void run() {
                    Long mID = mDb.formsDao().insertForm(form);
                    //form.setFormFieldList(fieldsList);
                    form.setId(mID.intValue());
                    mDb.formsDao().insertFormWithFields(form);
                    Log.d(TAG, "form id = " + form.getId() + " and returned Long = " + mID);
                }
            });
        }
        catch(Exception e){
            e.printStackTrace();
        }

    }

    private String readJsonFile(){
        String json = null;
        try{
            InputStream is = getAssets().open("demoforms.json");
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            json = new String(buffer);
            return json;
        } catch (Exception e){
            e.printStackTrace();
            return null;
        }

        //return json;
    }
}
