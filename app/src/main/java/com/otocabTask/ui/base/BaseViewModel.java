package com.otocabTask.ui.base;

import androidx.databinding.ObservableBoolean;
import androidx.lifecycle.AndroidViewModel;

import com.otocabTask.constants.AppController;
import com.otocabTask.data.repo.AppRepo;

import java.lang.ref.WeakReference;

/**
 * Created by aya mohamed on 08/02/18.
 */

public abstract class BaseViewModel<N> extends AndroidViewModel {

    private final AppRepo appRepo;

    private WeakReference<N> mNavigator;

    private final ObservableBoolean mIsLoading = new ObservableBoolean(false);

    public BaseViewModel(AppRepo appRepo) {
        super(AppController.getInstance());
        this.appRepo = appRepo;
    }

    @Override
    protected void onCleared() {
        super.onCleared();
    }

    public N getNavigator(){
        return mNavigator.get();
    }

    public void setNavigator(N navigator) {
        this.mNavigator = new WeakReference<>(navigator);
    }

    public AppRepo getAppRepo() {
        return appRepo;
    }

    public ObservableBoolean getIsLoading() {
        return mIsLoading;
    }

    public void setIsLoading(boolean isLoading) {
        mIsLoading.set(isLoading);
    }
}
