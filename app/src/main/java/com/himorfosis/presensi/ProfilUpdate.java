package com.himorfosis.presensi;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class ProfilUpdate extends AppCompatActivity {

    EditText nama, username, password, telp, ttl, alamat;
//    TextView gender;
    LinearLayout lingender;

    String getnama, getusername, getpassword, gettelp, getttl, getalamat, getgender, getid;

    String[] strgender = {"Wanita", "Pria"};
    int pilihgender;

    ProgressDialog pDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.profil_update);

        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setCustomView(R.layout.toolbar);

        TextView title = (TextView) getSupportActionBar().getCustomView().findViewById(R.id.title);
        Button kembali = (Button) getSupportActionBar().getCustomView().findViewById(R.id.kembali);
        title.setText("Perbarui profil");

        // Progress dialog
        pDialog = new ProgressDialog(this);
        pDialog.setCancelable(false);

        nama = findViewById(R.id.nama);
        username = findViewById(R.id.username);
        password = findViewById(R.id.password);
        telp = findViewById(R.id.telp);
        ttl = findViewById(R.id.ttl);
        alamat = findViewById(R.id.alamat);
//        gender = findViewById(R.id.gender);
//        lingender = findViewById(R.id.lingender);
        Button update = findViewById(R.id.update);

        Intent data = getIntent();

        getid = data.getStringExtra("id");
        getnama = data.getStringExtra("nama");
        getusername = data.getStringExtra("username");
        getpassword = data.getStringExtra("password");
        gettelp = data.getStringExtra("telp");
        getttl = data.getStringExtra("ttl");
        getalamat = data.getStringExtra("alamat");
        getgender = data.getStringExtra("gender");

        nama.setText(getnama);
        username.setText(getusername);
        password.setText(getpassword);
        telp.setText(gettelp);
        ttl.setText(getttl);
        alamat.setText(getalamat);
//        gender.setText(getgender);

        kembali.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent in = new Intent(ProfilUpdate.this, Profil.class);
                startActivity(in);

            }
        });

//        lingender.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//                AlertDialog dialog = new AlertDialog.Builder(getApplicationContext())
//
//                        .setTitle("Pilih jenis kelamin : ")
//                        .setSingleChoiceItems(strgender, 0, new DialogInterface.OnClickListener() {
//                            @Override
//                            public void onClick(DialogInterface dialog, int which) {
//
//                                pilihgender = which;
//
//                            }
//                        })
//
//                        .setNegativeButton("Batal", new DialogInterface.OnClickListener() {
//                            @Override
//                            public void onClick(DialogInterface dialog, int which) {
//
//                                dialog.dismiss();
//
//                            }
//                        })
//
//                        .setPositiveButton("Pilih", new DialogInterface.OnClickListener() {
//                            @Override
//                            public void onClick(DialogInterface dialog, int which) {
//
//                                gender.setText(strgender[pilihgender]);
//
//                                getgender = strgender[pilihgender];
//
//                            }
//                        })
//
//                        .create();
//                dialog.show();
//
//            }
//        });

        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                getnama = nama.getText().toString();
                getusername = username.getText().toString();
                getpassword = password.getText().toString();
                gettelp = telp.getText().toString();
                getttl = ttl.getText().toString();
                getalamat = alamat.getText().toString();
//                getgender = gender.getText().toString();

                if (getnama.equals("") || getusername.equals("") || getpassword.equals("") || gettelp.equals("") || getttl.equals("") || getalamat.equals("") || getgender.equals("")) {

                    Util.toastShow(getApplicationContext(), "Harap lengkapi data");

                } else {

                    update_post();

                }

            }
        });

    }

    private void update_post() {


        pDialog.setMessage("Presensi Berangkat ...");
        showDialog();

        //Creating a string request
        StringRequest stringRequest = new StringRequest(Request.Method.POST, Koneksi.karyawan_update, new Response.Listener<String>() {
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

                        finish();

                    } else {


                        hideDialog();

                        Util.toastShow(getApplicationContext(), obj.getString("message"));

//                        Intent in = new Intent(PresensiTambah.this, Presensi.class);
//                        startActivity(in);

                        finish();

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

                params.put("id", getid);
                params.put("nama", getnama);
                params.put("username", getusername);
                params.put("password", getpassword);
                params.put("no_tlpn", gettelp);
                params.put("jenis_kelamin", getgender);
                params.put("ttl", getttl);
                params.put("alamat", getalamat);

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
