package com.codeprogression.boisecodecamp.api.models;

import com.google.gson.annotations.Expose;

import java.util.List;

public class SpeakerResponse {
    @Expose List<Speaker> speakers;

    public List<Speaker> getSpeakers() {
        return speakers;
    }
}
