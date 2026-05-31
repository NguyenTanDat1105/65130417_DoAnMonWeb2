package k65cntt.nguyentandat.WebsiteQuanLyNoiBoCuaHangLaptop.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import jakarta.servlet.http.HttpSession;
import k65cntt.nguyentandat.WebsiteQuanLyNoiBoCuaHangLaptop.Entity.SanPham;
import k65cntt.nguyentandat.WebsiteQuanLyNoiBoCuaHangLaptop.Repository.DanhMucRepository;
import k65cntt.nguyentandat.WebsiteQuanLyNoiBoCuaHangLaptop.Repository.SanPhamRepository;
import k65cntt.nguyentandat.WebsiteQuanLyNoiBoCuaHangLaptop.Repository.ThuongHieuRepository;

@Controller
public class SanPhamController {

    @Autowired
    private SanPhamRepository sanPhamRepository;

    @Autowired
    private ThuongHieuRepository thuongHieuRepository;

    @Autowired
    private DanhMucRepository danhMucRepository;

    // 1. TRANG DANH SÁCH SẢN PHẨM (Cả Quản trị viên và Nhân viên đều được xem)
    @GetMapping("/san-pham")
    public String showSanPhamPage(HttpSession session, ModelMap m) {
        String role = (String) session.getAttribute("role");
        if (role == null || (!role.trim().equals("Quản trị viên") && !role.trim().equals("Nhân viên"))) {
            return "redirect:/login"; 
        }

        List<SanPham> dsSanPham = sanPhamRepository.findAll();
        m.addAttribute("dsSanPham", dsSanPham);
        return "sanpham"; 
    }

    // 2. HIỂN THỊ FORM THÊM MỚI (Cả Quản trị viên và Nhân viên đều được thêm)
    @GetMapping("/san-pham/them")
    public String showThemForm(HttpSession session, ModelMap m) {
        String role = (String) session.getAttribute("role");
        if (role == null || (!role.trim().equals("Quản trị viên") && !role.trim().equals("Nhân viên"))) {
            return "redirect:/login";
        }

        m.addAttribute("sanPham", new SanPham()); 
        m.addAttribute("dsThuongHieu", thuongHieuRepository.findAll()); 
        m.addAttribute("dsDanhMuc", danhMucRepository.findAll());       
        return "them-sanpham"; 
    }

    // 3. HIỂN THỊ FORM SỬA SẢN PHẨM (Cả Quản trị viên và Nhân viên đều được sửa)
    @GetMapping("/san-pham/sua/{id}")
    public String showSuaForm(@PathVariable("id") Integer id, HttpSession session, ModelMap m) {
        String role = (String) session.getAttribute("role");
        if (role == null || (!role.trim().equals("Quản trị viên") && !role.trim().equals("Nhân viên"))) {
            return "redirect:/login";
        }

        SanPham sp = sanPhamRepository.findById(id).orElse(null);
        if (sp == null) {
            return "redirect:/san-pham"; 
        }

        m.addAttribute("sanPham", sp); 
        m.addAttribute("dsThuongHieu", thuongHieuRepository.findAll());
        m.addAttribute("dsDanhMuc", danhMucRepository.findAll());
        return "sua-sanpham"; 
    }

    // 4. XỬ LÝ LƯU DỮ LIỆU (Dùng chung cho cả THÊM và SỬA)
    @PostMapping("/san-pham/luu")
    public String saveSanPham(SanPham sanPham, 
                              @RequestParam("maThuongHieu") Integer maThuongHieu,
                              @RequestParam("maDanhMuc") Integer maDanhMuc,
                              HttpSession session) {
        String role = (String) session.getAttribute("role");
        if (role == null || !role.trim().equals("Quản trị viên")) {
            return "redirect:/login";
        }

        if (maThuongHieu != null) {
            sanPham.setThuongHieu(thuongHieuRepository.findById(maThuongHieu).orElse(null));
        }
        if (maDanhMuc != null) {
            sanPham.setDanhMuc(danhMucRepository.findById(maDanhMuc).orElse(null));
        }

        if (sanPham.getGiaNhap() == null) {
            sanPham.setGiaNhap(sanPham.getGiaBan() != null ? sanPham.getGiaBan() : java.math.BigDecimal.ZERO);
        }

        sanPhamRepository.save(sanPham); 
        return "redirect:/san-pham"; // Lưu xong quay về trang danh sách
    }

    // 5. XỬ LÝ XÓA SẢN PHẨM (Bảo mật: CHỈ duy nhất Quản trị viên được xóa)
    @GetMapping("/san-pham/xoa/{id}")
    public String deleteSanPham(@PathVariable("id") Integer id, HttpSession session) {
        String role = (String) session.getAttribute("role");
        if (role == null || !role.trim().equals("Quản trị viên")) {
            return "redirect:/san-pham"; 
        }

        sanPhamRepository.deleteById(id);
        return "redirect:/san-pham"; 
    }
 // 6. HIỂN THỊ CHI TIẾT SẢN PHẨM
    @GetMapping("/san-pham/chi-tiet/{id}")
    public String showChiTietSanPham(@PathVariable("id") Integer id, HttpSession session, ModelMap m) {
        // Kiểm tra xem nhân viên/admin đã đăng nhập chưa
        if (session.getAttribute("user") == null) {
            return "redirect:/login";
        }
        
        // Tìm sản phẩm trong DB bằng ID
        SanPham sp = sanPhamRepository.findById(id).orElse(null);
        if (sp == null) {
            return "redirect:/san-pham"; // Nếu không tìm thấy, quay ngược lại trang danh sách
        }
        
        // Đưa đối tượng sản phẩm ra ngoài giao diện chi tiết
        m.addAttribute("sanPham", sp);
        return "chi-tiet-sanpham";
    }
}