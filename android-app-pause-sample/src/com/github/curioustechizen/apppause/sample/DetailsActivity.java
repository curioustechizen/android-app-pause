package com.github.curioustechizen.apppause.sample;

import android.os.Bundle;

public class DetailsActivity extends SampleAppPauseBaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setNameDisplay(getString(R.string.activity_detail));
        setNextActivity(MainActivity.class); 
    }
}
