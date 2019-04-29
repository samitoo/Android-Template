package com.hemingwaywest.utiliserve.database;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

@Dao
public interface FormListDao {

    @Query("SELECT * FROM forms")
    List<FormListEntry> loadAllForms();

    @Insert
    void insertForm(FormListEntry formEntry);

    @Insert
    void insertAll(FormListEntry ...formEntries);

    @Update(onConflict = OnConflictStrategy.REPLACE)
    void updateForm(FormListEntry formEntry);

    @Delete
    void deleteForm(FormListEntry formEntry);

}
