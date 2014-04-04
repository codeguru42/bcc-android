package com.codeprogression.boisecodecamp.api;

import android.content.Context;
import android.content.ContextWrapper;
import android.net.Uri;

import com.codeprogression.boisecodecamp.R;
import com.codeprogression.boisecodecamp.utils.ResourceUtils;

import java.io.IOException;
import java.util.Collections;

import retrofit.client.Client;
import retrofit.client.Request;
import retrofit.client.Response;
import retrofit.mime.TypedByteArray;

import static com.codeprogression.boisecodecamp.utils.LogUtils.LOGD;

public class MockLanyrdClient extends ContextWrapper implements Client {


    public MockLanyrdClient(Context base) {
        super(base);
    }

    @SuppressWarnings("unchecked")
    @Override
    public Response execute(Request request) throws IOException {
        Uri uri = Uri.parse(request.getUrl());

        LOGD("MOCK SERVER", "fetching uri: " + uri.toString());

        String responseString = "";
        String path = uri.getPath();
        if (path != null) {
            if (path.equalsIgnoreCase("/2014/bcc2014/schedule/b1200ddb9996154d.v1.json")) {
                responseString = ResourceUtils.loadFromResource(getResources(), R.raw.x_schedule);

            } else if (path.equalsIgnoreCase("/2014/bcc2014/speakers/b1200ddb9996154d.v1.json")) {
                responseString = ResourceUtils.loadFromResource(getResources(), R.raw.x_speakers);
            }
        }

//        makeItSlow();

        if (responseString.equals("")){
            return new Response(request.getUrl(), 404, "NOT FOUND", Collections.EMPTY_LIST, new TypedByteArray("application/json", responseString.getBytes()));
        } else{
            return new Response(request.getUrl(), 200, "SUCCESS", Collections.EMPTY_LIST, new TypedByteArray("application/json", responseString.getBytes()));
        }

    }

    @SuppressWarnings("UnusedDeclaration")
    private void makeItSlow(){
        try {
            Thread.sleep(10000);
        } catch (InterruptedException ignore) { }
    }


}
