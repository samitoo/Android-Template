package com.hemingwaywest.utiliserve.database;

import android.arch.persistence.room.Entity;
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

@Entity(tableName = "forms")
public class Forms {

    @PrimaryKey(autoGenerate = true)
    private int id;
    private String mFormType;

    @Ignore
    //Constructor for new, generates ID
    public Forms(String formType){
        mFormType = formType;
    }

    //Constructor; when reading, will already have an ID
    public Forms(int id, String formType){
        this.id = id;
        mFormType = formType;
    }


    public String getmFormType() {return mFormType;}

    public void setmFormType(String mFormType) {this.mFormType = mFormType;}



}
