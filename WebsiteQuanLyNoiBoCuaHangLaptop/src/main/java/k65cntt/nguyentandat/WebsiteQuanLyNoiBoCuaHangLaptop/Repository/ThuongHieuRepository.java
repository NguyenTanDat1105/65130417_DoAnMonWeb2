package k65cntt.nguyentandat.WebsiteQuanLyNoiBoCuaHangLaptop.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import k65cntt.nguyentandat.WebsiteQuanLyNoiBoCuaHangLaptop.Entity.ThuongHieu;

@Repository
public interface ThuongHieuRepository extends JpaRepository<ThuongHieu, Integer> {
}
