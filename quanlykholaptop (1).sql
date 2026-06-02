-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Máy chủ: 127.0.0.1
-- Thời gian đã tạo: Th6 02, 2026 lúc 08:55 AM
-- Phiên bản máy phục vụ: 10.4.32-MariaDB
-- Phiên bản PHP: 8.2.12

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Cơ sở dữ liệu: `quanlykholaptop`
--

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `danhmuc`
--

CREATE TABLE `danhmuc` (
  `MaDanhMuc` int(11) NOT NULL,
  `TenDanhMuc` varchar(100) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

--
-- Đang đổ dữ liệu cho bảng `danhmuc`
--

INSERT INTO `danhmuc` (`MaDanhMuc`, `TenDanhMuc`) VALUES
(1, 'Laptop Gaming'),
(2, 'Laptop Văn Phòng');

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `sanpham`
--

CREATE TABLE `sanpham` (
  `MaSanPham` int(11) NOT NULL,
  `TenSanPham` varchar(200) NOT NULL,
  `MaThuongHieu` int(11) NOT NULL,
  `MaDanhMuc` int(11) NOT NULL,
  `CPU` varchar(100) DEFAULT NULL,
  `RAM` varchar(50) DEFAULT NULL,
  `OCung` varchar(100) DEFAULT NULL,
  `CardDoHoa` varchar(100) DEFAULT NULL,
  `ManHinh` varchar(100) DEFAULT NULL,
  `GiaNhap` decimal(38,2) NOT NULL,
  `GiaBan` decimal(38,2) NOT NULL,
  `SoLuongTon` int(11) NOT NULL DEFAULT 0,
  `HinhAnh` varchar(255) DEFAULT 'no-image.png'
) ;

--
-- Đang đổ dữ liệu cho bảng `sanpham`
--

INSERT INTO `sanpham` (`MaSanPham`, `TenSanPham`, `MaThuongHieu`, `MaDanhMuc`, `CPU`, `RAM`, `OCung`, `CardDoHoa`, `ManHinh`, `GiaNhap`, `GiaBan`, `SoLuongTon`, `HinhAnh`) VALUES
(1, 'Laptop ASUS Vivobook 14', 1, 2, 'Intel Core i5', '16GB DDR4', '512GB SSD', 'Intel Iris Xe', '14 inch FHD', 12000000.00, 14500000.00, 10, 'no-image.png'),
(2, 'Laptop Dell Inspiron 15', 2, 2, 'Intel Core i7', '16GB', '512GB SSD', 'RTX 3050', '15.6 inch FHD', 16000000.00, 18900000.00, 3, 'no-image.png'),
(3, 'Asus ROG Strix G15', 1, 1, '', '16GB', 'SSD 512GB PCle', 'RTX 4060 ', '', 15000000.00, 18699000.00, 5, NULL),
(5, 'Asus TUF Gaming A15', 1, 1, 'Ryzen 7 7445HS', '16GB DDR4', 'SSD 512GB PCle', 'RTX 5030', '15.6 inch FHD 144Hz', 18000000.00, 22234000.00, 11, '');

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `taikhoan`
--

CREATE TABLE `taikhoan` (
  `MaTaiKhoan` int(11) NOT NULL,
  `HoTen` varchar(255) NOT NULL,
  `Email` varchar(255) NOT NULL,
  `MatKhau` varchar(255) NOT NULL,
  `SoDienThoai` varchar(255) DEFAULT NULL,
  `VaiTro` varchar(255) NOT NULL,
  `TrangThai` tinyint(1) DEFAULT 1,
  `NgayTao` datetime DEFAULT current_timestamp()
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

--
-- Đang đổ dữ liệu cho bảng `taikhoan`
--

INSERT INTO `taikhoan` (`MaTaiKhoan`, `HoTen`, `Email`, `MatKhau`, `SoDienThoai`, `VaiTro`, `TrangThai`, `NgayTao`) VALUES
(1, 'Nguyễn Văn A', 'adminA@gmail.com', '123456789', '0901234567', 'Quản trị viên ', 1, '2026-05-31 13:12:21'),
(2, 'Nguyễn Văn Nhân Viên 1', 'nhanvien1@gmail.com', '123456', '0987654321', 'Nhân viên', 1, '2026-05-31 20:01:02'),
(3, 'Trần Thị Nhân Viên 2', 'nhanvien2@gmail.com', '123456', '0123456789', 'Nhân viên', 1, NULL),
(4, 'Đặng Thị Nhân Viên 3', 'nhanvien3@gmail.com', '123456', '0011223344', 'Nhân viên', 1, NULL);

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `thuonghieu`
--

CREATE TABLE `thuonghieu` (
  `MaThuongHieu` int(11) NOT NULL,
  `TenThuongHieu` varchar(100) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

--
-- Đang đổ dữ liệu cho bảng `thuonghieu`
--

INSERT INTO `thuonghieu` (`MaThuongHieu`, `TenThuongHieu`) VALUES
(1, 'ASUS'),
(2, 'Dell');

--
-- Chỉ mục cho các bảng đã đổ
--

--
-- Chỉ mục cho bảng `danhmuc`
--
ALTER TABLE `danhmuc`
  ADD PRIMARY KEY (`MaDanhMuc`),
  ADD UNIQUE KEY `TenDanhMuc` (`TenDanhMuc`);

--
-- Chỉ mục cho bảng `sanpham`
--
ALTER TABLE `sanpham`
  ADD PRIMARY KEY (`MaSanPham`),
  ADD KEY `MaThuongHieu` (`MaThuongHieu`),
  ADD KEY `MaDanhMuc` (`MaDanhMuc`);

--
-- Chỉ mục cho bảng `taikhoan`
--
ALTER TABLE `taikhoan`
  ADD PRIMARY KEY (`MaTaiKhoan`),
  ADD UNIQUE KEY `Email` (`Email`);

--
-- Chỉ mục cho bảng `thuonghieu`
--
ALTER TABLE `thuonghieu`
  ADD PRIMARY KEY (`MaThuongHieu`),
  ADD UNIQUE KEY `TenThuongHieu` (`TenThuongHieu`);

--
-- AUTO_INCREMENT cho các bảng đã đổ
--

--
-- AUTO_INCREMENT cho bảng `danhmuc`
--
ALTER TABLE `danhmuc`
  MODIFY `MaDanhMuc` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=3;

--
-- AUTO_INCREMENT cho bảng `sanpham`
--
ALTER TABLE `sanpham`
  MODIFY `MaSanPham` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT cho bảng `taikhoan`
--
ALTER TABLE `taikhoan`
  MODIFY `MaTaiKhoan` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=6;

--
-- AUTO_INCREMENT cho bảng `thuonghieu`
--
ALTER TABLE `thuonghieu`
  MODIFY `MaThuongHieu` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=3;

--
-- Các ràng buộc cho các bảng đã đổ
--

--
-- Các ràng buộc cho bảng `sanpham`
--
ALTER TABLE `sanpham`
  ADD CONSTRAINT `sanpham_ibfk_1` FOREIGN KEY (`MaThuongHieu`) REFERENCES `thuonghieu` (`MaThuongHieu`),
  ADD CONSTRAINT `sanpham_ibfk_2` FOREIGN KEY (`MaDanhMuc`) REFERENCES `danhmuc` (`MaDanhMuc`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
