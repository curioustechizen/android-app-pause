package com.github.curioustechizen.apppause.sample;


import com.github.curioustechizen.android.apppause.AbstractAppPauseActivity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v4.content.LocalBroadcastManager;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;


public class SampleAppPauseBaseActivity extends AbstractAppPauseActivity{

    protected TextView mTvName, mTvRequestInfo;
    protected Button mBtnNext;
    private Context mContext;
    private IntentFilter mRequestIntentFilter;
    private LocalBroadcastManager mLocalBroadcastManager;
    private BroadcastReceiver mReceiver = new BroadcastReceiver() {
        
        @Override
        public void onReceive(Context context, Intent intent) {
            if(Constants.ACTION_REQUEST_RECEIVED.equals(intent.getAction())){
                setRequestInfo();
            }
        }
    };
    
    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mContext = this;
        mTvName = (TextView)findViewById(R.id.tvActivityName);
        mBtnNext = (Button)findViewById(R.id.btnNext);
        mTvRequestInfo = (TextView)findViewById(R.id.tvRequest);
        mLocalBroadcastManager = LocalBroadcastManager.getInstance(getApplicationContext());
        mRequestIntentFilter = new IntentFilter(Constants.ACTION_REQUEST_RECEIVED);
    }
    
    protected void setNameDisplay(final String displayString){
        mTvName.setText(displayString);
    }
    
    protected void setNextActivity(final Class<? extends SampleAppPauseBaseActivity> activityClazz){
        mBtnNext.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                mContext.startActivity(new Intent(mContext, activityClazz));
            }
        });

    }
    
    protected void setRequestInfo() {
        String lastRequestUserAgent = SampleAppPauseApplication.getLastRequestUserAgent();
        if(lastRequestUserAgent != null){
            mTvRequestInfo.setText(String.format("Request from %s at %tT",
                    lastRequestUserAgent,
                    SampleAppPauseApplication.getLastRequestTimestamp()));
        } else {
            mTvRequestInfo.setText(R.string.request_info_placeholder);
        }
    }
    
    @Override
    protected void onResume() {
        super.onResume();
        setRequestInfo();
        mLocalBroadcastManager.registerReceiver(mReceiver, mRequestIntentFilter);
    }
    
    @Override
    protected void onPause() {
        super.onPause();
        mLocalBroadcastManager.unregisterReceiver(mReceiver);
    }
}
