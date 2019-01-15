package com.himorfosis.presensi;

import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class PresensiDetail extends AppCompatActivity {

    TextView nama, tanggal, waktu, ketwaktu, lokasi, jenis, latitude, longtitude;
    String getnama, gettanggal, getwaktu, getlokasi, getjenis, getlatitude, getlongtitude;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.presensi_detail);

        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setCustomView(R.layout.toolbar);

        TextView title = (TextView) getSupportActionBar().getCustomView().findViewById(R.id.title);
        Button kembali = (Button) getSupportActionBar().getCustomView().findViewById(R.id.kembali);
        title.setText("Presensi Detail");

        kembali.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

//                finish();
                Intent in = new Intent(PresensiDetail.this, Presensi.class);
                startActivity(in);


            }
        });

        nama = findViewById(R.id.nama);
        tanggal = findViewById(R.id.tanggal);
        waktu = findViewById(R.id.waktu);
        ketwaktu = findViewById(R.id.ketwaktu);
        lokasi = findViewById(R.id.lokasi);
        jenis = findViewById(R.id.jenis);
        latitude = findViewById(R.id.latitude);
        longtitude = findViewById(R.id.longtitude);

        Intent data = getIntent();

        getnama = data.getStringExtra("nama");
        gettanggal = data.getStringExtra("tanggal");
        getwaktu = data.getStringExtra("waktu");
        getlokasi = data.getStringExtra("lokasi");
        getjenis = data.getStringExtra("jenis");
        getlatitude = data.getStringExtra("latitude");
        getlongtitude = data.getStringExtra("longtitude");

        nama.setText(getnama);
        tanggal.setText(gettanggal);
        waktu.setText(getwaktu);
        ketwaktu.setText("Waktu " + getjenis  + " Kerja");
        lokasi.setText(getlokasi);
        jenis.setText(getjenis);
        latitude.setText(getlatitude);
        longtitude.setText(getlongtitude);

    }
}
