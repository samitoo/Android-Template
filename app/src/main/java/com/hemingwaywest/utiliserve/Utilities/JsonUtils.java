package com.hemingwaywest.utiliserve.Utilities;

import android.content.Context;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.HttpURLConnection;

/**
 * Project: utiliserve
 * Created by HemingwayWest, LLC, Copyright (c) 2019. All Rights Reserved.
 * User: Samitoo
 * Date: 4/12/2019
 * url:  www.HemingwayWest.com
 * Last Modified: $file.lastModified
 * Notes:
 */
public final class JsonUtils {

    public static String[] getArrayDataFromFormsJson(Context context, String formsJsonStr) throws JSONException{

        //TODO Decide on a final form of the JSON data structure

        //Use to pull the top variable of the JSON
        final String FJ_LIST = "dataList";

        final String FJ_NAME = "form_name";

        final String FJ_DETAILS = "form_details";

        //Use to grab error codes
        final String FJ_MESSAGE_CODE = "cod";

        //Array to hold the values of each entry temporarily
        String[] parsedFormList = null;

        JSONObject formListJson = new JSONObject(formsJsonStr);

        //Check for an error
        if (formListJson.has(FJ_MESSAGE_CODE)){
            int errorCode = formListJson.getInt(FJ_MESSAGE_CODE);

            //TODO Add in more connection error codes
            switch(errorCode) {
                case HttpURLConnection.HTTP_OK:
                    break;
                case HttpURLConnection.HTTP_NOT_FOUND:
                    return null;
                default:
                    return null;
            }
        }

        JSONArray formListArray = formListJson.getJSONArray(FJ_LIST);
        parsedFormList = new String[formListArray.length()];

        for (int i = 0; i < formListArray.length(); i++) {

            String formTitle;
            String description;

            //Get a single form entry item as json object
            JSONObject formEntry = formListArray.getJSONObject(i);

            formTitle = formEntry.getString(FJ_NAME);
            description = formEntry.getString(FJ_DETAILS);

            parsedFormList[i] = formTitle + " - " + description;

        }

        return parsedFormList;
    }


}
