package com.hemingwaywest.utiliserve;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.hemingwaywest.utiliserve.Models.FormsViewModel;
import com.hemingwaywest.utiliserve.Utilities.FormListRecycleAdapter;
import com.hemingwaywest.utiliserve.database.Forms;

import java.util.List;
import java.util.concurrent.Executors;

import androidx.appcompat.widget.Toolbar;


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
    private static final String TOOLBAR_TITLE = "";
    View formView;
    private RecyclerView myRecyclerView;
    private FormListRecycleAdapter mRecycleAdapter;
    private DividerItemDecoration decoration;

    //Loading and Errors
    private TextView mErrorMessageDisplay;
    private ProgressBar mLoadingBar;

    //DB
    private FormsViewModel viewModel;



    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //return super.onCreateView(inflater, container, savedInstanceState);
        formView = inflater.inflate(R.layout.fragment_forms, container, false);
        initViews();
        loadFormData();

        //return super.onCreateView(inflater, container, savedInstanceState);
        //return inflater.inflate(R.layout.fragment_forms, container, false);
        return formView;
    }

    //region PRIVATE HELPER METHODS
    private void initViews() {
        //Find all variables in xml
        myRecyclerView = (RecyclerView)formView.findViewById(R.id.form_recyclerView);
        mErrorMessageDisplay = (TextView)formView.findViewById(R.id.tv_error_message_display);
        mLoadingBar = (ProgressBar)formView.findViewById(R.id.pb_loading_indicator);

        //Setup adapter
        mRecycleAdapter = new FormListRecycleAdapter(getContext(), this);
        decoration = new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL);
        myRecyclerView.addItemDecoration(decoration);
        myRecyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        myRecyclerView.setAdapter(mRecycleAdapter);

        //Setup Toolbar
        Toolbar toolbar = getActivity().findViewById(R.id.toolbar);
        toolbar.setTitle(R.string.app_name);
        //Disables back listener
        ((AppCompatActivity)getActivity()).setSupportActionBar(toolbar);




    }


    //Setup the view model so that live data can auto refresh from the DB
    private void setupViewModel(){
        viewModel = ViewModelProviders.of(this).get(FormsViewModel.class);
        viewModel.getListOfForms().observe(this, new Observer<List<Forms>>() {
            @Override
            public void onChanged(@Nullable List<Forms> forms) {
                Log.d(TAG, "Updating list of forms from Livedata in FormViewModel");
                mRecycleAdapter.setFormData(forms);
            }
        });

    }

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
        setupViewModel();
    }

    //endregion

    //region PUBLIC HELPER METHODS FOR TEST
    public void deleteEntireDB(){
        Executors.newSingleThreadScheduledExecutor().execute(new Runnable() {
            @Override
            public void run() {
                viewModel.deleteEntireDB();
            }
        });
    }
    public  void reloadDB(){
        Executors.newSingleThreadScheduledExecutor().execute(new Runnable() {
            @Override
            public void run() {
                viewModel.reloadDB();
            }
        });
    }
    //endregion

    @Override
    public void onItemClickListener(int itemId) {
        Bundle bundle = new Bundle();
        bundle.putInt("form_id", itemId);
        bundle.putBoolean("from_queue", false);
        Log.d(TAG, "Fragment id passed number: " + itemId);
        FormBlankFragment frag = new FormBlankFragment();
        frag.setArguments(bundle);
        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.fragment_container, frag);
        transaction.addToBackStack("fragmentDetail");
        transaction.commit();

    }

    @Override
    public void onResume() {
        super.onResume();
        //This way we refresh the DB on all resumes, not just loads.
        //retrieveForms();

    }


}
