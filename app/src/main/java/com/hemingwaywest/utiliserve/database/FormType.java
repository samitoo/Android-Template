package com.hemingwaywest.utiliserve.database;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.Index;
import android.arch.persistence.room.PrimaryKey;

/**
 * Project: Utiliserve
 * HemingwayWest, LLC. Copyright (c) 2019  All rights reserved.
 * User: Samitoo
 * Date: 6/14/2019
 * url: www.HemingwayWest.com
 * Notes:
 */
/*@Entity(indices = {@Index("form_id")},tableName = "form_type", foreignKeys = @ForeignKey(entity = Forms.class,
                                                            parentColumns = "id",
                                                            childColumns = "form_id"))*/
public class FormType {

    @PrimaryKey(autoGenerate = true)
    private int id;
    private int form_id;
    private String name;

    @Ignore
    public FormType(int form_id, String name){
        this.form_id = form_id;
        this.name = name;
    }

    public FormType(int id, int form_id, String name){
        this.id = id;
        this.form_id = form_id;
        this.name = name;
    }


    //Getters
    public int getId(){return id;}
    public int getForm_id(){return form_id;}
    public String getName() {return name;}

    //Setters
    public void setId(int id){this.id = id;}
    public void setForm_id(int parentID){this.form_id = parentID;}
    public void setName(String name) {this.name = name;}
}
