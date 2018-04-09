package chopde.developer.userbehaviour;

import android.Manifest;
import android.app.IntentService;
import android.app.Notification;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Location;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.util.Log;

import com.google.android.gms.location.ActivityRecognitionResult;
import com.google.android.gms.location.DetectedActivity;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.PlaceFilter;
import com.google.android.gms.location.places.PlaceLikelihood;
import com.google.android.gms.location.places.PlaceLikelihoodBufferResponse;
import com.google.android.gms.location.places.Places;
import com.google.android.gms.location.places.PlaceDetectionClient;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import static android.content.ContentValues.TAG;
import static chopde.developer.userbehaviour.Utilities.calender;

/**
 * Created by id on 3/27/2018.
 */

public class ActivityRecognize extends IntentService {
    PlaceDetectionClient mPlaceDetectionClient;
    Context context;
    SharedPreferences locationFile,activityFile;
    SharedPreferences.Editor locationEditor,activityEditor;
    Date date;

    public ActivityRecognize() {super("ActivityRecognize");}

    public ActivityRecognize(String name) {
        super(name);
    }

    public void onCreate()
    {
        super.onCreate();
        mPlaceDetectionClient = Places.getPlaceDetectionClient(this);
        context = this.getApplicationContext();
        date = calender.getTime();
        assign();
    }



    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        if (ActivityRecognitionResult.hasResult(intent)) {
            ActivityRecognitionResult result = ActivityRecognitionResult.extractResult(intent);
            handleActivities(result.getProbableActivities());
            FetchLocation();
        }
    }

    private void handleActivities(List<DetectedActivity> pActivities)
    {
        int currentActivity = -1;
        for(DetectedActivity unitActivity : pActivities)
        {
            switch(unitActivity.getType())
            {
                case DetectedActivity.IN_VEHICLE :
                {
                    Log.e("ActivityRecognition", "IN Vehicle: " + unitActivity.getConfidence());
                   if(unitActivity.getConfidence() >= 75)
                        {
                        currentActivity = activity.Driving.ordinal();
                    }
                    break;
                }
                case DetectedActivity.ON_BICYCLE :
                {
                    Log.e("ActivityRecognition", "On Bicycle: " + unitActivity.getConfidence());
                    if(unitActivity.getConfidence() >= 75)
                    {
                        currentActivity = activity.Cycling.ordinal();
                    }
                    break;
                }
                case DetectedActivity.ON_FOOT :
                {
                    Log.e("ActivityRecognition", "Walking: " + unitActivity.getConfidence());
                    if(unitActivity.getConfidence() >= 75)
                    {
                        currentActivity = activity.Walking.ordinal();
                    }
                    break;
                }
                case DetectedActivity.STILL:
                {
                    Log.e("ActivityRecognition", "Lazy: " + unitActivity.getConfidence());
                    if(unitActivity.getConfidence() >= 75)
                    {
                        currentActivity = activity.Still.ordinal();

                    }
                    break;
                }
                case DetectedActivity.RUNNING:
                {
                    Log.e("ActivityRecognition", "Running: " + unitActivity.getConfidence());
                   if(unitActivity.getConfidence() >= 75)
                    {
                        currentActivity = activity.Running.ordinal();

                    }
                    break;
                }
                case DetectedActivity.UNKNOWN:
                {
                    Log.e("ActivityRecognition", "Unknown: " + unitActivity.getConfidence());
                    currentActivity = -1;
                    break;
                }
            }
            if(currentActivity != -1) {
                Log.e("Current Activity",Integer.toString(currentActivity));
                int val = activityFile.getInt(Integer.toString(currentActivity+1), 0);
                val+=1;
                activityEditor.putInt(Integer.toString(currentActivity+1), val);
                activityEditor.apply();
            }
        }
    }

    public int returnDate()
    {
        String day = new SimpleDateFormat("EE", Locale.ENGLISH).format(date.getTime());
        Log.e("DAY", day);
        switch(day)
        {
            case "Mon": {
                return 1;
            }
            case "Tue": {
               return 2;
            }
            case "Wed": {
                return 3;
            }
            case "Thu": {
                return 4;
            }
            case "Fri": {
                return 5;
            }
            case "Sat": {
                return 6;
            }
            case "Sun": {
                return 7;
            }
        }
        return 1;
    }

    public void FetchLocation()
    {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            Log.i(TAG,"Permission Denied");
        }
        else
        {
            //List<String> filters = new ArrayList<>();
            Task<PlaceLikelihoodBufferResponse> pResult = mPlaceDetectionClient.getCurrentPlace(null);
            pResult.addOnCompleteListener(new OnCompleteListener<PlaceLikelihoodBufferResponse>() {
                @Override
                public void onComplete(@NonNull Task<PlaceLikelihoodBufferResponse> task) {
                    PlaceLikelihoodBufferResponse likelyPlaces = task.getResult();
                    for(PlaceLikelihood placeLikelihood : likelyPlaces)
                    {
                        if(placeLikelihood.getLikelihood() > 0.8)
                        {
                            fillValues(placeLikelihood.getPlace().getPlaceTypes());
                            Log.i(TAG, String.format("Place '%s' has likelihood: %s",
                                    placeLikelihood.getPlace().getName(),
                                    placeLikelihood.getPlace().getPlaceTypes()));
                        }

                    }
                }
            });
            Log.i(TAG,"Permission Granted");
        }
    }

    public void fillValues(List<Integer> id)
    {
        String tempString;
            for(int tempID : id)
            {
                tempString = Integer.toString(tempID);
                if(Utilities.sortedids.contains(tempID))
                {
                    int k = locationFile.getInt(tempString,0);
                    k++;
                    locationEditor.putInt(tempString,k);
                    locationEditor.apply();
                    Log.i("Writing " + tempString,Integer.toString(k));
                }
            }

    }

    public void assign()
    {
        String day = new SimpleDateFormat("EE", Locale.ENGLISH).format(date.getTime());
        Log.e("DAY", day);
        switch(day)
        {
            case "Mon": {
                Log.e("DAY", Integer.toString(1));
                locationFile = context.getSharedPreferences(getString(R.string.file_key_location_mon), Context.MODE_PRIVATE);
                activityFile = context.getSharedPreferences(getString(R.string.file_key_activity_mon), Context.MODE_PRIVATE);
                break;
            }
            case "Tue": {
                Log.e("DAY", Integer.toString(2));
                locationFile = context.getSharedPreferences(getString(R.string.file_key_location_tue), Context.MODE_PRIVATE);
                activityFile = context.getSharedPreferences(getString(R.string.file_key_activity_tue), Context.MODE_PRIVATE);
                break;
            }
            case "Wed": {
                Log.e("DAY", Integer.toString(3));
                locationFile = context.getSharedPreferences(getString(R.string.file_key_location_wed), Context.MODE_PRIVATE);
                activityFile = context.getSharedPreferences(getString(R.string.file_key_activity_wed), Context.MODE_PRIVATE);
                break;
            }
            case "Thu": {
                Log.e("DAY", Integer.toString(4));
                locationFile = context.getSharedPreferences(getString(R.string.file_key_location_thu), Context.MODE_PRIVATE);
                activityFile = context.getSharedPreferences(getString(R.string.file_key_activity_thu), Context.MODE_PRIVATE);
                break;
            }
            case "Fri": {
                Log.e("DAY", Integer.toString(5));
                locationFile = context.getSharedPreferences(getString(R.string.file_key_location_fri), Context.MODE_PRIVATE);
                activityFile = context.getSharedPreferences(getString(R.string.file_key_activity_fri), Context.MODE_PRIVATE);
                break;
            }
            case "Sat": {
                Log.e("DAY", Integer.toString(6));
                locationFile = context.getSharedPreferences(getString(R.string.file_key_location_sat), Context.MODE_PRIVATE);
                activityFile = context.getSharedPreferences(getString(R.string.file_key_activity_sat), Context.MODE_PRIVATE);
                break;
            }
            case "Sun": {
                Log.e("DAY", Integer.toString(7));
                locationFile = context.getSharedPreferences(getString(R.string.file_key_location_sun), Context.MODE_PRIVATE);
                activityFile = context.getSharedPreferences(getString(R.string.file_key_activity_sun), Context.MODE_PRIVATE);
                break;
            }
        }
        locationEditor = locationFile.edit();
        activityEditor = activityFile.edit();
    }

}
