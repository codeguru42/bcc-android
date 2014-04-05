package com.codeprogression.boisecodecamp.events;

import com.codeprogression.boisecodecamp.api.models.Session;

import java.util.List;

public class SessionsChangedEvent {

    private List<Session> sessions;

    public SessionsChangedEvent(List<Session> sessions) {
        this.sessions = sessions;
    }

    public List<Session> getSessions() {
        return sessions;
    }
}
