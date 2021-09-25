package com.otocabTask.utils;

import android.content.Context;
import android.text.TextUtils;
import android.util.Patterns;
import android.widget.EditText;

import com.otocabTask.R;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;


/**
 * Created by Aya Mohamed on 11/14/2019.
 */
public final class ValidationUtils {

    /**
     * @param context
     * @param phone
     * @param tlPhone
     * @return
     */
    public static boolean phoneValidation(Context context, EditText phone, TextInputLayout tlPhone) {
        if (TextUtils.isEmpty(phone.getText().toString())) {
            tlPhone.setError(context.getResources().getString(R.string.empty_phone));
            return false;
        }
        /*if (phone.getText().toString().length() < 13) {
            phone.setError(mResourceProvider.getString(R.string.invalid_phone));
            return false;
        }*/
        return true;
    }

    /**
     * @param context
     * @param pwd
     * @param tlPwd
     * @return
     */
    public static boolean pwdValidation(Context context, TextInputEditText pwd, TextInputLayout tlPwd) {
        if (TextUtils.isEmpty(pwd.getText().toString())) {
            tlPwd.setError(context.getResources().getString(R.string.empty_pwd));
            return false;
        }
        /*if (!pwd.getText().toString().matches(AppConstants.PWD_PATTERN)) {
            tlPwd.setError(context.getResources().getString(R.string.password_lenght));
            return false;
        }*/
        return true;
    }

    /**
     * @param context
     * @param email
     * @return
     */
    public static boolean emailValidation(Context context, TextInputEditText email) {
        if (TextUtils.isEmpty(email.getText().toString())) {
            email.setError(context.getResources().getString(R.string.empty_email));
            return false;
        }
        if (!Patterns.EMAIL_ADDRESS.matcher(email.getText().toString()).matches()) {
            email.setError(context.getResources().getString(R.string.invalid_email));
            return false;
        }
        return true;
    }


    public static boolean emailValidation(Context context, EditText email) {
        if (TextUtils.isEmpty(email.getText().toString())) {
            email.setError(context.getResources().getString(R.string.empty_email));
            return false;
        }
        if (!Patterns.EMAIL_ADDRESS.matcher(email.getText().toString()).matches()) {
            email.setError(context.getResources().getString(R.string.invalid_email));
            return false;
        }
        return true;
    }

    /**
     * @param context
     * @param text
     * @param tltext
     * @return
     */
    public static boolean emptyValidation(Context context, TextInputEditText text, TextInputLayout tltext) {
        if (TextUtils.isEmpty(text.getText().toString())) {
            tltext.setError(context.getResources().getString(R.string.empty_field));
            return false;
        }
        return true;
    }

    public static boolean confirmPassword(Context context, TextInputEditText pwd, TextInputEditText confirmPwd) {
        if (pwd.getText().toString().equals(confirmPwd.getText().toString())) {
            return true;
        } else {
            confirmPwd.setError(context.getResources().getString(R.string.password_confirm));
            return false;
        }
    }
}
