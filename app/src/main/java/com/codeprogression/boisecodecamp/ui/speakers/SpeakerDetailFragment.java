package com.codeprogression.boisecodecamp.ui.speakers;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.codeprogression.boisecodecamp.R;
import com.codeprogression.boisecodecamp.api.models.OtherUrl;
import com.codeprogression.boisecodecamp.api.models.Speaker;
import com.codeprogression.boisecodecamp.ui.core.BaseFragment;
import com.codeprogression.boisecodecamp.ui.core.ListTagHandler;
import com.f2prateek.dart.Dart;
import com.f2prateek.dart.InjectExtra;
import com.google.common.base.Function;
import com.google.common.collect.Collections2;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import butterknife.OnItemClick;

public class SpeakerDetailFragment extends BaseFragment {

    @Inject
    Picasso picasso;

    @InjectExtra("SPEAKER")
    Speaker speaker;

    @InjectView(R.id.speaker_name)
    TextView speakerName;
    @InjectView(R.id.speaker_image)
    ImageView speakerImage;
    @InjectView(R.id.speaker_twitter)
    TextView twitterView;
    @InjectView(R.id.speaker_bio)
    TextView bioView;
    @InjectView(R.id.speaker_other)
    ListView otherView;

    private List<String> urls;

    public static SpeakerDetailFragment getInstance(Speaker speaker) {
        SpeakerDetailFragment fragment = new SpeakerDetailFragment();
        Bundle bundle = new Bundle();
        bundle.putParcelable("SPEAKER", speaker);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.speaker_detail_fragment, container, false);
        ButterKnife.inject(this, view);
        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Dart.inject(this, getArguments());


    }

    @Override
    public void onResume() {
        super.onResume();

        updateDisplay();
    }

    private void updateDisplay() {
        renderImage(speaker);
        speakerName.setText(speaker.getName());
        final String twitter = speaker.getTwitter();

        if (twitter == null || twitter.isEmpty()){
            twitterView.setVisibility(View.GONE);
        }else {
            twitterView.setText(twitter);
            twitterView.setVisibility(View.VISIBLE);
        }

        String bio = speaker.getBio() != null && !speaker.getBio().isEmpty()
                ? speaker.getBio()
                : speaker.getRole();
        bioView.setText(Html.fromHtml(bio, null, new ListTagHandler()));

        urls =  new ArrayList<>(Collections2.transform(speaker.getElsewhere(), new Function<OtherUrl, String>() {
            @Override
            public String apply(OtherUrl input) {
                return input.getUrl();
            }
        }));

        otherView.setAdapter(new ArrayAdapter<>(getActivity(), android.R.layout.simple_list_item_1, urls));
    }

    private void renderImage(Speaker speaker) {

        String speakerImage300 = speaker.getImage300();

        picasso.load(speakerImage300)
                .into(speakerImage);
    }

    @OnClick(R.id.speaker_twitter)
    void onTwitterClick(){
        try {
            startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("twitter://user?screen_name=" + speaker.getTwitter())));
        }catch (Exception e) {
            startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://twitter.com/#!/" + speaker.getTwitter())));
        }
    }

    @OnItemClick(R.id.speaker_other)
    void onOtherClick(int position){
        String url = urls.get(position);
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setData(Uri.parse(url));
        startActivity(intent);
    }

}
