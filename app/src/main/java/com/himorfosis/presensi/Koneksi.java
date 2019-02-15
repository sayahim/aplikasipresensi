package com.himorfosis.presensi;

public class Koneksi {

    public static final String URL = "http://192.168.1.105/";

    public static final String login = URL + "presensi/api/login.php";

    // presensi
    public static final String presensi = URL + "presensi/api/presensi.php";
    public static final String presensi_post = URL + "presensi/api/presensi_tambah.php";
    public static final String presensi_pulang = URL + "presensi/api/presensi_update.php";
    public static final String presensi_cek = URL + "presensi/api/presensi_cek.php";

    // izin
    public static final String izin = URL + "presensi/api/izin.php";
    public static final String izin_post = URL + "presensi/api/izin_tambah.php";

    // karyawan
    public static final String karyawan_post = URL + "presensi/api/karyawan_update.php";
    public static final String karyawan_profil = URL + "presensi/api/karyawan.php";
    public static final String karyawan_update = URL + "presensi/api/karyawan_update.php";

    public static final String gambar = URL + "presensi/gambar/izin/";

}
