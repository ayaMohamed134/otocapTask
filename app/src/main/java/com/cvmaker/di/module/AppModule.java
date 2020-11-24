package com.cvmaker.di.module;

import android.app.Application;
import android.content.Context;

import androidx.lifecycle.ViewModel;

import com.cvmaker.constants.ViewModelFactory;
import com.cvmaker.data.local.prefs.AppPreferencesHelper;
import com.cvmaker.data.local.prefs.PreferencesHelper;
import com.cvmaker.di.PreferenceInfo;
import com.cvmaker.utils.AppConstants;
import com.cvmaker.utils.rx.AppSchedulerProvider;
import com.cvmaker.utils.rx.SchedulerProvider;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.Map;

import javax.inject.Provider;
import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * created by Aya mohamed on 8/2/2018.
 */

@Module
public class AppModule {

    @Provides
    @Singleton
    Context provideContext(Application application) {
        return application;
    }

    @Provides
    @PreferenceInfo
    String providePreferenceName() {
        return AppConstants.PREF_NAME;
    }

    @Provides
    @Singleton
    Gson provideGson() {
        return new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create();
    }

    @Provides
    SchedulerProvider provideSchedulerProvider() {
        return new AppSchedulerProvider();
    }

    @Provides
    @Singleton
    PreferencesHelper providePreferencesHelper(AppPreferencesHelper appPreferencesHelper) {
        return appPreferencesHelper;
    }

    @Provides
    @Singleton
    ViewModelFactory provideViewModelFactory(Map<Class<? extends ViewModel>, Provider<ViewModel>> viewModels) {
        return new ViewModelFactory(viewModels);
    }

}
