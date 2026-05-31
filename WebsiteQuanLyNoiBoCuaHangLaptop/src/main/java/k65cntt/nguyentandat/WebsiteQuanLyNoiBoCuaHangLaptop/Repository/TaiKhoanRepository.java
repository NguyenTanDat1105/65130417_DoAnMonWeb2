package k65cntt.nguyentandat.WebsiteQuanLyNoiBoCuaHangLaptop.Repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import k65cntt.nguyentandat.WebsiteQuanLyNoiBoCuaHangLaptop.Entity.TaiKhoan;

@Repository
public interface TaiKhoanRepository extends JpaRepository<TaiKhoan, Integer> {
    // Hàm tìm kiếm tài khoản bằng Email để phục vụ đăng nhập
    Optional<TaiKhoan> findByEmail(String email);
}