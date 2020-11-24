package com.cvmaker.data.repo;

import androidx.lifecycle.MutableLiveData;

import com.cvmaker.data.local.prefs.AppPreferencesHelper;
import com.cvmaker.utils.rx.SchedulerProvider;

import javax.inject.Inject;
import javax.inject.Singleton;

import io.reactivex.disposables.CompositeDisposable;


@Singleton
public class AppRepo {

    private final static String TAG = AppRepo.class.getSimpleName();
    @Inject
    AppPreferencesHelper appPreferencesHelper;
    protected final MutableLiveData<Boolean> isLoadingLiveData;
    private final CompositeDisposable mCompositeDisposable;
    SchedulerProvider mSchedulerProvider;

    @Inject
    public AppRepo(AppPreferencesHelper appPreferencesHelper,
                   SchedulerProvider mSchedulerProvider) {
        this.appPreferencesHelper = appPreferencesHelper;
        this.mSchedulerProvider = mSchedulerProvider;
        this.isLoadingLiveData = new MutableLiveData<>();
        this.mCompositeDisposable = new CompositeDisposable();
    }

    public CompositeDisposable getCompositeDisposable() {
        return mCompositeDisposable;
    }

    public SchedulerProvider getSchedulerProvider() {
        return mSchedulerProvider;
    }

    public boolean isLangSelected() {
        return appPreferencesHelper.getIsLangSelected();
    }

    public String getLang() {
        return appPreferencesHelper.getLang();
    }
}
