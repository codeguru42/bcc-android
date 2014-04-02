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

import butterknife.ButterKnife;
import butterknife.InjectView;

public class BetterSessionListAdapter extends ArrayAdapter<Session> implements IListable {

    private List<Session> list;

    public BetterSessionListAdapter(Context context, List<Session> list) {
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
            holder = new ViewHolder(convertView);
            assert convertView != null;
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


    static class ViewHolder {

        @InjectView(R.id.title)         TextView titleView;
        @InjectView(R.id.times)         TextView timesView;
        @InjectView(R.id.speaker_names) TextView speakerNamesView;
        @InjectView(R.id.space)         TextView spaceView;

        private ViewHolder(View view) {
            ButterKnife.inject(this, view);
        }
    }
}
