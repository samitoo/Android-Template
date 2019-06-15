package com.hemingwaywest.utiliserve.database;

import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;

/**
 * Project: Utiliserve
 * HemingwayWest, LLC. Copyright (c) 2019  All rights reserved.
 * User: Samitoo
 * Date: 6/14/2019
 * url: www.HemingwayWest.com
 * Notes:
 */
@Entity(tableName = "form_field", foreignKeys = @ForeignKey(entity = Forms.class,
                                                            parentColumns = "id",
                                                            childColumns = "form_id"))
public class FormField {

    @PrimaryKey(autoGenerate = true)
    private int id;
    private int form_id;
    private String mName;
    private String mValue;

    @Ignore
    public FormField(String name, String value){
        mName = name;
        mValue = value;
    }

    public FormField(int id, String name, String value){
        this.id = id;
        mName = name;
        mValue = value;
    }


    //Getters
    public String getmName() {return mName;}
    public String getmValue() {return mValue;}

    //Setters
    public void setmName(String mName) {this.mName = mName;}
    public void setmValue(String mValue) {this.mValue = mValue;}



}
