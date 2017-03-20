package com.example.rajukumarsingh.bloodbank;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity implements LocationListener {
    Button donor,acceptor,blood_bank,emergency;
    Double d1, d2;
    LocationManager locationManager;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        donor= (Button) findViewById(R.id.donor);
        acceptor= (Button) findViewById(R.id.button6);
        blood_bank= (Button) findViewById(R.id.button7);
        emergency= (Button) findViewById(R.id.button8);


        locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, this);



        donor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(MainActivity.this,Donor.class);
                intent.putExtra("Latitude",d1);
                intent.putExtra("Longitude",d2);
                startActivity(intent);
            }
        });
        acceptor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent1=new Intent(MainActivity.this,Acceptor.class);
                intent1.putExtra("Latitude",d1);
                intent1.putExtra("Longitude",d2);
                startActivity(intent1);
            }
        });
        emergency.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent2=new Intent(MainActivity.this,Bloodbank_list.class);
                intent2.putExtra("Latitude",d1);
                intent2.putExtra("Longitude",d2);
                startActivity(intent2);
            }
        });

    }

    @Override
    public void onLocationChanged(Location location) {
        d1 = location.getLatitude();
        d2 = location.getLongitude();
        //Toast.makeText(this, ""+d1, Toast.LENGTH_SHORT).show();
       // Toast.makeText(this, ""+d2, Toast.LENGTH_SHORT).show();

    }

    @Override
    public void onStatusChanged(String s, int i, Bundle bundle) {

    }

    @Override
    public void onProviderEnabled(String s) {

    }

    @Override
    public void onProviderDisabled(String s) {

    }
}
