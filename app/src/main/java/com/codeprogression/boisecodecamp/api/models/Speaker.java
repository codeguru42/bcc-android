package com.codeprogression.boisecodecamp.api.models;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.common.collect.ComparisonChain;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class Speaker implements Parcelable, Comparable<Speaker> {

    @Expose String name;
    @Expose String role;
    @Expose String twitter;
    @Expose String bio;
    @Expose String speakerBio;
    @SerializedName("image_75")
    @Expose String image75;
    @SerializedName("image_300")
    @Expose String image300;
    @Expose String webUrl;
    @Expose List<OtherUrl> elsewhere;

    public Speaker(Parcel in) {
        name = in.readString();
        role = in.readString();
        twitter = in.readString();
        bio = in.readString();
        speakerBio = in.readString();
        image75 = in.readString();
        image300 = in.readString();
        webUrl = in.readString();
        elsewhere = new ArrayList<>();
        in.readList(elsewhere, OtherUrl.class.getClassLoader());
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
        dest.writeString(role);
        dest.writeString(twitter);
        dest.writeString(bio);
        dest.writeString(speakerBio);
        dest.writeString(image75);
        dest.writeString(image300);
        dest.writeString(webUrl);
        dest.writeList(elsewhere);
    }

    public static final Creator<Speaker> CREATOR = new Creator<Speaker>() {
        @Override
        public Speaker createFromParcel(Parcel in) {
            return new Speaker(in);
        }

        @Override
        public Speaker[] newArray(int size) {
            return new Speaker[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    public String getBio() {
        return bio;
    }

    public String getWebUrl() {
        return webUrl;
    }

    public String getImage300() {
        return image300;
    }

    public String getName() {
        return name;
    }

    public List<OtherUrl> getElsewhere() {
        return elsewhere;
    }

    public String getRole() {
        return role;
    }

    public String getTwitter() {
        return twitter;
    }

    public String getImage75() {
        return image75;
    }

    public String getSpeakerBio() {
        return speakerBio;
    }

    @Override
    public int compareTo(Speaker another) {
        return ComparisonChain.start()
                .compare(getName(), another.getName(), String.CASE_INSENSITIVE_ORDER)
                .compare(getName(), another.getName())
                .result();
    }
}
