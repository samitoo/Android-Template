package com.hemingwaywest.utiliserve.Utilities;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.hemingwaywest.utiliserve.Models.Queue;
import com.hemingwaywest.utiliserve.R;

import java.util.List;

/**
 * Project: utiliserve-reboot
 * HemingwayWest, LLC. Copyright (c) 2019  All rights reserved.
 * User: Samitoo
 * Date: 4/28/2019
 * url: www.HemingwayWest.com
 * Notes:
 */
public class QueueListRecycleAdapter extends RecyclerView.Adapter<QueueListRecycleAdapter.MyViewHolder> {

    private Context mContext;
    private List<Queue> mQueue;

    public QueueListRecycleAdapter(Context context, List<Queue> queueList){
        mQueue = queueList;
        mContext = context;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        Context context = viewGroup.getContext();
        int layoutForListItem = R.layout.item_queue;
        LayoutInflater inflater = LayoutInflater.from(context);

        View v = inflater.inflate(layoutForListItem, viewGroup, false);

        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder myViewHolder, int position) {
        //Get position
        Queue queue = mQueue.get(position);
        //Get Values
        String title = queue.getmName();
        String status = queue.getmStatus();
        //Set Values
        myViewHolder.tv_formTitle.setText(title);
        myViewHolder.tv_formStatus.setText(status);


    }

    @Override
    public int getItemCount() {
        return mQueue.size();
    }

    //TODO Implement View.OnClickListener
    public class MyViewHolder extends RecyclerView.ViewHolder {

        public final TextView tv_formTitle;
        public final TextView tv_formStatus;

        public MyViewHolder( View itemView) {
            super(itemView);

            tv_formTitle = (TextView)itemView.findViewById(R.id.form_title);
            tv_formStatus = (TextView)itemView.findViewById(R.id.form_status);

        }
    }
}
