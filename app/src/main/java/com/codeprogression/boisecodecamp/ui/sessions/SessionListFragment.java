package com.codeprogression.boisecodecamp.ui.sessions;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.Parcelable;
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
import com.codeprogression.boisecodecamp.services.LanyrdIntentService;
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

        getActivity().registerReceiver(receiver, new IntentFilter(LanyrdIntentService.SESSION_RESPONSE));

        if (getListAdapter() == null) {
            Intent intent = new Intent(getActivity(), LanyrdIntentService.class);
            intent.setAction(LanyrdIntentService.SESSION_REQUEST);
            getActivity().startService(intent);
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        getActivity().unregisterReceiver(receiver);

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

    private BroadcastReceiver receiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent.getAction().equals(LanyrdIntentService.SESSION_RESPONSE)){
                List<Session> sessions = intent.getParcelableArrayListExtra(LanyrdIntentService.SESSION_LIST);
                handleSuccess(sessions);
            }
        }
    };

}

