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
public abstract class FormsDao {

    //Get the blank template forms
    @Query("SELECT * FROM forms WHERE formType ='Blank'")
    public abstract LiveData<List<Forms>> getAllTemplates();

    @Query("SELECT * FROM forms WHERE formType='Complete'")
    public abstract LiveData<List<Forms>> getAllForQ();

    //Clear forms table
    @Query("DELETE FROM forms")
    public abstract void deleteAll();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    public abstract Long insertForm(Forms form);

    @Insert
    public abstract void insertAll(Forms ...forms);

    @Update(onConflict = OnConflictStrategy.REPLACE)
    public abstract void updateForm(Forms form);

    @Delete
    public abstract void deleteForm(Forms form);

    //Relationship workarounds
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    public abstract void insertFieldList(List<FormField> fields);

    @Update(onConflict = OnConflictStrategy.REPLACE)
    public abstract void updateFieldList(List<FormField> fields);

    @Query("SELECT * FROM forms WHERE id =:id")
    public abstract Forms getForm(int id);

    @Query("SELECT * FROM form_field WHERE form_id =:formid")
    public abstract List<FormField>getFormFieldList(int formid);

    //Insert a new form with all field children
    public void insertFormWithFields(Forms form){
        List<FormField> fields = form.getFormFieldList();
        for (int i = 0; i < fields.size(); i++) {
            fields.get(i).setForm_id(form.getId());
        }
        insertFieldList(fields);
        insertForm(form);
    }

    public void updateFormWithFields(Forms form){
        List<FormField> fields = form.getFormFieldList();
        insertFieldList(fields);
    }



}
