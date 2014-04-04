package com.codeprogression.boisecodecamp.ui.sessions;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.codeprogression.boisecodecamp.R;
import com.codeprogression.boisecodecamp.api.models.Session;
import com.codeprogression.boisecodecamp.api.models.SessionsResponse;
import com.codeprogression.boisecodecamp.core.AndroidModule;
import com.codeprogression.boisecodecamp.ui.core.BaseListFragment;
import com.codeprogression.boisecodecamp.ui.sessions.adapters.SessionListAdapter;
import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;

public class SessionListFragment extends BaseListFragment {

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
        if (getListAdapter() == null){
            new SessionBackgroundTask().execute((Void[])null);
        }
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);
        Intent intent = new Intent(getActivity(), SessionDetailActivity.class);
        Bundle bundle = new Bundle();
        bundle.putParcelableArrayList("SESSION_LIST", new ArrayList<>(((SessionListAdapter)getListAdapter()).getList()));
        bundle.putInt("POSITION", position);
        intent.putExtras(bundle);
        startActivity(intent);
    }

    class SessionBackgroundTask extends AsyncTask<Void, Void, List<Session>>{

        @Override
        protected List<Session> doInBackground(Void... params) {

            try {
                URL url = new URL("http://lanyrd.com/2014/bcc2014/schedule/b1200ddb9996154d.v1.json");
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();

                InputStreamReader reader = new InputStreamReader(connection.getInputStream());

                SessionsResponse sessionsResponse = AndroidModule.getGson()
                        .getAdapter(SessionsResponse.class).fromJson(reader);

                return sessionsResponse.getSessions();

            } catch (java.io.IOException e) {
                e.printStackTrace();
            }
            return new ArrayList<>();
        }

        @Override
        protected void onPostExecute(List<Session> sessions) {
            super.onPostExecute(sessions);

            SessionListAdapter listAdapter = (SessionListAdapter)getListAdapter();

            if (listAdapter == null){
                SessionListAdapter adapter = new SessionListAdapter(getActivity(), sessions);
                setListAdapter(adapter);
            } else {
                listAdapter.updateSessionList(sessions);
            }
        }
    }

}
