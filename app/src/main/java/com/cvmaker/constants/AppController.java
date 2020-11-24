package com.cvmaker.constants;

import android.app.Activity;
import android.app.Service;
import android.os.Build;
import android.text.TextUtils;

import androidx.multidex.MultiDexApplication;

import com.bumptech.glide.Glide;
import com.cvmaker.data.local.prefs.AppPreferencesHelper;
import com.cvmaker.di.component.DaggerAppComponent;
import com.cvmaker.utils.AppConstants;
import com.cvmaker.utils.DateUtils;
import com.facebook.stetho.Stetho;

import java.util.UUID;

import javax.inject.Inject;

import dagger.android.AndroidInjector;
import dagger.android.DispatchingAndroidInjector;
import dagger.android.HasActivityInjector;
import dagger.android.HasServiceInjector;

import static com.cvmaker.utils.LogUtils.LOGE;


public final class AppController extends MultiDexApplication implements HasActivityInjector, HasServiceInjector {

    @Inject
    DispatchingAndroidInjector<Activity> activityDispatchingAndroidInjector;
    @Inject
    DispatchingAndroidInjector<Service> dispatchingServiceInjector;
    @Inject
    AppPreferencesHelper appPreferencesHelper;

    private static final String TAG = AppController.class.getSimpleName();
    private static AppController controller;

    // Resource Provider
    private DateUtils dateUtils;

    public DateUtils getDateUtils() {
        if (dateUtils == null)
            dateUtils = new DateUtils(appPreferencesHelper);

        return dateUtils;
    }

    /**
     * Returns the consumer friendly device name
     */
    public static String getDeviceName() {
        String manufacturer = Build.MANUFACTURER;
        String model = Build.MODEL;
        if (model.startsWith(manufacturer)) {
            return capitalize(model);
        }
        return capitalize(manufacturer) + " " + model;
    }


    @Override
    public void onTrimMemory(int level) {
        super.onTrimMemory(level);
        Glide.get(this).trimMemory(level);
    }

    private static String capitalize(String str) {
        if (TextUtils.isEmpty(str)) {
            return str;
        }
        char[] arr = str.toCharArray();
        boolean capitalizeNext = true;

        StringBuilder phrase = new StringBuilder();
        for (char c : arr) {
            if (capitalizeNext && Character.isLetter(c)) {
                phrase.append(Character.toUpperCase(c));
                capitalizeNext = false;
                continue;
            } else if (Character.isWhitespace(c)) {
                capitalizeNext = true;
            }
            phrase.append(c);
        }

        return phrase.toString();
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Stetho.initializeWithDefaults(this);

        DaggerAppComponent.builder()
                .application(this)
                .build()
                .inject(this);

        controller = this;

        dateUtils = getDateUtils();
        String uniqueID = UUID.randomUUID().toString();
        LOGE("deviceId: " + uniqueID + " - deviceModel: " + getDeviceName());
        if (appPreferencesHelper.getDeviceId().equalsIgnoreCase(AppConstants.STRING_NULL_INDEX)) {
            appPreferencesHelper.setDeviceId(uniqueID);
        }
        appPreferencesHelper.setDeviceModel(getDeviceName());
    }

    public static AppController getInstance() {
        return controller;
    }

    @Override
    public AndroidInjector<Activity> activityInjector() {
        return activityDispatchingAndroidInjector;
    }

    @Override
    public AndroidInjector<Service> serviceInjector() {
        return dispatchingServiceInjector;
    }
}
