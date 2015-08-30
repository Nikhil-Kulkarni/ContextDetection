package com.example.nikhilkulkarni.contextdetection;

import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.ActivityRecognition;
import com.google.android.gms.location.ActivityRecognitionApi;

/**
 * Created by nikhilkulkarni on 8/30/15.
 */
public class ActivityRecognitionScan implements GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener {

    private Context context;
    private static final String TAG = "ActivityRecognition";
    private GoogleApiClient mAPIClient;
    private static PendingIntent callbackIntent;

    public ActivityRecognitionScan(Context context) {
        this.context = context;
    }

    public void startActivityRecognitionScan() {
        mAPIClient = new GoogleApiClient.Builder(this.context)
                .addApi(ActivityRecognition.API)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .build();
        mAPIClient.connect();
        Log.d(TAG, "startActivityRecognitionScan");
    }

    public void stopActivityRecognitionScan() {
        mAPIClient.disconnect();
    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {
        Log.d(TAG, "connectionFailed");
    }

    @Override
    public void onConnected(Bundle bundle) {
        Log.d(TAG, "connected");
        Intent intent = new Intent(context, ActivityRecognitionService.class);
        callbackIntent = PendingIntent.getService(this.context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        ActivityRecognition.ActivityRecognitionApi.requestActivityUpdates(mAPIClient, 0, callbackIntent);
    }

    @Override
    public void onConnectionSuspended(int i) {

    }
}
