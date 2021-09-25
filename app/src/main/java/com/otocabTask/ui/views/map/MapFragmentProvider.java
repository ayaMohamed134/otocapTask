package com.otocabTask.ui.views.map;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

/**
 * Created by Aya Mohamed on 11/18/2019.
 */
@Module
public abstract class MapFragmentProvider {

    @ContributesAndroidInjector
    abstract MapFragment provideMapFragmentFactory();
}
