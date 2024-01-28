package pack.sor.carnote;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import java.util.Date;
import java.util.concurrent.TimeUnit;

import pack.sor.carnote.model.AutoData;

/**
 * Aktywność liczenia czasu potrzebnego na rozpędzenie auta do setki
 */

public class GpsActivity extends AppCompatActivity implements LocationListener
{
    public static final String NEW_GPS_RECORD = "NEW_GPS_RECORD";

    private TextView bestSpeed;
    private TextView currentSpeed;
    private TextView lastSpeed;
    private TextView infoText;

    private LocationManager locationManager;

    private Date startTime;
    private boolean wasCounted;
    private long diffInSec;

    private AutoData autoData;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        obtainExtras();
        setContentView(R.layout.gps_layout);
        setTitle(getResources().getString(R.string.gps_title));

        bestSpeed = (TextView) findViewById(R.id.best_speed);
        lastSpeed = (TextView) findViewById(R.id.last_speed);
        currentSpeed = (TextView) findViewById(R.id.current_speed);

        bestSpeed.setText(autoData.getBestSpeed() + " sec");
        infoText = (TextView) findViewById(R.id.info);
        infoText.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                startTime = new Date();
                wasCounted = false;
            }
        });
        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
    }

    private void obtainExtras()
    {
        autoData = (AutoData) getIntent().getExtras().getSerializable(MenuActivity.SPECIAL_DATA);
    }

    @SuppressLint("MissingPermission")
    @Override
    protected void onResume()
    {
        super.onResume();
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 500L, 2.0f, this);
    }

    @Override
    protected void onPause()
    {
        super.onPause();
        locationManager.removeUpdates(this);
    }

    @Override
    public void onLocationChanged(Location location)
    {
        float speed = location.getSpeed();
        float kmhspeed = speed * 3600 / 1000;
        currentSpeed.setText(kmhspeed + " km/h");

        if (kmhspeed < 5)
        {
            wasCounted = false;
            infoText.setText("Możesz ruszać");
        }
        if (kmhspeed >= 100 && !wasCounted)
        {
            infoText.setText("Zatrzymaj się");
            long diffInMs = new Date().getTime() - startTime.getTime();
            diffInSec = TimeUnit.MILLISECONDS.toSeconds(diffInMs);
            lastSpeed.setText(diffInSec + " sec");
            if (autoData.getBestSpeed().compareTo(diffInSec) > 0)
            {
                bestSpeed.setText(diffInSec + " sec");
            }
        }
    }

    @Override
    public void onBackPressed()
    {
        Intent intent = new Intent();
        intent.putExtra(NEW_GPS_RECORD, diffInSec);
        setResult(Activity.RESULT_CANCELED, intent);
        finish();
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras)
    {

    }

    @Override
    public void onProviderEnabled(String provider)
    {

    }

    @Override
    public void onProviderDisabled(String provider)
    {

    }
}
