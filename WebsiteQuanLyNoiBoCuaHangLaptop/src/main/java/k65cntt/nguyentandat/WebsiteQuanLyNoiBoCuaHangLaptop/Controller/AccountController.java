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
import k65cntt.nguyentandat.WebsiteQuanLyNoiBoCuaHangLaptop.Entity.TaiKhoan;
import k65cntt.nguyentandat.WebsiteQuanLyNoiBoCuaHangLaptop.Repository.TaiKhoanRepository;
import k65cntt.nguyentandat.WebsiteQuanLyNoiBoCuaHangLaptop.Service.TaiKhoanService;

@Controller
public class AccountController {

    @Autowired
    private TaiKhoanService taiKhoanService;

    @Autowired
    private TaiKhoanRepository taiKhoanRepository;

    // Điều hướng trang chủ gốc
    @GetMapping("/")
    public String trangChuGoc(HttpSession session) {
        if (session.getAttribute("user") != null) {
            return "redirect:/dashboard"; 
        }
        return "redirect:/login"; 
    }

    // Hiển thị trang Đăng nhập
    @GetMapping("/login")
    public String showLoginPage(HttpSession session) {
        if (session.getAttribute("user") != null) {
            return "redirect:/dashboard";
        }
        return "login"; 
    }

    // Xử lý dữ liệu Đăng nhập
    @PostMapping("/login")
    public String handleLogin(@RequestParam("email") String email, @RequestParam("password") String password, HttpSession session, ModelMap m) {
        TaiKhoan taiKhoan = taiKhoanService.login(email, password);
        if (taiKhoan != null) {
            session.setAttribute("user", taiKhoan);
            session.setAttribute("fullName", taiKhoan.getHoTen());
            session.setAttribute("role", taiKhoan.getVaiTro());
            return "redirect:/dashboard";
        } else {
            m.addAttribute("error", "Email hoặc mật khẩu không chính xác, hoặc tài khoản đã bị khóa!");
            return "login";
        }
    }

    // Xử lý Đăng xuất
    @GetMapping("/logout")
    public String handleLogout(HttpSession session) {
        session.invalidate(); 
        return "redirect:/login";
    }

    // Trang Bảng điều khiển chính
    @GetMapping("/dashboard")
    public String showDashboard(HttpSession session, ModelMap m) { 
        TaiKhoan user = (TaiKhoan) session.getAttribute("user");
        if (user == null) {
            return "redirect:/login";
        }
        return "dashboard";
    }

    // =========================================================================
    // PHẦN CHỨC NĂNG: QUẢN LÝ NHÂN VIÊN (BẢO MẬT: CHỈ QUẢN TRỊ VIÊN ĐƯỢC VÀO)
    // =========================================================================

    // 1. HIỂN THỊ DANH SÁCH NHÂN VIÊN
    @GetMapping("/nhan-vien")
    public String showNhanVienPage(HttpSession session, ModelMap m) {
        String role = (String) session.getAttribute("role");
        if (role == null || !role.trim().equals("Quản trị viên")) {
            return "redirect:/dashboard"; 
        }
        
        List<TaiKhoan> dsTaiKhoan = taiKhoanRepository.findAll();
        m.addAttribute("dsTaiKhoan", dsTaiKhoan);
        return "nhanvien"; 
    }

    // 2. HIỂN THỊ FORM THÊM NHÂN VIÊN MỚI
    @GetMapping("/nhan-vien/them")
    public String showThemNhanVienForm(HttpSession session, ModelMap m) {
        String role = (String) session.getAttribute("role");
        if (role == null || !role.trim().equals("Quản trị viên")) {
            return "redirect:/dashboard";
        }
        
        m.addAttribute("taiKhoan", new TaiKhoan());
        return "them-nhanvien"; 
    }

    // 3. HIỂN THỊ FORM SỬA NHÂN VIÊN
    @GetMapping("/nhan-vien/sua/{id}")
    public String showSuaNhanVienForm(@PathVariable("id") Integer id, HttpSession session, ModelMap m) {
        String role = (String) session.getAttribute("role");
        if (role == null || !role.trim().equals("Quản trị viên")) {
            return "redirect:/dashboard";
        }
        
        TaiKhoan tk = taiKhoanRepository.findById(id).orElse(null);
        if (tk == null) {
            return "redirect:/nhan-vien";
        }
        m.addAttribute("taiKhoan", tk);
        return "sua-nhanvien"; 
    }

    // 4. XỬ LÝ LƯU DỮ LIỆU (Dùng chung cho cả THÊM và SỬA)
    @PostMapping("/nhan-vien/luu")
    public String saveNhanVien(TaiKhoan taiKhoan, HttpSession session) {
        String role = (String) session.getAttribute("role");
        if (role == null || !role.trim().equals("Quản trị viên")) {
            return "redirect:/dashboard";
        }
        
        // --- ĐOẠN ĐÃ SỬA ĐỂ TRÁNH LỖI BIẾN TRẠNG THÁI ---
        if (taiKhoan.getMaTaiKhoan() == null) {
            // Trường hợp THÊM MỚI: Tự động kích hoạt tài khoản
            // Nếu phương thức trong Entity của bạn là kiểu Boolean, hãy dùng dòng dưới đây:
            taiKhoan.setTrangThai(true); 
            
            // LƯU Ý: Nếu sau khi đổi thành true mà vẫn báo đỏ, hãy kiểm tra file TaiKhoan.java 
            // xem bạn đặt tên hàm là gì (ví dụ: setStatus(true) hoặc setKichHoat(true)) và sửa lại từ này cho khớp.
        } else {
            // Trường hợp CHỈNH SỬA: Giữ nguyên trạng thái cũ từ cơ sở dữ liệu
            TaiKhoan taiKhoanCu = taiKhoanRepository.findById(taiKhoan.getMaTaiKhoan()).orElse(null);
            if (taiKhoanCu != null) {
                taiKhoan.setTrangThai(taiKhoanCu.getTrangThai());
                // (Nếu đổi tên hàm ở trên thì ở đây cũng đổi tương ứng thành .setStatus(taiKhoanCu.getStatus()))
            }
        }
        // ------------------------------------------
        
        taiKhoanRepository.save(taiKhoan);
        return "redirect:/nhan-vien"; 
    }

    // 5. XỬ LÝ XÓA TÀI KHOẢN
    @GetMapping("/nhan-vien/xoa/{id}")
    public String deleteNhanVien(@PathVariable("id") Integer id, HttpSession session) {
        String role = (String) session.getAttribute("role");
        if (role == null || !role.trim().equals("Quản trị viên")) {
            return "redirect:/dashboard";
        }
        
        taiKhoanRepository.deleteById(id);
        return "redirect:/nhan-vien"; 
    }
}