package com.hemingwaywest.utiliserve;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.hemingwaywest.utiliserve.Models.FormListItem;
import com.hemingwaywest.utiliserve.Utilities.JsonUtils;
import com.hemingwaywest.utiliserve.Utilities.RecyclerViewAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.w3c.dom.Text;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

/**
 * Project: utiliserve
 * Created by HemingwayWest, LLC, Copyright (c) 2019. All Rights Reserved.
 * User: Samitoo
 * Date: 4/8/2019
 * url:  www.HemingwayWest.com
 * Last Modified: $file.lastModified
 * Notes: This class will setup the recycler view and connect the data to the adapter and then
 * to the recycler view.
 * Contains an inner class to run the Async Task of populating the view
 */
public class FormFragment extends Fragment {

    View formView;
    private RecyclerView myRecyclerView;
    //private List<FormListItem> mData;
    private JSONArray mJSONarray;
    private RecyclerViewAdapter mRecycleAdapter;

    //Loading and Errors
    private TextView mErrorMessageDisplay;
    private ProgressBar mLoadingBar;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //return super.onCreateView(inflater, container, savedInstanceState);
        formView = inflater.inflate(R.layout.fragment_forms, container, false);

        //Find all variables in xml
        myRecyclerView = (RecyclerView)formView.findViewById(R.id.form_recyclerView);
        mErrorMessageDisplay = (TextView)formView.findViewById(R.id.tv_error_message_display);
        mLoadingBar = (ProgressBar)formView.findViewById(R.id.pb_loading_indicator);

        //Pass data to the adapter MOVED TO ASYNC
        /*RecyclerViewAdapter recycleAdapter = new RecyclerViewAdapter(getContext(), mData);
        myRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        myRecyclerView.setAdapter(recycleAdapter);*/

        mRecycleAdapter = new RecyclerViewAdapter();
        myRecyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        myRecyclerView.setAdapter(mRecycleAdapter);


        loadFormData();

        //return super.onCreateView(inflater, container, savedInstanceState);
        //return inflater.inflate(R.layout.fragment_forms, container, false);
        return formView;
    }

    //region PRIVATE HELPER METHODS
    private void showFormViews(){
        mErrorMessageDisplay.setVisibility(View.INVISIBLE);
        myRecyclerView.setVisibility(View.VISIBLE);
    }
    private void showErrorMessage(){
        myRecyclerView.setVisibility(View.INVISIBLE);
        mErrorMessageDisplay.setVisibility(View.VISIBLE);
    }
    //endregion

    //region JSON METHODS
    private void loadFormData(){
        showFormViews();
        new FetchFormTask().execute();
    }
    private String loadJSONFromAsset(){
        String json = null;
        try{
            //Get assets requires context (so activities)
            InputStream is = formView.getContext().getAssets().open("MOCK_DATA.json");
            int size = is.available();
            byte [] buffer = new byte[size];
            is.read(buffer);
            is.close();
            json = new String(buffer, "UTF-8");
        }catch (IOException ex){
            ex.printStackTrace();
            return null;
        }

        return json;
    }

    private void getJSONobj(){
        try{
            mJSONarray = new JSONArray(loadJSONFromAsset());
        }catch (JSONException e){
            e.printStackTrace();
        }
    }


    /*private void LoadDataFromJSON(){
        getJSONobj();
        //TODO: Put JSON data into array format
        RecyclerViewAdapter rvAdapter = new RecyclerViewAdapter(formView.getContext(), mData);
        myRecyclerView.setAdapter(rvAdapter);
    }*/

    //endregion

    /**
     * Private inner class to start the background thread activity
     * TODO Check to see if in debug mode or live mode from preferences
     */
    public class FetchFormTask extends AsyncTask<String, Void, String[]> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            mLoadingBar.setVisibility(View.VISIBLE);
        }

        @Override
        protected String[] doInBackground(String... params) {
            //If there's no data pulled, dip out
            //TODO Use this to pull data from preferences
            /*if (params.length ==0){
                return null;
            }*/

            //TODO Add url request logic here

            try{
                //stuff
                String jsonFormResponse = loadJSONFromAsset();
                String[] simpleFormListJson = JsonUtils.getArrayDataFromFormsJson(formView.getContext(), jsonFormResponse);
                return  simpleFormListJson;
            }catch (Exception e){
                e.printStackTrace();
                return null;
            }
        }

        @Override
        protected void onPostExecute(String[] formListData) {
            mLoadingBar.setVisibility(View.INVISIBLE);
            if (formListData != null){
                showFormViews();

            }
            else{
                showErrorMessage();
            }
        }
    }
}
