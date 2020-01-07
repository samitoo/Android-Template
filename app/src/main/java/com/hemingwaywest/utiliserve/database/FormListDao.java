package com.hemingwaywest.utiliserve.database;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface FormListDao {

    @Query("SELECT * FROM formsList")
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
