package com.codeprogression.boisecodecamp.ui.sessions.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.codeprogression.boisecodecamp.R;
import com.codeprogression.boisecodecamp.api.models.Session;

import java.util.List;

public class SessionListAdapter extends ArrayAdapter<Session> {

    private List<Session> list;

    public SessionListAdapter(Context context, List<Session> list) {
        super(context, R.layout.session_list_item_view, list);
        this.list = list;
    }

    public List<Session> getList() {
        return list;
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {


        View view = LayoutInflater.from(getContext()).inflate(R.layout.session_list_item_view, parent, false);

        TextView titleView = (TextView) view.findViewById(R.id.title);
        TextView timesView = (TextView) view.findViewById(R.id.times);
        TextView speakerNamesView = (TextView) view.findViewById(R.id.speaker_names);
        TextView spaceView = (TextView) view.findViewById(R.id.space);

        Session session = getItem(position);

        titleView.setText(session.getTitle());
        timesView.setText(session.getTimes());
        speakerNamesView.setText(session.getCombinedSpeakers());
        spaceView.setText(session.getSpace());

        return view;
    }

    public void updateSessionList(List<Session> sessions) {
        this.clear();
        this.addAll(sessions);
        this.list = sessions;
        notifyDataSetChanged();
    }

}
