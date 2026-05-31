package k65cntt.nguyentandat.WebsiteQuanLyNoiBoCuaHangLaptop.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import jakarta.servlet.http.HttpSession;
import k65cntt.nguyentandat.WebsiteQuanLyNoiBoCuaHangLaptop.Entity.SanPham;
import k65cntt.nguyentandat.WebsiteQuanLyNoiBoCuaHangLaptop.Repository.DanhMucRepository;
import k65cntt.nguyentandat.WebsiteQuanLyNoiBoCuaHangLaptop.Repository.SanPhamRepository;
//import k65cntt.nguyentandat.WebsiteQuanLyNoiBoCuaHangLaptop.Service.SanPhamService;
import k65cntt.nguyentandat.WebsiteQuanLyNoiBoCuaHangLaptop.Repository.ThuongHieuRepository;

@Controller
public class SanPhamController {

    @Autowired
    private SanPhamRepository sanPhamRepository;

    @Autowired
    private ThuongHieuRepository thuongHieuRepository;

    @Autowired
    private DanhMucRepository danhMucRepository;

    // 1. TRANG DANH SÁCH SẢN PHẨM
    @GetMapping("/san-pham")
    public String showSanPhamPage(HttpSession session, ModelMap m) {
        String role = (String) session.getAttribute("role");
        if (role == null || !role.trim().equals("Quản trị viên")) {
            return "redirect:/login"; 
        }

        List<SanPham> dsSanPham = sanPhamRepository.findAll();
        m.addAttribute("dsSanPham", dsSanPham);
        return "sanpham"; 
    }

    // 2. HIỂN THỊ FORM THÊM MỚI
    @GetMapping("/san-pham/them")
    public String showThemForm(HttpSession session, ModelMap m) {
        String role = (String) session.getAttribute("role");
        if (role == null || !role.trim().equals("Quản trị viên")) {
            return "redirect:/login";
        }

        m.addAttribute("sanPham", new SanPham()); // Đối tượng rỗng để binding vào form
        m.addAttribute("dsThuongHieu", thuongHieuRepository.findAll()); // Lấy danh sách Hãng để chọn
        m.addAttribute("dsDanhMuc", danhMucRepository.findAll());       // Lấy danh sách Danh mục để chọn
        return "them-sanpham"; // Sẽ tạo file này ở Bước 3
    }

    // 3. HIỂN THỊ FORM SỬA SẢN PHẨM
    @GetMapping("/san-pham/sua/{id}")
    public String showSuaForm(@PathVariable("id") Integer id, HttpSession session, ModelMap m) {
        String role = (String) session.getAttribute("role");
        if (role == null || !role.trim().equals("Quản trị viên")) {
            return "redirect:/login";
        }

        SanPham sp = sanPhamRepository.findById(id).orElse(null);
        if (sp == null) {
            return "redirect:/san-pham"; // Nếu không tìm thấy ID, quay về danh sách
        }

        m.addAttribute("sanPham", sp); // Đổ dữ liệu cũ của sản phẩm ra form
        m.addAttribute("dsThuongHieu", thuongHieuRepository.findAll());
        m.addAttribute("dsDanhMuc", danhMucRepository.findAll());
        return "sua-sanpham"; // Sẽ tạo file này ở Bước 3
    }

    // 4. XỬ LÝ LƯU DỮ LIỆU (Dùng chung cho cả THÊM và SỬA)
    @PostMapping("/san-pham/luu")
    public String saveSanPham(SanPham sanPham, HttpSession session) {
        String role = (String) session.getAttribute("role");
        if (role == null || !role.trim().equals("Quản trị viên")) {
            return "redirect:/login";
        }

        // JPA tự động phân biệt: Nếu đối tượng có maSanPham trùng trong DB -> thực hiện UPDATE
        // Nếu maSanPham là null hoặc chưa từng tồn tại -> thực hiện INSERT mới
        sanPhamRepository.save(sanPham); 
        return "redirect:/san-pham"; // Lưu xong quay về trang danh sách
    }

    // 5. XỬ LÝ XÓA SẢN PHẨM
    @GetMapping("/san-pham/xoa/{id}")
    public String deleteSanPham(@PathVariable("id") Integer id, HttpSession session) {
        String role = (String) session.getAttribute("role");
        if (role == null || !role.trim().equals("Quản trị viên")) {
            return "redirect:/login";
        }

        sanPhamRepository.deleteById(id);
        return "redirect:/san-pham"; // Xóa xong quay lại danh sách
    }
}
