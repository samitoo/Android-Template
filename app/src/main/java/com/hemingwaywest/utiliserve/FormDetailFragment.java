package com.hemingwaywest.utiliserve;

import android.app.Activity;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.TextView;
import androidx.appcompat.widget.Toolbar;

import com.hemingwaywest.utiliserve.Utilities.AppExecutors;
import com.hemingwaywest.utiliserve.Utilities.HideKeyboardUtil;
import com.hemingwaywest.utiliserve.database.AppDatabase;
import com.hemingwaywest.utiliserve.database.FormField;
import com.hemingwaywest.utiliserve.database.Forms;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.Executors;


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
    private List<FormField> mFormFields;
    private Forms formToUpdate;

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
            final int formID = bundle.getInt("form_id");
            mFormID = formID;
            AppExecutors.getInstance().diskIO().execute(new Runnable() {
                @Override
                public void run() {
                    formToUpdate = mDb.formsDao().getForm(formID);
                }
            });

            //Load the data on the form
            populateUI(formToUpdate);
        }

        Log.d(TAG, "Loading Detail Fragment");
        return formDetailView;
    }

    private void initViews(){
        HideKeyboardUtil.setHideKeyboardOnTouch(getContext(), formDetailView);


        mSaveButton = formDetailView.findViewById(R.id.saveButton);
        /*mSaveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onSaveButtonClicked();
            }
        }); */

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

    private void addTouchEventToNonEditText(View view){
        if (!(view instanceof EditText)){
            formDetailView.setOnTouchListener(new View.OnTouchListener(){
                public boolean onTouch(View v, MotionEvent event){
                    hideSoftKeyboard();
                    return false;
                }
            });
        }
    }

    private void hideSoftKeyboard(){
        InputMethodManager imm = (InputMethodManager)getActivity().getSystemService(Activity.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(getActivity().getCurrentFocus().getWindowToken(), 0);
    }

  /*  public void onSaveButtonClicked(){
        //capture data in new form object
        String title = mFormTitle.getText().toString();
        String description = mFormDescription.getText().toString();
        //TODO logic around form type?  Currently all are save, need update.
        String formType = getResources().getString(R.string.form_type_complete);

        //insert new item
        if(mFormID == DEFAULT_FORM_ID) {
            //create new Form object with Title and description
            final Forms form = new Forms(formType, title, description);
            //create form field objects associated with the new form
            final FormField[] fields = getList(form);
            //Convert object for DB
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
        }
        //Update item
        else if (mFormID != DEFAULT_FORM_ID){
            final FormField[] updateFields = getList(formToUpdate);
            final List<FormField> updateFieldsList = Arrays.asList(updateFields);
            formToUpdate.setFormFieldList(updateFieldsList);
            AppExecutors.getInstance().diskIO().execute(new Runnable() {
                @Override
                public void run() {
                    mDb.formsDao().insertFormWithFields(formToUpdate);
                }
            });
        }

        Log.d(TAG, "Save Pressed");
        getFragmentManager().popBackStackImmediate();

    }

    //Temp function catch all the values in the fields on this form
    /*private FormField[] getList(Forms form){
        return new FormField[]{
                new FormField(form.getId(), "hydrant", mHydrantNo.getText().toString()),
                new FormField(form.getId(), "address", mHydrantAdd.getText().toString()),
                new FormField(form.getId(), "station", mStationNo.getText().toString()),
                new FormField(form.getId(), "zip", mZipCode.getText().toString()),
                new FormField(form.getId(), "hysub", mHydrantSub.getText().toString())
        };
    }*/

    private void populateUI(Forms form){
        Log.d(TAG, "populateUI: Calling me!");
//        if (form==null){
//            return;
//        } else {
            //Test Values
            Log.d(TAG, "populateUI: calling my else!");
            mHydrantNo.setText("filled");
            mHydrantAdd.setText("filled");
            mStationNo.setText("filled");
            mZipCode.setText("filled");
            mHydrantSub.setText("filled");

            //Loop through views and update
        Executors.newSingleThreadExecutor().execute(new Runnable() {
            @Override
            public void run() {
                Log.d(TAG, "run: THREAD");
                mFormFields = mDb.formsDao().getFormFieldList(mFormID);
                Log.d(TAG, "populateUI: " + mFormFields);
                //All UI updates have to come from main thread or big fat error!
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        tempFillFormFields(mFormFields);
                    }
                });

            }
            });
        }

        private void tempFillFormFields(List<FormField> fields){
            if(fields != null) {
                for (int i = 0; i < fields.size(); i++) {
                    String name = fields.get(i).getName();
                    String value = fields.get(i).getValue();
                    Log.d(TAG, "run: name: " + name + " value: " + value);
                    switch (fields.get(i).getName()) {
                        case "hydrant":
                            mHydrantNo.setText(fields.get(i).getValue());
                            break;
                        case "address":
                            mHydrantAdd.setText(fields.get(i).getValue());
                            break;
                        case "station":
                            mStationNo.setText(fields.get(i).getValue());
                            break;
                        case "zip":
                            mZipCode.setText(fields.get(i).getValue());
                            break;
                        case "hysub":
                            mHydrantSub.setText(fields.get(i).getValue());
                            break;
                    }
                }
            }
        }


}
