package com.codeprogression.boisecodecamp.ui.speakers;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.codeprogression.boisecodecamp.R;
import com.codeprogression.boisecodecamp.api.models.Speaker;
import com.codeprogression.boisecodecamp.events.SpeakersReceivedEvent;
import com.codeprogression.boisecodecamp.ui.core.BaseListFragment;
import com.codeprogression.boisecodecamp.ui.speakers.adapters.SpeakerListAdapter;
import com.squareup.otto.Bus;
import com.squareup.otto.Subscribe;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.ButterKnife;

public class SpeakerListFragment extends BaseListFragment {

    @Inject Bus bus;

    public static SpeakerListFragment newInstance() {
        SpeakerListFragment fragment = new SpeakerListFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.speaker_list_fragment, container, false);
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

    @Subscribe
    public void onSpeakersReceived(SpeakersReceivedEvent event){
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
        } else {
            adapter.updateSpeakerList(speakers);
        }
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
}
