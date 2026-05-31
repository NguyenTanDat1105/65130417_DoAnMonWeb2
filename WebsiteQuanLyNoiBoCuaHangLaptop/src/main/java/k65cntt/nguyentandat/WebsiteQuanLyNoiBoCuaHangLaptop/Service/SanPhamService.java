package k65cntt.nguyentandat.WebsiteQuanLyNoiBoCuaHangLaptop.Service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import k65cntt.nguyentandat.WebsiteQuanLyNoiBoCuaHangLaptop.Entity.SanPham;
import k65cntt.nguyentandat.WebsiteQuanLyNoiBoCuaHangLaptop.Repository.SanPhamRepository;

@Service
public class SanPhamService {

    @Autowired
    private SanPhamRepository sanPhamRepository;

    // Hàm lấy danh sách toàn bộ Laptop trong kho
    public List<SanPham> getAllSanPhams() {
        return sanPhamRepository.findAll();
    }
}