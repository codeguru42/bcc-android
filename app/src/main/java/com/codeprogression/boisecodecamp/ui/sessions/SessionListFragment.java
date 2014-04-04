package com.codeprogression.boisecodecamp.ui.sessions;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.codeprogression.boisecodecamp.R;
import com.codeprogression.boisecodecamp.api.LanyrdApi;
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

import javax.inject.Inject;
import javax.inject.Named;

import butterknife.ButterKnife;

import static com.codeprogression.boisecodecamp.utils.LogUtils.LOGD;
import static com.codeprogression.boisecodecamp.utils.LogUtils.makeLogTag;

public class SessionListFragment extends BaseListFragment {

    public static final String TAG = makeLogTag(SessionListFragment.class);
    private SessionBackgroundTask sessionBackgroundTask;

    @Inject
    @Named("MOCK")
    LanyrdApi api;

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
        if (getListAdapter() == null) {
            sessionBackgroundTask = new SessionBackgroundTask();
            sessionBackgroundTask.execute((Void[]) null);
        }
    }

    @Override
    public void onPause() {
        super.onPause();
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

    class SessionBackgroundTask extends AsyncTask<Void, Void, List<Session>> {

        @Override
        protected List<Session> doInBackground(Void... params) {

            SessionsResponse sessionsResponse = api.getSessions();
            List<Session> list = sessionsResponse.getSessions();
            return list != null ? list : new ArrayList<Session>();
        }

        @Override
        protected void onPostExecute(List<Session> sessions) {
            super.onPostExecute(sessions);

            SessionListAdapter listAdapter = (SessionListAdapter) getListAdapter();

            if (listAdapter == null) {
                SessionListAdapter adapter = new SessionListAdapter(getActivity(), sessions);
                setListAdapter(adapter);
            } else {
                listAdapter.updateSessionList(sessions);
            }
        }
    }

}


/*

        FragmentActivity activity = getActivity();

        LOGD(TAG, "Running onPostExecute for session list");
        if (activity == null){
            LOGD(TAG, "Activity is null");
        } else {
            LOGD(TAG, "Activity is " + (activity.isFinishing() ? "finishing" : "alive"));
        }
        if (activity == null || activity.isFinishing()){
            LOGD(TAG, "ABORT! ABORT!");
            return;
        }

        ---------------


        LOGD(TAG, "Canceling background task FTW!");
        sessionBackgroundTask.cancel(true);

 */