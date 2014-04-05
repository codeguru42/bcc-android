package com.codeprogression.boisecodecamp.events;

import com.codeprogression.boisecodecamp.api.models.Speaker;

import java.util.List;

public class SpeakersReceivedEvent {
    private List<Speaker> speakers;

    public SpeakersReceivedEvent(List<Speaker> speakers) {

        this.speakers = speakers;
    }

    public List<Speaker> getSpeakers() {
        return speakers;
    }
}
