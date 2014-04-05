package com.codeprogression.boisecodecamp.events;

import com.codeprogression.boisecodecamp.api.models.Session;

import java.util.List;

public class SessionsReceivedEvent {

    private List<Session> sessions;

    public SessionsReceivedEvent(List<Session> sessions) {
        this.sessions = sessions;
    }

    public List<Session> getSessions() {
        return sessions;
    }
}
