
package com.github.curioustechizen.apppause.sample;

import android.os.Bundle;

public class MainActivity extends SampleAppPauseBaseActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setNameDisplay(getString(R.string.activity_main));
        setNextActivity(SettingsActivity.class);
    }
}
