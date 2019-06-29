package com.hemingwaywest.utiliserve;

import android.arch.lifecycle.ViewModelProviders;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.hemingwaywest.utiliserve.Models.FormsViewModel;

public class MainActivity extends AppCompatActivity
        implements SharedPreferences.OnSharedPreferenceChangeListener {

    boolean mDebugMode = false;
    boolean mSync = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //Swap from the launcher theme back to to the original before calling super
        setTheme(R.style.AppTheme_NoActionBar);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        BottomNavigationView bottomNav = findViewById(R.id.bottom_navigation);
        bottomNav.setOnNavigationItemSelectedListener(navListener);

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
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
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
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            Intent startSettingsActivity = new Intent(this, SettingsActivity.class);
            startActivity(startSettingsActivity);
            return true;
        }
        if(id == R.id.action_delete_db){
            Log.d("Main Menu", "Delete pressed");
           createAlertForDelete();
        }

        if(id==R.id.action_reload_db){
            FormFragment formFrag = (FormFragment)getSupportFragmentManager().findFragmentById(R.id.fragment_container);
            formFrag.reloadDB();
        }

        if(id==R.id.action_account){
            getSupportFragmentManager().beginTransaction().replace
                    (R.id.fragment_container, new AccountFragment()).commit();
        }

        if(id==R.id.action_help){
            startActivity(new Intent(Intent.ACTION_VIEW).setData(Uri.parse("https://www.hemingwaywest.com/")));
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
}
