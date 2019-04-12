package com.hemingwaywest.utiliserve.Utilities;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.widget.TextView;

import com.hemingwaywest.utiliserve.Models.FormListItem;
import com.hemingwaywest.utiliserve.R;

import org.w3c.dom.Text;

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
public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.MyViewHolder> {

    //Require a view holder, a context, and data
    Context mContext;
    List<FormListItem> mData;

    public RecyclerViewAdapter(Context mContext, List<FormListItem> mData) {
        this.mContext = mContext;
        this.mData = mData;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v;
        v = LayoutInflater.from(mContext).inflate(R.layout.item_form,viewGroup,false);
        MyViewHolder vHolder = new MyViewHolder(v);

        return vHolder;
    }

    @Override
    /**
     * Fetch Data from Model
     */
    public void onBindViewHolder(@NonNull MyViewHolder myViewHolder, int i) {
        myViewHolder.tv_formName.setText(mData.get(i).getFormTitle());
        myViewHolder.tv_formDetails.setText(mData.get(i).getFormDetails());

    }

    @Override
    public int getItemCount() {
        return mData.size();
    }


    /**
     * Recycler view needs a viewholder class
     * This bind the data to the layout for the list item
     */
    public static class MyViewHolder extends RecyclerView.ViewHolder{

        private TextView tv_formName;
        private TextView tv_formDetails;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            tv_formName = (TextView)itemView.findViewById(R.id.form_title);
            tv_formDetails = (TextView)itemView.findViewById(R.id.form_details);

        }
    }

}
