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
import com.codeprogression.boisecodecamp.ui.core.BaseListFragment;
import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;

public class TypicalSessionListFragment extends BaseListFragment {

    public static TypicalSessionListFragment newInstance() {
        return new TypicalSessionListFragment();
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

        AsyncTask<Void, Void, List<Session>> task = new AsyncTask<Void, Void, List<Session>>() {

            @Override
            protected List<Session> doInBackground(Void... params) {

                try {
                    URL url = new URL("http://lanyrd.com/2014/bcc2014/schedule/b1200ddb9996154d.v1.json");
                    HttpURLConnection connection = (HttpURLConnection) url.openConnection();

                    Gson gson = new GsonBuilder()
                            .setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
                            .create();

                    InputStreamReader reader = new InputStreamReader(connection.getInputStream());

                    SessionsResponse sessionsResponse = gson.getAdapter(SessionsResponse.class).fromJson(reader);

                    return sessionsResponse.getSessions();

                } catch (java.io.IOException e) {
                    e.printStackTrace();
                }
                return null;
            }

            @Override
            protected void onPostExecute(List<Session> sessions) {
                super.onPostExecute(sessions);

                BadSessionListAdapter listAdapter = (BadSessionListAdapter)getListAdapter();

                if (listAdapter == null){
                    BadSessionListAdapter adapter = new BadSessionListAdapter(getActivity(), sessions);
                    setListAdapter(adapter);
                } else {
                    listAdapter.updateSessionList(sessions);
                }
            }
        };

        task.execute((Void[])null);
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);
        Intent intent = new Intent(getActivity(), SessionDetailActivity.class);
        Bundle bundle = new Bundle();
        bundle.putParcelableArrayList("SESSION_LIST", new ArrayList<>(((IListable)getListAdapter()).getList()));
        bundle.putInt("POSITION", position);
        intent.putExtras(bundle);
        startActivity(intent);
    }


}
