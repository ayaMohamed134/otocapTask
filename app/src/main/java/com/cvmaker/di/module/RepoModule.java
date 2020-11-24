package com.cvmaker.di.module;

/**
 * created by Aya mohamed on 2/9/2020.
 */

import com.cvmaker.data.local.prefs.AppPreferencesHelper;
import com.cvmaker.data.repo.AppRepo;
import com.cvmaker.utils.rx.SchedulerProvider;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class RepoModule {

    @Provides
    @Singleton
    AppRepo provideAppRepo(AppPreferencesHelper appPreferencesHelper,
                            SchedulerProvider mSchedulerProvider) {
        return new AppRepo(appPreferencesHelper, mSchedulerProvider);
    }

}
