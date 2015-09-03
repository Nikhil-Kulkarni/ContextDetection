package com.example.nikhilkulkarni.contextdetection;

import android.app.IntentService;
import android.content.Context;
import android.content.Intent;

import com.google.android.gms.location.ActivityRecognitionResult;
import com.google.android.gms.location.DetectedActivity;

import android.util.Log;
import android.widget.Toast;

import java.util.logging.Handler;

/**
 * Created by nikhilkulkarni on 8/30/15.
 */
public class ActivityRecognitionService extends IntentService {

    private static final String TAG = "ActivityRecognition";

    public ActivityRecognitionService() {
        super("ActivityRecognitionService");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        if (ActivityRecognitionResult.hasResult(intent)) {
            ActivityRecognitionResult result = ActivityRecognitionResult.extractResult(intent);
            Log.d(TAG, "ActivityRecognitionResult: " + getFriendlyName(result.getMostProbableActivity().getType()));
            final Context context = getApplicationContext();
            final CharSequence text = getFriendlyName(result.getMostProbableActivity().getType());

            android.os.Handler handler = new android.os.Handler(getMainLooper());
            handler.post(new Runnable() {
                @Override
                public void run() {
                    int duration = Toast.LENGTH_LONG;
                    Toast toast = Toast.makeText(context, text, duration);
                    toast.show();
                }
            });

            System.out.println(result.toString());
        } else {
            System.out.println("No activity recognition data");
            final Context context = getApplicationContext();
            final CharSequence text = "No activity recognition data was returned";

            android.os.Handler handler = new android.os.Handler(getMainLooper());
            handler.post(new Runnable() {
                @Override
                public void run() {
                    int duration = Toast.LENGTH_LONG;
                    Toast toast = Toast.makeText(context, text, duration);
                    toast.show();
                }
            });
        }
    }

    private String actType(String activity) {
        return activity;
    }

    private String getFriendlyName(int activityType) {
        switch (activityType ) {
            case DetectedActivity.IN_VEHICLE:
                return "Driving";
            case DetectedActivity.ON_BICYCLE:
                return "On Bicycle";
            case DetectedActivity.ON_FOOT:
                return "Walking";
            case DetectedActivity.TILTING:
                return "Tilting";
            case DetectedActivity.STILL:
                return "Still";
            default:
                return "unknown";
        }
    }
}
