package com.hemingwaywest.utiliserve;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.hemingwaywest.utiliserve.Models.FormsViewModel;
import com.hemingwaywest.utiliserve.Models.Queue;
import com.hemingwaywest.utiliserve.Models.QueueViewModel;
import com.hemingwaywest.utiliserve.Utilities.QueueListRecycleAdapter;
import com.hemingwaywest.utiliserve.database.AppDatabase;
import com.hemingwaywest.utiliserve.database.Forms;

import java.util.ArrayList;
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
public class QueueFragment extends Fragment implements QueueListRecycleAdapter.ItemClickListener {

    private static final String TAG = QueueFragment.class.getSimpleName();
    private static final String TOOLBAR_TITLE = "Utiliserve";

    View queueView;
    private RecyclerView rvQueue;
    private QueueListRecycleAdapter queueListRecycleAdapter;
    private DividerItemDecoration decoration;
    //DB
    private QueueViewModel viewModel;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //return super.onCreateView(inflater, container, savedInstanceState);

        //Find variables in xml
        queueView = inflater.inflate(R.layout.fragment_queue, container, false);
        //Connect views
        initViews();
        setupViewModel();
        return queueView;
    }

    private void initViews() {

        //Find the recycler
        rvQueue = (RecyclerView)queueView.findViewById(R.id.queue_recyclerView);
        //Make Dummy Data
        //mQueueList = Queue.createQueueListDummyData(15);

        //Setup adapter
        queueListRecycleAdapter = new QueueListRecycleAdapter(getContext(), this);
        decoration = new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL);
        rvQueue.addItemDecoration(decoration);
        rvQueue.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        rvQueue.setAdapter(queueListRecycleAdapter);

        //Setup Toolbar
        Toolbar toolbar = getActivity().findViewById(R.id.toolbar);
        toolbar.setTitle(TOOLBAR_TITLE);
        toolbar.setNavigationIcon(null);
    }

    //Setup the view model so that live data can auto refresh from the DB
    private void setupViewModel(){
        viewModel = ViewModelProviders.of(this).get(QueueViewModel.class);
        viewModel.getListOfForms().observe(this, new Observer<List<Forms>>() {
            @Override
            public void onChanged(@Nullable List<Forms> forms) {
                Log.d(TAG, "Updating list of forms from Livedata in QueueViewModel");
                queueListRecycleAdapter.setQueueData(forms);
            }
        });

    }

    @Override
    public void onItemClickListener(int itemId) {
        //Data to send
        Bundle bundle = new Bundle();
        //TODO Call to DB to get the Fields for The selected form
        bundle.putInt("TEST", 1);
        FormDetailFragment frag = new FormDetailFragment();
        frag.setArguments(bundle);
        //Change fragments
        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.fragment_container, frag);
        transaction.addToBackStack("fragmentDetail");
        transaction.commit();

    }
}
