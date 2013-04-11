
package com.github.curioustechizen.apppause.sample;

import com.github.curioustechizen.android.apppause.AbstractAppPauseApplication;
import com.koushikdutta.async.http.server.AsyncHttpServer;
import com.koushikdutta.async.http.server.AsyncHttpServerRequest;
import com.koushikdutta.async.http.server.AsyncHttpServerResponse;
import com.koushikdutta.async.http.server.HttpServerRequestCallback;

import android.content.Intent;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;

public class SampleAppPauseApplication extends AbstractAppPauseApplication {

    private static final String LOG_TAG = "AppPauseSample";
    private AsyncHttpServer mServer;
    private LocalBroadcastManager mLocalBroadcastManager;
    private static String sLastKnownUserAgent = null;
    private static long sLastRequestTimestamp;
       

    @Override
    public void onCreate() {
        super.onCreate();
        
        /*
         * The following code serves the purpose of this sample - but in a real app, you probably want to:
         * 
         * 1) Put the AsyncServer code in an Android Service
         * 2) Start the Service during onAppResume()
         * 3) Stop the Service during onAppPause()
         */
        
        mLocalBroadcastManager = LocalBroadcastManager.getInstance(getApplicationContext());
        mServer = new AsyncHttpServer();
        mServer.get("/", new HttpServerRequestCallback() {
            
            @Override
            public void onRequest(AsyncHttpServerRequest request,
                    AsyncHttpServerResponse response) {
                sLastKnownUserAgent = request.getHeaders().getHeaders().get("User-Agent");
                sLastRequestTimestamp = System.currentTimeMillis();
                broadcastRequestReceived();
                response.send("Hello World!");
            }
        });
    }
    
    @Override
    protected void onAppPause() {
        Log.d(LOG_TAG, getString(R.string.app_pause));
        stopServer();
    }

    @Override
    protected void onAppResume() {
        Log.d(LOG_TAG, getString(R.string.app_resume));
        startServer();
    }

    private void startServer() {
        mServer.listen(Constants.DEFAULT_SERVER_PORT);
    }
    

    private void stopServer() {
        mServer.stop();
    }
    
    private void broadcastRequestReceived() {
        mLocalBroadcastManager.sendBroadcast(new Intent(Constants.ACTION_REQUEST_RECEIVED));
    }
    
    public static String getLastRequestUserAgent(){
        return sLastKnownUserAgent;
    }
    
    public static long getLastRequestTimestamp(){
        return sLastRequestTimestamp;
    }
}
