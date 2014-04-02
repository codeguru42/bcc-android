package com.codeprogression.boisecodecamp.ui.speakers;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.codeprogression.boisecodecamp.R;
import com.codeprogression.boisecodecamp.api.LanyrdApi;
import com.codeprogression.boisecodecamp.api.models.Speaker;
import com.codeprogression.boisecodecamp.api.models.SpeakerResponse;
import com.codeprogression.boisecodecamp.ui.speakers.adapters.SpeakerListAdapter;
import com.codeprogression.boisecodecamp.ui.core.BaseListFragment;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.ButterKnife;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class SpeakerListFragment extends BaseListFragment {

    @Inject LanyrdApi api;

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
        api.getSpeakers(new Callback<SpeakerResponse>() {
            @Override
            public void success(SpeakerResponse speakerResponse, Response response) {
                List<Speaker> speakers = speakerResponse.getSpeakers();
                SpeakerListAdapter adapter = ((SpeakerListAdapter)getListAdapter());
                if (adapter == null){
                    adapter = new SpeakerListAdapter(getActivity(), speakers);
                    setListAdapter(adapter);
                } else {
                    adapter.updateSpeakerList(speakers);
                }
            }

            @Override
            public void failure(RetrofitError error) {

            }
        });
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
