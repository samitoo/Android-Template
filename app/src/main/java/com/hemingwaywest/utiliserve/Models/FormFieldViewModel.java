package com.hemingwaywest.utiliserve.Models;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.hemingwaywest.utiliserve.database.AppDatabase;
import com.hemingwaywest.utiliserve.database.FormField;

import java.util.List;

/**
 * Project: Utiliserve
 * HemingwayWest, LLC. Copyright (c) 2019  All rights reserved.
 * User: Samitoo
 * Date: 1/25/2020
 * url: www.HemingwayWest.com
 * Notes:
 */

public class FormFieldViewModel extends AndroidViewModel {

    private static final String TAG = FormFieldViewModel.class.getSimpleName();
    private AppDatabase database = AppDatabase.getInstance(this.getApplication());

    private LiveData<List<FormField>> listOfFields;

    public FormFieldViewModel(@NonNull Application application) {
        super(application);
        Log.d(TAG, "Actively retrieving the Forms from the Database");
        //listOfFields = database.formFieldDao().getFormFieldById(formNumber);
    }

    public LiveData<List<FormField>> getListOfFields(int formNumber){
        listOfFields = database.formFieldDao().getFormFieldById(formNumber);
        return listOfFields;
    }
}
