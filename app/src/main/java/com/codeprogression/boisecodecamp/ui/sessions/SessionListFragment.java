package com.codeprogression.boisecodecamp.ui.sessions;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.codeprogression.boisecodecamp.R;
import com.codeprogression.boisecodecamp.api.LanyrdApi;
import com.codeprogression.boisecodecamp.api.core.GsonRestRequest;
import com.codeprogression.boisecodecamp.api.models.Session;
import com.codeprogression.boisecodecamp.api.models.SessionsResponse;
import com.codeprogression.boisecodecamp.ui.core.BaseListFragment;
import com.codeprogression.boisecodecamp.ui.sessions.adapters.SessionListAdapter;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;

import butterknife.ButterKnife;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

import static com.codeprogression.boisecodecamp.utils.LogUtils.makeLogTag;

public class SessionListFragment extends BaseListFragment {

    @SuppressWarnings("UnusedDeclaration")
    public static final String TAG = makeLogTag(SessionListFragment.class);

    @Inject
    RequestQueue requestQueue;
    private GsonRestRequest<SessionsResponse> request;

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
            request =
                    new GsonRestRequest<>("http://lanyrd.com/2014/bcc2014/schedule/b1200ddb9996154d.v1.json",
                            SessionsResponse.class,
                            new com.android.volley.Response.Listener<SessionsResponse>() {
                                @Override
                                public void onResponse(SessionsResponse sessionsResponse) {
                                    FragmentActivity activity = getActivity();
                                    if (activity == null || activity.isFinishing()){
                                        return;
                                    }
                                    handleSuccess(sessionsResponse.getSessions());
                                }
                            },
                            new com.android.volley.Response.ErrorListener() {
                                @Override
                                public void onErrorResponse(VolleyError volleyError) {
                                    FragmentActivity activity = getActivity();
                                    if (activity == null || activity.isFinishing()){
                                        return;
                                    }
                                    handleError();
                                }
                            }
                    );
            requestQueue.add(request);
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        if (request != null){
            request.cancel();
        }
    }

    private void handleSuccess(List<Session> sessions) {
        SessionListAdapter listAdapter = (SessionListAdapter) getListAdapter();

        if (listAdapter == null) {
            SessionListAdapter adapter = new SessionListAdapter(getActivity(), sessions);
            setListAdapter(adapter);
        } else {
            listAdapter.updateSessionList(sessions);
        }
    }

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

