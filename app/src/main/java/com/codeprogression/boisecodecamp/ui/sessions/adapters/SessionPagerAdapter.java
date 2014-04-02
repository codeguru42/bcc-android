package com.codeprogression.boisecodecamp.ui.sessions.adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.codeprogression.boisecodecamp.api.models.Session;
import com.codeprogression.boisecodecamp.ui.sessions.SessionDetailFragment;

import java.util.List;

public class SessionPagerAdapter extends FragmentStatePagerAdapter {

    private List<Session> sessionList;

    public SessionPagerAdapter(FragmentManager fm, List<Session> sessionList) {
        super(fm);
        this.sessionList = sessionList;
    }

    @Override
    public Fragment getItem(int position) {
        return SessionDetailFragment.getInstance(sessionList.get(position));
    }

    @Override
    public int getCount() {
        return sessionList.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return (sessionList.get(position)).getTitle();
    }
}
