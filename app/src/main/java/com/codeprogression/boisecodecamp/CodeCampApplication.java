package com.codeprogression.boisecodecamp;

import android.content.Intent;

import com.codeprogression.boisecodecamp.core.DaggerApplication;
import com.codeprogression.boisecodecamp.services.LanyrdService;
import com.crashlytics.android.Crashlytics;

public class CodeCampApplication extends DaggerApplication {

    private static CodeCampApplication instance;

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;

        Intent intent = new Intent(this, LanyrdService.class);
        startService(intent);
    }

    @Override
    public void onTerminate() {
        super.onTerminate();
        Intent intent = new Intent(this, LanyrdService.class);
        stopService(intent);
    }

    public static CodeCampApplication getInstance(){
        return instance;
    }
}
