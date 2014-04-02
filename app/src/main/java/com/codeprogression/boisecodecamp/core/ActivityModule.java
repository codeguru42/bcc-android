package com.codeprogression.boisecodecamp.core;

import android.content.Context;

import com.codeprogression.boisecodecamp.api.CodeCampApiWrapper;
import com.codeprogression.boisecodecamp.ui.sessions.BetterSessionListFragment;
import com.codeprogression.boisecodecamp.ui.HomeFragment;
import com.codeprogression.boisecodecamp.ui.MainActivity;
import com.codeprogression.boisecodecamp.ui.sessions.SessionDetailActivity;
import com.codeprogression.boisecodecamp.ui.sessions.SessionDetailFragment;
import com.codeprogression.boisecodecamp.ui.sessions.SessionListFragment;
import com.codeprogression.boisecodecamp.ui.sessions.TypicalSessionListFragment;
import com.codeprogression.boisecodecamp.ui.speakers.SpeakerDetailActivity;
import com.codeprogression.boisecodecamp.ui.speakers.SpeakerDetailFragment;
import com.codeprogression.boisecodecamp.ui.speakers.SpeakerGridFragment;
import com.codeprogression.boisecodecamp.ui.speakers.SpeakerListFragment;
import com.codeprogression.boisecodecamp.ui.core.BaseActivity;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module(
        injects = {
                MainActivity.class,

                HomeFragment.class,

                SessionListFragment.class,
                SessionDetailActivity.class,
                SessionDetailFragment.class,

                SpeakerGridFragment.class,
                SpeakerListFragment.class,
                SpeakerDetailActivity.class,
                SpeakerDetailFragment.class,

                BetterSessionListFragment.class,
                TypicalSessionListFragment.class
        },
        addsTo = AndroidModule.class,
        library = true
)
public class ActivityModule {

    private BaseActivity activity;

    public ActivityModule(BaseActivity activity) {

        this.activity = activity;
    }

    @Provides
    @Singleton
    @ForActivity
    Context provideApplicationContext() {
        return activity;
    }

    @Provides @Singleton @ForActivity
    CodeCampApiWrapper provideApiWrapper(CodeCampApiWrapper api){
        return api;
    }
}
