package com.hemingwaywest.utiliserve;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v14.preference.PreferenceFragment;
import android.support.v14.preference.SwitchPreference;
import android.support.v7.preference.CheckBoxPreference;
import android.support.v7.preference.EditTextPreference;
import android.support.v7.preference.ListPreference;
import android.support.v7.preference.Preference;
import android.support.v7.preference.PreferenceCategory;
import android.support.v7.preference.PreferenceFragmentCompat;
import android.support.v7.preference.PreferenceScreen;
import android.util.Log;

import com.hemingwaywest.utiliserve.R;

/**
 * Project: utiliserve
 * Created by HemingwayWest, LLC, Copyright (c) 2019. All Rights Reserved.
 * User: Samitoo
 * Date: 4/12/2019
 * url:  www.HemingwayWest.com
 * Last Modified: $file.lastModified
 * Notes:
 */
public class SettingsFragment extends PreferenceFragmentCompat
        implements SharedPreferences.OnSharedPreferenceChangeListener{
    @Override
    public void onCreatePreferences(Bundle bundle, String s) {
        addPreferencesFromResource(R.xml.pref_settings);

        //To update the preference summaries on change
        SharedPreferences sharedPreferences = getPreferenceScreen().getSharedPreferences();
        PreferenceScreen prefScreen = getPreferenceScreen();
        int count = prefScreen.getPreferenceCount();

        //Loop through categories
        for (int i = 0; i < count; i++) {
            //Preference p = prefScreen.getPreference(i);
            //Get the preference parent categories
            PreferenceCategory pc = (PreferenceCategory) prefScreen.getPreference(i);
            //Get preferences in the categories
            int categoryCount = pc.getPreferenceCount();
            Log.d("Preferences", "CategoryCount: " + categoryCount);
            for (int j = 0; j < categoryCount; j++) {
                Preference p = pc.getPreference(j);
                //Checkbox is set via an xml attribute
                //TODO Add preferences here that can't have default value because it's in xml
                if(!(p instanceof CheckBoxPreference || p instanceof SwitchPreference)){
                    String value = sharedPreferences.getString(p.getKey(), "");
                    setPreferenceSummary(p, value);
                    Log.d("Preferences", "P: " + p + " Value: " + value);
                }
            }



           /* //Checkbox is set via an xml attribute
            if(!(p instanceof CheckBoxPreference)){
                String value = sharedPreferences.getString(p.getKey(), "");
                setPreferenceSummary(p, value);
                Log.d("Preferences", "P: " + p + " Value: " + value);
            }*/
        }
    }

    //To update the preference summaries on change
    //TODO Check / validate data before saving UD: 6-28
    private void setPreferenceSummary(Preference preference, String value){
        if (preference instanceof ListPreference){
            //For list preferences, figure out the label of the selected value
            ListPreference listPreference = (ListPreference) preference;
            int prefIndex = listPreference.findIndexOfValue(value);
            //Check if not null, then get Label associated with ^value
            if (prefIndex >= 0){
                listPreference.setSummary(listPreference.getEntries()[prefIndex]);

            }
        }else  if ( preference instanceof EditTextPreference){
            preference.setSummary(value);
        }
    }

    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
        Preference preference = findPreference(key);
        if (null != preference){
            if ( ! (preference instanceof CheckBoxPreference) ){
                String value = sharedPreferences.getString(preference.getKey(), "");
                setPreferenceSummary(preference, value);
            }
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getPreferenceScreen().getSharedPreferences().registerOnSharedPreferenceChangeListener(this);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        getPreferenceScreen().getSharedPreferences().unregisterOnSharedPreferenceChangeListener(this);
    }
}
