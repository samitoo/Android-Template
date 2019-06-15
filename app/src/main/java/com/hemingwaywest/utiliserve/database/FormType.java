package com.hemingwaywest.utiliserve.database;

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
@Entity(tableName = "form_type", foreignKeys = @ForeignKey(entity = Forms.class,
                                                            parentColumns = "id",
                                                            childColumns = "form_id"))
public class FormType {

    @PrimaryKey(autoGenerate = true)
    private int id;
    private int form_id;
    private String mName;

    @Ignore
    public FormType(String name){
        mName = name;
    }

    public FormType(int id, String name){
        this.id = id;
        mName = name;
    }


    public String getmName() {return mName;}

    public void setmName(String mName) {this.mName = mName;}
}
