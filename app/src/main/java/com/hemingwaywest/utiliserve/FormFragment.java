package com.hemingwaywest.utiliserve;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.hemingwaywest.utiliserve.Models.FormListItem;
import com.hemingwaywest.utiliserve.Utilities.RecyclerViewAdapter;

import org.json.JSONArray;
import org.json.JSONException;

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
 * Notes:
 */
public class FormFragment extends Fragment {

    View formView;
    private RecyclerView myRecyclerView;
    private List<FormListItem> mData;
    private JSONArray mJSONarray;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //return super.onCreateView(inflater, container, savedInstanceState);
        formView = inflater.inflate(R.layout.fragment_forms, container, false);
        myRecyclerView = (RecyclerView)formView.findViewById(R.id.form_recyclerView);
        RecyclerViewAdapter recycleAdapter = new RecyclerViewAdapter(getContext(), mData);
        myRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        myRecyclerView.setAdapter(recycleAdapter);
        return null;
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


    private void LoadDataFromJSON(){
        getJSONobj();
        //TODO: Put JSON data into array format
        RecyclerViewAdapter rvAdapter = new RecyclerViewAdapter(formView.getContext(), mData);
        myRecyclerView.setAdapter(rvAdapter);
    }
}
