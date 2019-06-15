package com.hemingwaywest.utiliserve.database;

import android.arch.lifecycle.LiveData;
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

@Dao
public interface FormsDao {

    @Query("SELECT * FROM forms")
    LiveData<List<Forms>> getAll();

    @Insert
    void insertForm(Forms form);

    @Update(onConflict = OnConflictStrategy.REPLACE)
    void updateForm(Forms form);

    @Delete
    void deleteForm(Forms form);
}
