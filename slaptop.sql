-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1:3306
-- Generation Time: Dec 25, 2024 at 05:49 PM
-- Server version: 8.3.0
-- PHP Version: 8.2.18

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `slaptop`
--

-- --------------------------------------------------------

--
-- Table structure for table `cart`
--

DROP TABLE IF EXISTS `cart`;
CREATE TABLE IF NOT EXISTS `cart` (
  `id` int NOT NULL AUTO_INCREMENT,
  `quantity` int DEFAULT NULL,
  `product_product_id` int DEFAULT NULL,
  `user_user_id` int DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK64i6i4am0d54dsva90ut9rtqb` (`product_product_id`),
  KEY `FK13vhj9pyn74fi0tvoqg8yxc27` (`user_user_id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Dumping data for table `cart`
--

INSERT INTO `cart` (`id`, `quantity`, `product_product_id`, `user_user_id`) VALUES
(1, 1, 12, 13);

-- --------------------------------------------------------

--
-- Table structure for table `category`
--

DROP TABLE IF EXISTS `category`;
CREATE TABLE IF NOT EXISTS `category` (
  `danh_muc_id` int NOT NULL AUTO_INCREMENT,
  `image_name` varchar(255) DEFAULT NULL,
  `tendanhmuc` varchar(255) DEFAULT NULL,
  `trang_thai` bit(1) DEFAULT NULL,
  PRIMARY KEY (`danh_muc_id`)
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Dumping data for table `category`
--

INSERT INTO `category` (`danh_muc_id`, `image_name`, `tendanhmuc`, `trang_thai`) VALUES
(1, 'Screenshot 2024-08-24 164121.png', 'Acer', b'1'),
(2, 'Screenshot 2024-08-29 031235.png', 'ViVo', b'1'),
(3, 'Screenshot 2024-08-24 164121.png', 'Nino', b'1'),
(4, 'Screenshot 2024-08-29 033826.png', 'HP', b'1'),
(5, 'Screenshot 2024-08-24 164121.png', 'Lenovo', b'1'),
(6, 'Screenshot 2024-08-24 171156.png', 'Microsoft', b'1'),
(7, 'Screenshot 2024-08-24 171156.png', 'Apple', b'1'),
(8, 'Screenshot 2024-08-29 031235.png', 'MSI', b'1'),
(9, 'Screenshot 2024-09-19 123428.png', 'Dell', b'1'),
(10, 'Screenshot 2024-10-05 235816.png', 'Asus', b'1');

-- --------------------------------------------------------

--
-- Table structure for table `goods`
--

DROP TABLE IF EXISTS `goods`;
CREATE TABLE IF NOT EXISTS `goods` (
  `goods_id` int NOT NULL AUTO_INCREMENT,
  `created_on` datetime(6) DEFAULT NULL,
  `tongtien` int DEFAULT NULL,
  `updated_on` datetime(6) DEFAULT NULL,
  `product_product_id` int DEFAULT NULL,
  `category_name` varchar(255) DEFAULT NULL,
  `product_name` varchar(255) DEFAULT NULL,
  `soluong` int DEFAULT NULL,
  PRIMARY KEY (`goods_id`),
  UNIQUE KEY `UKfrn90sef5fr2hn7tec06eqpgb` (`product_product_id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Dumping data for table `goods`
--

INSERT INTO `goods` (`goods_id`, `created_on`, `tongtien`, `updated_on`, `product_product_id`, `category_name`, `product_name`, `soluong`) VALUES
(1, '2024-12-25 12:15:48.064243', NULL, '2024-12-25 12:15:48.064243', NULL, 'Acer', 'Asus ROG Strix G15', NULL),
(2, '2024-12-25 12:30:03.814333', NULL, '2024-12-25 12:30:03.814333', NULL, 'Acer', 'Acer Aspire 3', 2),
(3, '2024-12-25 12:30:47.247163', NULL, '2024-12-25 12:30:47.247163', NULL, 'Acer', 'Acer Aspire 3', 2);

-- --------------------------------------------------------

--
-- Table structure for table `order_address`
--

DROP TABLE IF EXISTS `order_address`;
CREATE TABLE IF NOT EXISTS `order_address` (
  `id` int NOT NULL AUTO_INCREMENT,
  `address` varchar(255) DEFAULT NULL,
  `email` varchar(255) DEFAULT NULL,
  `ghichu` varchar(255) DEFAULT NULL,
  `guest_name` varchar(255) DEFAULT NULL,
  `sdt` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=44 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Dumping data for table `order_address`
--

INSERT INTO `order_address` (`id`, `address`, `email`, `ghichu`, `guest_name`, `sdt`) VALUES
(6, 'sdasddsa', 'huynguyenhonggia2703@gmail.com', 'asdasdsad', 'huy', '3279743213'),
(7, 'sadasasd', 'mynguyen1@gmail.com', 'dfssfsdf', 'huy', '912982932'),
(8, 'sadasasd', 'mynguyen1@gmail.com', 'dfssfsdf', 'huy', '912982932'),
(9, 'sadasasd', 'mynguyen1@gmail.com', 'dfssfsdf', 'huy', '912982932'),
(10, 'sdasddsa', 'mynguyen120108@gmail.com', 'ádasddasasdasddas', 'huy', '3279743213'),
(11, 'asdadsa', 'da0276308@gmail.com', 'asdasddsa', 'huy', '912982932'),
(12, 'asdadsa', 'da0276308@gmail.com', 'asdasddsa', 'huy', '912982932'),
(13, 'sdasddsa', 'huynguyenhonggia2703@gmail.com', 'asdasd', 'huy', '1271293'),
(14, 'sdasddsa', 'huynguyenhonggia2703@gmail.com', 'saasdsa', 'huy', '3279743213'),
(15, 'sdasddsa', 'huynguyenhonggia2703@gmail.com', 'saasdsa', 'huy', '3279743213'),
(16, 'asdasd', 'mynguyen120108@gmail.com', 'adssadads', 'huy', '3279743213'),
(17, 'asdasd', 'mynguyen120108@gmail.com', 'adssadads', 'huy', '3279743213'),
(18, '209ChanhHungP4Q8', 'huynguyenhonggia2703@gmail.com', 'can than nhe', 'huy', '912982932'),
(19, '209ChanhHungP4Q8', 'huynguyenhonggia2703@gmail.com', 'can than nhe', 'huy', '912982932'),
(20, '209ChanhHungP4Q8', 'huynguyenhonggia2703@gmail.com', 'can than nhe', 'huy', '912982932'),
(21, 'asdasd', 'mynguyen120108@gmail.com', 'ádad', 'huy', '3279743213'),
(22, 'asdasd', 'mynguyen120108@gmail.com', 'ádad', 'huy', '3279743213'),
(23, 'asdasd', 'mynguyen120108@gmail.com', 'ádad', 'huy', '3279743213'),
(24, 'asd 20/21', 'huynguyenhonggia2703@gmail.com', 'asassa', 'huy', '3279743213'),
(25, 'asd 20/21', 'huynguyenhonggia2703@gmail.com', 'asassa', 'huy', '3279743213'),
(26, 'asd 20/21', 'huynguyenhonggia2703@gmail.com', 'asassa', 'huy', '3279743213'),
(27, 'as2212/20', 'huynguyenhonggia2703@gmail.com', 'hjgjhghjg', 'huy', '3279743213'),
(28, 'as2212/20', 'huynguyenhonggia2703@gmail.com', 'hjgjhghjg', 'huy', '3279743213'),
(29, 'as2212/20', 'huynguyenhonggia2703@gmail.com', 'hjgjhghjg', 'huy', '3279743213'),
(30, '', '', '', '', ''),
(31, '', 'huynguyenhonggia2703@gmail.com', '', '', ''),
(32, '', 'huynguyenhonggia2703@gmail.com', '', '', ''),
(33, '', 'huynguyenhonggia2703@gmail.com', '', '', ''),
(34, '', 'huynguyenhonggia2703@gmail.com', '', '', ''),
(35, '', 'huynguyenhonggia2703@gmail.com', '', '', ''),
(36, '', 'huynguyenhonggia2703@gmail.com', '', '', ''),
(37, '', 'huynguyenhonggia2703@gmail.com', '', '', ''),
(38, '', 'huynguyenhonggia2703@gmail.com', '', '', ''),
(39, '', 'huynguyenhonggia2703@gmail.com', '', '', ''),
(40, '', 'huynguyenhonggia2703@gmail.com', '', '', ''),
(41, '', 'huynguyenhonggia2703@gmail.com', '', '', ''),
(42, '', 'huynguyenhonggia2703@gmail.com', '', '', ''),
(43, '', 'huynguyenhonggia2703@gmail.com', '', '', '');

-- --------------------------------------------------------

--
-- Table structure for table `products`
--

DROP TABLE IF EXISTS `products`;
CREATE TABLE IF NOT EXISTS `products` (
  `product_id` int NOT NULL AUTO_INCREMENT,
  `baohanh` int NOT NULL,
  `cauhinh` varchar(255) DEFAULT NULL,
  `danh_muc` varchar(255) DEFAULT NULL,
  `gia` int NOT NULL,
  `gia_sale` int NOT NULL,
  `hinhanh` varchar(255) DEFAULT NULL,
  `mota` varchar(255) DEFAULT NULL,
  `sale` int NOT NULL,
  `soluong` int NOT NULL,
  `tensanpham` varchar(255) DEFAULT NULL,
  `trangthai` bit(1) DEFAULT NULL,
  PRIMARY KEY (`product_id`)
) ENGINE=InnoDB AUTO_INCREMENT=33 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Dumping data for table `products`
--

INSERT INTO `products` (`product_id`, `baohanh`, `cauhinh`, `danh_muc`, `gia`, `gia_sale`, `hinhanh`, `mota`, `sale`, `soluong`, `tensanpham`, `trangthai`) VALUES
(12, 2, 'Intel Core i3-1215U, Intel UHD Graphics, 8GB RAM, 256GB SSD', 'Vivo', 10000000, 9000000, 'laptop1.jpg', 'Laptop giá cả phải chăng, phù hợp với học sinh, sinh viên', 10, 60, 'Lenovo IdeaPad Slim 3', b'1'),
(13, 3, 'Intel i5, 8GB RAM, 512GB SSD', 'Dell', 15000000, 13500000, 'laptop11.jpg', 'Dell Inspiron 3501 – sự kết hợp hoàn hảo giữa hiệu năng và phong cách. Với thiết kế mỏng nhẹ, sắc sảo, chiếc laptop này không chỉ là công cụ làm việc mà còn là người bạn đồng hành lý tưởng cho những ai yêu thích sự tinh tế. Cấu hình mạnh mẽ giúp bạn xử lý', 10, 50, 'Dell Inspiron 3501', b'1'),
(14, 2, 'Intel i7, 16GB RAM, 1TB SSD', 'Asus', 25000000, 22000000, 'laptop14.jpg', 'Asus ROG Strix G15 – một chiến binh thực thụ trong thế giới gaming. Được trang bị sức mạnh vượt trội với chip Intel i7 và card đồ họa rời, đây là lựa chọn hàng đầu cho các game thủ. Màn hình 144Hz cùng hệ thống đèn LED RGB rực rỡ mang lại trải nghiệm thị ', 12, 30, 'Asus ROG Strix G15', b'1'),
(15, 3, 'AMD Ryzen 5, 16GB RAM, 512GB SSD', 'HP', 18000000, 16500000, 'laptop9.jpg', 'HP ProBook 445 G8 là chiếc laptop dành riêng cho những doanh nhân hiện đại. Với hiệu năng mạnh mẽ và thời lượng pin lên đến 10 giờ, nó là trợ thủ đắc lực giúp bạn chinh phục mọi thử thách. Thiết kế sang trọng và bảo mật cao cấp giúp bảo vệ dữ liệu tối đa.', 8, 40, 'HP ProBook 445 G8', b'1'),
(16, 1, 'Intel i3, 4GB RAM, 256GB SSD', 'Acer', 10000000, 9500000, 'laptop21.jpg', 'Acer Aspire 3 – giải pháp hoàn hảo cho học sinh, sinh viên và nhân viên văn phòng với mức giá phải chăng. Laptop này nổi bật với thiết kế thanh lịch, dễ dàng di chuyển, cùng khả năng hoạt động ổn định trong các tác vụ cơ bản.', 5, 4, 'Acer Aspire 3', b'1'),
(17, 2, 'Intel i5, 16GB RAM, 1TB SSD', 'Lenovo', 22000000, 20000000, 'laptop6.jpg', 'Lenovo IdeaPad 5 – bạn đồng hành lý tưởng cho những ai đam mê sáng tạo. Với cấu hình mạnh mẽ và màn hình Full HD sắc nét, sản phẩm này mang lại trải nghiệm hình ảnh sống động, giúp bạn làm việc và giải trí thật hiệu quả.', 9, 20, 'Lenovo IdeaPad 5', b'1'),
(18, 3, 'Intel i7, 32GB RAM, 1TB SSD', 'Dell', 30000000, 28000000, 'laptop12.jpg', 'Dell Alienware m15 – biểu tượng của sức mạnh và sự sang trọng. Với cấu hình khủng và hệ thống tản nhiệt tiên tiến, đây là lựa chọn hoàn hảo cho các game thủ chuyên nghiệp hoặc những người yêu thích hiệu suất vượt trội.', 7, 15, 'Dell Alienware m15', b'1'),
(19, 1, 'Apple M1, 8GB RAM, 256GB SSD', 'Apple', 28000000, 26500000, 'laptop17.jpg', 'MacBook Air M1 – đỉnh cao công nghệ với chip M1 mạnh mẽ của Apple. Với thiết kế siêu nhẹ, hiệu năng vượt trội và thời lượng pin kéo dài đến 15 giờ, đây là lựa chọn hoàn hảo cho những ai luôn di chuyển.', 5, 25, 'MacBook Air M1', b'1'),
(20, 1, 'Apple M2, 16GB RAM, 512GB SSD', 'Apple', 40000000, 38000000, 'laptop18.jpg', 'MacBook Pro M2 – sản phẩm đỉnh cao dành cho những người sáng tạo nội dung chuyên nghiệp. Với chip M2 mạnh mẽ, màn hình Retina sắc nét và thiết kế nhôm nguyên khối cao cấp, đây là công cụ không thể thiếu để bạn chinh phục mọi thử thách.', 10, 10, 'MacBook Pro M2', b'1'),
(21, 2, 'Intel i5, 8GB RAM, 256GB SSD', 'Microsoft', 25000000, 23000000, 'laptop4.jpg', 'Microsoft Surface Pro 8 – thiết kế 2 trong 1 linh hoạt, biến hóa giữa laptop và tablet chỉ trong giây lát. Với màn hình cảm ứng PixelSense sắc nét và bàn phím rời tiện lợi, đây là sản phẩm lý tưởng cho những ai yêu thích sự di động.', 8, 15, 'Microsoft Surface Pro 8', b'1'),
(22, 2, 'Intel i9, 32GB RAM, 2TB SSD', 'MSI', 50000000, 45000000, 'laptop2.jpg', 'MSI Titan GT77 – quái vật hiệu năng trong làng gaming laptop. Với cấu hình mạnh mẽ nhất thị trường, hệ thống đèn RGB rực rỡ và thiết kế hầm hố, đây là chiếc laptop sinh ra dành cho các game thủ chuyên nghiệp.', 10, 5, 'MSI Titan GT77', b'1'),
(23, 3, 'Intel i9, 32GB RAM, 1TB SSD', 'Asus', 35000000, 33000000, 'laptop15.jpg', 'Asus Zephyrus G14 – laptop gaming mỏng nhẹ nhất thế giới, với hiệu năng mạnh mẽ và màn hình QHD siêu sắc nét. Đây là sự lựa chọn hoàn hảo cho game thủ yêu thích sự linh hoạt và di động.', 6, 20, 'Asus Zephyrus G14', b'1'),
(24, 2, 'AMD Ryzen 7, 16GB RAM, 512GB SSD', 'Lenovo', 19000000, 17500000, 'laptop7.jpg', 'Lenovo Yoga Slim 7 – siêu phẩm đa năng với khả năng xoay gập linh hoạt 360 độ. Cấu hình mạnh mẽ kết hợp với màn hình cảm ứng OLED sắc nét, thích hợp cho cả công việc và giải trí.', 8, 25, 'Lenovo Yoga Slim 7', b'1'),
(25, 3, 'Intel i7, 16GB RAM, 512GB SSD', 'HP', 23000000, 21500000, 'laptop10.jpg', 'HP Spectre x360 – laptop cao cấp với thiết kế kim loại nguyên khối sang trọng. Sản phẩm nổi bật với màn hình 4K HDR sắc nét và bút cảm ứng, mang lại trải nghiệm sáng tạo vượt trội.', 7, 15, 'HP Spectre x360', b'1'),
(26, 1, 'Apple M1 Pro, 16GB RAM, 512GB SSD', 'Apple', 50000000, 48000000, 'laptop19.jpg', 'MacBook Pro 16-inch M1 Pro – siêu phẩm dành cho những chuyên gia sáng tạo. Với hiệu năng đột phá từ chip M1 Pro và màn hình Liquid Retina XDR, đây là công cụ tối thượng cho mọi lĩnh vực.', 4, 10, 'MacBook Pro 16-inch M1 Pro', b'1'),
(27, 2, 'Intel i5, 16GB RAM, 512GB SSD', 'Acer', 19000000, 17500000, 'laptop22.jpg', 'Acer Nitro 5 – laptop gaming quốc dân với thiết kế hiện đại, tản nhiệt tối ưu và hiệu năng mạnh mẽ. Đây là lựa chọn lý tưởng cho game thủ tầm trung muốn chinh phục mọi tựa game.', 8, 40, 'Acer Nitro 5', b'1'),
(28, 3, 'Intel i7, 16GB RAM, 1TB SSD', 'MSI', 32000000, 30000000, 'laptop3.jpg', 'MSI Stealth 15M – laptop gaming mỏng nhất thế giới, kết hợp hoàn hảo giữa thiết kế sang trọng và hiệu năng vượt trội. Tận hưởng trải nghiệm chơi game đỉnh cao mọi lúc, mọi nơi.', 6, 12, 'MSI Stealth 15M', b'1'),
(29, 2, 'Intel i7, 16GB RAM, 256GB SSD', 'Microsoft', 28000000, 26500000, 'laptop5.jpg', 'Microsoft Surface Laptop Studio – laptop 2 trong 1 đỉnh cao với màn hình cảm ứng PixelSense và chế độ sáng tạo studio độc đáo. Sự kết hợp giữa thiết kế và sức mạnh chưa từng có.', 5, 18, 'Microsoft Surface Laptop Studio', b'1'),
(30, 3, 'Intel i9, 32GB RAM, 1TB SSD', 'Dell', 45000000, 43000000, 'laptop13.jpg', 'Dell XPS 15 – chuẩn mực của sự hoàn hảo. Với màn hình InfinityEdge 4K Ultra HD và hiệu năng mạnh mẽ, đây là lựa chọn tối ưu cho các chuyên gia sáng tạo nội dung.', 4, 10, 'Dell XPS 15', b'1'),
(31, 3, 'AMD Ryzen 9, 32GB RAM, 1TB SSD', 'Asus', 37000000, 35000000, 'laptop16.jpg', 'Asus ROG Flow X13 – laptop gaming xoay gập 360 độ độc đáo, nhỏ gọn nhưng vẫn mang đến hiệu năng đỉnh cao. Kết hợp tuyệt vời giữa chơi game và tính di động.', 5, 15, 'Asus ROG Flow X13', b'1'),
(32, 2, 'Intel i5, 8GB RAM, 512GB SSD', 'Lenovo', 15000000, 14000000, 'laptop8.jpg', 'Lenovo ThinkPad E14 – biểu tượng của sự bền bỉ và hiệu năng ổn định. Với thiết kế cổ điển và tính năng bảo mật vượt trội, đây là sự lựa chọn đáng tin cậy cho môi trường doanh nghiệp.', 7, 30, 'Lenovo ThinkPad E14', b'1');

-- --------------------------------------------------------

--
-- Table structure for table `product_order`
--

DROP TABLE IF EXISTS `product_order`;
CREATE TABLE IF NOT EXISTS `product_order` (
  `id` int NOT NULL AUTO_INCREMENT,
  `order_date` date DEFAULT NULL,
  `order_id` varchar(255) DEFAULT NULL,
  `payment_type` varchar(255) DEFAULT NULL,
  `price` int DEFAULT NULL,
  `quantity` int DEFAULT NULL,
  `status` varchar(255) DEFAULT NULL,
  `order_address_id` int DEFAULT NULL,
  `product_product_id` int DEFAULT NULL,
  `user_dtls_user_id` int DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UKqcdbxaeuc7c5gahwh0dutg04r` (`order_address_id`),
  KEY `FKc21xjijmrp1l0hnipk4d4ua74` (`product_product_id`),
  KEY `FKmg4cghmdx7rqja66swjgqggm` (`user_dtls_user_id`)
) ENGINE=InnoDB AUTO_INCREMENT=14 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Dumping data for table `product_order`
--

INSERT INTO `product_order` (`id`, `order_date`, `order_id`, `payment_type`, `price`, `quantity`, `status`, `order_address_id`, `product_product_id`, `user_dtls_user_id`) VALUES
(1, '2024-12-25', 'DHf58a6569-4530-4a8f-9181-df465032b855', 'ONLINE', 9000000, 1, 'DANG_XULY', 37, 12, 13),
(2, '2024-12-25', 'DH42687b86-8719-4709-84e1-e04a2224859b', 'ONLINE', 9000000, 1, 'DANG_XULY', 38, 12, 13),
(3, '2024-12-25', 'DHbd6a0b6a-08c3-45d9-ac15-268306c8bd25', 'ONLINE', 9000000, 1, 'DANG_XULY', 39, 12, 13),
(4, '2024-12-25', 'DH35bce497-cd9d-4add-b35e-d88bbcff30d1', 'ONLINE', 9000000, 1, 'DANG_XULY', 40, 12, 13),
(5, '2024-12-25', 'DH421212af-6ce8-4ab9-af8c-114b16b5c141', 'ONLINE', 9000000, 1, 'DANG_XULY', 41, 12, 13),
(6, '2024-12-25', 'DHb7532586-d944-4531-bdb4-03ee185efa96', 'ONLINE', 9000000, 1, 'DANG_XULY', 42, 12, 13),
(7, '2024-12-25', 'DHaebae84b-4f92-4167-b8e5-a0bc2fb03f15', 'ONLINE', 9000000, 1, 'DANG_XULY', 43, 12, 13);

-- --------------------------------------------------------

--
-- Table structure for table `user_dtls`
--

DROP TABLE IF EXISTS `user_dtls`;
CREATE TABLE IF NOT EXISTS `user_dtls` (
  `user_id` int NOT NULL AUTO_INCREMENT,
  `account_non_locked` bit(1) DEFAULT NULL,
  `created_on` datetime(6) DEFAULT NULL,
  `diachi` varchar(255) DEFAULT NULL,
  `email` varchar(255) DEFAULT NULL,
  `failed_attempts` int DEFAULT NULL,
  `hoat_dong` bit(1) DEFAULT NULL,
  `lock_time` datetime(6) DEFAULT NULL,
  `password` varchar(255) DEFAULT NULL,
  `profile_image` varchar(255) DEFAULT NULL,
  `reset_token` varchar(255) DEFAULT NULL,
  `role` varchar(255) DEFAULT NULL,
  `sdt` int DEFAULT NULL,
  `updated_on` datetime(6) DEFAULT NULL,
  `user_name` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`user_id`)
) ENGINE=InnoDB AUTO_INCREMENT=17 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Dumping data for table `user_dtls`
--

INSERT INTO `user_dtls` (`user_id`, `account_non_locked`, `created_on`, `diachi`, `email`, `failed_attempts`, `hoat_dong`, `lock_time`, `password`, `profile_image`, `reset_token`, `role`, `sdt`, `updated_on`, `user_name`) VALUES
(12, b'1', '2024-12-16 19:21:21.174348', 'NON', 'admin1@1', 0, b'1', NULL, '$2a$10$5Mkz2PIDhw7w2gnG9JsGiexy.aKUwUGEZLnNBdqzyJjfg5MdYoU7q', 'Screenshot 2024-08-29 031235.png', NULL, 'ROLE_ADMIN', 912982932, '2024-12-16 19:29:14.700428', 'huy@a'),
(13, b'1', '2024-12-16 21:25:30.562372', '2a2314241', 'huynguyenhonggia2703@gmail.com', 0, b'1', NULL, '$2a$10$uOuqJ55wQUjvWBUT1vh62OMZapkGLqUSxRWi/85UoJZGuqzrabWty', 'laptop15.jpg', '06dc5f08-ac30-424a-ae4a-b33ad60d9cb9', 'ROLE_USER', 912982932, '2024-12-24 23:26:42.449112', 'ha my'),
(14, b'1', '2024-12-16 22:53:39.292628', 'NON', 'admin2@2', 0, b'1', NULL, '$2a$10$m4XWQy5JS04/LGK9raNLtu9NLa/06XciQQMAo8AyQvlmJaSaD7232', 'Screenshot 2024-08-24 164121.png', NULL, 'ROLE_ADMIN', 912982932, '2024-12-16 22:53:39.292628', 'Admin 2'),
(15, b'1', '2024-12-19 21:22:56.249978', '2a2314241', 'mynguyen08@gmail.com', 0, b'1', NULL, '$2a$10$zMsUXLkuXkCPVEce83EUwe2odRH/FrsgyGnIHqUFjdhOUJELYiXYW', 'laptop11.jpg', NULL, 'ROLE_USER', 912982932, '2024-12-19 21:22:56.249978', 'mac@gmail.com'),
(16, b'1', '2024-12-19 21:23:51.687474', '2a2314241', 'huy@a', 0, b'1', NULL, '$2a$10$e5dXXLVwcymKtDoQ/1TvTu7uh5ZmHsrWH49E8YqKSa7XRLbFKyJNO', 'laptop13.jpg', NULL, 'ROLE_USER', 912982932, '2024-12-19 21:23:51.687474', 'lala');

--
-- Constraints for dumped tables
--

--
-- Constraints for table `cart`
--
ALTER TABLE `cart`
  ADD CONSTRAINT `FK13vhj9pyn74fi0tvoqg8yxc27` FOREIGN KEY (`user_user_id`) REFERENCES `user_dtls` (`user_id`),
  ADD CONSTRAINT `FK64i6i4am0d54dsva90ut9rtqb` FOREIGN KEY (`product_product_id`) REFERENCES `products` (`product_id`);

--
-- Constraints for table `goods`
--
ALTER TABLE `goods`
  ADD CONSTRAINT `FKolbm5x8588ibx2fl5m660wki5` FOREIGN KEY (`product_product_id`) REFERENCES `products` (`product_id`);

--
-- Constraints for table `product_order`
--
ALTER TABLE `product_order`
  ADD CONSTRAINT `FK8frxalwc79tpxo7hgqp3hsjck` FOREIGN KEY (`order_address_id`) REFERENCES `order_address` (`id`),
  ADD CONSTRAINT `FKc21xjijmrp1l0hnipk4d4ua74` FOREIGN KEY (`product_product_id`) REFERENCES `products` (`product_id`),
  ADD CONSTRAINT `FKmg4cghmdx7rqja66swjgqggm` FOREIGN KEY (`user_dtls_user_id`) REFERENCES `user_dtls` (`user_id`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
