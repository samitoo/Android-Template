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
    private String formType;
    private String name;
    private String description;

    @Ignore
    //Constructor for new, generates ID
    public Forms(String formType, String name, String description){
        this.formType = formType;
        this.name = name;
        this.description = description;
    }

    //Constructor; when reading, will already have an ID
    public Forms(int id, String formType, String name, String description){
        this.id = id;
        this.formType = formType;
        this.name = name;
        this.description = description;
    }

    //getters
    public int getId(){return id;}
    public String getFormType() {return formType;}
    public String getName() {return name;}
    public String getDescription() {return description;}

    public void setId(){this.id = id;}
    public void setFormType(String formType) {this.formType = formType;}
    public void setName(String name){this.name = name;}
    public void setDescription(String name){this.description = description;}


    public static Forms[] prepopulateFormsData(){
        return new Forms[]{
                new Forms("blank","Hydrant","Inspection Report"),
                new Forms("blank","Hydrant","Inspection Report")
        };
    }

}
