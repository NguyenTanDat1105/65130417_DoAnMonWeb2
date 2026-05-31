package k65cntt.nguyentandat.WebsiteQuanLyNoiBoCuaHangLaptop.Entity;

import java.math.BigDecimal;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MapsId;
import jakarta.persistence.Table;

@Entity
@Table(name = "ChiTietDonHang")
@IdClass(ChiTietDonHangId.class)
public class ChiTietDonHang {

    @Id
    @Column(name = "MaDonHang")
    private Integer maDonHang;

    @Id
    @Column(name = "MaSanPham")
    private Integer maSanPham;

    @ManyToOne
    @MapsId("maDonHang")
    @JoinColumn(name = "MaDonHang", insertable = false, updatable = false)
    private DonHang donHang;

    @ManyToOne
    @MapsId("maSanPham")
    @JoinColumn(name = "MaSanPham", insertable = false, updatable = false)
    private SanPham sanPham;

    @Column(name = "SoLuong", nullable = false)
    private Integer soLuong;

    @Column(name = "DonGia", nullable = false)
    private BigDecimal donGia;

    public ChiTietDonHang() {}

    public Integer getMaDonHang() { return maDonHang; }
    public void setMaDonHang(Integer maDonHang) { this.maDonHang = maDonHang; }

    public Integer getMaSanPham() { return maSanPham; }
    public void setMaSanPham(Integer maSanPham) { this.maSanPham = maSanPham; }

    public DonHang getDonHang() { return donHang; }
    public void setDonHang(DonHang donHang) { this.donHang = donHang; }

    public SanPham getSanPham() { return sanPham; }
    public void setSanPham(SanPham sanPham) { this.sanPham = sanPham; }

    public Integer getSoLuong() { return soLuong; }
    public void setSoLuong(Integer soLuong) { this.soLuong = soLuong; }

    public BigDecimal getDonGia() { return donGia; }
    public void setDonGia(BigDecimal donGia) { this.donGia = donGia; }
}
