package com.hemingwaywest.utiliserve.Utilities;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.TextView;

import com.hemingwaywest.utiliserve.R;
import com.hemingwaywest.utiliserve.database.FormListEntry;
import com.hemingwaywest.utiliserve.database.Forms;

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
public class FormListRecycleAdapter extends RecyclerView.Adapter<FormListRecycleAdapter.MyViewHolder> {

    private Context mContext;
    private List<Forms> mFormEntries;
    final private ItemClickListener mItemClickListener;


    /*public FormListRecycleAdapter(Context mContext, List<FormListEntry> mData) {
        this.mContext = mContext;
        this.mFormData = mData;
    }*/
    //Default constructor for now, using set method for now
    public FormListRecycleAdapter(Context context, ItemClickListener listener ){
        mContext = context;
        mItemClickListener = listener;
    }

    /**
     * This gets called when each new viewholder is created.
     *
     * @param viewGroup The viewgroup that these viewholders are contained within
     * @param viewType if the recycler view has more than one type of item, you use this viewType Integer
     *          to provide a different layout.
     * @return A new Recycler view Adapter that holds the view for each list item
     */
    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        Context context = viewGroup.getContext();
        int layoutIdForListItem = R.layout.item_form;
        LayoutInflater inflater = LayoutInflater.from(context);
        boolean shouldAttachToParentImmediately = false;

        View v = inflater.inflate(layoutIdForListItem, viewGroup, shouldAttachToParentImmediately);

        return new MyViewHolder(v);
    }

    @Override
    /**
     * Fetch Data from Model
     */
    public void onBindViewHolder(MyViewHolder myViewHolder, int position) {

        //Get the Data from a cursor
        Forms formEntry = mFormEntries.get(position);
        //String title = formEntry.getFormTitle();
        //String description = formEntry.getFormDetails();

        //Set the values
       // myViewHolder.tv_formName.setText(title);
        //myViewHolder.tv_formDetails.setText(description);


    }

    @Override
    public int getItemCount() {
        if (null == mFormEntries) return 0;
        return mFormEntries.size();
    }

    public void setFormData (List<Forms> formData){
        mFormEntries = formData;
        notifyDataSetChanged();
    }

    public List<Forms> getmFormEntries(){
        return mFormEntries;
    }


    public interface ItemClickListener {
        void onItemClickListener(int itemId);
    }

    /**
     * Inner Class for views
     * Recycler view needs a viewholder class
     * This bind the data to the layout for the list item
     */
    class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        public final TextView tv_formName;
        public final TextView tv_formDetails;

        //Constructor
        public MyViewHolder(View itemView) {
            super(itemView);

            tv_formName = (TextView)itemView.findViewById(R.id.form_title);
            tv_formDetails = (TextView)itemView.findViewById(R.id.form_details);
            itemView.setOnClickListener(this);

        }

        @Override
        public void onClick(View v) {
            int elementId = mFormEntries.get(getAdapterPosition()).getId();
            mItemClickListener.onItemClickListener(elementId);
        }
    }


}
