package com.codeprogression.boisecodecamp.api.core;

import android.content.Context;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.toolbox.HurlStack;
import com.squareup.okhttp.OkHttpClient;

import org.apache.http.Header;
import org.apache.http.HttpResponse;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Map;
import java.util.concurrent.TimeUnit;

public class OkHttpStack extends HurlStack {

    private final OkHttpClient client;

    public OkHttpStack(Context context) {
        this(context, new OkHttpClient());
    }
    public OkHttpStack(Context context, OkHttpClient client) {
        if (client == null) {
            throw new NullPointerException("Client must not be null.");
        }

        client.setConnectTimeout(30, TimeUnit.SECONDS);
        client.setReadTimeout(10, TimeUnit.SECONDS);
        this.client = client;
    }

    @Override
    protected HttpURLConnection createConnection(URL url) throws IOException {

        return client.open(url);
    }

    @SuppressWarnings("UnusedDeclaration")
    private void makeItSlow() {
        try {
            Thread.sleep(10000);
        } catch (InterruptedException ignore) {
        }
    }
}
