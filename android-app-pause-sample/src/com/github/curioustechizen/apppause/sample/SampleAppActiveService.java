package com.github.curioustechizen.apppause.sample;

import com.github.curioustechizen.android.apppause.AbstractAppActiveService;

import android.util.Log;

public class SampleAppActiveService extends AbstractAppActiveService {

    private static final String LOG_TAG = "AppPauseSample";
    @Override
    protected void onAppPause() {
        Log.d(LOG_TAG, getString(R.string.app_pause));
    }

    @Override
    protected void onAppResume() {
        Log.d(LOG_TAG, getString(R.string.app_resume));
    }

}
