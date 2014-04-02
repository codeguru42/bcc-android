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

public class TypicalSessionListAdapter extends ArrayAdapter<Session> implements IListable {

    private List<Session> list;

    public TypicalSessionListAdapter(Context context, List<Session> list) {
        super(context, R.layout.session_list_item_view, list);
        this.list = list;
    }

    public List<Session> getList() {
        return list;
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        Session session = getItem(position);

        ViewHolder holder;
        if (convertView == null){
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.session_list_item_view, parent, false);
            assert convertView != null;

            holder = new ViewHolder();
            holder.titleView = (TextView) convertView.findViewById(R.id.title);
            holder.timesView = (TextView) convertView.findViewById(R.id.times);
            holder.speakerNamesView = (TextView) convertView.findViewById(R.id.speaker_names);
            holder.spaceView = (TextView) convertView.findViewById(R.id.space);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        holder.titleView.setText(session.getTitle());
        holder.timesView.setText(session.getTimes());
        holder.speakerNamesView.setText(session.getCombinedSpeakers());
        holder.spaceView.setText(session.getSpace());

        return convertView;
    }

    public void updateSessionList(List<Session> sessions) {
        this.clear();
        this.addAll(sessions);
        this.list = sessions;
        notifyDataSetChanged();
    }


    private class ViewHolder {

        public TextView titleView;
        public TextView timesView;
        public TextView speakerNamesView;
        public TextView spaceView;
    }
}
