package com.codeprogression.boisecodecamp.services;

import android.content.Intent;
import android.os.IBinder;

import com.codeprogression.boisecodecamp.api.LanyrdApi;
import com.codeprogression.boisecodecamp.api.models.Session;
import com.codeprogression.boisecodecamp.api.models.SessionsResponse;
import com.codeprogression.boisecodecamp.api.models.Speaker;
import com.codeprogression.boisecodecamp.api.models.SpeakerResponse;
import com.codeprogression.boisecodecamp.events.SessionsChangedEvent;
import com.codeprogression.boisecodecamp.events.SpeakersReceivedEvent;
import com.squareup.otto.Bus;
import com.squareup.otto.Produce;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class LanyrdService extends ServiceBase {

    @Inject
    LanyrdApi api;

    @Inject
    Bus bus;

    private List<Session> sessions = new ArrayList<>();
    private List<Speaker> speakers = new ArrayList<>();

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        bus.register(this);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        bus.unregister(this);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        getSessions();
        getSpeakers();
        return START_STICKY;
    }

    private void getSessions() {
        api.getSessions(new Callback<SessionsResponse>() {
            ;

            @Override
            public void success(SessionsResponse sessionsResponse, Response response) {
                handleSessionsSuccess(sessionsResponse.getSessions());
            }

            @Override
            public void failure(RetrofitError error) {
            }
        });
    }

    private void getSpeakers() {
        api.getSpeakers(new Callback<SpeakerResponse>() {
            ;

            @Override
            public void success(SpeakerResponse speakerResponse, Response response) {
                handleSpeakersSuccess(speakerResponse.getSpeakers());
            }

            @Override
            public void failure(RetrofitError error) {
            }
        });
    }

    private void handleSpeakersSuccess(List<Speaker> speakers) {
        this.speakers = speakers;
        bus.post(new SpeakersReceivedEvent(speakers));
    }

    private void handleSessionsSuccess(List<Session> sessions) {
        this.sessions = sessions;
        bus.post(new SessionsChangedEvent(sessions));
    }

    @Produce
    public SessionsChangedEvent onSessionsReceived(){
        return new SessionsChangedEvent(sessions);
    }

    @Produce
    public SpeakersReceivedEvent onSpeakersReceived(){
        return new SpeakersReceivedEvent(speakers);
    }


}
