package com.himorfosis.presensi;

public class PresensiClassData {

    private Integer id;
    private Integer id_karyawan;
    private String waktu;
    private String tanggal;
    private String lokasi;
    private String latitude;
    private String longtitude;
    private String jenis;
    private String berangkat;
    private String pulang;
    private String nama;

    public Integer getId_karyawan() {
        return id_karyawan;
    }

    public void setId_karyawan(Integer id_karyawan) {
        this.id_karyawan = id_karyawan;
    }

    public String getWaktu() {
        return waktu;
    }

    public void setWaktu(String waktu) {
        this.waktu = waktu;
    }

    public String getLokasi() {
        return lokasi;
    }

    public void setLokasi(String lokasi) {
        this.lokasi = lokasi;
    }

    public String getJenis() {
        return jenis;
    }

    public void setJenis(String jenis) {
        this.jenis = jenis;
    }

    public String getBerangkat() {
        return berangkat;
    }

    public void setBerangkat(String berangkat) {
        this.berangkat = berangkat;
    }

    public String getPulang() {
        return pulang;
    }

    public void setPulang(String pulang) {
        this.pulang = pulang;
    }

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLongtitude() {
        return longtitude;
    }

    public void setLongtitude(String longtitude) {
        this.longtitude = longtitude;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTanggal() {
        return tanggal;
    }

    public void setTanggal(String tanggal) {
        this.tanggal = tanggal;
    }
}
