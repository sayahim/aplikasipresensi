package com.himorfosis.presensi;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

public class IzinAdapter extends ArrayAdapter<IzinClassData> {

    Context context;
    List<IzinClassData> list;


    public IzinAdapter(Context context, List<IzinClassData> objects) {

        super(context, R.layout.rowizin, objects);
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
            convertView = layoutInflater.inflate(R.layout.rowizin, null);

        }

        View v = convertView;

        final IzinClassData data = list.get(position);

        TextView nama = (TextView) v.findViewById(R.id.nama);
        TextView jenis = (TextView) v.findViewById(R.id.jenis);
        TextView mulai = (TextView) v.findViewById(R.id.tgl_mulai);
        TextView selesai = (TextView) v.findViewById(R.id.tgl_selesai);

        nama.setText(data.getNama());
        jenis.setText(data.getJenis());
        mulai.setText(data.getTgl_mulai());
        selesai.setText(data.getTgl_akhir());

        return v;

    }

}
