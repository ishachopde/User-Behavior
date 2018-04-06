package chopde.developer.userbehaviour;

import android.Manifest;
import android.app.IntentService;
import android.app.Notification;
import android.content.Intent;
import android.content.pm.PackageManager;
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

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import static android.content.ContentValues.TAG;

/**
 * Created by id on 3/27/2018.
 */

public class ActivityRecognize extends IntentService {
    PlaceDetectionClient mPlaceDetectionClient;

    public ActivityRecognize() {super("ActivityRecognize");}

    public ActivityRecognize(String name) {
        super(name);
    }

    @Override
    public void onCreate()
    {
        super.onCreate();
        //mPlaceDetectionClient = Places.getPlaceDetectionClient(this, null);
        mPlaceDetectionClient = Places.getPlaceDetectionClient(this);    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        if (ActivityRecognitionResult.hasResult(intent)) {
            ActivityRecognitionResult result = ActivityRecognitionResult.extractResult(intent);
            handleActivities(result.getProbableActivities());

            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                Log.i(TAG,"Permission Denied");
            }
            else
            {
                List<String> filters = new ArrayList<>();
                filters.add(String.valueOf(Place.TYPE_CAFE));
                PlaceFilter pf = new PlaceFilter(false,filters);
                Task<PlaceLikelihoodBufferResponse> pResult = mPlaceDetectionClient.getCurrentPlace(pf);
                pResult.addOnCompleteListener(new OnCompleteListener<PlaceLikelihoodBufferResponse>() {
                    @Override
                    public void onComplete(@NonNull Task<PlaceLikelihoodBufferResponse> task) {
                        PlaceLikelihoodBufferResponse likelyPlaces = task.getResult();
                        for(PlaceLikelihood placeLikelihood : likelyPlaces)
                        {
                            Log.i(TAG, String.format("Place '%s' has likelihood: %s",
                                    placeLikelihood.getPlace().getName(),
                                    placeLikelihood.getPlace().getPlaceTypes()));
                        }
                    }
                });
                Log.i(TAG,"Permission Granted");

            }
        }
    }

    private void handleActivities(List<DetectedActivity> pActivities)
    {
        for(DetectedActivity unitActivity : pActivities)
        {
            switch(unitActivity.getType())
            {
                case DetectedActivity.IN_VEHICLE :
                {
                    Log.e("ActivityRecognition", "IN Vehicle: " + unitActivity.getConfidence());
                   //if(unitActivity.getConfidence() >= 75)
                    {
                        NotificationCompat.Builder builder = new NotificationCompat.Builder(this);
                        builder.setContentText("Are you Driving?");
                        builder.setSmallIcon(R.mipmap.ic_launcher);
                        builder.setContentTitle(getString(R.string.app_name));
                        NotificationManagerCompat.from(this).notify(0,builder.build());
                    }
                    break;
                }
                case DetectedActivity.ON_BICYCLE :
                {
                    Log.e("ActivityRecognition", "On Bicycle: " + unitActivity.getConfidence());
                    //if(unitActivity.getConfidence() >= 75)
                    {
                        NotificationCompat.Builder builder = new NotificationCompat.Builder(this);
                        builder.setContentText("Are you Cycling?");
                        builder.setSmallIcon(R.mipmap.ic_launcher);
                        builder.setContentTitle(getString(R.string.app_name));
                        NotificationManagerCompat.from(this).notify(0,builder.build());
                    }
                    break;
                }
                case DetectedActivity.ON_FOOT :
                {
                    Log.e("ActivityRecognition", "Walking: " + unitActivity.getConfidence());
                    NotificationCompat.Builder builder = new NotificationCompat.Builder(this);
                    builder.setContentText("Are you Lazy?");
                    builder.setSmallIcon(R.mipmap.ic_launcher);
                    builder.setContentTitle(getString(R.string.app_name));
                    NotificationManagerCompat.from(this).notify(0,builder.build());
                    break;
                }
                case DetectedActivity.STILL:
                {
                    Log.e("ActivityRecognition", "Lazy: " + unitActivity.getConfidence());
                    //if(unitActivity.getConfidence() >= 75)
                    {
                        NotificationCompat.Builder builder = new NotificationCompat.Builder(this);
                        builder.setContentText("Are you Lazy?");
                        builder.setSmallIcon(R.mipmap.ic_launcher);
                        builder.setContentTitle(getString(R.string.app_name));
                        NotificationManagerCompat.from(this).notify(0,builder.build());
                    }
                    break;
                }
                case DetectedActivity.RUNNING:
                {
                    Log.e("ActivityRecognition", "Running: " + unitActivity.getConfidence());
                   //if(unitActivity.getConfidence() >= 75)
                    {
                        NotificationCompat.Builder builder = new NotificationCompat.Builder(this);
                        builder.setContentText("Are you Running?");
                        builder.setSmallIcon(R.mipmap.ic_launcher);
                        builder.setContentTitle(getString(R.string.app_name));
                        NotificationManagerCompat.from(this).notify(0,builder.build());
                    }
                    break;
                }
                case DetectedActivity.UNKNOWN:
                {
                    Log.e("ActivityRecognition", "Unknown: " + unitActivity.getConfidence());
                    break;
                }
            }
        }
    }
}
