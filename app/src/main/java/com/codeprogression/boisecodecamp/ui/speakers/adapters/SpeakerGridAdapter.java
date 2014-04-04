package com.codeprogression.boisecodecamp.ui.speakers.adapters;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.codeprogression.boisecodecamp.api.models.Speaker;
import com.codeprogression.boisecodecamp.ui.speakers.views.SpeakerGridItemView;

import java.util.List;

public class SpeakerGridAdapter extends BaseAdapter implements IListable {
    private Context context;
    private List<Speaker> list;

    public SpeakerGridAdapter(Context context, List<Speaker> list) {
        super();
        this.context = context;
        this.list = list;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {

        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return list.get(position).getWebUrl().hashCode();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        SpeakerGridItemView view = convertView == null
            ? new SpeakerGridItemView(context)
            : (SpeakerGridItemView)convertView;

        view.render((Speaker) getItem(position));

        return view;
    }

    public void updateSpeakerList(List<Speaker> speakers) {
        list.clear();
        list.addAll(speakers);
        notifyDataSetChanged();
    }

    public List<Speaker> getList() {
        return list;
    }
}
