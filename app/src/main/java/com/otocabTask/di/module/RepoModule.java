package com.otocabTask.di.module;

/**
 * created by Aya mohamed on 2/9/2020.
 */

import com.otocabTask.data.local.prefs.AppPreferencesHelper;
import com.otocabTask.data.repo.AppRepo;
import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class RepoModule {

    @Provides
    @Singleton
    AppRepo provideAppRepo(AppPreferencesHelper appPreferencesHelper) {
        return new AppRepo(appPreferencesHelper);
    }

}
