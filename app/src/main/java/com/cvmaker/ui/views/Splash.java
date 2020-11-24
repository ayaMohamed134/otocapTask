package com.cvmaker.ui.views;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.lifecycle.ViewModelProvider;

import com.cvmaker.R;
import com.cvmaker.constants.ViewModelFactory;
import com.cvmaker.databinding.ActivitySplashBinding;
import com.cvmaker.ui.base.BaseActivity;
import com.cvmaker.ui.base.BaseViewModel;

import javax.inject.Inject;

import pub.devrel.easypermissions.EasyPermissions;

/**
 * Aya Mohamed 12/11/2019
 */
public class Splash extends BaseActivity<ActivitySplashBinding, BaseViewModel> {

    @Inject
    ViewModelFactory factory;
    private BaseViewModel mBaseViewModel;
    private ActivitySplashBinding mBinding;
    public static int SPLASH_DISPLAY_LENGTH = 2000;
    int PERMISSION_ALL = 1;
    String[] PERMISSIONS = {
            Manifest.permission.CAMERA
    };

    @Override
    public int getBindingVariable() {
        return 0;
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_splash;
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
            handler();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (grantResults.length > 0 && (grantResults[0] == PackageManager.PERMISSION_GRANTED)) {
            Log.i("onRequestPermissions", "you can call the number by clicking on the button");
        }
        handler();
        return;
    }

    private void handler() {
        new Handler(getMainLooper()).postDelayed(() -> {
           // start app
        }, SPLASH_DISPLAY_LENGTH);
    }


}
