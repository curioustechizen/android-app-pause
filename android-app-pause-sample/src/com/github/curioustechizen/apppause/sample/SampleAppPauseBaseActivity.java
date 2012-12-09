package com.github.curioustechizen.apppause.sample;

import com.github.curioustechizen.android.apppause.AbstractAppActiveService;
import com.github.curioustechizen.android.apppause.AbstractAppPauseActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;


public class SampleAppPauseBaseActivity extends AbstractAppPauseActivity{

    protected TextView mTvName;
    protected Button mBtnNext;
    private Context mContext;
    
    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mContext = this;
        mTvName = (TextView)findViewById(R.id.tvActivityName);
        mBtnNext = (Button)findViewById(R.id.btnNext);
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
    
    @Override
    protected Class<? extends AbstractAppActiveService> getAppActiveServiceClass() {
        return SampleAppActiveService.class;
    }
}
