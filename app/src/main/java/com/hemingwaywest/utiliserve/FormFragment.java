package com.hemingwaywest.utiliserve;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
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

import com.hemingwaywest.utiliserve.Models.FormsViewModel;
import com.hemingwaywest.utiliserve.Utilities.AppExecutors;
import com.hemingwaywest.utiliserve.Utilities.FormListRecycleAdapter;
import com.hemingwaywest.utiliserve.database.AppDatabase;
import com.hemingwaywest.utiliserve.database.FormListEntry;
import com.hemingwaywest.utiliserve.database.Forms;

import java.util.List;
import android.support.v7.widget.Toolbar;


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
        initViews();
        mDb = AppDatabase.getInstance(getContext());
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
        toolbar.setTitle(TOOLBAR_TITLE);
        toolbar.setNavigationIcon(null);
    }

    //Setup the view model so that live data can auto refresh from the DB
    private void setupViewModel(){
        FormsViewModel viewModel = ViewModelProviders.of(this).get(FormsViewModel.class);
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
        //retrieveForms();
        //new FetchFormTask().execute();
    }

   /* private void retrieveForms() {
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
    } */

    //endregion

    @Override
    public void onItemClickListener(int itemId) {
        //TODO Launch the detail view with the itemID as an extra in the intent
        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.fragment_container, new FormDetailFragment());
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
