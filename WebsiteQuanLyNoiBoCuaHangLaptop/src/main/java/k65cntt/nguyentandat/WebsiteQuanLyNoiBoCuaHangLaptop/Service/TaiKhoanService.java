package k65cntt.nguyentandat.WebsiteQuanLyNoiBoCuaHangLaptop.Service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import k65cntt.nguyentandat.WebsiteQuanLyNoiBoCuaHangLaptop.Entity.TaiKhoan;
import k65cntt.nguyentandat.WebsiteQuanLyNoiBoCuaHangLaptop.Repository.TaiKhoanRepository;

@Service
public class TaiKhoanService {

    @Autowired
    private TaiKhoanRepository taiKhoanRepository;

    public TaiKhoan login(String email, String matKhau) {
        // --- PHẦN IN RA CONSOLE ĐỂ TÌM LỖI ---
        System.out.println("=== HỆ THỐNG ĐANG KIỂM TRA ĐĂNG NHẬP ===");
        System.out.println("1. Email nhập từ web : [" + email + "]");
        System.out.println("2. Pass nhập từ web  : [" + matKhau + "]");

        Optional<TaiKhoan> oTaiKhoan = taiKhoanRepository.findByEmail(email);
        
        if (oTaiKhoan.isPresent()) {
            TaiKhoan tk = oTaiKhoan.get();
            System.out.println("-> TÌM THẤY TÀI KHOẢN TRONG DATABASE!");
            System.out.println("3. Pass trong DB     : [" + tk.getMatKhau() + "]");
            System.out.println("4. Trạng thái DB     : " + tk.getTrangThai());
            
            // --- XỬ LÝ LOGIC ---
            // So sánh mật khẩu bằng .equals()
            if (tk.getMatKhau().equals(matKhau)) {
                
                // Dùng Boolean.TRUE.equals() để an toàn tuyệt đối, tránh lỗi NullPointerException
                if (Boolean.TRUE.equals(tk.getTrangThai())) {
                    System.out.println("=> KẾT LUẬN: ĐĂNG NHẬP THÀNH CÔNG!");
                    return tk;
                } else {
                    System.out.println("=> KẾT LUẬN: THẤT BẠI - Tài khoản bị khóa (TrangThai = false/null)");
                }
            } else {
                System.out.println("=> KẾT LUẬN: THẤT BẠI - Sai mật khẩu!");
            }
        } else {
            System.out.println("=> KẾT LUẬN: THẤT BẠI - Không tìm thấy Email này trong Database!");
        }
        
        System.out.println("========================================\n");
        return null; // Trả về null nếu có bất kỳ bước nào thất bại
    }
}
