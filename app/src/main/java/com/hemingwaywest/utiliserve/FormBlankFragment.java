package com.hemingwaywest.utiliserve;

import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.hemingwaywest.utiliserve.Models.FormFieldViewModel;
import com.hemingwaywest.utiliserve.Utilities.AppExecutors;
import com.hemingwaywest.utiliserve.Utilities.FormBlankRecycleAdapter;
import com.hemingwaywest.utiliserve.database.AppDatabase;
import com.hemingwaywest.utiliserve.database.FormField;
import com.hemingwaywest.utiliserve.database.Forms;

import org.w3c.dom.Text;

import java.lang.reflect.Array;
import java.text.Normalizer;
import java.util.ArrayList;
import java.util.List;

import static java.lang.String.valueOf;

public class FormBlankFragment extends Fragment {

    private static final String TAG = FormFragment.class.getSimpleName();
    private static final String BUNDLE_EXTRA = "bundleExtra";
    private View blankFormView;
    private RecyclerView mRecyclerView;
    private FormBlankRecycleAdapter mRecycleAdapter;
    private DividerItemDecoration decoration;

    // Constant for default task id to be used when not in update mode
    private static final int DEFAULT_FORM_ID = -1;
    private int mFormID = DEFAULT_FORM_ID;
    //DB
    private AppDatabase mDb;
    private FormFieldViewModel viewModel;
    private Forms mForm;
    private List<FormField> mFormFields;
    private Button mSaveButton;

    private TextView mTestText;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //return super.onCreateView(inflater, container, savedInstanceState);
        blankFormView = inflater.inflate(R.layout.form_blank, container, false);
        //Connect to DB
        mDb = AppDatabase.getInstance(getContext());
        //Find Views
        initViews();
        checkBundle();
        setupViewModel();


        return blankFormView;
    }

    private void checkBundle() {
        Bundle bundle = this.getArguments();
        if(bundle != null) {
            Log.d(TAG, "Bundle pass successful " + bundle);
            mFormID = bundle.getInt("form_id");
        }
    }


    private void initViews() {
        mTestText = blankFormView.findViewById(R.id.formTestEditText);
        mRecyclerView = blankFormView.findViewById(R.id.formBlank_recyclerView);
        mSaveButton = blankFormView.findViewById(R.id.saveButton);
        mSaveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onSaveButtonClicked();
            }
        });
        //Setup Toolbar
        Toolbar toolbar = getActivity().findViewById(R.id.toolbar);
        toolbar.setTitle("Form Details");
        //toolbar.setNavigationIcon(R.drawable.ic_arrow_back_black_24dp);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //getActivity().onBackPressed();
                getFragmentManager().popBackStack();
            }
        });
        //Setup adapter
        mRecycleAdapter = new FormBlankRecycleAdapter(getContext());
        decoration = new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        mRecyclerView.setAdapter(mRecycleAdapter);
    }

    private void onSaveButtonClicked() {
        //get the fields as a shallow copy
        //TODO Create the NEW formfields to avoid copying with IDs
        mFormFields = new ArrayList<>(mRecycleAdapter.getFormData());
        final int parentID = mFormFields.get(0).getForm_id();
        final List<FormField> newFormFieldTemplate = getAllFormFields(parentID);
        //Save as new Form from template
        AppExecutors.getInstance().diskIO().execute(new Runnable() {
            @Override
            public void run() {
                //Get values from formFields Form parent
                Forms getForm = mDb.formsDao().getForm(parentID);
                Forms newForm = new Forms(getResources().getString(R.string.form_type_complete), getForm.getName(), getForm.getDescription());
                Long mFID = mDb.formsDao().insertForm(newForm);
                newForm.setId(mFID.intValue());
                newForm.setFormFieldList(newFormFieldTemplate);
                mDb.formsDao().insertFormWithFields(newForm);
                //mDb.formsDao().insertForm(newForm);
                //mDb.formsDao().insertFieldList(mFormFields);
            }
        });
        getFragmentManager().popBackStackImmediate();

    }

    private void setupViewModel(){
        viewModel = ViewModelProviders.of(this).get(FormFieldViewModel.class);
        viewModel.getListOfFields(mFormID).observe(this, new Observer<List<FormField>>() {
            @Override
            public void onChanged(List<FormField> formFields) {
                Log.d(TAG, "Updating list of forms from Livedata in FormViewModel");
                mRecycleAdapter.setFormData(formFields);
            }
        });
    }

    //Create new formFields to save to the DB as a clone of the template
    private List<FormField> getAllFormFields(int parentID){

        //Get local variable for adapter array
        List<FormField> mFormFields = new ArrayList<>(mRecycleAdapter.getFormData());
        final List<FormField> newFormField = new ArrayList<>();
        //Loop through and get the values
        for (int i=0; i < mRecycleAdapter.getItemCount(); i++){
            String fieldName="";
            String fieldValue = "";
            String fieldType = "";
            List<String> optionsList = new ArrayList<>();

            fieldName = mFormFields.get(i).getName();
            fieldType = mFormFields.get(i).getFieldType();
            if (mFormFields.get(i).getFieldType().equals("text")) {
                fieldValue = ((TextView) mRecyclerView.findViewHolderForAdapterPosition(i).
                        itemView.findViewById(R.id.formfield_value)).getText().toString();
            }
            else if (mFormFields.get(i).getFieldType().equals("select")){
                fieldValue = ((Spinner) mRecyclerView.findViewHolderForAdapterPosition(i).
                        itemView.findViewById(R.id.formfield_select)).getSelectedItem().toString();
            }
            if (mFormFields.get(i).getOptionsList() != null){
                optionsList.addAll(mFormFields.get(i).getOptionsList());
            }

            //Create new formField object with values
            FormField tempField = new FormField(parentID, fieldName, fieldValue, fieldType, optionsList);
            //Add to our List
            newFormField.add(tempField);
        }

        return newFormField;
    }


}
