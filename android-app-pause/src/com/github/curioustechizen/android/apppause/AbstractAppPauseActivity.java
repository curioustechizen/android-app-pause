
package com.github.curioustechizen.android.apppause;

import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;

/**
 * Base {@code Activity} for the android-app-pause library. Every Activity in
 * the app must extend this Activity.
 */
public abstract class AbstractAppPauseActivity extends FragmentActivity {

    private AbstractAppPauseApplication mApplication;

    private boolean mRotated;

    private boolean mTreatRotationAsAppPause = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mApplication = (AbstractAppPauseApplication)getApplication();

        Boolean nonConfigState =
                (Boolean)getLastCustomNonConfigurationInstance();
        if (nonConfigState == null) {
            mRotated = false;
        } else {
            mRotated = nonConfigState.booleanValue();
        }

    }

    @Override
    protected void onStart() {
        super.onStart();

        /*
         * We check to see if onStart() is being called as a result of device
         * orientation change. If NOT, only then we bind to the application. If
         * mRotated is true, we are already bound to the application.
         */
        if (!mRotated) {
            mApplication.bind();
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        mRotated = false;

        /*
         * First, check if rotations should be treated as app pause. If yes,
         * then _always unbind_ in onStop().
         */
        if (mTreatRotationAsAppPause) {
            mApplication.unbind();
            return;
        }

        /*
         * The following code is only required to be executed if device
         * rotations should NOT be treated as app pauses (this is the default behaviour. 
         * 
         * It checks to see if the onStop() is being called because of an orientation change. 
         * If so, it does NOT perform the unbind.
         */

        // if (isChangingConfigurations()) {
        int changingConfig = getChangingConfigurations();
        if (BuildConfig.DEBUG) {
            Log.d("android-app-pause",
                    String.format("Changing Config: %d", changingConfig));
        }
        if ((changingConfig & ActivityInfo.CONFIG_ORIENTATION) == ActivityInfo.CONFIG_ORIENTATION) {
            mRotated = true;
        }
        // }
        if (!mRotated) {
            mApplication.unbind();
        }
    }

    @Override
    public Object onRetainCustomNonConfigurationInstance() {
        return mRotated ? Boolean.TRUE : Boolean.FALSE;
    }

    /**
     * Controls whether device orientation changes get treated as app pause
     * events. If you want device orientation changes to trigger app pause
     * events, call this method with an argument of {@code true}. <br/>
     * <br/>
     * <strong>Note:</strong> This method <strong>must be called from
     * {@code onCreate()} method</strong> of your sub-class of this
     * {@code Activity}.
     * 
     * @param treatRotationAsAppPause If {@code true}, device rotations will
     *            trigger app pause events. Defaults to {@code false}.
     */
    protected void setTreatRotationAsAppPause(boolean treatRotationAsAppPause) {
        this.mTreatRotationAsAppPause = treatRotationAsAppPause;
    }

}
