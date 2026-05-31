package k65cntt.nguyentandat.WebsiteQuanLyNoiBoCuaHangLaptop.Entity;

import java.math.BigDecimal;
import java.util.Date;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;

@Entity
@Table(name = "DonHang")
public class DonHang {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "MaDonHang")
    private Integer maDonHang;

    @ManyToOne
    @JoinColumn(name = "MaTaiKhoan", nullable = false)
    private TaiKhoan taiKhoan;

    @Column(name = "TenKhachHang", length = 100)
    private String tenKhachHang;

    @Column(name = "SoDienThoaiKhach", length = 15)
    private String soDienThoaiKhach;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "NgayTao", insertable = false, updatable = false)
    private Date ngayTao;

    @Column(name = "TongTien", nullable = false)
    private BigDecimal tongTien;

    @Column(name = "TrangThai", length = 50)
    private String trangThai;

    @Column(name = "GhiChu", length = 255)
    private String ghiChu;

    public DonHang() {}

	public Integer getMaDonHang() {
		return maDonHang;
	}

	public void setMaDonHang(Integer maDonHang) {
		this.maDonHang = maDonHang;
	}

	public TaiKhoan getTaiKhoan() {
		return taiKhoan;
	}

	public void setTaiKhoan(TaiKhoan taiKhoan) {
		this.taiKhoan = taiKhoan;
	}

	public String getTenKhachHang() {
		return tenKhachHang;
	}

	public void setTenKhachHang(String tenKhachHang) {
		this.tenKhachHang = tenKhachHang;
	}

	public String getSoDienThoaiKhach() {
		return soDienThoaiKhach;
	}

	public void setSoDienThoaiKhach(String soDienThoaiKhach) {
		this.soDienThoaiKhach = soDienThoaiKhach;
	}

	public Date getNgayTao() {
		return ngayTao;
	}

	public void setNgayTao(Date ngayTao) {
		this.ngayTao = ngayTao;
	}

	public BigDecimal getTongTien() {
		return tongTien;
	}

	public void setTongTien(BigDecimal tongTien) {
		this.tongTien = tongTien;
	}

	public String getTrangThai() {
		return trangThai;
	}

	public void setTrangThai(String trangThai) {
		this.trangThai = trangThai;
	}

	public String getGhiChu() {
		return ghiChu;
	}

	public void setGhiChu(String ghiChu) {
		this.ghiChu = ghiChu;
	}
}
