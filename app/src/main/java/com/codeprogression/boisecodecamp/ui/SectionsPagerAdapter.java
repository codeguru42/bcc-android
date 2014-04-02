package com.codeprogression.boisecodecamp.ui;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.codeprogression.boisecodecamp.R;
import com.codeprogression.boisecodecamp.ui.sessions.BetterSessionListFragment;
import com.codeprogression.boisecodecamp.ui.sessions.TypicalSessionListFragment;
import com.codeprogression.boisecodecamp.ui.speakers.SpeakerListFragment;
import com.codeprogression.boisecodecamp.ui.speakers.adapters.SpeakerPagerAdapter;

import java.util.Locale;

public class SectionsPagerAdapter extends FragmentPagerAdapter {

    private MainActivity mainActivity;

    public SectionsPagerAdapter(MainActivity mainActivity, FragmentManager fm) {
        super(fm);
        this.mainActivity = mainActivity;
    }

    @Override
    public Fragment getItem(int position) {
        // getItem is called to instantiate the fragment for the given page.
        // Return a PlaceholderFragment (defined as a static inner class below).
        switch (position) {
            case 1:
                return TypicalSessionListFragment.newInstance();
            case 2:
                return SpeakerListFragment.newInstance();
            case 0:
            default:
                return HomeFragment.newInstance(null, null);
        }
    }

    @Override
    public int getCount() {
        // Show 3 total pages.
        return 3;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        Locale l = Locale.getDefault();
        switch (position) {
            case 0:
                return mainActivity.getString(R.string.title_section1).toUpperCase(l);
            case 1:
                return mainActivity.getString(R.string.title_section2).toUpperCase(l);
            case 2:
                return mainActivity.getString(R.string.title_section3).toUpperCase(l);
        }
        return null;
    }
}
