package com.otocabTask.ui.base;

import android.annotation.TargetApi;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.os.Build;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import androidx.annotation.LayoutRes;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;

import com.otocabTask.utils.AppConstants;
import com.otocabTask.utils.NetworkUtils;

import java.util.Locale;

import dagger.android.AndroidInjection;

import static com.otocabTask.utils.LogUtils.LOGE;


/**
 * Created by aya mohamed on 08/02/18.
 */

public abstract class BaseActivity<T extends ViewDataBinding, V extends BaseViewModel> extends AppCompatActivity
        implements BaseFragment.Callback {

    // TODO
    // this can probably depend on isLoading variable of BaseViewModel,
    // since its going to be common for all the activities
    private T mViewDataBinding;
    private V mViewModel;
    private ProgressDialog pDialog;

    /**
     * Override for set binding variable
     *
     * @return variable id
     */
    public abstract int getBindingVariable();

    /**
     * @return layout resource id
     */
    public abstract
    @LayoutRes
    int getLayoutId();

    /**
     * Override for set view model
     *
     * @return view model instance
     */
    public abstract V getViewModel();

    @Override
    public void onFragmentAttached() {

    }

    @Override
    public void onFragmentDetached(String tag) {

    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        performDependencyInjection();
        super.onCreate(savedInstanceState);
        performDataBinding();
        changeLang();
    }

    public void changeLang() {
        if (!getViewModel().getAppRepo().isLangSelected()) {
            Resources res = getResources();
            // Change locale settings in the app.
            DisplayMetrics dm = res.getDisplayMetrics();
            android.content.res.Configuration conf = res.getConfiguration();
            conf.setLocale(new Locale(AppConstants.AR)); // API 17+ only.
            Locale.setDefault(new Locale(AppConstants.AR));
            // Use conf.locale = new Locale(...) if targeting lower versions
            res.updateConfiguration(conf, dm);
            getBaseContext().getResources().updateConfiguration(conf,
                    getBaseContext().getResources().getDisplayMetrics());
            LOGE("Local : " + conf.locale);
        } else {
            String lang = getViewModel().getAppRepo().getLang();
            Resources res = getResources();
            // Change locale settings in the app.
            DisplayMetrics dm = res.getDisplayMetrics();
            android.content.res.Configuration conf = res.getConfiguration();
            conf.setLocale(new Locale(lang)); // API 17+ only.
            Locale.setDefault(new Locale(lang));
            // Use conf.locale = new Locale(...) if targeting lower versions
            res.updateConfiguration(conf, dm);
            getBaseContext().getResources().updateConfiguration(conf,
                    getBaseContext().getResources().getDisplayMetrics());
            LOGE("Local : " + conf.locale);
        }
    }

    public T getViewDataBinding() {
        return mViewDataBinding;
    }

    public void performDependencyInjection() {
        AndroidInjection.inject(this);
    }

    @TargetApi(Build.VERSION_CODES.M)
    public boolean hasPermission(String permission) {
        return Build.VERSION.SDK_INT < Build.VERSION_CODES.M ||
                checkSelfPermission(permission) == PackageManager.PERMISSION_GRANTED;
    }

    public void hideKeyboard() {
        View view = getCurrentFocus();
        if (view != null) {
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            if (imm != null) {
                imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
            }
        }
    }

    public void hideLoading() {

    }

    public boolean isNetworkConnected() {
        return NetworkUtils.isNetworkConnected(getApplicationContext());
    }

    public void openActivityOnTokenExpire() {

    }


    @TargetApi(Build.VERSION_CODES.M)
    public void requestPermissionsSafely(String[] permissions, int requestCode) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            requestPermissions(permissions, requestCode);
        }
    }

    public void showAlertDialog(String message, String message_type) {
    }

    private void performDataBinding() {
        mViewDataBinding = DataBindingUtil.setContentView(this, getLayoutId());
        mViewModel = mViewModel == null ? getViewModel() : mViewModel;
        mViewDataBinding.setVariable(getBindingVariable(), mViewModel);
        mViewDataBinding.executePendingBindings();
    }
}
