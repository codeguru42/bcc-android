package com.codeprogression.boisecodecamp.services;

import android.content.Intent;
import android.os.Bundle;

import com.codeprogression.boisecodecamp.api.LanyrdApi;
import com.codeprogression.boisecodecamp.api.models.Session;
import com.codeprogression.boisecodecamp.api.models.SessionsResponse;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

public class LanyrdIntentService extends IntentServiceBase {

    public static String SESSION_REQUEST= "LanyrdIntentService.SESSION_REQUEST";
    public static String SESSION_RESPONSE = "LanyrdIntentService.SESSION_RESPONSE";
    public static final String SESSION_LIST = "LanyrdIntentService.SESSION_LIST";

    @Inject
    LanyrdApi api;

    public LanyrdIntentService() {
        super("Lanyrd Intent Service");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        String action = intent.getAction();
        if (action == null){
            return;
        }
        if (action.equals(SESSION_REQUEST)){

            SessionsResponse sessionsResponse = api.getSessions();
            List<Session> sessions = sessionsResponse.getSessions();

            Bundle bundle = new Bundle();

            Intent sessionIntent = new Intent(SESSION_RESPONSE);
            bundle.putParcelableArrayList(SESSION_LIST, new ArrayList<>(sessions));
            sessionIntent.putExtras(bundle);

            LanyrdIntentService.this.sendBroadcast(sessionIntent);

        }
    }

}
