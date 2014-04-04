package com.codeprogression.boisecodecamp.utils;

import android.content.res.Resources;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import static com.codeprogression.boisecodecamp.utils.LogUtils.LOGE;
import static com.codeprogression.boisecodecamp.utils.LogUtils.makeLogTag;

public final class ResourceUtils {

    private static final String TAG = makeLogTag(ResourceUtils.class);

    public static String loadFromResource(Resources r, int resourceId){
        final StringBuilder data = new StringBuilder();

        try {
            final BufferedReader br = new BufferedReader(new InputStreamReader(r.openRawResource(resourceId)));
            String line;
            while ((line = br.readLine()) != null){
                data.append(line);
            }
            br.close();
        } catch (IOException e) {
            e.printStackTrace();
            LOGE(TAG, "Failed to load resource");
        }

        return data.toString();
    }

    private ResourceUtils(){}
}
