package com.codeprogression.boisecodecamp.events;

import com.codeprogression.boisecodecamp.api.models.Speaker;

import java.util.List;

public class SpeakersChangedEvent {
    private List<Speaker> speakers;

    public SpeakersChangedEvent(List<Speaker> speakers) {

        this.speakers = speakers;
    }

    public List<Speaker> getSpeakers() {
        return speakers;
    }
}
