package com.himorfosis.presensi;

import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class Profil extends AppCompatActivity {

    TextView nama, username, ttl, phone, alamat;
    String getnama, getusername, getpassword, gettelp, getttl, getalamat, getgender, getid;
    FrameLayout frame;
    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.profil);

        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setCustomView(R.layout.toolbar);

        TextView title = (TextView) getSupportActionBar().getCustomView().findViewById(R.id.title);
        Button kembali = (Button) getSupportActionBar().getCustomView().findViewById(R.id.kembali);
        title.setText("Profil Karyawan");

        kembali.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent in = new Intent(Profil.this, Utama.class);
                startActivity(in);

            }
        });

        nama = findViewById(R.id.nama);
        username = findViewById(R.id.username);
        ttl = findViewById(R.id.ttl);
        phone = findViewById(R.id.phone);
        alamat = findViewById(R.id.alamat);
        frame = findViewById(R.id.framprofil);
        progressBar = findViewById(R.id.progress);
        Button updateprofil = findViewById(R.id.perbarui);

        getid = Util.getData("akun", "id", getApplicationContext());

        getProfil();

        updateprofil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent in = new Intent(Profil.this, ProfilUpdate.class);
                in.putExtra("nama", getnama);
                in.putExtra("username", getusername);
                in.putExtra("password", getpassword);
                in.putExtra("id", getid);
                in.putExtra("gender", getgender);
                in.putExtra("ttl", getttl);
                in.putExtra("alamat", getalamat);
                in.putExtra("telp", gettelp);

                startActivity(in);

            }
        });

    }

    private void getProfil() {

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, Koneksi.karyawan_profil, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {

                        progressBar.setVisibility(View.GONE);
                        frame.setVisibility(View.VISIBLE);

                        try {

                            JSONArray jsonArray = response.getJSONArray("karyawan");

                            //now looping through all the elements of the json array
                            for (int i = 0; i < jsonArray.length(); i++) {

                                //getting the json object of the particular index inside the array
                                JSONObject jsonObject = jsonArray.getJSONObject(i);

                                //creating a hero object and giving them the values from json object
                                ProfilClassData item = new ProfilClassData();

                                item.setId_karyawan(jsonObject.getInt("id_karyawan"));
                                item.setNama(jsonObject.getString("nama"));
                                item.setUsername(jsonObject.getString("username"));
                                item.setPassword(jsonObject.getString("password"));
                                item.setTelp(jsonObject.getString("no_tlpn"));
                                item.setKelamin(jsonObject.getString("jenis_kelamin"));
                                item.setTtl(jsonObject.getString("tempat_tgl_lahir"));
                                item.setAlamat(jsonObject.getString("alamat"));

                                String id = String.valueOf(item.getId_karyawan());

                                Log.e("id", "" +id);

                                if (getid.equals(id)) {

                                    nama.setText(item.getNama());
                                    username.setText(item.getUsername());
                                    ttl.setText(item.getTtl());
                                    phone.setText(item.getTelp());
                                    alamat.setText(item.getAlamat());

                                    getid = String.valueOf(item.getId_karyawan());
                                    getnama = item.getNama();
                                    getusername = item.getUsername();
                                    getpassword = item.getPassword();
                                    gettelp = item.getTelp();
                                    getgender = item.getKelamin();
                                    getttl = item.getTtl();
                                    getalamat = item.getAlamat();

                                }

                            }

                            progressBar.setVisibility(View.GONE);



                        } catch (JSONException e) {
                            e.printStackTrace();

                            Log.e("error", "" + e);

                            progressBar.setVisibility(View.GONE);

//                            checkout.setVisibility(View.INVISIBLE);

                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                        Log.e("error", "" +error);

                        progressBar.setVisibility(View.GONE);


                    }
                });

        //adding the string request to request queue
        Volley.getInstance().addToRequestQueue(jsonObjectRequest);


    }

}
