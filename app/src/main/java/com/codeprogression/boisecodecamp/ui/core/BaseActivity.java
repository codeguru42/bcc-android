package com.codeprogression.boisecodecamp.ui.core;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;

import com.codeprogression.boisecodecamp.CodeCampApplication;

import dagger.ObjectGraph;

public class BaseActivity extends ActionBarActivity {

    private ObjectGraph activityGraph;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityGraph = CodeCampApplication.getInstance().getApplicationGraph().plus(getModules());
        activityGraph.inject(this);
    }

    private Object[] getModules() {
        return new Object[]{
            ActivityModule.class
        };
    }

    public void inject(Object obj) {
        activityGraph.inject(obj);
    }
}
