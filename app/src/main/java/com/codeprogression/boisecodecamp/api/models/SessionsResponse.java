package com.codeprogression.boisecodecamp.api.models;

import com.google.gson.annotations.Expose;

import java.util.ArrayList;
import java.util.List;

public class SessionsResponse {
    @Expose String timezone;
    @Expose List<SessionsPerDay> sessions;

    private class SessionsPerDay {
        @Expose String day;

        public List<Session> getSessions() {
            return sessions;
        }

        @Expose List<Session> sessions;
    }

    public List<Session> getSessions(){
        List<Session> list = new ArrayList<>();
        for (SessionsPerDay sessionsPerDay : sessions) {
            for (Session session : sessionsPerDay.getSessions()) {
                if (!session.getSpace().equalsIgnoreCase("proposed")){
                    list.add(session);
                }
            }
        }
        return list;
    }
}
