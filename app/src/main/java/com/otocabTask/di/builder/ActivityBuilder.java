package com.otocabTask.di.builder;

import com.otocabTask.ui.views.MainActivity;
import com.otocabTask.ui.views.map.MapFragmentProvider;
import com.otocabTask.ui.views.map.listOfLocations.ListOfLocationsFragmentProvider;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

/**
 * created by Aya mohamed on 8/2/2018.
 * // TODO add All Activities and fragments here
 */

@Module
public abstract class ActivityBuilder {

    @ContributesAndroidInjector(modules = {MapFragmentProvider.class, ListOfLocationsFragmentProvider.class})
    abstract MainActivity bindMainActivity();

}
