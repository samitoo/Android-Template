package com.hemingwaywest.utiliserve.database;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

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
    //List<FormType> getAll();

    @Insert
    void insertFormType(FormType formType);

    @Update(onConflict = OnConflictStrategy.REPLACE)
    void updateFormType(FormType formType);

    @Delete
    void deleteFormType(FormType formType);
}
