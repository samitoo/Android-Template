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
 * Date: 6/16/2019
 * url: www.HemingwayWest.com
 * Notes:
 */
public class QueueViewModel extends AndroidViewModel {

    private static final String TAG = QueueViewModel.class.getSimpleName();
    private AppDatabase database = AppDatabase.getInstance(this.getApplication());

    private LiveData<List<Forms>> listOfForms;

    //Constructor
    public QueueViewModel(@NonNull Application application) {
        super(application);
        Log.d(TAG, "Actively retrieving the Forms from the Database");
        listOfForms = database.formsDao().getAllForQ();
    }

    public LiveData<List<Forms>> getListOfForms(){return listOfForms;}


}
