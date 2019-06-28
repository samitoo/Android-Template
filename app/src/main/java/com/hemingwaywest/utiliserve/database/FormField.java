package com.hemingwaywest.utiliserve.database;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.Index;
import android.arch.persistence.room.PrimaryKey;

import static android.arch.persistence.room.ForeignKey.CASCADE;

/**
 * Project: Utiliserve
 * HemingwayWest, LLC. Copyright (c) 2019  All rights reserved.
 * User: Samitoo
 * Date: 6/14/2019
 * url: www.HemingwayWest.com
 * Notes:
 */
@Entity(indices ={@Index("form_id")} ,tableName = "form_field")
//, foreignKeys = @ForeignKey(entity = Forms.class,
//                                                            parentColumns = "id",
//                                                            childColumns = "form_id",
//                                                            onDelete = CASCADE)
public class FormField {

    @PrimaryKey(autoGenerate = true)
    private int id;
    private int form_id;
    private String name;
    private String value;

    @Ignore
    public FormField(int form_id, String name, String value){
        this.form_id = form_id;
        this.name = name;
        this.value = value;
    }

    public FormField(int id, int form_id, String name, String value){
        this.id = id;
        this.form_id = form_id;
        this.name = name;
        this.value = value;
    }


    //Getters
    public int getId(){return id;}
    public int getForm_id(){return form_id;}
    public String getName() {return name;}
    public String getValue() {return value;}

    //Setters
    public void setId(int id){this.id = id;}
    public void setForm_id(int parentID){this.form_id = parentID;}
    public void setName(String name) {this.name = name;}
    public void setValue(String value) {this.value = value;}



}
