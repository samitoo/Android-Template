package com.hemingwaywest.utiliserve;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.hemingwaywest.utiliserve.Models.Queue;
import com.hemingwaywest.utiliserve.Utilities.QueueListRecycleAdapter;

import java.util.ArrayList;

/**
 * Project: utiliserve
 * Created by HemingwayWest, LLC, Copyright (c) 2019. All Rights Reserved.
 * User: Samitoo
 * Date: 4/8/2019
 * url:  www.HemingwayWest.com
 * Last Modified: $file.lastModified
 * Notes:
 */
public class QueueFragment extends Fragment {

    View queueView;
    private RecyclerView rvQueue;
    private QueueListRecycleAdapter queueListRecycleAdapter;
    private DividerItemDecoration decoration;
    ArrayList<Queue> mQueueList;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //return super.onCreateView(inflater, container, savedInstanceState);

        queueView = inflater.inflate(R.layout.fragment_queue, container, false);
        //Find the recycler
        rvQueue = (RecyclerView)queueView.findViewById(R.id.queue_recyclerView);
        //Make Dummy Data
        mQueueList = Queue.createQueueListDummyData(15);

        queueListRecycleAdapter = new QueueListRecycleAdapter(getContext(), mQueueList);
        decoration = new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL);
        rvQueue.addItemDecoration(decoration);
        rvQueue.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        rvQueue.setAdapter(queueListRecycleAdapter);


        return queueView;
    }
}
