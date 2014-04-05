package com.codeprogression.boisecodecamp.services;

import android.content.Intent;
import android.os.Bundle;
import android.os.IBinder;

import com.codeprogression.boisecodecamp.api.LanyrdApi;
import com.codeprogression.boisecodecamp.api.models.Session;
import com.codeprogression.boisecodecamp.api.models.SessionsResponse;
import com.codeprogression.boisecodecamp.events.SessionsReceivedEvent;
import com.squareup.otto.Bus;
import com.squareup.otto.Produce;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import dagger.Provides;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class LanyrdService extends ServiceBase {

    @Inject
    LanyrdApi api;

    @Inject
    private Bus bus;

    public List<Session> sessions;

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
        return START_STICKY;
    }

    private void getSessions() {
        api.getSessions(new Callback<SessionsResponse>() {;

            @Override
            public void success(SessionsResponse sessionsResponse, Response response) {
                handleSuccess(sessionsResponse.getSessions());
            }

            @Override
            public void failure(RetrofitError error) {
            }
        });
    }

    private void handleSuccess(List<Session> sessions) {
        this.sessions = sessions;
        bus.post(new SessionsReceivedEvent(sessions));
    }

    @Produce
    public SessionsReceivedEvent onSessionsReceived(){
        return new SessionsReceivedEvent(sessions);
    }


}
