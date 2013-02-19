
package com.github.curioustechizen.apppause.sample;

import com.github.curioustechizen.android.apppause.AbstractAppPauseApplication;

import android.util.Log;

public class SampleAppPauseApplication extends AbstractAppPauseApplication {

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
