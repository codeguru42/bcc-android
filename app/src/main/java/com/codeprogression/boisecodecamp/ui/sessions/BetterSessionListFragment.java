package com.codeprogression.boisecodecamp.ui.sessions;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.codeprogression.boisecodecamp.R;
import com.codeprogression.boisecodecamp.api.CodeCampApiWrapper;
import com.codeprogression.boisecodecamp.api.models.Session;
import com.codeprogression.boisecodecamp.ui.sessions.adapters.BestSessionListAdapter;
import com.codeprogression.boisecodecamp.ui.sessions.adapters.IListable;
import com.codeprogression.boisecodecamp.ui.core.BaseListFragment;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.ButterKnife;

public class BetterSessionListFragment extends BaseListFragment {

    @Inject
    CodeCampApiWrapper api;

    public static BetterSessionListFragment newInstance() {
        return new BetterSessionListFragment();
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
        api.getSessions(new CodeCampApiWrapper.SessionCallback() {

            public void success(List<Session> sessions) {
                BestSessionListAdapter listAdapter = (BestSessionListAdapter) getListAdapter();

                if (listAdapter == null) {
                    BestSessionListAdapter adapter = new BestSessionListAdapter(getActivity(), sessions);
                    setListAdapter(adapter);
                } else {
                    listAdapter.updateSessionList(sessions);
                }
            }

            public void error(CodeCampApiWrapper.ApiError error) {
            }

        });

    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);
        Intent intent = new Intent(getActivity(), SessionDetailActivity.class);
        Bundle bundle = new Bundle();
        bundle.putParcelableArrayList("SESSION_LIST", new ArrayList<>(((IListable) getListAdapter()).getList()));
        bundle.putInt("POSITION", position);
        intent.putExtras(bundle);
        startActivity(intent);
    }
}