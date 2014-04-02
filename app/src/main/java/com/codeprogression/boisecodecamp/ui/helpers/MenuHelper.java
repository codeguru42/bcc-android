package com.codeprogression.boisecodecamp.ui.helpers;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.codeprogression.boisecodecamp.R;
import com.codeprogression.boisecodecamp.ui.SettingsActivity;

public final class MenuHelper {

    public static void addSettingsMenu(MenuInflater menuInflater, Menu menu){
        menuInflater.inflate(R.menu.settings, menu);
    }

    public static boolean onSettingsItemSelected(Context context, MenuItem item){
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            Intent intent = new Intent(context, SettingsActivity.class);
            context.startActivity(intent);
            return true;
        }
        return false;
    }

    public static boolean onBackPressed(Activity activity, MenuItem item) {
        if (item.getItemId() == android.R.id.home){
            activity.onBackPressed();
            return true;
        }
        return false;
    }
}
