package com.example.langthangcoffee;

import java.util.ArrayList;
import java.util.List;

public class DonHang {
    private int maDonHang;
    private String diaChiGiaoHang;
    private int thanhTien;
    private int phiGiaoHang;
    private int soTienThanhToan;
    private String sdtKhachHang;
    private String maVoucher;
    // Tinh trang -> 0: mới khởi tạo, 1: chờ xác nhận, 2: đang giao, 3: giao thành công, 4: đã hủy
    private int tinhTrang;
    private List<LichSuOrder> lichSuOrderList = new ArrayList<LichSuOrder>();
    private Voucher voucher;

    public DonHang() {

    }

    ;

    public void setVoucher(Voucher voucher) {
        this.voucher = voucher;
    }

    public Voucher getVoucher() {
        return voucher;
    }

    public List<LichSuOrder> getLichSuOrderList() {
        return lichSuOrderList;
    }

    public int getTinhTrang() {
        return tinhTrang;
    }

    public int getMaDonHang() {
        return maDonHang;
    }

    public int getThanhTien() {
        return thanhTien;
    }

    public int getPhiGiaoHang() {
        return phiGiaoHang;
    }

    public int getSoTienThanhToan() {
        return soTienThanhToan;
    }

    public String getDiaChiGiaoHang() {
        return diaChiGiaoHang;
    }

    public String getSdtKhachHang() {
        return sdtKhachHang;
    }

    public String getMaVoucher() {
        return maVoucher;
    }

    public void setMaDonHang(int maDonHang) {
        this.maDonHang = maDonHang;
    }

    public void setDiaChiGiaoHang(String diaChiGiaoHang) {
        this.diaChiGiaoHang = diaChiGiaoHang;
    }

    public void setThanhTien(int thanhTien) {
        this.thanhTien = thanhTien;
    }

    public void setPhiGiaoHang(int phiGiaoHang) {
        this.phiGiaoHang = phiGiaoHang;
    }

    public void setSoTienThanhToan(int soTienThanhToan) {
        this.soTienThanhToan = soTienThanhToan;
    }

    public void setSdtKhachHang(String sdtKhachHang) {
        this.sdtKhachHang = sdtKhachHang;
    }

    public void setMaVoucher(String maVoucher) {
        this.maVoucher = maVoucher;
    }

    public void setTinhTrang(int tinhTrang) {
        this.tinhTrang = tinhTrang;
    }

    public void setLichSuOrderList(List<LichSuOrder> lichSuOrderList) {
        this.lichSuOrderList = lichSuOrderList;
    }


}
