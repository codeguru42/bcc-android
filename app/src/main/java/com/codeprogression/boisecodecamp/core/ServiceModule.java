package com.codeprogression.boisecodecamp.core;

import android.content.Context;

import com.codeprogression.boisecodecamp.api.CodeCampApiWrapper;
import com.codeprogression.boisecodecamp.services.LanyrdIntentService;
import com.codeprogression.boisecodecamp.ui.HomeFragment;
import com.codeprogression.boisecodecamp.ui.MainActivity;
import com.codeprogression.boisecodecamp.ui.core.BaseActivity;
import com.codeprogression.boisecodecamp.ui.sessions.SessionDetailActivity;
import com.codeprogression.boisecodecamp.ui.sessions.SessionDetailFragment;
import com.codeprogression.boisecodecamp.ui.sessions.SessionListFragment;
import com.codeprogression.boisecodecamp.ui.speakers.SpeakerDetailActivity;
import com.codeprogression.boisecodecamp.ui.speakers.SpeakerDetailFragment;
import com.codeprogression.boisecodecamp.ui.speakers.SpeakerGridFragment;
import com.codeprogression.boisecodecamp.ui.speakers.SpeakerListFragment;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module(
        injects = {
                LanyrdIntentService.class
        },
        addsTo = AndroidModule.class,
        library = true
)
public class ServiceModule {

    private Context context;

    public ServiceModule(Context context) {
        this.context = context;

    }

    @Provides
    @Singleton
    @ForService
    Context provideServiceContext() {
        return context;
    }

}
