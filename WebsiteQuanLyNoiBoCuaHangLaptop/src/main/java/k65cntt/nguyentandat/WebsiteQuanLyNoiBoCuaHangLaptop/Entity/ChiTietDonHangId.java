package k65cntt.nguyentandat.WebsiteQuanLyNoiBoCuaHangLaptop.Entity;

import java.io.Serializable;
import java.util.Objects;

public class ChiTietDonHangId implements Serializable {
    private Integer maDonHang;
    private Integer maSanPham;

    public ChiTietDonHangId() {}

    public ChiTietDonHangId(Integer maDonHang, Integer maSanPham) {
        this.maDonHang = maDonHang;
        this.maSanPham = maSanPham;
    }

    // Bắt buộc phải triển khai equals và hashCode cho khóa phức hợp
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ChiTietDonHangId that = (ChiTietDonHangId) o;
        return Objects.equals(maDonHang, that.maDonHang) && Objects.equals(maSanPham, that.maSanPham);
    }

    @Override
    public int hashCode() {
        return Objects.hash(maDonHang, maSanPham);
    }

    // Getter và Setter
    public Integer getMaDonHang() { return maDonHang; }
    public void setMaDonHang(Integer maDonHang) { this.maDonHang = maDonHang; }

    public Integer getMaSanPham() { return maSanPham; }
    public void setMaSanPham(Integer maSanPham) { this.maSanPham = maSanPham; }
}
