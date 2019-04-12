package com.hemingwaywest.utiliserve.Models;

/**
 * Project: utiliserve
 * Created by HemingwayWest, LLC, Copyright (c) 2019. All Rights Reserved.
 * User: Samitoo
 * Date: 4/8/2019
 * url:  www.HemingwayWest.com
 * Last Modified: $file.lastModified
 * Notes: Model for the Form layout on the recycler view.
 */
public class FormListItem {

    private String mFormTitle;
    private String mFormDetails;

    public FormListItem() {
    }

    //Constructor
    public FormListItem(String formTitle, String formDetails) {
        mFormTitle = formTitle;
        mFormDetails = formDetails;
    }

    //Setters
    public void setmFormTitle(String formTitle) {
        this.mFormTitle = formTitle;
    }

    public void setmFormDetails(String formDetails) {
        this.mFormDetails = formDetails;
    }

    //Getters
    public String getFormTitle() {
        return mFormTitle;
    }

    public String getFormDetails() {
        return mFormDetails;
    }

}
