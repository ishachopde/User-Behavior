package chopde.developer.userbehaviour;

import android.Manifest;
import android.app.IntentService;
import android.app.PendingIntent;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.util.Log;

import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.PlaceDetectionClient;
import com.google.android.gms.location.places.PlaceFilter;
import com.google.android.gms.location.places.PlaceLikelihood;
import com.google.android.gms.location.places.PlaceLikelihoodBufferResponse;
import com.google.android.gms.location.places.Places;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

import java.util.ArrayList;
import java.util.List;

import static android.content.ContentValues.TAG;

/**
 * Created by id on 4/9/2018.
 */

public class locationFetcher extends IntentService {

    PlaceDetectionClient mPlaceDetectionClient;
    locationFetcher()
    {
        super("Fetch Location");
    }

    @Override
    public void onCreate()
    {
        super.onCreate();
        mPlaceDetectionClient = Places.getPlaceDetectionClient(this);
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        FetchLocation();
    }

    public void FetchLocation()
    {
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
