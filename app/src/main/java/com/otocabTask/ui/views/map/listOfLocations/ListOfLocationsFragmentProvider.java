package com.otocabTask.ui.views.map.listOfLocations;

import com.otocabTask.ui.views.map.MapFragment;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

/**
 * Created by Aya Mohamed on 11/18/2019.
 */
@Module
public abstract class ListOfLocationsFragmentProvider {

    @ContributesAndroidInjector
    abstract ListOfLocationsFragment provideListOfLocationsFragmentFactory();
}
