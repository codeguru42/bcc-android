package com.codeprogression.boisecodecamp.ui.speakers.adapters;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import com.codeprogression.boisecodecamp.api.models.Speaker;
import com.codeprogression.boisecodecamp.ui.speakers.views.SpeakerListItemView;

import java.util.List;

public class SpeakerListAdapter extends ArrayAdapter<Speaker> {

    private List<Speaker> list;

    public List<Speaker> getList() {
        return list;
    }

    public SpeakerListAdapter(Context context, List<Speaker> list) {
        super(context, 0, list);
        this.list = list;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        SpeakerListItemView view = convertView == null
            ? new SpeakerListItemView(getContext())
            : (SpeakerListItemView)convertView;

        view.render(getItem(position));

        return view;
    }

    public void updateSpeakerList(List<Speaker> speakers) {
        clear();
        addAll(speakers);
        this.list = speakers;
        notifyDataSetChanged();
    }
}
