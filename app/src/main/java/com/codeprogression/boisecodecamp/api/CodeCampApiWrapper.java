package com.codeprogression.boisecodecamp.api;

import com.codeprogression.boisecodecamp.api.models.Session;
import com.codeprogression.boisecodecamp.api.models.SessionsResponse;
import com.codeprogression.boisecodecamp.api.models.Speaker;
import com.codeprogression.boisecodecamp.api.models.SpeakerResponse;

import java.util.List;

import javax.inject.Inject;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class CodeCampApiWrapper {

    LanyrdApi api;

    @Inject
    public CodeCampApiWrapper(LanyrdApi api) {
        this.api = api;
    }

    public void getSessions(final SessionCallback callback) {

        // Get cached copy if not stale

        api.getSessions(new Callback<SessionsResponse>(){
            @Override
            public void success(SessionsResponse sessionsResponse, Response response) {
                // cache a copy

                callback.success(sessionsResponse.getSessions());
            }

            @Override
            public void failure(RetrofitError error) {
                if (error.isNetworkError()){
                    callback.error(ApiError.NET_404);
                } else {
                    callback.error(ApiError.UNKNOWN);
                }
            }
        });
    }

    @SuppressWarnings("UnusedDeclaration")
    public void getSpeakers(final SpeakerCallback callback) {

        api.getSpeakers(new Callback<SpeakerResponse>() {
            @Override
            public void success(SpeakerResponse speakerResponse, Response response) {
                callback.success(speakerResponse.getSpeakers());
            }

            @Override
            public void failure(RetrofitError error) {

                if (error.isNetworkError()){
                    callback.error(ApiError.NET_404);
                } else {
                    callback.error(ApiError.UNKNOWN);
                }
            }
        });
    }

    public interface SessionCallback {
        void success(List<Session> sessions);
        void error(ApiError error);
    }


    public interface SpeakerCallback {
        void success(List<Speaker> speakers);
        void error(ApiError error);
    }

    public static class ApiError extends Exception {
        public static final ApiError UNKNOWN = new ApiError();
        public static final ApiError NET_404 = new ApiError("404: NOT FOUND");

        public ApiError(String detailMessage) {
            super(detailMessage);
        }

        public ApiError() {

        }
    }
}
