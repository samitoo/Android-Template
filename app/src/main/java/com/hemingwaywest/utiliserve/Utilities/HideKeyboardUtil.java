package com.hemingwaywest.utiliserve.Utilities;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.view.ViewGroup;

public class HideKeyboardUtil {

    private View view;
    private Context context;
    private static final String TAG = HideKeyboardUtil.class.getSimpleName();

    public HideKeyboardUtil(View v, Context c){
        view = v;
        context = c;
    }

    public static void setHideKeyboardOnTouch(final Context context, final View view){
        try{
            if (!(view instanceof EditText)){
                view.setOnTouchListener(new View.OnTouchListener(){
                    public boolean onTouch(View v, MotionEvent event){
                        hideSoftKeyboard(context, view);
                        return false;
                    }
                });
            }
            //If a layout container, iterate over children and seed recursion.
            if (view instanceof ViewGroup) {

                for (int i = 0; i < ((ViewGroup) view).getChildCount(); i++) {

                    View innerView = ((ViewGroup) view).getChildAt(i);

                    setHideKeyboardOnTouch(context, innerView);
                }
            }

        }
        catch (Exception e){
            e.printStackTrace();
        }
    }

    private static void hideSoftKeyboard(Context context, View v){
        InputMethodManager imm = (InputMethodManager)context.getSystemService(Activity.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
    }

}
