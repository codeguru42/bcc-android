package com.codeprogression.boisecodecamp.api.models;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;

public class OtherUrl implements Parcelable {
    @Expose
    String url;

    public OtherUrl(Parcel in) {
        url = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(url);
    }

    public static final Creator<OtherUrl> CREATOR = new Creator<OtherUrl>() {
        @Override
        public OtherUrl createFromParcel(Parcel in) {
            return new OtherUrl(in);
        }

        @Override
        public OtherUrl[] newArray(int size) {
            return new OtherUrl[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    public String getUrl() {
        return url;
    }
}
