package com.himorfosis.presensi;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

public class SplashScreen extends AppCompatActivity {

    String username, pass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash_screen);

        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {

                username = Util.getData("akun", "username", getApplicationContext());
                pass = Util.getData("akun", "password", getApplicationContext());

                Log.e("akun", ""+username);
                Log.e("akun", ""+pass);
//                Log.e("akun", ""+getuser);

                if (username == null || username.equals("") && pass == null || pass.equals("")) {

                    Log.e("data", "null");

                    Intent intent = new Intent(SplashScreen.this, Login.class);
                    startActivity(intent);

                } else {

                    Intent intent = new Intent(SplashScreen.this, Utama.class);
                    startActivity(intent);

                }

            }
        }, 2000L);

    }
}
