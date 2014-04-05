package com.codeprogression.boisecodecamp.services;

import android.app.Service;

import com.codeprogression.boisecodecamp.CodeCampApplication;
import com.codeprogression.boisecodecamp.core.ServiceModule;

import java.util.Arrays;
import java.util.List;

import dagger.ObjectGraph;

public abstract class ServiceBase extends Service {
    private ObjectGraph activityGraph;

    @Override
    public void onCreate() {
        super.onCreate();
        // Create the activity graph by .plus-ing our modules onto the application graph.
        CodeCampApplication application = (CodeCampApplication) getApplication();
        activityGraph = application.getApplicationGraph().plus(getModules().toArray());

        // Inject ourselves so subclasses will have dependencies fulfilled when this method returns.
        activityGraph.inject(this);
    }
    protected List<Object> getModules() {
        return Arrays.<Object>asList(new ServiceModule(this));
    }

    public void inject(Object object) {
        activityGraph.inject(object);
    }

}