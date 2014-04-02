package com.codeprogression.boisecodecamp.core;

import android.content.Context;
import android.net.http.HttpResponseCache;

import com.codeprogression.boisecodecamp.BuildConfig;
import com.codeprogression.boisecodecamp.CodeCampApplication;
import com.codeprogression.boisecodecamp.api.LanyrdApi;
import com.codeprogression.boisecodecamp.ui.speakers.views.SpeakerGridItemView;
import com.codeprogression.boisecodecamp.ui.speakers.views.SpeakerListItemView;
import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.otto.Bus;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.io.IOException;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import retrofit.RestAdapter;
import retrofit.client.OkClient;
import retrofit.converter.Converter;
import retrofit.converter.GsonConverter;

@Module(
        injects = {
                CodeCampApplication.class,
                SpeakerGridItemView.class,
                SpeakerListItemView.class,
        },
        library = true
)
public class AndroidModule {

    private DaggerApplication application;

    public AndroidModule(DaggerApplication application) {

        this.application = application;
    }

    @Provides
    @Singleton
    @ForApplication
    Context provideApplicationContext() {
        return application;
    }

    @Provides @Singleton
    Bus provideBus(){
        return new Bus();
    }

    @Provides @Singleton
    Picasso providePicasso(){
        Picasso picasso = Picasso.with(application);
        picasso.setDebugging(BuildConfig.DEBUG);

        return picasso;
    }

    @Provides @Singleton
    LanyrdApi provideLanyrdApi(){
        RestAdapter.Builder builder = new RestAdapter.Builder();
        OkClient client = new OkClient();

        OkHttpClient okHttpClient = new OkHttpClient();
        File cacheDir = new File(application.getFilesDir(), "response_cache");
        cacheDir.setWritable(true, true);
        try {
            HttpResponseCache cache = HttpResponseCache.install(cacheDir, 1024);
            okHttpClient.setResponseCache(cache);
        } catch (IOException e) {
            e.printStackTrace();
        }

        RestAdapter adapter = builder.setEndpoint("http://lanyrd.com")
                .setLogLevel(BuildConfig.DEBUG ? RestAdapter.LogLevel.FULL : RestAdapter.LogLevel.NONE)
                .setClient(new OkClient(okHttpClient))
                .setConverter(getGsonConverter())
                .build();
        return adapter.create(LanyrdApi.class);
    }

    private Converter getGsonConverter() {
        Gson gson = new GsonBuilder()
                .setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
                .create();
        return new GsonConverter(gson);
    }

}
