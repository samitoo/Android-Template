package com.hemingwaywest.utiliserve;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.hemingwaywest.utiliserve.Utilities.AppExecutors;
import com.hemingwaywest.utiliserve.database.AppDatabase;
import com.hemingwaywest.utiliserve.database.FormField;
import com.hemingwaywest.utiliserve.database.Forms;

import java.util.List;

import static java.lang.String.valueOf;

public class FormBlankFragment extends Fragment {

    private static final String TAG = FormFragment.class.getSimpleName();
    private static final String BUNDLE_EXTRA = "bundleExtra";
    private View blankFormView;
    // Constant for default task id to be used when not in update mode
    private static final int DEFAULT_FORM_ID = -1;
    private int mFormID = DEFAULT_FORM_ID;
    private AppDatabase mDb;
    private Forms mForm;
    private List<FormField> mFormFields;

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

        mTestText.setText("log");

        //Check bundle and fill detail page
        Bundle bundle = this.getArguments();
        if(bundle != null) {
            Log.d(TAG, "Bundle pass successful " + bundle);
            mTestText.setText("check");
            //mSaveButton.setText("Update");
            final int formID = bundle.getInt("form_id");
            mFormID = formID;
            AppExecutors.getInstance().diskIO().execute(new Runnable() {
                @Override
                public void run() {
                    mForm = mDb.formsDao().getForm(formID);
                    mFormFields = mDb.formsDao().getFormFieldList(formID);
                    Log.d(TAG, "Number of Formfields " + mFormFields.size());
                }
            });
        }

        return blankFormView;
    }

    private void initViews() {
        mTestText = blankFormView.findViewById(R.id.formTestEditText);
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
    }

}
