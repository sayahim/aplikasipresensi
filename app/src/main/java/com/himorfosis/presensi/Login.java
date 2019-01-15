package com.himorfosis.presensi;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class Login extends AppCompatActivity {

    String getusername, getpassword, getid;

    ProgressDialog pDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);

        final EditText username = findViewById(R.id.username);
        final EditText password = findViewById(R.id.password);
        final Button login = findViewById(R.id.login);

        //      Progress dialog
        pDialog = new ProgressDialog(Login.this);
        pDialog.setCancelable(false);

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                getusername = username.getText().toString();
                getpassword = password.getText().toString();

                Log.e("user", "" +getusername);
                Log.e("pass", "" +getpassword);

                if (getusername.equals("") || getpassword.equals("")) {

                    Util.toastShow(getApplicationContext(), "Mohon isi data");

                } else {

                    login();

                }

            }
        });

    }

    private void login() {

        pDialog.setMessage("Login ...");
        showDialog();

        StringRequest stringRequest = new StringRequest(Request.Method.POST, Koneksi.login,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        //If we are getting success from server

                        Log.e("response", " " + response);

                        try {

                            JSONObject data = new JSONObject(response);

                            if (!data.getBoolean("error")) {

                                JSONObject akun = data.getJSONObject("user");

                                String email = akun.getString("username");
                                String pass = akun.getString("password");
                                String id = akun.getString("id");

                                Log.e("akun", "" + email);
                                Log.e("akun", "" + pass);


                                Util.saveData("akun", "username", getusername, getApplicationContext());
                                Util.saveData("akun", "password", getpassword, getApplicationContext());
                                Util.saveData("akun", "id", id, getApplicationContext());

                                Intent intent = new Intent(Login.this, Utama.class);
                                startActivity(intent);

                                Util.toastShow(getApplicationContext(), "Login Sukses");

                            } else {

                                Util.toastShow(getApplicationContext(), "Login gagal");

                            }

                            hideDialog();

                        } catch (JSONException e) {
                            e.printStackTrace();

                            Log.e("error", "" +e );

                            Util.toastShow(getApplicationContext(), "Login Gagal");

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

                        Util.toastShow(getApplicationContext(), "Login Gagal");


                    }
                }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                //Adding parameters to request

                // mengirim data json ke server
                params.put("username", getusername);
                params.put("password", getpassword);

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
