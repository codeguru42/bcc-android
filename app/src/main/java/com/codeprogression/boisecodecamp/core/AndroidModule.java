package com.codeprogression.boisecodecamp.core;

import android.content.Context;

import com.codeprogression.boisecodecamp.CodeCampApplication;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module(
        injects = {
                CodeCampApplication.class
        },
        library = true
)
public class AndroidModule {

    private DaggerApplication application;

    public AndroidModule(DaggerApplication application) {

        this.application = application;
    }

    @Provides
    @Singleton
    @ForApplication
    Context provideApplicationContext() {
        return application;
    }
}
