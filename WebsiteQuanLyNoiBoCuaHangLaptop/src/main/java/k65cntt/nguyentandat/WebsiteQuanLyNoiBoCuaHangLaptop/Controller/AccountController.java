package k65cntt.nguyentandat.WebsiteQuanLyNoiBoCuaHangLaptop.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import jakarta.servlet.http.HttpSession;
import k65cntt.nguyentandat.WebsiteQuanLyNoiBoCuaHangLaptop.Entity.TaiKhoan;
import k65cntt.nguyentandat.WebsiteQuanLyNoiBoCuaHangLaptop.Service.TaiKhoanService;

@Controller
public class AccountController {

    @Autowired
    private TaiKhoanService taiKhoanService;
    
    // ================= BỔ SUNG HÀM NÀY VÀO ĐÂY =================
    @GetMapping("/")
    public String trangChuGoc(HttpSession session) {
        // Nếu người dùng đã đăng nhập trước đó (Session vẫn còn)
        if (session.getAttribute("user") != null) {
            return "redirect:/dashboard"; // Dẫn thẳng vào trang chủ Dashboard chuẩn
        }
        // Nếu chưa đăng nhập, tự động chuyển hướng sang trang đăng nhập
        return "redirect:/login"; 
    }
    // ===========================================================

    // 1. Hiển thị trang Đăng nhập (GET)
    @GetMapping("/login")
    public String showLoginPage(HttpSession session) {
        // Nếu đã đăng nhập rồi thì tự động chuyển hướng, không cho vào lại trang login
        if (session.getAttribute("user") != null) {
            return "redirect:/dashboard";
        }
        return "login"; // Trả về file login.html trong thư mục templates
    }

    // 2. Xử lý dữ liệu Đăng nhập khi bấm nút gửi (POST)
    @PostMapping("/login")
    public String handleLogin(
            @RequestParam("email") String email,
            @RequestParam("password") String password,
            HttpSession session,
            ModelMap m) { 

        // Gọi dịch vụ kiểm tra thông tin đăng nhập
        TaiKhoan taiKhoan = taiKhoanService.login(email, password);

        if (taiKhoan != null) {
            // Đăng nhập thành công -> Lưu thông tin vào Session để kiểm tra phân quyền
            session.setAttribute("user", taiKhoan);
            session.setAttribute("fullName", taiKhoan.getHoTen());
            session.setAttribute("role", taiKhoan.getVaiTro());
            
            // Điều hướng sang trang bảng điều khiển chính
            return "redirect:/dashboard";
        } else {
            // Đăng nhập thất bại -> Gửi thông báo lỗi ra giao diện bằng ModelMap m
            m.addAttribute("error", "Email hoặc mật khẩu không chính xác, hoặc tài khoản đã bị khóa!");
            return "login";
        }
    }

    // 3. Xử lý Đăng xuất
    @GetMapping("/logout")
    public String handleLogout(HttpSession session) {
        session.invalidate(); // Xóa toàn bộ Session
        return "redirect:/login";
    }

    // 4. Trang Bảng điều khiển chính
    @GetMapping("/dashboard")
    public String showDashboard(HttpSession session, ModelMap m) { // Đã sửa thành ModelMap m
        TaiKhoan user = (TaiKhoan) session.getAttribute("user");
        
        // Nếu chưa đăng nhập mà cố tình vào link này -> Bắt quay lại trang Login
        if (user == null) {
            return "redirect:/login";
        }
        
        // Trả về trang dashboard.html
        return "dashboard";
    }
}