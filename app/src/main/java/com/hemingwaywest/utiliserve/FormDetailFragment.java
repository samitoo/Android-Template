package com.hemingwaywest.utiliserve;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.TextView;
import android.support.v7.widget.Toolbar;

import com.hemingwaywest.utiliserve.Utilities.AppExecutors;
import com.hemingwaywest.utiliserve.database.AppDatabase;
import com.hemingwaywest.utiliserve.database.FormField;
import com.hemingwaywest.utiliserve.database.Forms;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;



/**
 * Project: utiliserve-reboot
 * HemingwayWest, LLC. Copyright (c) 2019  All rights reserved.
 * User: Samitoo
 * Date: 4/28/2019
 * url: www.HemingwayWest.com
 * Notes:
 */

//TODO Form detail should be blank by default or updated based on db, modify to work like tasks
public class FormDetailFragment extends Fragment {

    private static final String TAG = FormDetailFragment.class.getSimpleName();
    private static final String BUNDLE_EXTRA = "bundleExtra";
    // Constant for default task id to be used when not in update mode
    private static final int DEFAULT_FORM_ID = -1;
    private int mFormID = DEFAULT_FORM_ID;

    private View formDetailView;
    private AppDatabase mDb;

    //Loading and Errors
    private TextView mErrorMessageDisplay, mFormTitle, mFormDescription;
    private ProgressBar mLoadingBar;

    private EditText mHydrantNo, mHydrantAdd, mStationNo, mZipCode, mHydrantSub;
    private RadioButton mRadioCommercial, mRadioMultiFamily, mRadioResidential, mRadioRural;
    private Button mSaveButton;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //return super.onCreateView(inflater, container, savedInstanceState);
        formDetailView= inflater.inflate(R.layout.form_hydrant_inspection, container, false);
        mDb = AppDatabase.getInstance(getContext());
        initViews();

        //Check bundle and fill detail page
        Bundle bundle = this.getArguments();
        if(bundle != null) {
            Log.d(TAG, "Bundle pass successful " + bundle);
            mSaveButton.setText("Update");
        }

        Log.d(TAG, "Loading Detail Fragment");
        return formDetailView;
    }

    private void initViews(){
        mSaveButton = formDetailView.findViewById(R.id.saveButton);
        mSaveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onSaveButtonClicked();
            }
        });

        //Should always have these 2
        mFormTitle = formDetailView.findViewById(R.id.tv_form_title);
        mFormDescription = formDetailView.findViewById(R.id.tv_form_description);
        //TODO Pass which form so I can get the title / description

        //TODO REMOVE ME; FAKING IT UNTIL I CAN FIND A WAY TO DYNAMICALLY DO VIEWS
        mHydrantNo = formDetailView.findViewById(R.id.et_hydrantNumber);
        mHydrantAdd = formDetailView.findViewById(R.id.et_hydrantAddress);
        mStationNo = formDetailView.findViewById(R.id.et_stationNo);
        mZipCode = formDetailView.findViewById(R.id.et_zipCode);
        mHydrantSub = formDetailView.findViewById(R.id.et_hydrantSub);
        //Radio
        mRadioCommercial = formDetailView.findViewById(R.id.r_commercial);
        mRadioMultiFamily = formDetailView.findViewById(R.id.r_mutliFamily);
        mRadioResidential = formDetailView.findViewById(R.id.r_residential);
        mRadioRural = formDetailView.findViewById(R.id.r_rural);

        Toolbar toolbar = getActivity().findViewById(R.id.toolbar);
        toolbar.setTitle("Form Details");
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back_black_24dp);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().onBackPressed();
            }
        });
    }

    public void onSaveButtonClicked(){
        //capture data in new form object
        String title = mFormTitle.getText().toString();
        String description = mFormDescription.getText().toString();
        //TODO logic around form type?  Currently all are save, need update.
        String formType = "filled";

        final Forms form = new Forms(formType, title, description);
        final FormField[] fields = getList(form);
        final List<FormField> fieldsList = Arrays.asList(fields);

        AppExecutors.getInstance().diskIO().execute(new Runnable() {
            @Override
            public void run() {
                //Add error checking with default
                //mDb.formFieldDao().insertAll(fields);

                Long mID = mDb.formsDao().insertForm(form);
                form.setFormFieldList(fieldsList);
                form.setId(mID.intValue());

                mDb.formsDao().insertFormWithFields(form);
                Log.d(TAG, "form id = " + form.getId() + " and returned Long = " + mID);
            }
        });
        Log.d(TAG, "Save Pressed");
        getFragmentManager().popBackStackImmediate();

    }

    //Temp function catch all the values in the fields on this form
    private FormField[] getList(Forms form){
        return new FormField[]{
                new FormField(form.getId(), "Hydrant Number", mHydrantNo.getText().toString()),
                new FormField(form.getId(), "Hydrant Number", mHydrantAdd.getText().toString()),
                new FormField(form.getId(), "Hydrant Number", mStationNo.getText().toString()),
                new FormField(form.getId(), "Hydrant Number", mZipCode.getText().toString()),
                new FormField(form.getId(), "Hydrant Number", mHydrantSub.getText().toString())
        };
    }

    private void populateUI(Forms form){
        if (form==null){
            return;
        }
        //Loop through views and update
    }


}
