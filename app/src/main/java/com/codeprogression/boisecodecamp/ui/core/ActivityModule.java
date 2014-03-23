package com.codeprogression.boisecodecamp.ui.core;

import com.codeprogression.boisecodecamp.core.AndroidModule;
import com.codeprogression.boisecodecamp.ui.HomeFragment;
import com.codeprogression.boisecodecamp.ui.MainActivity;
import com.codeprogression.boisecodecamp.ui.SessionListFragment;
import com.codeprogression.boisecodecamp.ui.SpeakerListFragment;

import dagger.Module;

@Module(
        injects = {
                MainActivity.class,
                HomeFragment.class,
                SessionListFragment.class,
                SpeakerListFragment.class
        },
        addsTo = AndroidModule.class,
        library = true
)
public class ActivityModule {
}
