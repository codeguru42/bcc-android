package com.codeprogression.boisecodecamp.ui.sessions.views;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.codeprogression.boisecodecamp.R;
import com.codeprogression.boisecodecamp.api.models.Session;

import butterknife.ButterKnife;
import butterknife.InjectView;

public class SessionListItemView extends RelativeLayout{

    @InjectView(R.id.title)
    TextView titleView;

    @InjectView(R.id.times)
    TextView timesView;

    @InjectView(R.id.speaker_names)
    TextView speakerNamesView;

    @InjectView(R.id.space)
    TextView spaceView;

    public SessionListItemView(Context context) {
        this(context, null);
    }

    public SessionListItemView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public SessionListItemView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }

    private void init() {
        View view = LayoutInflater.from(getContext()).inflate(R.layout.session_list_item_view, this, true);
        ButterKnife.inject(this, view);
    }

    public void render(Session session) {
        titleView.setText(session.getTitle());
        timesView.setText(session.getTimes());
        speakerNamesView.setText(session.getCombinedSpeakers());
        spaceView.setText(session.getSpace());
    }

}
