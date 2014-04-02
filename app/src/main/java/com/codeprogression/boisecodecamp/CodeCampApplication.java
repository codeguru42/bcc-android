package com.codeprogression.boisecodecamp;

import com.codeprogression.boisecodecamp.core.DaggerApplication;
import com.crashlytics.android.Crashlytics;

public class CodeCampApplication extends DaggerApplication {

    private static CodeCampApplication instance;

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;

//        if (!BuildConfig.DEBUG) {
//            Crashlytics.start(this);
//        }
    }

    public static CodeCampApplication getInstance(){
        return instance;
    }
}
