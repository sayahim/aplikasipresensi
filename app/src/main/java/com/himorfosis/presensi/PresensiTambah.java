package com.himorfosis.presensi;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class PresensiTambah extends AppCompatActivity {

    EditText lokasi;
    TextView tvlatitude, tvlongtitude;
    TextView waktu;

    String getlokasi, getlatitude, getlongtitude, getid, getwaktu, gettanggal, getjenispresensi, getjam;
    Button presensi, peta;

    ProgressDialog pDialog;

    LocationUser loc;
    double latitude, longitude;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.presensi_tambah);

        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setCustomView(R.layout.toolbar);

        TextView title = (TextView) getSupportActionBar().getCustomView().findViewById(R.id.title);
        Button kembali = (Button) getSupportActionBar().getCustomView().findViewById(R.id.kembali);

        // Progress dialog
        pDialog = new ProgressDialog(this);
        pDialog.setCancelable(false);

        waktu = findViewById(R.id.waktu);
        lokasi = findViewById(R.id.lokasi);
        presensi = findViewById(R.id.tambah);
        tvlatitude = findViewById(R.id.latitude);
        tvlongtitude = findViewById(R.id.longtitude);
        peta = findViewById(R.id.peta);

        Intent data = getIntent();

        getjenispresensi = data.getStringExtra("data");

        getid = Util.getData("akun", "id", getApplicationContext());

        presensi.setText("Presensi " + getjenispresensi);
        title.setText("Presensi " + getjenispresensi);

        // get time presention

        waktu();

        waktu.setText(getwaktu);

        // get location user

        loc = new LocationUser(PresensiTambah.this);

        // check if GPS enabled
        if(loc.canGetLocation()) {

            latitude = loc.getLatitude();
            longitude = loc.getLongitude();

            // \n is for new line
//            Toast.makeText(getApplicationContext(), "Your Location is - \nLat: " + latitude + "\nLong: " + longitude, Toast.LENGTH_LONG).show();

            Log.e("lat long", "" +latitude + ", " + longitude);

            getlatitude = Double.toString(latitude);
            getlongtitude = Double.toString(longitude);

            tvlatitude.setText(getlatitude);
            tvlongtitude.setText(getlongtitude);

        } else {

            // can't get location
            loc.showSettingsAlert();


        }

        kembali.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent in = new Intent(PresensiTambah.this, Presensi.class);
                startActivity(in);

            }
        });

        presensi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                getlokasi = lokasi.getText().toString();

                // mengecek jam untuk presensi

                int jam = Integer.valueOf(getjam);

                if (jam > 8 ) {

                    Util.toastShow(getApplicationContext(), "Maaf anda tidak terlambat presensi");

                } else {

                    if (getlokasi.equals("") || getlatitude.equals("") || getlongtitude.equals("") || getwaktu.equals("")) {

                        Util.toastShow(getApplicationContext(), "Harap lengkapi data");

                    } else {

                        Log.e("id", "" + getid);
                        Log.e("waktu", "" + getwaktu);
                        Log.e("tanggal", "" + gettanggal);
                        Log.e("latitude", "" + getlatitude);
                        Log.e("longtitude", "" + getlongtitude);
                        Log.e("lokasi", "" + getlokasi);
                        Log.e("jenis", "" + getjenispresensi);


                        postPresensi();

                    }

                }

            }
        });

        peta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                Uri gmmIntentUri = Uri.parse("geo:" + getlongtitude +"," +getlatitude);
                Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
                mapIntent.setPackage("com.google.android.apps.maps");
                startActivity(mapIntent);


            }
        });

    }

    private void waktu() {

        Calendar cal = Calendar.getInstance();

        DateFormat date = new SimpleDateFormat("dd-MM-yyyy");
        DateFormat time = new SimpleDateFormat("kk:mm");
        DateFormat jam = new SimpleDateFormat("kk");
        getwaktu = time.format(cal.getTime());
        gettanggal = date.format(cal.getTime());
        getjam = jam.format(cal.getTime());


        Log.e("jam : ", "" + getjam);
        Log.e("waktu : ", "" + getwaktu);
        Log.e("tanggal : ", "" + gettanggal);

    }

    private void postPresensi() {

        pDialog.setMessage("Presensi Berangkat ...");
        showDialog();

        //Creating a string request
        StringRequest stringRequest = new StringRequest(Request.Method.POST, Koneksi.presensi_post, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                //If we are getting success from server

                Log.e("response", " " + response);

                try {
                    //converting response to json object
                    JSONObject obj = new JSONObject(response);

                    Log.e("obj", "" + obj);

                    if (!obj.getBoolean("error")) {

                        Util.toastShow(getApplicationContext(), obj.getString("message"));

                        //Starting profile activity
//                        Intent intent = new Intent(PresensiTambah.this, Presensi.class);
//                        startActivity(intent);

                        Intent in = new Intent(PresensiTambah.this, Presensi.class);
                        startActivity(in);

                    } else {


                        hideDialog();

                        Util.toastShow(getApplicationContext(), obj.getString("message"));

//                        Intent in = new Intent(PresensiTambah.this, Presensi.class);
//                        startActivity(in);

                        Intent in = new Intent(PresensiTambah.this, Presensi.class);
                        startActivity(in);

                    }

                } catch (JSONException e) {

                    e.printStackTrace();

                    hideDialog();

                    Util.toastShow(getApplicationContext(), e.getMessage());

//                    Intent in = new Intent(PresensiTambah.this, Presensi.class);
//                    startActivity(in);
                    finish();


                }
            }
        },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        //You can handle error here if you want

                        hideDialog();

                        Util.toastShow(getApplicationContext(), "Gagal");


                    }
                }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();

                //Adding parameters to request
//                params.put("id_kasir", getid);

                params.put("id_karyawan", getid);
                params.put("waktu", getwaktu);
                params.put("tanggal", gettanggal);
                params.put("lokasi", getlokasi);
                params.put("latitude", getlatitude);
                params.put("longtitude", getlongtitude);
                params.put("jenis_presensi", getjenispresensi);

                //returning parameter
                return params;
            }
        };

        Volley.getInstance().addToRequestQueue(stringRequest);

    }

    private void showDialog() {

        if (!pDialog.isShowing())
            pDialog.show();

    }

    private void hideDialog() {

        if (pDialog.isShowing())
            pDialog.dismiss();

    }


}
