package com.hemingwaywest.utiliserve.database;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

/**
 * Project: Utiliserve
 * HemingwayWest, LLC. Copyright (c) 2019  All rights reserved.
 * User: Samitoo
 * Date: 6/14/2019
 * url: www.HemingwayWest.com
 * Notes:
 */

//@Dao
public interface FormTypeDao {

    //@Query("SELECT * FROM form_type")
    //List<FormType> getAllTemplates();

    @Insert
    void insertFormType(FormType formType);

    @Update(onConflict = OnConflictStrategy.REPLACE)
    void updateFormType(FormType formType);

    @Delete
    void deleteFormType(FormType formType);
}
