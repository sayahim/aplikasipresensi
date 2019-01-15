package com.himorfosis.presensi;

public class IzinClassData {

    private Integer id_karyawan;
    private String tgl_mulai;
    private String tgl_akhir;
    private String keterangan;
    private String jenis;
    private String gambar;
    private String nama;


    public Integer getId_karyawan() {
        return id_karyawan;
    }

    public void setId_karyawan(Integer id_karyawan) {
        this.id_karyawan = id_karyawan;
    }

    public String getTgl_mulai() {
        return tgl_mulai;
    }

    public void setTgl_mulai(String tgl_mulai) {
        this.tgl_mulai = tgl_mulai;
    }

    public String getTgl_akhir() {
        return tgl_akhir;
    }

    public void setTgl_akhir(String tgl_akhir) {
        this.tgl_akhir = tgl_akhir;
    }

    public String getKeterangan() {
        return keterangan;
    }

    public void setKeterangan(String keterangan) {
        this.keterangan = keterangan;
    }

    public String getJenis() {
        return jenis;
    }

    public void setJenis(String jenis) {
        this.jenis = jenis;
    }

    public String getGambar() {
        return gambar;
    }

    public void setGambar(String gambar) {
        this.gambar = gambar;
    }

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }
}
