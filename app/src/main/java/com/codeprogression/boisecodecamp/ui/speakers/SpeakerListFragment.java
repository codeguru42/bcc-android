package com.codeprogression.boisecodecamp.ui.speakers;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.codeprogression.boisecodecamp.R;
import com.codeprogression.boisecodecamp.api.models.Speaker;
import com.codeprogression.boisecodecamp.events.RequestSpeakersEvent;
import com.codeprogression.boisecodecamp.events.SpeakersChangedEvent;
import com.codeprogression.boisecodecamp.ui.core.BaseListFragment;
import com.codeprogression.boisecodecamp.ui.speakers.adapters.SpeakerListAdapter;
import com.squareup.otto.Bus;
import com.squareup.otto.Subscribe;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.ButterKnife;
import butterknife.InjectView;

public class SpeakerListFragment extends BaseListFragment implements SwipeRefreshLayout.OnRefreshListener {

    @Inject Bus bus;

    private SwipeRefreshLayout view;


    @InjectView(R.id.empty_scroller)
    View scroller;

    public static SpeakerListFragment newInstance() {
        SpeakerListFragment fragment = new SpeakerListFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = (SwipeRefreshLayout)inflater.inflate(R.layout.speaker_list_fragment, container, false);
        ButterKnife.inject(this, view);
        view.setOnRefreshListener(this);
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

    @Subscribe
    public void onSpeakersReceived(SpeakersChangedEvent event){
        handleSuccess(event.getSpeakers());
    }

    private void handleSuccess(List<Speaker> speakers) {
        if (speakers == null){
            return;
        }
        SpeakerListAdapter adapter = ((SpeakerListAdapter)getListAdapter());
        if (adapter == null){
            adapter = new SpeakerListAdapter(getActivity(), speakers);
            setListAdapter(adapter);
            getListView().setEmptyView(scroller);
        } else {
            adapter.updateSpeakerList(speakers);
        }

        view.setRefreshing(false);
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        Intent intent = new Intent(getActivity(), SpeakerDetailActivity.class);
        Bundle bundle = new Bundle();
        bundle.putParcelableArrayList("SPEAKER_LIST", new ArrayList<>(((SpeakerListAdapter)getListAdapter()).getList()));
        bundle.putInt("POSITION", position);
        intent.putExtras(bundle);
        startActivity(intent);
    }


    @Override
    public void onRefresh() {
        bus.post(new RequestSpeakersEvent());
    }
}
