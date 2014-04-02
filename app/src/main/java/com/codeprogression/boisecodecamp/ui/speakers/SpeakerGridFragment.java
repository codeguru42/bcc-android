package com.codeprogression.boisecodecamp.ui.speakers;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.GridView;

import com.codeprogression.boisecodecamp.R;
import com.codeprogression.boisecodecamp.api.LanyrdApi;
import com.codeprogression.boisecodecamp.api.models.Speaker;
import com.codeprogression.boisecodecamp.api.models.SpeakerResponse;
import com.codeprogression.boisecodecamp.ui.speakers.adapters.SpeakerGridAdapter;
import com.codeprogression.boisecodecamp.ui.core.BaseFragment;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.ButterKnife;
import butterknife.InjectView;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class SpeakerGridFragment extends BaseFragment implements AbsListView.OnItemClickListener {

    @InjectView(R.id.grid)
    GridView gridView;

    @Inject LanyrdApi api;

    private SpeakerGridAdapter adapter;

    public static SpeakerGridFragment newInstance() {
        SpeakerGridFragment fragment = new SpeakerGridFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.speaker_grid_fragment, container, false);
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

                if (adapter == null){
                    adapter = new SpeakerGridAdapter(getActivity(), speakers);
                    gridView.setAdapter(adapter);
                    gridView.setOnItemClickListener(SpeakerGridFragment.this);
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
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Intent intent = new Intent(getActivity(), SpeakerDetailActivity.class);
        Bundle bundle = new Bundle();
        bundle.putParcelableArrayList("SPEAKER_LIST", new ArrayList<>(adapter.getList()));
        bundle.putInt("POSITION", position);
        intent.putExtras(bundle);
        startActivity(intent);
    }

    public interface Callbacks {
    }

}
