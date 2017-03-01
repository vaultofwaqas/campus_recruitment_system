package com.waqkz.campusrecruitmentsystem;

import android.app.Application;

import com.bumptech.glide.request.target.ViewTarget;

/**
 * Created by Adnan Ahmed on 3/1/2017.
 */
public class App extends Application {

    @Override public void onCreate() {
        super.onCreate();
        ViewTarget.setTagId(R.id.glide_tag);
    }
}
