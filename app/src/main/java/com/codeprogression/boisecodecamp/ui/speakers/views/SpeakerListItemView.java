package com.codeprogression.boisecodecamp.ui.speakers.views;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.codeprogression.boisecodecamp.CodeCampApplication;
import com.codeprogression.boisecodecamp.R;
import com.codeprogression.boisecodecamp.api.models.Speaker;
import com.squareup.picasso.Picasso;

import javax.inject.Inject;

import butterknife.ButterKnife;
import butterknife.InjectView;

public class SpeakerListItemView extends RelativeLayout{

    @Inject
    Picasso picasso;

    @InjectView(R.id.speaker_image)
    ImageView imageView;

    @InjectView(R.id.speaker_name)
    TextView speakerName;

    public SpeakerListItemView(Context context) {
        this(context, null);
    }

    public SpeakerListItemView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public SpeakerListItemView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }

    private void init() {
        View view = LayoutInflater.from(getContext()).inflate(R.layout.speaker_list_item_view, this, true);
        ButterKnife.inject(this, view);
        CodeCampApplication.getInstance().getApplicationGraph().inject(this);
    }

    public void render(Speaker speaker) {
        renderImage(speaker);
        speakerName.setText(speaker.getName());
    }

    private void renderImage(Speaker speaker) {
        String speakerImage = speaker.getImage75();

        picasso.load(speakerImage)
                .placeholder(android.R.drawable.ic_menu_gallery)
                .error(android.R.drawable.ic_menu_report_image)
                .fit()
                .into(imageView);
    }
}
