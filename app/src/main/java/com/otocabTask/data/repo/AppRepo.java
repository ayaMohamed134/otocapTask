package com.otocabTask.data.repo;

import androidx.lifecycle.MutableLiveData;

import com.otocabTask.data.local.prefs.AppPreferencesHelper;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class AppRepo {

    private final static String TAG = AppRepo.class.getSimpleName();
    @Inject
    AppPreferencesHelper appPreferencesHelper;
    protected final MutableLiveData<Boolean> isLoadingLiveData;

    @Inject
    public AppRepo(AppPreferencesHelper appPreferencesHelper) {
        this.appPreferencesHelper = appPreferencesHelper;
        this.isLoadingLiveData = new MutableLiveData<>();
    }

    public boolean isLangSelected() {
        return appPreferencesHelper.getIsLangSelected();
    }

    public String getLang() {
        return appPreferencesHelper.getLang();
    }
}
