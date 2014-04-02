package com.codeprogression.boisecodecamp.ui.core;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;

import com.codeprogression.boisecodecamp.CodeCampApplication;
import com.codeprogression.boisecodecamp.core.ActivityModule;
import com.codeprogression.boisecodecamp.ui.helpers.MenuHelper;
import com.f2prateek.dart.Dart;

import butterknife.ButterKnife;
import dagger.ObjectGraph;

public class BaseActivity extends ActionBarActivity {

    private ObjectGraph activityGraph;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityGraph = CodeCampApplication.getInstance().getApplicationGraph().plus(getModules());
        activityGraph.inject(this);

        Dart.inject(this);

    }

    @Override
    public void setContentView(int layoutResID) {
        super.setContentView(layoutResID);
        ButterKnife.inject(this);
    }

    private Object[] getModules() {
        return new Object[]{
            new ActivityModule(this)
        };
    }

    public void inject(Object obj) {
        activityGraph.inject(obj);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuHelper.addSettingsMenu(getMenuInflater(), menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return MenuHelper.onBackPressed(this, item)
                || MenuHelper.onSettingsItemSelected(this, item)
                || super.onOptionsItemSelected(item);
    }

}
