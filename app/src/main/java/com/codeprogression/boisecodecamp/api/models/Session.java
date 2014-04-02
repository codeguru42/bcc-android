package com.codeprogression.boisecodecamp.api.models;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.common.base.Joiner;
import com.google.common.collect.ComparisonChain;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import org.joda.time.DateTime;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Session implements Parcelable, Serializable, Comparable<Session> {


    @Expose String id;
    @Expose String title;
    @Expose @SerializedName("abstract") String description;
    @Expose List<Speaker> speakers;
    @Expose String space;
    @Expose long startTimeEpoch;
    @Expose long endTimeEpoch;
    @Expose List<Topic> topics;
    @Expose String webUrl;
    @Expose String day;
    @Expose String times;
    @Expose String otherUrl;
    @Expose String startTime;
    @Expose String endTime;
//  @Expose SessionTypes types;

    public Session(Parcel in) {
        id = in.readString();
        title = in.readString();
        description = in.readString();
        speakers = new ArrayList<>();
        in.readList(speakers, Speaker.class.getClassLoader());
        space = in.readString();
        startTimeEpoch = in.readLong();
        endTimeEpoch = in.readLong();
        topics = new ArrayList<>();
        in.readList(topics, Topic.class.getClassLoader());
        webUrl = in.readString();
        day = in.readString();
        times = in.readString();
        otherUrl = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeString(title);
        dest.writeString(description);
        dest.writeList(speakers);
        dest.writeString(space);
        dest.writeLong(startTimeEpoch);
        dest.writeLong(endTimeEpoch);
        dest.writeList(topics);
        dest.writeString(webUrl);
        dest.writeString(day);
        dest.writeString(times);
        dest.writeString(otherUrl);
    }

    public static final Creator<Session> CREATOR = new Creator<Session>() {
        @Override
        public Session createFromParcel(Parcel in) {
            return new Session(in);
        }

        @Override
        public Session[] newArray(int size) {
            return new Session[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }


    public DateTime getStartTime(){
        return new DateTime(startTimeEpoch);
    }

    public DateTime getEndTime(){
        return new DateTime(endTimeEpoch);
    }

    public long getStartTimeEpoch() {
        return startTimeEpoch;
    }

    public List<Topic> getTopics() {
        return topics;
    }

    public String getWeburl() {
        return webUrl;
    }

    public String getTimes() {
        return times;
    }

    public String getId() {
        return id;
    }


    public List<Speaker> getSpeakers() {
        return speakers;
    }

    public String getTitle() {
        return title;
    }

    public String getSpace() {
        return space;
    }

    public String getDay() {
        return day;
    }

    public String getOtherUrl() {
        return otherUrl;
    }

    public String getDescription() {
        return description;
    }

    @Override
    public int compareTo(Session another) {

                if (getStartTime().isBefore(another.getStartTime())){
                    return -1;
                } else if (getStartTime().isAfter(another.getStartTime())){
                    return 1;
                } else {
                    return ComparisonChain.start()
                            .compare(getTitle(), another.getTitle(), String.CASE_INSENSITIVE_ORDER)
                            .compare(getTitle(), another.getTitle())
                            .result();
                }
    }

    public String getCombinedSpeakers() {

        List<String> authors = new ArrayList<>();
        for (Speaker speaker : getSpeakers()) {
            authors.add(speaker.getName());
        }
        return Joiner.on(", ").join(authors);
    }
}
