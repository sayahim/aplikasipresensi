package com.himorfosis.presensi;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import static java.security.AccessController.getContext;

public class Izin extends AppCompatActivity {

    FloatingActionButton tambah;
    ProgressBar progressBar;
    TextView kosong;
    ListView list;

    List<IzinClassData> dataizin = new ArrayList<>();
    IzinAdapter adapter;

    String getid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.izin);

        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setCustomView(R.layout.toolbar);

        TextView title = (TextView) getSupportActionBar().getCustomView().findViewById(R.id.title);
        Button kembali = (Button) getSupportActionBar().getCustomView().findViewById(R.id.kembali);
        title.setText("Izin");

        kembali.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

//                finish();
                Intent in = new Intent(Izin.this, Utama.class);
                startActivity(in);

            }
        });


        tambah = findViewById(R.id.tambah);
        progressBar = findViewById(R.id.progress);
        kosong = findViewById(R.id.kosong);
        list = findViewById(R.id.list);

        getid = Util.getData("akun", "id", getApplicationContext());

        getIzin();

        tambah.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent in = new Intent(Izin.this, IzinTambah.class);
                startActivity(in);

            }
        });

    }

    private void getIzin() {

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, Koneksi.izin, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {

                        progressBar.setVisibility(View.GONE);
                        list.setVisibility(View.VISIBLE);

                        try {

                            JSONArray jsonArray = response.getJSONArray("izin");

                            //now looping through all the elements of the json array
                            for (int i = 0; i < jsonArray.length(); i++) {

                                //getting the json object of the particular index inside the array
                                JSONObject jsonObject = jsonArray.getJSONObject(i);

                                //creating a hero object and giving them the values from json object
                                IzinClassData item = new IzinClassData();

                                item.setId_karyawan(jsonObject.getInt("id_karyawan"));
                                item.setTgl_mulai(jsonObject.getString("tgl_mulai"));
                                item.setTgl_akhir(jsonObject.getString("tgl_akhir"));
                                item.setKeterangan(jsonObject.getString("keterangan"));
                                item.setJenis(jsonObject.getString("jenis"));
                                item.setGambar(jsonObject.getString("gambar"));
                                item.setNama(jsonObject.getString("nama"));

                                String id = String.valueOf(item.getId_karyawan());

                                Log.e("id", "" +id);

                                if (getid.equals(id)) {

                                    dataizin.add(item);

                                }

                            }

                            progressBar.setVisibility(View.GONE);

                            if (dataizin.isEmpty()) {

                                kosong.setVisibility(View.VISIBLE);
                                kosong.setText("Izin kosong");

                            }

                            adapter = new IzinAdapter(getApplicationContext(), dataizin);
                            Collections.reverse(dataizin);

                            list.setAdapter(adapter);

                            list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                @Override
                                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                                    Intent in = new Intent(getApplicationContext(), IzinDetail.class);

                                    IzinClassData data = dataizin.get(position);

//                                    in.putExtra("id", String.valueOf(data.getId_karyawan()));
                                    in.putExtra("mulai", data.getTgl_mulai());
                                    in.putExtra("selesai", data.getTgl_akhir());
                                    in.putExtra("keterangan", data.getKeterangan());
                                    in.putExtra("jenis", data.getJenis());
                                    in.putExtra("gambar", Koneksi.gambar + data.getGambar());

                                    startActivity(in);

                                }
                            });


                        } catch (JSONException e) {
                            e.printStackTrace();

                            Log.e("error", "" + e);

                            progressBar.setVisibility(View.GONE);
                            kosong.setVisibility(View.VISIBLE);
                            kosong.setText("Izin kosong");
//                            checkout.setVisibility(View.INVISIBLE);

                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                        Log.e("error", "" +error);

                        progressBar.setVisibility(View.GONE);

                        //displaying the error in toast if occurred
//                        Toast.makeText(getContext(), error.getMessage(), Toast.LENGTH_SHORT).show();

                        kosong.setVisibility(View.VISIBLE);
                        kosong.setText("Izin kosong");
//                        checkout.setVisibility(View.INVISIBLE);
//

                    }
                });

        //adding the string request to request queue
        Volley.getInstance().addToRequestQueue(jsonObjectRequest);

    }

}
