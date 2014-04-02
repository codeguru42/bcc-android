package com.codeprogression.boisecodecamp.ui.speakers.adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.codeprogression.boisecodecamp.api.models.Speaker;
import com.codeprogression.boisecodecamp.ui.speakers.SpeakerDetailFragment;

import java.util.List;

public class SpeakerPagerAdapter extends FragmentStatePagerAdapter {

    private List<Speaker> speakerList;

    public SpeakerPagerAdapter(FragmentManager fm, List<Speaker> speakerList) {
        super(fm);
        this.speakerList = speakerList;
    }

    @Override
    public Fragment getItem(int position) {
        return SpeakerDetailFragment.getInstance(speakerList.get(position));
    }

    @Override
    public int getCount() {
        return speakerList.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return (speakerList.get(position)).getName();
    }
}
