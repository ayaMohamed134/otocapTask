package com.cvmaker.di.module;

import androidx.lifecycle.ViewModelProvider;

import com.cvmaker.constants.ViewModelFactory;

import dagger.Binds;
import dagger.Module;

@Module
public abstract class ViewModelModule {

    /*@Binds
    @IntoMap
    @ViewModelKey(ScannerViewModel.class)
    abstract ViewModel bindProfileViewModel(ScannerViewModel scannerViewModel);*/

    @Binds
    abstract ViewModelProvider.Factory bindViewModelFactory(ViewModelFactory factory);
}

