package com.codeprogression.boisecodecamp.ui.speakers;

import android.os.Bundle;
import android.support.v4.view.ViewPager;

import com.codeprogression.boisecodecamp.R;
import com.codeprogression.boisecodecamp.api.models.Speaker;
import com.codeprogression.boisecodecamp.ui.speakers.adapters.SpeakerPagerAdapter;
import com.codeprogression.boisecodecamp.ui.core.BaseActivity;

import java.util.List;

public class SpeakerDetailActivity extends BaseActivity {

    SpeakerPagerAdapter sectionsPagerAdapter;

    ViewPager viewPager;

    List<Speaker> speakerList;

    int position;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.speaker_detail_activity);

        getActionBar().setHomeButtonEnabled(true);
        getActionBar().setDisplayHomeAsUpEnabled(true);

        Bundle extras = getIntent().getExtras();
        assert extras != null;

        speakerList = extras.getParcelableArrayList("SPEAKER_LIST");
        position = extras.getInt("POSITION");


        setupPager();
    }

    private void setupPager() {
        sectionsPagerAdapter = new SpeakerPagerAdapter(getSupportFragmentManager(), speakerList);
        viewPager = (ViewPager) findViewById(R.id.pager);
        viewPager.setAdapter(sectionsPagerAdapter);
        viewPager.setCurrentItem(position);
        getSupportActionBar().setTitle(sectionsPagerAdapter.getPageTitle(position));

        viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                getSupportActionBar().setTitle(sectionsPagerAdapter.getPageTitle(position));
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }
}
