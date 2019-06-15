package com.hemingwaywest.utiliserve;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.TextView;
import android.support.v7.widget.Toolbar;


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

    private static String LAYOUT_TO_LOAD = "form_hydrant_inspection";

    private View formDetailView;

    //Loading and Errors
    private TextView mErrorMessageDisplay;
    private ProgressBar mLoadingBar;

    private EditText mFormNumber;
    private RadioButton mRadioCommercial;
    private RadioButton mRadioMultiFamily;
    private RadioButton mRadioResidential;
    private RadioButton mRadioRural;
    private EditText mFormDetails;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //return super.onCreateView(inflater, container, savedInstanceState);
        formDetailView= inflater.inflate(R.layout.form_hydrant_inspection, container, false);

        /*//Find all variables in XML
        mFormNumber = formDetailView.findViewById(R.id.et_form_number);
        mRadioCommercial = formDetailView.findViewById(R.id.r_commercial);
        mRadioMultiFamily = formDetailView.findViewById(R.id.r_mutliFamily);
        mRadioResidential = formDetailView.findViewById(R.id.r_mutliFamily);
        mRadioRural = formDetailView.findViewById(R.id.r_rural);
        mFormDetails = formDetailView.findViewById(R.id.et_form_details);*/


        Toolbar toolbar = getActivity().findViewById(R.id.toolbar);
        toolbar.setTitle("Form Details");
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back_black_24dp);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().onBackPressed();
            }
        });

        Log.d(TAG, "Loading Detail Fragment");
        return formDetailView;


    }
}
