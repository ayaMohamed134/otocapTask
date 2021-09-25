package com.otocabTask.di.module;

import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.otocabTask.constants.ViewModelFactory;
import com.otocabTask.di.ViewModelKey;
import com.otocabTask.ui.base.BaseViewModel;
import com.otocabTask.ui.views.map.MapViewModel;
import com.otocabTask.ui.views.map.listOfLocations.ListOfLocationsViewModel;

import dagger.Binds;
import dagger.Module;
import dagger.Provides;
import dagger.multibindings.IntoMap;

@Module
public abstract class ViewModelModule {

    @Binds
    @IntoMap
    @ViewModelKey(ListOfLocationsViewModel.class)
    abstract ViewModel bindListOfLocationsViewModel(ListOfLocationsViewModel listOfLocationsViewModel);

    @Binds
    @IntoMap
    @ViewModelKey(MapViewModel.class)
    abstract ViewModel bindMapViewModel(MapViewModel mapViewModel);


    @Binds
    abstract ViewModelProvider.Factory bindViewModelFactory(ViewModelFactory factory);
}

