package com.hemingwaywest.utiliserve;

import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.hemingwaywest.utiliserve.Utilities.AppExecutors;
import com.hemingwaywest.utiliserve.Utilities.FormListRecycleAdapter;
import com.hemingwaywest.utiliserve.Utilities.JsonUtils;
import com.hemingwaywest.utiliserve.database.AppDatabase;
import com.hemingwaywest.utiliserve.database.FormListEntry;

import org.json.JSONArray;
import org.json.JSONException;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import android.support.v7.widget.Toolbar;
import java.util.concurrent.Executors;


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
public class FormFragment extends Fragment implements FormListRecycleAdapter.ItemClickListener {

    private static final String TAG = FormFragment.class.getSimpleName();
    private static final String TOOLBAR_TITLE = "Utiliserve";
    View formView;
    private RecyclerView myRecyclerView;
    private JSONArray mJSONarray;
    private FormListRecycleAdapter mRecycleAdapter;
    private DividerItemDecoration decoration;

    //Loading and Errors
    private TextView mErrorMessageDisplay;
    private ProgressBar mLoadingBar;

    //DB
    private AppDatabase mDb;



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
        /*FormListRecycleAdapter recycleAdapter = new FormListRecycleAdapter(getContext(), mData);
        myRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        myRecyclerView.setAdapter(recycleAdapter);*/

        mRecycleAdapter = new FormListRecycleAdapter(getContext(), this);
        decoration = new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL);
        myRecyclerView.addItemDecoration(decoration);
        myRecyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        myRecyclerView.setAdapter(mRecycleAdapter);

        Toolbar toolbar = getActivity().findViewById(R.id.toolbar);
        toolbar.setTitle(TOOLBAR_TITLE);
        toolbar.setNavigationIcon(null);

        mDb = AppDatabase.getInstance(getContext());
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
    private void loadFormData(){
        showFormViews();
        retrieveForms();
        //new FetchFormTask().execute();
    }

    private void retrieveForms() {
        Log.d(TAG, "Retrieving data from DB");
        AppExecutors.getInstance().diskIO().execute(new Runnable() {
            @Override
            public void run() {
                final List<FormListEntry> forms = mDb.formDao().loadAllForms();
                //TODO simplify with architecture later
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        mRecycleAdapter.setFormData(forms);
                    }
                });
            }
        });
    }

    //endregion
    public void onClick(){

    }

    @Override
    public void onItemClickListener(int itemId) {
        //TODO Launch the detail view with the itemID as an extra in the intent
        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.fragment_container, new FormDetailFragment());
        transaction.addToBackStack("fragmentDetail");
        transaction.commit();

        //getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, selectedFragment).commit();


    }

    @Override
    public void onResume() {
        super.onResume();
        //This way we refresh the DB on all resumes, not just loads.
        retrieveForms();

    }

    //region JSON METHODS
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
        FormListRecycleAdapter rvAdapter = new FormListRecycleAdapter(formView.getContext(), mData);
        myRecyclerView.setAdapter(rvAdapter);
    }*/

    //endregion

    /**
     * Private inner class to start the background thread activity
     * TODO Check to see if in debug mode or live mode from preferences
     */
   /* public class FetchFormTask extends AsyncTask<String, Void, String[]> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            mLoadingBar.setVisibility(View.VISIBLE);
        }

        @Override
        protected String[] doInBackground(String... params) {
            //If there's no data pulled, dip out
            //TODO Use this to pull data from preferences
            //if (params.length ==0){
            //    return null;
            //}

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
    } */
}
