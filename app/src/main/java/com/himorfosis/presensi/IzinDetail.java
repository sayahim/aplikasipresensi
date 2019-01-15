package com.himorfosis.presensi;

import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;

public class IzinDetail extends AppCompatActivity {

    TextView mulai, selesai, jenis, keterangan;
    ImageView gambar;

    String getmulai, getselesai, getjenis, getketerangan, getgambar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.izin_detail);

        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setCustomView(R.layout.toolbar);

        TextView title = (TextView) getSupportActionBar().getCustomView().findViewById(R.id.title);
        Button kembali = (Button) getSupportActionBar().getCustomView().findViewById(R.id.kembali);
        title.setText("Izin Detail");

        kembali.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent in = new Intent(IzinDetail.this, Izin.class);
                startActivity(in);

            }
        });

        gambar = findViewById(R.id.gambar);
        mulai = findViewById(R.id.mulai);
        selesai = findViewById(R.id.selesai);
        jenis = findViewById(R.id.jenis);
        keterangan = findViewById(R.id.keterangan);
        gambar = findViewById(R.id.gambar);

        Intent data = getIntent();

        getmulai = data.getStringExtra("mulai");
        getselesai = data.getStringExtra("selesai");
        getjenis = data.getStringExtra("jenis");
        getketerangan = data.getStringExtra("keterangan");
        getgambar = data.getStringExtra("gambar");

        Log.e("gambar", ""+gambar);

        mulai.setText(getmulai);
        selesai.setText(getselesai);
        jenis.setText(getjenis);
        keterangan.setText(getketerangan);

        Glide.with(getApplicationContext())
                .load(getgambar)
                .into(gambar);

        Glide.with(getApplicationContext()).load(getgambar).into(gambar);

    }
}
