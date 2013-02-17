package com.github.curioustechizen.android.apppause;

import android.app.Application;

/**
 * This abstract {@code Application} must be extended for the android-app-pause library to function properly.
 * Also, the implementation class must be registered in the AndroidManifest.xml  
 *
 */
public abstract class AbstractAppPauseApplication extends Application {

    /*
     * No synchronization required since bind() and unbind() are always called from
     * the same thread (the "main thread" or the "UI thread")
     * 
     * This is because bind() is always called from an Activity's onStart(),
     *  and unbind() always from an Activity's onStop().  
     */
    private int mBoundCount = 0;
    
    protected void bind(){
        if(mBoundCount == 0){
            this.onAppResume();
        }
        mBoundCount ++;
    }
    
    protected void unbind(){
        mBoundCount --;
        if(mBoundCount == 0){
            this.onAppPause();
        }
    }
    
    /**
     * This method is called when the app goes into the background. This means 
     * no {@code Activity} from the app is visible to the user any longer. The
     * typical tasks to perform at this point would be to stop HTTP 
     * communication, cancel any scheduled Alarms, unregister any 
     * BroadcastReceivers etc.
     * <br/><br/>
     * Do note that it doesn't make sense to perform any UI tasks here
     *  since <em>the user is not interacting with your app at this point</em>.
     * <br/><br/>
     * Do not perform long-running operations here.
     *   
     */
    protected abstract void onAppPause();
    
    /**
     * This method is called when the app comes back to the foreground. This
     * means at least one of the {@code Activity}s from the app is now visible
     * to the user.
     * <br/><br/>
     * Do not perform long-running operations here.
     */
    protected abstract void onAppResume();
}
