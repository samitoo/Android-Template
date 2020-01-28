package com.hemingwaywest.utiliserve.database;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

/**
 * Project: Utiliserve
 * HemingwayWest, LLC. Copyright (c) 2019  All rights reserved.
 * User: Samitoo
 * Date: 6/14/2019
 * url: www.HemingwayWest.com
 * Notes:
 */
@Dao
public abstract class FormFieldDao {

    @Query("SELECT * FROM form_field")
    public abstract List<FormField> getAll();

    @Query("SELECT * FROM form_field where form_id =:id")
    public abstract LiveData<List<FormField>> getFormFieldById(int id);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    public abstract Long insertFormField(FormField formField);


    @Insert
    public abstract void insertFieldList(List<FormField> fields);

    @Insert
    public  abstract void insertAll(FormField ...formFields);

    @Update(onConflict = OnConflictStrategy.REPLACE)
    public abstract void updateFormField(FormField formField);

    @Delete
    public abstract void deleteFormField(FormField formField);
}
