package com.hemingwaywest.utiliserve.database;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.Index;
import android.arch.persistence.room.PrimaryKey;

import java.util.List;

import static android.arch.persistence.room.ForeignKey.CASCADE;

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
    //TODO add forgein key for user to forms
    //private int userID;
    private String formType;
    private String name;
    private String description;

    @Ignore
    private List<FormField> formFieldList;

    @Ignore
    //Constructor for new, generates ID
    public Forms(String formType, String name, String description){
       // this.userID = userID;
        this.formType = formType;
        this.name = name;
        this.description = description;
    }

    //Constructor; when reading, will already have an ID
    public Forms(int id,String formType, String name, String description){
        this.id = id;
       // this.userID = userID;
        this.formType = formType;
        this.name = name;
        this.description = description;
    }

    //getters
    public int getId(){return id;}
   // public int getUserID(){return userID;}
    public String getFormType() {return formType;}
    public String getName() {return name;}
    public String getDescription() {return description;}
    public List<FormField> getFormFieldList() {return formFieldList;}

    public void setId(int id){this.id = id;}
  //  public void setUserID(int userID){this.userID = userID;}
    public void setFormType(String formType) {this.formType = formType;}
    public void setName(String name){this.name = name;}
    public void setDescription(String name){this.description = description;}
    public void setFormFieldList(List<FormField> formFieldList) {this.formFieldList = formFieldList;}


    //TODO Generate static reports from a number in user preferences
    public static Forms[] prepopulateFormsData(){
        return new Forms[]{
                new Forms("blank","Hydrant","Inspection Report")
        };
    }

    private static User createUser(){
        return new User("sammy","hamdan");
    }




}
