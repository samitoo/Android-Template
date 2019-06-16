package com.hemingwaywest.utiliserve.database;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;

/**
 * Project: Utiliserve
 * HemingwayWest, LLC. Copyright (c) 2019  All rights reserved.
 * User: Samitoo
 * Date: 6/15/2019
 * url: www.HemingwayWest.com
 * Notes:
 */

@Entity
public class User {

    @PrimaryKey(autoGenerate = true)
    private int id;
    private String firstName;
    private String lastName;

    @Ignore
    //Constructor for it to ignore, no auto generated element
    public User(String firstName, String lastName){
        this.firstName = firstName;
        this.lastName = lastName;
    }


    //Constructor
    public User(int id, String firstName, String lastName){
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
    }



    //Getters
    public int getId() {return id;}
    public String getFirstName(){return firstName;}
    public String getLastName(){return lastName;}

    //Setters
    public void setId(int id) {this.id = id;}
    public void setFirstName(String firstName) {this.firstName = firstName;}
    public void setLastName(String lastName) {this.lastName = lastName;}
}
