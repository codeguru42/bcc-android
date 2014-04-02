package com.codeprogression.boisecodecamp.api;

import com.codeprogression.boisecodecamp.api.models.SessionsResponse;
import com.codeprogression.boisecodecamp.api.models.SpeakerResponse;


import retrofit.Callback;
import retrofit.http.GET;

public interface LanyrdApi {

    @GET("/2014/bcc2014/schedule/b1200ddb9996154d.v1.json")
    void getSessions(Callback<SessionsResponse> callback);

    @GET("/2014/bcc2014/speakers/b1200ddb9996154d.v1.json")
    void getSpeakers(Callback<SpeakerResponse> callback);
}
