package chopde.developer.userbehaviour;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class Data extends AppCompatActivity {

    int d[][];

    Context context;
    SharedPreferences locationFile_mon,locationFile_tue,locationFile_wed,locationFile_thu,locationFile_fri,locationFile_sat,locationFile_sun;
    SharedPreferences activityFile_mon,activityFile_tue,activityFile_wed,activityFile_thu,activityFile_fri,activityFile_sat,activityFile_sun;
    SharedPreferences locationFile,activityFile;
    TextView tv1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_data);
        context = this.getApplicationContext();
        assign();
        tv1 = (TextView) findViewById(R.id.textView);
        filldata();
        d= new int[7][49];
    }

    public void filldata()
    {
        for(int i = 1; i<=7;i++)
        {
            assignNow(i);
            for(int tempID : Utilities.ids)
            {

            }
        }
    }
    public void assignNow(int i)
    {
        switch(i) {
            case 1: {
                locationFile_mon = context.getSharedPreferences(getString(R.string.file_key_location_mon), Context.MODE_PRIVATE);
                activityFile_mon = context.getSharedPreferences(getString(R.string.file_key_activity_mon), Context.MODE_PRIVATE);
                break;
            }
            case 2:
            {
                locationFile_tue = context.getSharedPreferences(getString(R.string.file_key_location_tue), Context.MODE_PRIVATE);
                activityFile_tue = context.getSharedPreferences(getString(R.string.file_key_activity_tue), Context.MODE_PRIVATE);
                break;
            }
            case 3:
            {
                locationFile_wed = context.getSharedPreferences(getString(R.string.file_key_location_wed), Context.MODE_PRIVATE);
                activityFile_wed = context.getSharedPreferences(getString(R.string.file_key_activity_wed), Context.MODE_PRIVATE);
                break;
            }
            case 4:
            {
                locationFile_thu = context.getSharedPreferences(getString(R.string.file_key_location_thu), Context.MODE_PRIVATE);
                activityFile_thu = context.getSharedPreferences(getString(R.string.file_key_activity_thu), Context.MODE_PRIVATE);
                break;
            }
            case 5:
            {
                locationFile_fri = context.getSharedPreferences(getString(R.string.file_key_location_fri), Context.MODE_PRIVATE);
                activityFile_fri = context.getSharedPreferences(getString(R.string.file_key_activity_fri), Context.MODE_PRIVATE);
                break;
            }
            case 6:
            {
                locationFile_sat = context.getSharedPreferences(getString(R.string.file_key_location_sat), Context.MODE_PRIVATE);
                activityFile_sat = context.getSharedPreferences(getString(R.string.file_key_activity_sat), Context.MODE_PRIVATE);
                break;
            }
            case 7:
            {
                locationFile_sun = context.getSharedPreferences(getString(R.string.file_key_location_sun), Context.MODE_PRIVATE);
                activityFile_sun = context.getSharedPreferences(getString(R.string.file_key_activity_sun), Context.MODE_PRIVATE);
            }
        }
    }

    public void assign()
    {
        locationFile_mon = context.getSharedPreferences(getString(R.string.file_key_location_mon), Context.MODE_PRIVATE);
        locationFile_tue = context.getSharedPreferences(getString(R.string.file_key_location_tue), Context.MODE_PRIVATE);
        locationFile_wed = context.getSharedPreferences(getString(R.string.file_key_location_wed), Context.MODE_PRIVATE);
        locationFile_thu = context.getSharedPreferences(getString(R.string.file_key_location_thu), Context.MODE_PRIVATE);
        locationFile_fri = context.getSharedPreferences(getString(R.string.file_key_location_fri), Context.MODE_PRIVATE);
        locationFile_sat = context.getSharedPreferences(getString(R.string.file_key_location_sat), Context.MODE_PRIVATE);
        locationFile_sun = context.getSharedPreferences(getString(R.string.file_key_location_sun), Context.MODE_PRIVATE);
        activityFile_mon = context.getSharedPreferences(getString(R.string.file_key_activity_mon), Context.MODE_PRIVATE);
        activityFile_tue = context.getSharedPreferences(getString(R.string.file_key_activity_tue), Context.MODE_PRIVATE);
        activityFile_wed = context.getSharedPreferences(getString(R.string.file_key_activity_wed), Context.MODE_PRIVATE);
        activityFile_thu = context.getSharedPreferences(getString(R.string.file_key_activity_thu), Context.MODE_PRIVATE);
        activityFile_fri = context.getSharedPreferences(getString(R.string.file_key_activity_fri), Context.MODE_PRIVATE);
        activityFile_sat = context.getSharedPreferences(getString(R.string.file_key_activity_sat), Context.MODE_PRIVATE);
        activityFile_sun = context.getSharedPreferences(getString(R.string.file_key_activity_sun), Context.MODE_PRIVATE);
    }
}
