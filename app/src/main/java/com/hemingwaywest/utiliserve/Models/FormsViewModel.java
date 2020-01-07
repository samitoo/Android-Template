package com.hemingwaywest.utiliserve.Models;

import android.app.Application;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.annotation.NonNull;
import android.util.Log;

import com.hemingwaywest.utiliserve.database.AppDatabase;
import com.hemingwaywest.utiliserve.database.Forms;

import java.util.List;

/**
 * Project: Utiliserve
 * HemingwayWest, LLC. Copyright (c) 2019  All rights reserved.
 * User: Samitoo
 * Date: 6/14/2019
 * url: www.HemingwayWest.com
 * Notes:
 */
public class FormsViewModel extends AndroidViewModel {

    private static final String TAG = FormsViewModel.class.getSimpleName();
    private AppDatabase database = AppDatabase.getInstance(this.getApplication());

    private LiveData<List<Forms>> listOfForms;

    //Constructor
    public FormsViewModel(@NonNull Application application) {
        super(application);
        Log.d(TAG, "Actively retrieving the Forms from the Database");
        listOfForms = database.formsDao().getAllTemplates();
    }

    public LiveData<List<Forms>> getListOfForms(){return listOfForms;}

    public void deleteEntireDB(){
        database.formsDao().deleteAll();
    }
    public void reloadDB(){
        AppDatabase.loadDatabase(this.getApplication());
    }
}
