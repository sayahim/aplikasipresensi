package com.himorfosis.presensi;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class Tentang extends AppCompatActivity  {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tentang);

        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setCustomView(R.layout.toolbar);

        TextView title = (TextView) getSupportActionBar().getCustomView().findViewById(R.id.title);
        Button kembali = (Button) getSupportActionBar().getCustomView().findViewById(R.id.kembali);
        title.setText("Tentang Aplikias");

        kembali.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent in = new Intent(Tentang.this, Utama.class);
                startActivity(in);

            }
        });



//        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
//        fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                mapFragment.getMapAsync(new OnMapReadyCallback() {
//                    @Override
//                    public void onMapReady(GoogleMap googleMap) {
//                        googleMap.setMapType(GoogleMap.MAP_TYPE_TERRAIN);
//
//                        googleMap.addMarker(new MarkerOptions()
//                                .position(new LatLng(37.4233438, -122.0728817))
//                                .title("LinkedIn")
//                                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN)));
//
//                        googleMap.addMarker(new MarkerOptions()
//                                .position(new LatLng(37.4629101,-122.2449094))
//                                .title("Facebook")
//                                .snippet("Facebook HQ: Menlo Park"));
//
//                        googleMap.addMarker(new MarkerOptions()
//                                .position(new LatLng(37.3092293, -122.1136845))
//                                .title("Apple"));
//
//                        googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(37.4233438, -122.0728817), 10));
//                    }
//                });
//            }
//        });

    }

//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        // Inflate the menu; this adds items to the action bar if it is present.
//        getMenuInflater().inflate(R.menu.menu_main, menu);
//        return true;
//    }
//
//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        // Handle action bar item clicks here. The action bar will
//        // automatically handle clicks on the Home/Up button, so long
//        // as you specify a parent activity in AndroidManifest.xml.
//        int id = item.getItemId();
//
//        //noinspection SimplifiableIfStatement
//        if (id == R.id.action_settings) {
//            return true;
//        }
//
//        return super.onOptionsItemSelected(item);
//    }


}
