package com.himorfosis.presensi;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

public class PresensiAdapter extends ArrayAdapter<PresensiClassData> {

    Context context;
    List<PresensiClassData> list;


    public PresensiAdapter(Context context, List<PresensiClassData> objects) {

        super(context, R.layout.rowpresensi, objects);
        this.context = context;
        list = objects;
    }

    @Override
    public int getCount() {

        return super.getCount();

    }

    @SuppressLint("StaticFieldLeak")
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {


        if (convertView == null) {

            LayoutInflater layoutInflater;

            layoutInflater = LayoutInflater.from(getContext());
            convertView = layoutInflater.inflate(R.layout.rowpresensi, null);

        }

        View v = convertView;

        final PresensiClassData data = list.get(position);

        TextView nama = (TextView) v.findViewById(R.id.nama);
        TextView lokasi = (TextView) v.findViewById(R.id.lokasi);
        TextView tanggal = (TextView) v.findViewById(R.id.tanggal);
        TextView waktu = (TextView) v.findViewById(R.id.waktu);

        nama.setText(data.getNama());
        lokasi.setText(data.getLokasi());
        tanggal.setText(data.getTanggal());
        waktu.setText(data.getWaktu());

        return v;

    }


}
