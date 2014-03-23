package com.codeprogression.boisecodecamp.ui.core;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.ListFragment;

public class BaseListFragment extends ListFragment {
    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        ((BaseActivity)getActivity()).inject(this);
    }
}
