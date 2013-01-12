package com.github.curioustechizen.android.apppause;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;

/**
 * Base {@code Activity} for the android-app-pause library. Every Activity
 * in the app must extend this Activity. 
 */
public abstract class AbstractAppPauseActivity extends Activity {
    
    private Intent mAppServiceIntent;
    private boolean mBound = false;
    protected Class<? extends AbstractAppActiveService> mServiceClass;
    
    private ServiceConnection mConnection = new ServiceConnection() {

        @Override
        public void onServiceConnected(ComponentName className,
                IBinder service) {
            mBound = true;
        }

        public void onServiceDisconnected(ComponentName name) {
            mBound = false;
        };
    };
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mAppServiceIntent = new Intent(this, getAppActiveServiceClass());
    }
    
    @Override
    protected void onStart() {
        super.onStart();
        bindService(mAppServiceIntent, mConnection, Context.BIND_AUTO_CREATE);
        
    }
    
    @Override
    protected void onStop() {
        super.onStop();
        if(mBound){
            unbindService(mConnection);
            mBound = false;
        }
    }
    
    /**
     * This method must be overridden to return the class of your implementation of {@link AbstractAppActiveService}.
     * @return the class of your app implementation of {@code AbstractAppActiveService}
     */
    protected abstract Class<? extends AbstractAppActiveService> getAppActiveServiceClass();
}
