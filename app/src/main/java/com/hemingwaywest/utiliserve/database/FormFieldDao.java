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
public interface FormFieldDao {

    @Query("SELECT * FROM form_field")
    List<FormField> getAll();

    @Query("SELECT * FROM form_field where form_id =:id")
    public abstract LiveData<List<FormField>> getFormFieldById(int id);

    @Insert
    void insertFormField(FormField formField);

    @Insert
    void insertAll(FormField ...formFields);

    @Update(onConflict = OnConflictStrategy.REPLACE)
    void updateFormField(FormField formField);

    @Delete
    void deleteFormField(FormField formField);
}
