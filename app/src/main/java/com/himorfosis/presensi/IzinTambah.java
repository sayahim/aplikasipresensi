package com.himorfosis.presensi;

import android.Manifest;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.FileProvider;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import net.gotev.uploadservice.MultipartUploadRequest;
import net.gotev.uploadservice.UploadNotificationConfig;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.UUID;

public class IzinTambah extends AppCompatActivity {

    LinearLayout galeri, kamera;
    TextView mulai, selesai, jenis;
    EditText keterangan;
    ImageView gambar;
    Button kirimizin;

    String getmulai, getselesai, getjenis, getketerangan, path, getid;

    private Uri filePath;
    ProgressDialog pDialog;

    private static final int STORAGE_PERMISSION_CODE = 123;
    private int PICK_IMAGE_REQUEST = 1;
    public static final int RequestPermissionCode = 1;
    private static final int RESULT_LOAD_IMAGE = 1;
    private static final int REQUEST_IMAGE_CAPTURE = 2;

    private Bitmap bitmap;

    // kalender
    private int tahun, bulan, hari;
    Calendar calendar;

    String[] izin = {"Cuti", "Sakit"};
    int pilihizin;

    File photoFile;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.izin_tambah);

        //     Progress dialog
        pDialog = new ProgressDialog(this);
        pDialog.setCancelable(false);

        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setCustomView(R.layout.toolbar);

        TextView title = (TextView) getSupportActionBar().getCustomView().findViewById(R.id.title);
        Button kembali = (Button) getSupportActionBar().getCustomView().findViewById(R.id.kembali);
        title.setText("Tambah Izin");

        kembali.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent in = new Intent(IzinTambah.this, Izin.class);
                startActivity(in);

            }
        });

        galeri = findViewById(R.id.galeri);
        kamera = findViewById(R.id.kamera);
        mulai = findViewById(R.id.tgl_mulai);
        selesai = findViewById(R.id.tgl_selesai);
        jenis = findViewById(R.id.jenis);
        keterangan = findViewById(R.id.keterangan);
        gambar = findViewById(R.id.gambarizin);
        kirimizin = findViewById(R.id.kirimizin);

        // get id user

        getid = Util.getData("akun", "id", getApplicationContext());

        //Requesting storage permission
        requestStoragePermission();

        // Requesting camera permission

        EnableRuntimePermissionToAccessCamera();

        galeri.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent, "Select Picture"), RESULT_LOAD_IMAGE);


            }
        });

        kamera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent photoCaptureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(photoCaptureIntent, REQUEST_IMAGE_CAPTURE);

            }
        });

        mulai.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Calendar c = Calendar.getInstance();
                tahun = c.get(Calendar.YEAR);
                bulan = c.get(Calendar.MONTH);
                hari = c.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog datePickerDialog = new DatePickerDialog(IzinTambah.this,
                        new DatePickerDialog.OnDateSetListener() {

                            @Override
                            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {

                                String hari = String.valueOf(dayOfMonth);
                                String bulan = String.valueOf(monthOfYear + 1);
                                String tahun = String.valueOf(year);

                                mulai.setText(hari + "-" + bulan + "-" + tahun);

                            }
                        }, tahun, bulan, hari);
                datePickerDialog.show();

            }
        });

        selesai.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Calendar c = Calendar.getInstance();
                tahun = c.get(Calendar.YEAR);
                bulan = c.get(Calendar.MONTH);
                hari = c.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog datePickerDialog = new DatePickerDialog(IzinTambah.this,
                        new DatePickerDialog.OnDateSetListener() {

                            @Override
                            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {

                                selesai.setText(dayOfMonth + "-" + (monthOfYear + 1) + "-" + year);

                            }
                        }, tahun, bulan, hari);
                datePickerDialog.show();

            }
        });

        jenis.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AlertDialog dialog = new AlertDialog.Builder(IzinTambah.this)

                        .setTitle("Pilih jenis izin :")
                        .setSingleChoiceItems(izin, 0, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                                pilihizin = which;

                            }
                        })

                        .setNegativeButton("Batal", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                                dialog.dismiss();

                            }
                        })

                        .setPositiveButton("Pilih", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                                jenis.setText(izin[pilihizin]);

                                getjenis = jenis.getText().toString();

                                Log.e("jenis izin", "" + izin[pilihizin]);

                            }
                        })

                        .create();
                dialog.show();

            }
        });

        kirimizin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

//                path = getPath(filePath);

                getjenis = jenis.getText().toString();
                getketerangan = keterangan.getText().toString();
                getmulai = mulai.getText().toString();
                getselesai = selesai.getText().toString();

                if (getjenis.equals("") || getketerangan.equals("") || getmulai.equals("") || getselesai.equals("") || path.equals("")) {

                    Util.toastShow(getApplicationContext(), "Harap isi data secara lengkap");

                } else {

                    pDialog.setMessage("Upload izin ...");
                    showDialog();

                    postIzin();

                }
            }
        });
    }

    private void postIzin() {

//        path = getPath(filePath);

        Log.e("path upload", "" + path);

        // Uploading image

        try {

            String uploadId = UUID.randomUUID().toString();

            //Creating a multi part request
            new MultipartUploadRequest(this, uploadId, "http://jurukoding.com/presensi/api/izin_tambah.php")
                    .addFileToUpload(path, "gambar") //Adding file
                    .addParameter("id_karyawan", getid)
                    .addParameter("tgl_mulai", getmulai)
                    .addParameter("tgl_akhir", getselesai)
                    .addParameter("keterangan", getketerangan)
                    .addParameter("jenis", getjenis)

                    .setNotificationConfig(new UploadNotificationConfig())
                    .setMaxRetries(5)
                    .startUpload();

            //Starting the upload

            hideDialog();

            Util.toastShow(IzinTambah.this, "Berhasil");

            Intent intent = new Intent(IzinTambah.this, Izin.class);
            startActivity(intent);

        } catch (Exception exc) {

            Toast.makeText(this, exc.getMessage(), Toast.LENGTH_SHORT).show();

            hideDialog();

            Util.toastShow(IzinTambah.this, "Gagal");

            Intent intent = new Intent(IzinTambah.this, Izin.class);
            startActivity(intent);

        }

    }


    // Star activity for result method to Set captured image on image view after click.
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode) {

            case RESULT_LOAD_IMAGE:
                if (requestCode == RESULT_LOAD_IMAGE && resultCode == RESULT_OK && data != null && data.getData() != null) {

                    filePath = data.getData();

                    try {

                        // Adding captured image in bitmap.
                        bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), filePath);

                        // adding captured image in imageview.
                        gambar.setImageBitmap(bitmap);

                        path = getPath(filePath);

                        Log.e("path galeri sukses", "" + path);

                    } catch (IOException e) {

                        e.printStackTrace();
                    }

                }

            case REQUEST_IMAGE_CAPTURE:
                if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {

                    bitmap = (Bitmap) data.getExtras().get("data");
                    gambar.setImageBitmap(bitmap);

                    getImageUri(bitmap);

                }

        }


    }

    // Requesting runtime permission to access camera.
    public void EnableRuntimePermissionToAccessCamera() {

        if (ActivityCompat.shouldShowRequestPermissionRationale(IzinTambah.this,
                Manifest.permission.CAMERA)) {

            // Printing toast message after enabling runtime permission.
            Toast.makeText(IzinTambah.this, "CAMERA permission allows us to Access CAMERA app", Toast.LENGTH_LONG).show();

        } else {

            ActivityCompat.requestPermissions(IzinTambah.this, new String[]{Manifest.permission.CAMERA}, RequestPermissionCode);

        }
    }

    public Uri getImageUri(Bitmap inImage) {

        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        inImage.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
        path = MediaStore.Images.Media.insertImage(getApplicationContext().getContentResolver(), inImage, "Title", null);

        path = path + ".jpg";

        Log.e("path kamera sukses", "" + path);

        return Uri.parse(path);

    }

    //method to get the file path from uri
    public String getPath(Uri uri) {

        Cursor cursor = getContentResolver().query(uri, null, null, null, null);
        cursor.moveToFirst();
        String document_id = cursor.getString(0);
        document_id = document_id.substring(document_id.lastIndexOf(":") + 1);
        cursor.close();

        cursor = getContentResolver().query(
                android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                null, MediaStore.Images.Media._ID + " = ? ", new String[]{document_id}, null);
        cursor.moveToFirst();
        String path = cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.DATA));
        cursor.close();

        return path;
    }

    //Requesting permission
    private void requestStoragePermission() {

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED)
            return;

        if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.READ_EXTERNAL_STORAGE)) {

            //If the user has denied the permission previously your code will come to this block
            //Here you can explain why you need this permission
            //Explain here why you need this permission
        }
        //And finally ask for the permission
        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, STORAGE_PERMISSION_CODE);
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
