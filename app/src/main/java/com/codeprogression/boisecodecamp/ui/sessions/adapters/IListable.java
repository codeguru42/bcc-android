package com.codeprogression.boisecodecamp.ui.sessions.adapters;

import android.os.Parcelable;

import java.util.List;

public interface IListable {
    List<? extends Parcelable> getList();
}
