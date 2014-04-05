package com.codeprogression.boisecodecamp.api.core;

import com.android.volley.*;
import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import com.android.volley.toolbox.HttpHeaderParser;
import com.codeprogression.boisecodecamp.core.AndroidModule;
import com.google.common.net.HttpHeaders;
import com.google.gson.JsonSyntaxException;

import java.io.UnsupportedEncodingException;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;

public class GsonRestRequest<T> extends Request<T> {

    private final Listener<T> mListener;
    private Class<T> mClazz;
    private Type mType;

    public GsonRestRequest(String url,
                           Class<T> clazz,
                           Listener<T> listener,
                           ErrorListener errorListener) {
        super(Method.GET, url, errorListener);
        mClazz = clazz;
        mListener = listener;
    }

    @SuppressWarnings("UnusedDeclaration")
    public GsonRestRequest(String url,
                           Type type,
                           Listener<T> listener,
                           ErrorListener errorListener) {
        super(Method.GET, url, errorListener);
        mType = type;
        mListener = listener;
    }

    @Override
    protected void deliverResponse(T response) {
        mListener.onResponse(response);
    }

    @Override
    protected Response<T> parseNetworkResponse(NetworkResponse response) {
        try {
            String json = new String(
                    response.data, HttpHeaderParser.parseCharset(response.headers));
            T result;
            if (mClazz != null) {
                result = AndroidModule.getGson().fromJson(json, mClazz);
            } else {
                result = AndroidModule.getGson().fromJson(json, mType);
            }

            makeItSlow();
            return Response.success(result, HttpHeaderParser.parseCacheHeaders(response));
        } catch (UnsupportedEncodingException e) {
            return Response.error(new ParseError(e));
        } catch (JsonSyntaxException e) {
            return Response.error(new ParseError(e));
        }
    }


    @SuppressWarnings("UnusedDeclaration")
    private void makeItSlow() {
        try {
            Thread.sleep(10000);
        } catch (InterruptedException ignore) {
        }
    }
}