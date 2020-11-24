package com.cvmaker.di.component;

import android.app.Application;

import com.cvmaker.constants.AppController;
import com.cvmaker.di.builder.ActivityBuilder;
import com.cvmaker.di.module.AppModule;
import com.cvmaker.di.module.RepoModule;
import com.cvmaker.di.module.ViewModelModule;

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
