package com.codeprogression.boisecodecamp.core;

import android.app.Application;

import dagger.ObjectGraph;

public abstract class DaggerApplication extends Application {


    private ObjectGraph applicationGraph;

    @Override
    public void onCreate() {
        super.onCreate();
        applicationGraph = ObjectGraph.create(getModules());
    }

    public ObjectGraph getApplicationGraph() {
        return applicationGraph;
    }

    protected Object[] getModules() {
        return new Object[]{
                new AndroidModule(this)
        };
    }
}
