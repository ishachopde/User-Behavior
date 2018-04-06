package chopde.developer.userbehaviour;

import android.Manifest;
import android.app.PendingIntent;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.ActivityRecognition;
import com.google.android.gms.location.ActivityRecognitionClient;
public class MainActivity extends AppCompatActivity implements GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener{

    private static final String TAG = "ABC";
    private static final int PERMISSION_CONSTANT = 3;
    public GoogleApiClient myClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        myClient = new GoogleApiClient.Builder(this)
                        .addApi(ActivityRecognition.API)
                        .addConnectionCallbacks(this)
                        .addOnConnectionFailedListener(this)
                        .build();
        myClient.connect();
        if(ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED)
        {
            ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, PERMISSION_CONSTANT);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] results) {
        switch (requestCode)
        {
            case PERMISSION_CONSTANT:
            {
                if(results.length > 0 && results[0] == PackageManager.PERMISSION_GRANTED)
                {
                    Log.i("Permission:","LOCATION FINE ACCESS GRANTED");
                }
            }
        }
    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {
        Intent intent = new Intent(this, ActivityRecognize.class);
        PendingIntent pIntent = PendingIntent.getService(this,0 ,intent ,PendingIntent.FLAG_UPDATE_CURRENT);
        ActivityRecognitionClient activityRecognitionClient = ActivityRecognition.getClient(this);
        //ActivityRecognition.ActivityRecognitionApi.requestActivityUpdates(myClient, 3000, pIntent);
        activityRecognitionClient.requestActivityUpdates(3000,pIntent);
    }

    @Override
    public void onConnectionSuspended(int i) {
        Log.i(TAG,"Suspended");
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        Log.i(TAG,"Failed");
    }
}
