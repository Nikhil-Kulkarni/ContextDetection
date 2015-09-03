package com.example.nikhilkulkarni.contextdetection;

import android.app.IntentService;
import android.content.Context;
import android.content.Intent;

import com.google.android.gms.location.ActivityRecognitionResult;
import com.google.android.gms.location.DetectedActivity;

import android.util.Log;
import android.widget.Toast;

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
            Context context = getApplicationContext();
            CharSequence text = result.toString();
            int duration = Toast.LENGTH_LONG;
            Toast toast = Toast.makeText(context, text, duration);
            toast.show();

            System.out.println(result.toString());
        } else {
            System.out.println("No activity recognition data");
            Context context = getApplicationContext();
            CharSequence text = "No activity recognition data was returned";
            int duration = Toast.LENGTH_LONG;
            Toast toast = Toast.makeText(context, text, duration);
            toast.show();
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
