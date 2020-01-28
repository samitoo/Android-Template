package com.hemingwaywest.utiliserve.Utilities;

import android.content.Context;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.hemingwaywest.utiliserve.R;
import com.hemingwaywest.utiliserve.database.FormField;
import com.hemingwaywest.utiliserve.database.Forms;

import org.w3c.dom.Text;

import java.lang.reflect.Array;
import java.util.List;

/**
 * Project: utiliserve
 * Created by HemingwayWest, LLC, Copyright (c) 2019. All Rights Reserved.
 * User: Samitoo
 * Date: 1/25/2020
 * url:  www.HemingwayWest.com
 * Last Modified: $file.lastModified
 * Notes:
 */

public class FormBlankRecycleAdapter extends RecyclerView.Adapter<FormBlankRecycleAdapter.MyViewHolder> {

    private Context mContext;
    private List<FormField> mFormFields;
    private boolean isUpdateForm = false;
    private ArrayAdapter<String> spinnerArrayAdapter;
    //final private ItemClickListener

    public FormBlankRecycleAdapter(Context context){
        mContext = context;
    }


    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        mContext = parent.getContext();
        int layoutIdForListItem = R.layout.form_field;
        LayoutInflater inflater = LayoutInflater.from(mContext);

        View v = inflater.inflate(layoutIdForListItem, parent, false);

        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        //Get data from the cursor
        FormField fieldEntry = mFormFields.get(position);
        String name = fieldEntry.getName();
        String type = fieldEntry.getFieldType();
        String value = fieldEntry.getValue();
        List <String> options = fieldEntry.getOptionsList();

        //Set the values and toggle visibility
        holder.formFieldText.setText(name);
        //TODO add types to Strings list
        if (type.equals("select")){
            holder.formFieldSpinner.setVisibility(View.VISIBLE);
            holder.formFieldValue.setVisibility(View.INVISIBLE);
            spinnerArrayAdapter = new ArrayAdapter<>(
                    mContext,
                    android.R.layout.simple_spinner_item,
                    options);
            holder.formFieldSpinner.setAdapter(spinnerArrayAdapter);
        }

        //Check if viewing as update (Queuefragment)
        if (isUpdateForm){
            holder.formFieldValue.setText(value);
            //Set spinner
            if(type.equals("select")){
                int spinnerPos = spinnerArrayAdapter.getPosition(value);
                holder.formFieldSpinner.setSelection(spinnerPos);
            }
        }


    }

    @Override
    public int getItemCount() {
        if(null == mFormFields) return 0;
        return mFormFields.size();
    }

    public void setFormData(List<FormField> fieldData){
        mFormFields = fieldData;
        notifyDataSetChanged();
    }

    public List<FormField> getFormData(){
        return mFormFields;
    }

    public void setIsUpdateForm(boolean isUpdate){
        this.isUpdateForm = isUpdate;
    }

    /**
     * Inner Class for views
     * Recycler view needs a viewholder class
     * This bind the data to the layout for the list item
     */
    class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        public final Spinner formFieldSpinner;
        public final TextView formFieldText;
        public final TextView formFieldValue;

        //Constructor
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            formFieldSpinner = itemView.findViewById(R.id.formfield_select);
            formFieldText = itemView.findViewById(R.id.formfield_text);
            formFieldValue = itemView.findViewById(R.id.formfield_value);
        }

        @Override
        public void onClick(View view) {
            notifyDataSetChanged();
        }
    }
}
