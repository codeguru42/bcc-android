package com.codeprogression.boisecodecamp.ui.sessions;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;


import com.codeprogression.boisecodecamp.R;
import com.codeprogression.boisecodecamp.api.LanyrdApi;
import com.codeprogression.boisecodecamp.api.models.Session;
import com.codeprogression.boisecodecamp.api.models.SessionsResponse;
import com.codeprogression.boisecodecamp.ui.sessions.adapters.BadSessionListAdapter;
import com.codeprogression.boisecodecamp.ui.sessions.adapters.BestSessionListAdapter;
import com.codeprogression.boisecodecamp.ui.sessions.adapters.BetterSessionListAdapter;
import com.codeprogression.boisecodecamp.ui.sessions.adapters.IListable;
import com.codeprogression.boisecodecamp.ui.sessions.adapters.TypicalSessionListAdapter;
import com.codeprogression.boisecodecamp.ui.core.BaseListFragment;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.ButterKnife;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class SessionListFragment extends BaseListFragment {

    @Inject LanyrdApi api;

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
        api.getSessions(new Callback<SessionsResponse>() {
            @Override
            public void success(SessionsResponse sessionsResponse, Response response) {

                List<Session> sessions = sessionsResponse.getSessions();

//                useBadSessionListAdapter(sessions);
//                useSessionListAdapter(sessions);
//                useBetterSessionListAdapter(sessions);
                useBestSessionListAdapter(sessions);
            }

            @Override
            public void failure(RetrofitError error) {
            }
        });
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


    //region Session List Adapters

    //region Bad

    @SuppressWarnings("UnusedDeclaration")
    private void useBadSessionListAdapter(List<Session> sessions) {
        BadSessionListAdapter listAdapter = (BadSessionListAdapter)getListAdapter();

        if (listAdapter == null){
            BadSessionListAdapter adapter = new BadSessionListAdapter(getActivity(), sessions);
            setListAdapter(adapter);
        } else {
            listAdapter.updateSessionList(sessions);
        }
    }

    //endregion

    //region Normal

    @SuppressWarnings("UnusedDeclaration")
    private void useSessionListAdapter(List<Session> sessions) {
        TypicalSessionListAdapter listAdapter = (TypicalSessionListAdapter)getListAdapter();

        if (listAdapter == null){
            TypicalSessionListAdapter adapter = new TypicalSessionListAdapter(getActivity(), sessions);
            setListAdapter(adapter);
        } else {
            listAdapter.updateSessionList(sessions);
        }
    }

    //endregion

    //region Better

    @SuppressWarnings("UnusedDeclaration")
    private void useBetterSessionListAdapter(List<Session> sessions) {
        BetterSessionListAdapter listAdapter = (BetterSessionListAdapter)getListAdapter();

        if (listAdapter == null){
            BetterSessionListAdapter adapter = new BetterSessionListAdapter(getActivity(), sessions);
            setListAdapter(adapter);
        } else {
            listAdapter.updateSessionList(sessions);
        }
    }

    //endregion

    //region Best

    @SuppressWarnings("UnusedDeclaration")
    private void useBestSessionListAdapter(List<Session> sessions) {
        BestSessionListAdapter listAdapter = (BestSessionListAdapter)getListAdapter();

        if (listAdapter == null){
            BestSessionListAdapter adapter = new BestSessionListAdapter(getActivity(), sessions);
            setListAdapter(adapter);
        } else {
            listAdapter.updateSessionList(sessions);
        }
    }

    //endregion

    //endregion

}
