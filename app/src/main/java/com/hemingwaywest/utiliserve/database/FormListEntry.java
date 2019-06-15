package com.hemingwaywest.utiliserve.database;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;

/**
 * Project: utiliserve
 * Created by HemingwayWest, LLC, Copyright (c) 2019. All Rights Reserved.
 * User: Samitoo
 * Date: 4/8/2019
 * url:  www.HemingwayWest.com
 * Last Modified: $file.lastModified
 * Notes: Model for the Form layout on the recycler view.
 */

@Entity(tableName = "formsList")
public class FormListEntry {

    @PrimaryKey(autoGenerate = true)
    private  int id;
    private String mFormTitle;
    private String mFormDetails;

    @Ignore
    //Constructor
    //To add a new item to the DB, we need an ID, it's auto generated so we don't have it here
    public FormListEntry(String title, String description) {
        mFormTitle = title;
        mFormDetails = description;
    }

    //Constructor
    //When reading from the DB, the item will already have an ID
    //Room will use this one to make items and generate the key
    public FormListEntry(int id, String formTitle, String formDetails) {
        this.id = id;
        mFormTitle = formTitle;
        mFormDetails = formDetails;
    }

    //Setters
    public void setId(int id) { this.id = id; }
    public void setmFormTitle(String formTitle) {
        this.mFormTitle = formTitle;
    }
    public void setmFormDetails(String formDetails) {
        this.mFormDetails = formDetails;
    }

    //Getters
    public int getId() { return id; }
    public String getFormTitle() {
        return mFormTitle;
    }
    public String getFormDetails() {
        return mFormDetails;
    }

    /**
     *
     * @return returns some test data for the DB creation.
     */
    public static FormListEntry[] populateData(){
        return new FormListEntry[]{
                new FormListEntry("form 1", "Test Data 1"),
                new FormListEntry("form 2", "Test Data 2"),
                new FormListEntry("form 3", "Test Data 3"),
                new FormListEntry("form 4", "Test Data 4"),
                new FormListEntry("form 5", "Test Data 5"),
                new FormListEntry("form 6", "Test Data 6"),
                new FormListEntry("form 7", "Test Data 7"),
                new FormListEntry("form 8", "Test Data 8"),
                new FormListEntry("form 9", "Test Data 9"),
                new FormListEntry("form 10", "Test Data 10"),
                new FormListEntry("form 11", "Test Data 11"),
                new FormListEntry("form 12", "Test Data 12"),
                new FormListEntry("form 13", "Test Data 13"),
                new FormListEntry("form 14", "Test Data 14"),
                new FormListEntry("form 15", "Test Data 15"),
        };
    }



}
