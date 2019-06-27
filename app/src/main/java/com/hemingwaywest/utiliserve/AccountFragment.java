package com.hemingwaywest.utiliserve;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.os.Debug;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.Layout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Project: utiliserve
 * Created by HemingwayWest, LLC, Copyright (c) 2019. All Rights Reserved.
 * User: Samitoo
 * Date: 4/8/2019
 * url:  www.HemingwayWest.com
 * Last Modified: $file.lastModified
 * Notes:
 */
public class AccountFragment extends Fragment
    implements SharedPreferences.OnSharedPreferenceChangeListener{

    private final static String TAG = AccountFragment.class.getSimpleName();

    private TextView mUserName;
    private View accountView;
    private SharedPreferences mSharedPref;
    private ImageView mNameEdit, mContactEdit, mHelp;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //return super.onCreateView(inflater, container, savedInstanceState);
        //return inflater.inflate(R.layout.fragment_account, container, false);
        accountView = inflater.inflate(R.layout.fragment_account, container, false);
        setupViews();
        return accountView;


    }

    private void setupViews(){
        mUserName = accountView.findViewById(R.id.account_username);
        mNameEdit = accountView.findViewById(R.id.iv_name_edit);
        mNameEdit.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                ImageClick();
            }
        });
        mContactEdit = accountView.findViewById(R.id.iv_contact_edit);
        mContactEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ImageClick();
            }
        });
        mHelp = accountView.findViewById(R.id.iv_help);
        mHelp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Intent.ACTION_VIEW).setData(Uri.parse("https://www.hemingwaywest.com/")));
            }
        });
        setupSharedPreferences();

    }

    /**
     * Setup the settings preferences calls
     */
    private void setupSharedPreferences(){
        //TODO: Get more Items from preferences file
        mSharedPref = PreferenceManager.getDefaultSharedPreferences(getActivity());
        //Can attach the listener to "this" since the class implements SharedPreferences.OnSharedPreferenceChangeListener
        mSharedPref.registerOnSharedPreferenceChangeListener(this);
        Boolean debug = mSharedPref.getBoolean(
                (getString(R.string.pref_show_debug_key)), getResources().getBoolean(R.bool.pref_show_debug_default)
        );
        Log.d(TAG, debug.toString());
    }

    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
        Log.d(TAG, "shared pref changed");
        Log.d(TAG, "Key " +key);
        Log.d(TAG, "Get String returns: " + getString(R.string.pref_name_key));
        if (key.equals(getString(R.string.pref_name_key))){
            //Change to debug pull
            String name = mSharedPref.getString(
                    (getString(R.string.pref_name_key)), "John");
            mUserName.setText(name);
        }
    }

    private void ImageClick(){
        Log.d(TAG, "yas queen");
        Intent startSettingsActivity = new Intent(getContext(), SettingsActivity.class);
        startActivity(startSettingsActivity);
    }

}
