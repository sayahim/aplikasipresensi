package com.himorfosis.presensi;

import android.Manifest;
import android.content.Intent;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.test.mock.MockPackageManager;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

public class Utama extends AppCompatActivity {

    CardView presensi, izin, profil, logout;

    private static final int REQUEST_CODE_PERMISSION = 2;
    String mPermission = Manifest.permission.ACCESS_FINE_LOCATION;

    // GPSTracker class
    LocationUser lokasi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.utama);

        presensi = findViewById(R.id.presensi);
        izin = findViewById(R.id.izin);
        profil = findViewById(R.id.profil);
        logout = findViewById(R.id.logout);

        lokasi = new LocationUser(Utama.this);

        // check if GPS enabled
        if(lokasi.canGetLocation()) {

            double latitude = lokasi.getLatitude();
            double longitude = lokasi.getLongitude();

            // \n is for new line
//            Toast.makeText(getApplicationContext(), "Your Location is - \nLat: " + latitude + "\nLong: " + longitude, Toast.LENGTH_LONG).show();

            Log.e("lat long", "" +latitude + ", " + longitude);

        } else {
            // can't get location

            lokasi.showSettingsAlert();

        }

        presensi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent in = new Intent(Utama.this, Presensi.class);
                startActivity(in);

            }
        });

        izin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent in = new Intent(Utama.this, Izin.class);
                startActivity(in);

            }
        });

        profil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent in = new Intent(Utama.this, Profil.class);
                startActivity(in);

            }
        });

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                Util.deleteData("akun", getApplicationContext());

                Intent in = new Intent(Utama.this, Login.class);
                startActivity(in);

            }
        });

        // set location

        try {
            if (ActivityCompat.checkSelfPermission(this, mPermission) != MockPackageManager.PERMISSION_GRANTED) {

                ActivityCompat.requestPermissions(this, new String[]{mPermission}, REQUEST_CODE_PERMISSION);

                // If any permission above not allowed by user,
                // this condition will execute every time, else your else part will work
            }
        } catch (Exception e) {

            e.printStackTrace();

        }

    }

    boolean doubleBackToExitPressedOnce = false;

    @Override
    public void onBackPressed() {

        if (doubleBackToExitPressedOnce) {

            finishAffinity();

            super.onBackPressed();
            return;
        }

        this.doubleBackToExitPressedOnce = true;


    }

}
