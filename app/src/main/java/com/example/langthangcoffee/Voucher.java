package com.example.langthangcoffee;

import java.time.LocalDateTime;

public class Voucher {
    private String maVoucher;
    private TaiKhoan taiKhoan;
    private LoaiVoucher loaiVoucher;
    private LocalDateTime thoiGianTao;
    private LocalDateTime thoiGianHetHan;

    public Voucher() {

    }

    public void setLoaiVoucher(LoaiVoucher loaiVoucher) {
        this.loaiVoucher = loaiVoucher;
    }

    public void setMaVoucher(String maVoucher) {
        this.maVoucher = maVoucher;
    }

    public void setTaiKhoan(TaiKhoan taiKhoan) {
        this.taiKhoan = taiKhoan;
    }

    public void setThoiGianHetHan(LocalDateTime thoiGianHetHan) {
        this.thoiGianHetHan = thoiGianHetHan;
    }

    public void setThoiGianTao(LocalDateTime thoiGianTao) {
        this.thoiGianTao = thoiGianTao;
    }

    public String getMaVoucher() {
        return maVoucher;
    }

    public LoaiVoucher getLoaiVoucher() {
        return loaiVoucher;
    }

    public TaiKhoan getTaiKhoan() {
        return taiKhoan;
    }

    public LocalDateTime getThoiGianHetHan() {
        return thoiGianHetHan;
    }

    public LocalDateTime getThoiGianTao() {
        return thoiGianTao;
    }
}
