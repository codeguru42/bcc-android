package com.codeprogression.boisecodecamp.ui.sessions;

import java.util.List;

import android.os.Bundle;
import android.support.v4.view.ViewPager;

import com.codeprogression.boisecodecamp.R;
import com.codeprogression.boisecodecamp.api.models.Session;
import com.codeprogression.boisecodecamp.ui.sessions.adapters.SessionPagerAdapter;
import com.codeprogression.boisecodecamp.ui.core.BaseActivity;
import com.f2prateek.dart.Dart;
import com.f2prateek.dart.InjectExtra;

public class SessionDetailActivity extends BaseActivity {

    SessionPagerAdapter sectionsPagerAdapter;

    ViewPager viewPager;

    @InjectExtra("SESSION_LIST")
    List<Session> sessionList;

    @InjectExtra("POSITION")
    int position;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.session_detail_activity);

        Dart.inject(this);

        getActionBar().setHomeButtonEnabled(true);
        getActionBar().setDisplayHomeAsUpEnabled(true);

        setupPager();
    }

    private void setupPager() {
        sectionsPagerAdapter = new SessionPagerAdapter(getSupportFragmentManager(), sessionList);
        viewPager = (ViewPager) findViewById(R.id.pager);
        viewPager.setAdapter(sectionsPagerAdapter);
        viewPager.setCurrentItem(position);
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
