package com.example.langthangcoffee;

public class LoaiVoucher {
    private int maLoaiVoucher;
    private String tenLoaiVoucher;
    private String moTaVoucher;
    private String hinhAnh;
    private int giaTriGiamGia;
    public LoaiVoucher() {

    }
    public void setMaLoaiVoucher(int maLoaiVoucher) {
        this.maLoaiVoucher = maLoaiVoucher;
    }
    public void setTenLoaiVoucher(String tenLoaiVoucher) {
        this.tenLoaiVoucher = tenLoaiVoucher;
    }
    public void setMoTaVoucher(String moTaVoucher) {
        this.moTaVoucher = moTaVoucher;
    }
    public void setHinhAnh(String hinhAnh) {
        this.hinhAnh = hinhAnh;
    }
    public void setGiaTriGiamGia(int giaTriGiamGia) {
        this.giaTriGiamGia = giaTriGiamGia;
    }
    public String getTenLoaiVoucher() {
        return tenLoaiVoucher;
    }
    public String getMoTaVoucher() {
        return moTaVoucher;
    }
    public String getHinhAnh() {
        return hinhAnh;
    }
    public int getMaLoaiVoucher() {
        return maLoaiVoucher;
    }
    public int getGiaTriGiamGia() {
        return giaTriGiamGia;
    }

    @Override
    public String toString() {
        return tenLoaiVoucher;
    }
}
