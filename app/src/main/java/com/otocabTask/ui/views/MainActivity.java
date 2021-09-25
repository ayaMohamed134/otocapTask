package com.otocabTask.ui.views;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.otocabTask.R;
import com.otocabTask.constants.ViewModelFactory;
import com.otocabTask.databinding.ActivityMainBinding;
import com.otocabTask.ui.base.BaseActivity;
import com.otocabTask.ui.base.BaseViewModel;

import javax.inject.Inject;

import dagger.android.AndroidInjector;
import dagger.android.DispatchingAndroidInjector;
import dagger.android.support.HasSupportFragmentInjector;
import pub.devrel.easypermissions.EasyPermissions;

/**
 * Aya Mohamed 12/11/2019
 */
public class MainActivity extends BaseActivity<ActivityMainBinding, BaseViewModel> implements HasSupportFragmentInjector {

    @Inject
    ViewModelFactory factory;
    private BaseViewModel mBaseViewModel;
    private ActivityMainBinding mBinding;
    int PERMISSION_ALL = 1;
    String[] PERMISSIONS = {
            Manifest.permission.ACCESS_FINE_LOCATION
    };
    @Inject
    DispatchingAndroidInjector<Fragment> fragmentDispatchingAndroidInjector;

    @Override
    public int getBindingVariable() {
        return 0;
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    public BaseViewModel getViewModel() {
        mBaseViewModel = new ViewModelProvider(this, factory).get(BaseViewModel.class);
        return mBaseViewModel;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = getViewDataBinding();
        setupUI();
        subscribeToLiveData();
    }

    private void subscribeToLiveData() {

    }

    private void setupUI() {
        if (!EasyPermissions.hasPermissions(this, PERMISSIONS)) {
            ActivityCompat.requestPermissions(this, PERMISSIONS, PERMISSION_ALL);
        } else {

        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (grantResults.length > 0 && (grantResults[0] == PackageManager.PERMISSION_GRANTED)) {
            Log.i("onRequestPermissions", "you can call the number by clicking on the button");
        }

        return;
    }

    @Override
    public AndroidInjector<Fragment> supportFragmentInjector() {
        return fragmentDispatchingAndroidInjector;
    }
}
