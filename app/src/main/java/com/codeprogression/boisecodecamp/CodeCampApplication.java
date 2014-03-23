package com.codeprogression.boisecodecamp;

import com.codeprogression.boisecodecamp.core.DaggerApplication;

public class CodeCampApplication extends DaggerApplication {

    private static CodeCampApplication instance;

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
    }

    public static CodeCampApplication getInstance(){
        return instance;
    }
}
