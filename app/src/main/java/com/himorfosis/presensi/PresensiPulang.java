package com.himorfosis.presensi;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PresensiPulang extends Fragment {

    FloatingActionButton tambah;
    ProgressBar progressBar;
    TextView kosong;
    ListView list;

    String getid, getwaktu, gettanggal;

    List<PresensiClassData> datapresensi = new ArrayList<>();
    PresensiAdapter adapter;

//    LocationUser lokasi;

    ProgressDialog pDialog;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        //Returning the layout file after inflating
        //Change R.layout.tab1 in you classes
        return inflater.inflate(R.layout.presensi_list, container, false);

    }

    public void onViewCreated(final View view, Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        super.onViewCreated(view, savedInstanceState);
//        getActivity().setTitle("Penjualan");

        tambah = view.findViewById(R.id.tambah);
        progressBar = view.findViewById(R.id.progress);
        kosong = view.findViewById(R.id.kosong);
        list = view.findViewById(R.id.list);

        //      Progress dialog
        pDialog = new ProgressDialog(getContext());
        pDialog.setCancelable(false);

        getid = Util.getData("akun", "id", getContext());

        tambah.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                cekPresensi();

            }
        });

        waktu();

        getPresensi();

    }

    private void waktu() {

        Calendar cal = Calendar.getInstance();

        DateFormat date = new SimpleDateFormat("dd-MM-yyyy");
        DateFormat time = new SimpleDateFormat("kk:mm");

        gettanggal = date.format(cal.getTime());
        getwaktu = time.format(cal.getTime());

        Log.e("waktu : ", "" + getwaktu);
        Log.e("tanggal : ", "" + gettanggal);

    }

    private void cekPresensi() {

        pDialog.setMessage("Cek presensi ...");
        showDialog();

        StringRequest stringRequest = new StringRequest(Request.Method.POST, Koneksi.presensi_cek,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        //If we are getting success from server

                        Log.e("response", " " + response);

                        try {

                            JSONObject data = new JSONObject(response);

                            if (!data.getBoolean("error")) {

                                Intent in = new Intent(getContext(), PresensiTambah.class);
                                in.putExtra("data", "Pulang");
                                startActivity(in);

                            } else {

                                Util.toastShow(getContext(), data.getString("message"));

                            }

                            hideDialog();

                        } catch (JSONException e) {
                            e.printStackTrace();

                            Log.e("error", "" + e);

                            Util.toastShow(getContext(), "Koneksi bermasalah");

                        }

                        hideDialog();

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        //You can handle error here if you want

//                        Sumber.dialogHide(getApplicationContext());
                        hideDialog();

                        Log.e("error", "" + error);
                        Util.toastShow(getContext(), "Koneksi bermasalah");

                    }
                }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                //Adding parameters to request

                // mengirim data json ke server
                params.put("tanggal", gettanggal);
                params.put("id_karyawan", getid);
                params.put("jenis", "Pulang");


                //returning parameter
                return params;
            }
        };

        Volley.getInstance().addToRequestQueue(stringRequest);

    }

    private void getPresensi() {

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, Koneksi.presensi, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {

                        progressBar.setVisibility(View.GONE);
                        list.setVisibility(View.VISIBLE);

                        try {

                            JSONArray jsonArray = response.getJSONArray("presensi");

                            //now looping through all the elements of the json array
                            for (int i = 0; i < jsonArray.length(); i++) {

                                //getting the json object of the particular index inside the array
                                JSONObject jsonObject = jsonArray.getJSONObject(i);

                                //creating a hero object and giving them the values from json object
                                PresensiClassData item = new PresensiClassData();

                                item.setId(jsonObject.getInt("id_presensi"));
                                item.setId_karyawan(jsonObject.getInt("id_karyawan"));
                                item.setTanggal(jsonObject.getString("tanggal"));
                                item.setWaktu(jsonObject.getString("waktu"));
                                item.setLokasi(jsonObject.getString("lokasi"));
                                item.setLatitude(jsonObject.getString("latitude"));
                                item.setLongtitude(jsonObject.getString("longtitude"));
                                item.setJenis(jsonObject.getString("jenis_presensi"));
                                item.setNama(jsonObject.getString("nama"));

                                String id = String.valueOf(item.getId_karyawan());

                                Log.e("id", "" + getid);
                                Log.e("id", "" + id);

                                if (gettanggal.equals(item.getTanggal())) {

                                    if (item.getJenis().equals("Pulang")) {

                                        datapresensi.add(item);

                                    }

                                }

                            }

                            progressBar.setVisibility(View.GONE);

                            if (datapresensi.isEmpty()) {

                                kosong.setVisibility(View.VISIBLE);

                            }

                            adapter = new PresensiAdapter(getContext(), datapresensi);
                            Collections.reverse(datapresensi);

                            list.setAdapter(adapter);

                            list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                @Override
                                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                                    Intent in;

                                    PresensiClassData data = datapresensi.get(position);

                                    Log.e("latitude", "" + data.getLatitude());


                                    in = new Intent(getContext(), PresensiDetail.class);

                                    in.putExtra("id", String.valueOf(data.getId()));
                                    in.putExtra("id_karyawan", String.valueOf(data.getId_karyawan()));
                                    in.putExtra("tanggal", data.getTanggal());
                                    in.putExtra("waktu", data.getWaktu());
                                    in.putExtra("lokasi", data.getLokasi());
                                    in.putExtra("latitude", data.getLatitude());
                                    in.putExtra("longtitude", data.getLongtitude());
                                    in.putExtra("jenis", data.getJenis());
                                    in.putExtra("nama", data.getNama());

                                    startActivity(in);


                                }
                            });


                        } catch (JSONException e) {
                            e.printStackTrace();

                            Log.e("error", "" + e);

                            progressBar.setVisibility(View.GONE);
                            kosong.setVisibility(View.VISIBLE);

                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                        Log.e("error", "" + error);

                        progressBar.setVisibility(View.GONE);

                        //displaying the error in toast if occurred
//                        Toast.makeText(getContext(), error.getMessage(), Toast.LENGTH_SHORT).show();

                        kosong.setVisibility(View.VISIBLE);


                    }
                });

        //adding the string request to request queue
        Volley.getInstance().addToRequestQueue(jsonObjectRequest);

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
