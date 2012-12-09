package com.github.curioustechizen.apppause.sample;

import android.os.Bundle;

public class SettingsActivity extends SampleAppPauseBaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setNameDisplay(getString(R.string.activity_settings));
        setNextActivity(DetailsActivity.class);
    }
}
