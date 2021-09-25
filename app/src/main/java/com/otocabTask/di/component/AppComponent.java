package com.otocabTask.di.component;

import android.app.Application;

import com.otocabTask.constants.AppController;
import com.otocabTask.di.builder.ActivityBuilder;
import com.otocabTask.di.module.AppModule;
import com.otocabTask.di.module.RepoModule;
import com.otocabTask.di.module.ViewModelModule;

import javax.inject.Singleton;

import dagger.BindsInstance;
import dagger.Component;
import dagger.android.support.AndroidSupportInjectionModule;

/**
 * created by Aya mohamed on 8/2/2018.
 */

@Singleton
@Component(modules = {AndroidSupportInjectionModule.class,
        AppModule.class, RepoModule.class, ViewModelModule.class, ActivityBuilder.class})
public interface AppComponent {

    void inject(AppController app);

    @Component.Builder
    interface Builder {

        @BindsInstance
        Builder application(Application application);

        AppComponent build();
    }

}
