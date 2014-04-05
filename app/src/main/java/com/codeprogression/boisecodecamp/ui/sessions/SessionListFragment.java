package com.codeprogression.boisecodecamp.ui.sessions;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.codeprogression.boisecodecamp.R;
import com.codeprogression.boisecodecamp.api.models.Session;
import com.codeprogression.boisecodecamp.events.SessionsReceivedEvent;
import com.codeprogression.boisecodecamp.services.LanyrdIntentService;
import com.codeprogression.boisecodecamp.ui.core.BaseListFragment;
import com.codeprogression.boisecodecamp.ui.sessions.adapters.SessionListAdapter;
import com.squareup.otto.Bus;
import com.squareup.otto.Subscribe;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.ButterKnife;

import static com.codeprogression.boisecodecamp.utils.LogUtils.makeLogTag;


public class SessionListFragment extends BaseListFragment {

    @SuppressWarnings("UnusedDeclaration")
    public static final String TAG = makeLogTag(SessionListFragment.class);

    @Inject Bus bus;

    public static SessionListFragment newInstance() {
        return new SessionListFragment();
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.session_list_fragment, container, false);
        ButterKnife.inject(this, view);
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        bus.register(this);
    }

    @Override
    public void onPause() {
        super.onPause();
        bus.unregister(this);
    }

    /*
     * If your event subscription isn't firing, it could be because you have the wrong type .
     * Sometimes com.google.common.eventbus.Subscribe gets imported by Android Studio instead.
     */
    @Subscribe
    public void onSessionReceived(SessionsReceivedEvent event){
        handleSuccess(event.getSessions());
    }

    private void handleSuccess(List<Session> sessions) {
        if (sessions == null){
            return;
        }

        SessionListAdapter listAdapter = (SessionListAdapter) getListAdapter();

        if (listAdapter == null) {
            SessionListAdapter adapter = new SessionListAdapter(getActivity(), sessions);
            setListAdapter(adapter);
        } else {
            listAdapter.updateSessionList(sessions);
        }
    }

    @SuppressWarnings("UnusedDeclaration")
    private void handleError() {
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);
        Intent intent = new Intent(getActivity(), SessionDetailActivity.class);
        Bundle bundle = new Bundle();
        bundle.putParcelableArrayList("SESSION_LIST", new ArrayList<>(((SessionListAdapter) getListAdapter()).getList()));
        bundle.putInt("POSITION", position);
        intent.putExtras(bundle);
        startActivity(intent);
    }

}

