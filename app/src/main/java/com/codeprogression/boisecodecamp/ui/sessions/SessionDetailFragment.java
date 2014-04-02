package com.codeprogression.boisecodecamp.ui.sessions;

import android.os.Bundle;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.codeprogression.boisecodecamp.R;
import com.codeprogression.boisecodecamp.api.models.Session;
import com.codeprogression.boisecodecamp.ui.core.BaseFragment;
import com.codeprogression.boisecodecamp.ui.core.ListTagHandler;
import com.f2prateek.dart.Dart;
import com.f2prateek.dart.InjectExtra;

import butterknife.ButterKnife;
import butterknife.InjectView;

public class SessionDetailFragment extends BaseFragment {

    @InjectExtra("SESSION")
    Session session;

    @InjectView(R.id.session_title)
    TextView titleView;
    @InjectView(R.id.speaker_name)
    TextView speakerView;
    @InjectView(R.id.session_description)
    TextView descriptionView;
    @InjectView(R.id.times)
    TextView timesView;
    @InjectView(R.id.space)
    TextView spaceView;


    public static SessionDetailFragment getInstance(Session session) {
        SessionDetailFragment fragment = new SessionDetailFragment();
        Bundle bundle = new Bundle();
        bundle.putParcelable("SESSION", session);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.session_detail_fragment, container, false);
        ButterKnife.inject(this, view);
        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Dart.inject(this, getArguments());
    }

    @Override
    public void onResume() {
        super.onResume();
        updateDisplay();
    }

    private void updateDisplay() {

        titleView.setText(session.getTitle());
        speakerView.setText(session.getCombinedSpeakers());
        descriptionView.setText(Html.fromHtml(session.getDescription(), null, new ListTagHandler()));
        timesView.setText(session.getTimes());
        spaceView.setText(session.getSpace());
    }
}
