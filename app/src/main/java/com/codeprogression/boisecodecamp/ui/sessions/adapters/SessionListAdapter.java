package com.codeprogression.boisecodecamp.ui.sessions.adapters;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import com.codeprogression.boisecodecamp.R;
import com.codeprogression.boisecodecamp.api.models.Session;
import com.codeprogression.boisecodecamp.ui.sessions.views.SessionListItemView;

import java.util.List;

public class SessionListAdapter extends ArrayAdapter<Session> implements IListable {
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

        SessionListItemView view =
                convertView == null
                        ? new SessionListItemView(getContext())
                        : (SessionListItemView) convertView;

        view.render(getItem(position));

        return view;
    }

    public void updateSessionList(List<Session> sessions) {
        clear();
        addAll(sessions);
        this.list = sessions;
        notifyDataSetChanged();
    }
}
