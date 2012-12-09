
package com.github.curioustechizen.android.apppause;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;

/**
 * The {@code Service} that is central to the android-app-pause library. This 
 * {@code Service} is active as long as at least one {@code Activity} from the 
 * app is visible to the user. 
 * <br/><br/>
 * The {@link #onAppPause()} method gets executed when the app "goes into the 
 * background", i.e., when no {@code Activity} from the app is visible to the
 * user.
 * <br/><br/>
 * The {@link #onAppResume()} method gets executed when the app "comes to the
 * foreground", i.e., when one of the {@code Activity}s from the app becomes
 * visible to the user.
 */
public abstract class AbstractAppActiveService extends Service {
    
    private final IBinder mBinder = new LocalBinder();
    
    public class LocalBinder extends Binder {
        AbstractAppActiveService getService() {
            return AbstractAppActiveService.this;
        }
    }
    
    @Override
    public void onCreate() {
        super.onCreate();
    }
    
    public AbstractAppActiveService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        this.onAppResume();
        return mBinder;
    }
    
    @Override
    public void onDestroy() {
        super.onDestroy();
        this.onAppPause();
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
