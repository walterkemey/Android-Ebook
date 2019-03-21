-- phpMyAdmin SQL Dump
-- version 4.0.10.18
-- https://www.phpmyadmin.net
--
-- Host: localhost:3306
-- Generation Time: Mar 30, 2018 at 09:18 AM
-- Server version: 5.5.40-36.1-log
-- PHP Version: 5.6.30

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;

--
-- Database: `viavio7b_e_books_app`
--

-- --------------------------------------------------------

--
-- Table structure for table `tbl_admin`
--

CREATE TABLE IF NOT EXISTS `tbl_admin` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `username` varchar(100) NOT NULL,
  `password` varchar(100) NOT NULL,
  `email` varchar(200) NOT NULL,
  `image` varchar(255) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 AUTO_INCREMENT=2 ;

--
-- Dumping data for table `tbl_admin`
--

INSERT INTO `tbl_admin` (`id`, `username`, `password`, `email`, `image`) VALUES
(1, 'admin', 'admin', 'viaviwebtech@gmail.com', 'profile.png');

-- --------------------------------------------------------

--
-- Table structure for table `tbl_author`
--

CREATE TABLE IF NOT EXISTS `tbl_author` (
  `author_id` int(11) NOT NULL AUTO_INCREMENT,
  `author_name` varchar(255) NOT NULL,
  `author_image` varchar(255) NOT NULL,
  PRIMARY KEY (`author_id`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 AUTO_INCREMENT=30 ;

--
-- Dumping data for table `tbl_author`
--

INSERT INTO `tbl_author` (`author_id`, `author_name`, `author_image`) VALUES
(1, 'A. P. J. Abdul Kalam', '95826_A_P_J_Abdul_Kalam.jpg'),
(14, 'Mahatma Gandhi', '70539_Mahatma-Gandhi.jpg'),
(23, 'Kids', '16626_800px_COLOURBOX8104156.jpg'),
(24, 'Sue Monk Kidd', '15750_images.jpg'),
(25, 'Elisabeth Hasselbeck', '88079_IQScookbook-cover.jpg'),
(26, 'Vallabhbhai Patel', '22319_Sardar-Patel-Image-for-blog-Sandeep-Manudhane-SM-sir.jpg'),
(27, 'Jawaharlal Nehru', '70303_logo.jpg'),
(28, 'W Brian', '19929_arthur.jpeg'),
(29, 'Ricardo Barreiro', '67738_AVT_Ricardo-Barreiro_793.jpeg');

-- --------------------------------------------------------

--
-- Table structure for table `tbl_books`
--

CREATE TABLE IF NOT EXISTS `tbl_books` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `cat_id` int(11) NOT NULL,
  `aid` int(11) NOT NULL,
  `featured` int(1) NOT NULL DEFAULT '0',
  `book_title` varchar(500) NOT NULL,
  `book_description` text NOT NULL,
  `book_cover_img` varchar(255) NOT NULL,
  `book_bg_img` varchar(255) DEFAULT NULL,
  `book_file_type` varchar(255) NOT NULL,
  `book_file_url` varchar(255) NOT NULL,
  `total_rate` int(11) NOT NULL DEFAULT '0',
  `rate_avg` varchar(255) NOT NULL DEFAULT '0',
  `book_views` int(11) NOT NULL DEFAULT '0',
  `status` int(1) NOT NULL DEFAULT '1',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 AUTO_INCREMENT=53 ;

--
-- Dumping data for table `tbl_books`
--

INSERT INTO `tbl_books` (`id`, `cat_id`, `aid`, `featured`, `book_title`, `book_description`, `book_cover_img`, `book_bg_img`, `book_file_type`, `book_file_url`, `total_rate`, `rate_avg`, `book_views`, `status`) VALUES
(25, 6, 23, 1, 'Kids 16 steps drawing book', '<p>Lorem Ipsum&nbsp;is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry&#39;s standard dummy text ever since the 1500s, when an unknown printer took a galley of type and scrambled it to make a type specimen book. It has survived not only five centuries, but also the leap into electronic typesetting, remaining essentially unchanged. It was popularised in the 1960s with the release of Letraset sheets containing Lorem Ipsum passages, and more recently with desktop publishing software like Aldus PageMaker including versions of Lorem Ipsum.</p>\r\n', '7863_064a16f213e5864678cb214c3350ee524f209c96.jpg', '4068_live-tv-banner.png', 'local', 'http://www.viaviweb.in/envato/cc/e_books_app/uploads/1776_All-In-One-Videos.pdf', 2, '4', 14, 1),
(26, 6, 23, 1, 'The nose book', '<p>Lorem Ipsum&nbsp;is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry&#39;s standard dummy text ever since the 1500s, when an unknown printer took a galley of type and scrambled it to make a type specimen book. It has survived not only five centuries, but also the leap into electronic typesetting, remaining essentially unchanged. It was popularised in the 1960s with the release of Letraset sheets containing Lorem Ipsum passages, and more recently with desktop publishing software like Aldus PageMaker including versions of Lorem Ipsum.</p>\r\n', '18718_98b68e79b9327851e4cf61fc886058f8.jpg', '96479_Jobs_App_Banner.png', 'local', 'http://www.viaviweb.in/envato/cc/e_books_app/uploads/22956_Android-Live-TV-with-Material-Design.pdf', 2, '4', 13, 1),
(27, 6, 23, 1, 'By Dr.Seuss', '<pre>Lorem Ipsum&nbsp;is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry&#39;s standard dummy text ever since the 1500s, when an unknown printer took a galley of type and scrambled it to make a type specimen book. It has survived not only five centuries, but also the leap into electronic typesetting, remaining essentially unchanged. It was popularised in the 1960s with the release of Letraset sheets containing Lorem Ipsum passages, and more recently with desktop publishing software like Aldus PageMaker including versions of Lorem Ipsum.</pre>\r\n', '77299_9780394800134.jpg', '53394_ecommerce_app_banner.jpg', 'local', 'http://www.viaviweb.in/envato/cc/e_books_app/uploads/80850_Android-Online-MP3-with-Material-Design.pdf', 3, '4', 17, 1),
(28, 1, 24, 0, 'The secret life of bees', '<pre>Lorem Ipsum&nbsp;is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry&#39;s standard dummy text ever since the 1500s, when an unknown printer took a galley of type and scrambled it to make a type specimen book. It has survived not only five centuries, but also the leap into electronic typesetting, remaining essentially unchanged. It was popularised in the 1960s with the release of Letraset sheets containing Lorem Ipsum passages, and more recently with desktop publishing software like Aldus PageMaker including versions of Lorem Ipsum.</pre>\r\n', '63056_37435.jpg', '20166_Food_Delivery_Banner.jpg', 'local', 'http://www.viaviweb.in/envato/cc/e_books_app/uploads/33649_Online-Radio-With-Material-Design.pdf', 1, '2', 3, 1),
(29, 1, 24, 0, 'The death and life of charlie st cloud', '<pre>Lorem Ipsum&nbsp;is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry&#39;s standard dummy text ever since the 1500s, when an unknown printer took a galley of type and scrambled it to make a type specimen book. It has survived not only five centuries, but also the leap into electronic typesetting, remaining essentially unchanged. It was popularised in the 1960s with the release of Letraset sheets containing Lorem Ipsum passages, and more recently with desktop publishing software like Aldus PageMaker including versions of Lorem Ipsum.</pre>\r\n', '47762_36274218.jpg', '88187_Yoga_App_Banner.jpg', 'local', 'http://www.viaviweb.in/envato/cc/e_books_app/uploads/68397_Viavi-Top-5-Android-Apps-Bundle.pdf', 1, '2', 1, 1),
(30, 1, 24, 0, 'The brief wondrous life of oscar wao', '<pre>Lorem Ipsum &nbsp;is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry&#39;s standard dummy text ever since the 1500s, when an unknown printer took a galley of type and scrambled it to make a type specimen book. It has survived not only five centuries, but also the leap into electronic typesetting, remaining essentially unchanged. It was popularised in the 1960s with the release of Letraset sheets containing Lorem Ipsum passages, and more recently with desktop publishing software like Aldus PageMaker including versions of Lorem Ipsum.</pre>\r\n', '94775_b2319111dcc30039e362ce6d0392c422.641x1000x1.jpg', '84529_Yoga_App_Banner.jpg', 'local', 'http://www.viaviweb.in/envato/cc/e_books_app/uploads/39928_All-In-One-Videos.pdf', 0, '0', 2, 1),
(31, 2, 1, 0, 'Daily keys to success', '<pre>Lorem Ipsum &nbsp;is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry&#39;s standard dummy text ever since the 1500s, when an unknown printer took a galley of type and scrambled it to make a type specimen book. It has survived not only five centuries, but also the leap into electronic typesetting, remaining essentially unchanged. It was popularised in the 1960s with the release of Letraset sheets containing Lorem Ipsum passages, and more recently with desktop publishing software like Aldus PageMaker including versions of Lorem Ipsum.</pre>\r\n', '27363_298b64ccb9aaf7289ce8ea19776b4325--insight-teaching.jpg', '86304_Bundle_Banner_New.jpg', 'local', 'http://www.viaviweb.in/envato/cc/e_books_app/uploads/87871_Android-Live-TV-with-Material-Design.pdf', 1, '2', 2, 1),
(32, 2, 1, 0, 'Simplify your pursuit of success', '<pre>Lorem Ipsum &nbsp;is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry&#39;s standard dummy text ever since the 1500s, when an unknown printer took a galley of type and scrambled it to make a type specimen book. It has survived not only five centuries, but also the leap into electronic typesetting, remaining essentially unchanged. It was popularised in the 1960s with the release of Letraset sheets containing Lorem Ipsum passages, and more recently with desktop publishing software like Aldus PageMaker including versions of Lorem Ipsum.</pre>\r\n', '5056_27157424.jpg', '63698_news_app_combo_banner.jpg', 'local', 'http://www.viaviweb.in/envato/cc/e_books_app/uploads/52001_Android-Online-MP3-with-Material-Design.pdf', 0, '0', 5, 1),
(33, 2, 1, 0, 'Success in personal finance', '<pre>Lorem Ipsum &nbsp;is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry&#39;s standard dummy text ever since the 1500s, when an unknown printer took a galley of type and scrambled it to make a type specimen book. It has survived not only five centuries, but also the leap into electronic typesetting, remaining essentially unchanged. It was popularised in the 1960s with the release of Letraset sheets containing Lorem Ipsum passages, and more recently with desktop publishing software like Aldus PageMaker including versions of Lorem Ipsum.</pre>\r\n', '91183_designpoint-book-covers-personal-finance.jpg', '94173_Single_Hotel_Banner.jpg', 'local', 'http://www.viaviweb.in/envato/cc/e_books_app/uploads/14397_Online-Radio-With-Material-Design.pdf', 0, '0', 4, 1),
(34, 3, 14, 0, 'Ghost writers', '<pre>Lorem Ipsum &nbsp;is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry&#39;s standard dummy text ever since the 1500s, when an unknown printer took a galley of type and scrambled it to make a type specimen book. It has survived not only five centuries, but also the leap into electronic typesetting, remaining essentially unchanged. It was popularised in the 1960s with the release of Letraset sheets containing Lorem Ipsum passages, and more recently with desktop publishing software like Aldus PageMaker including versions of Lorem Ipsum.</pre>\r\n', '34641_book-cover.jpg', '85276_image_answer_banner.jpg', 'local', 'http://www.viaviweb.in/envato/cc/e_books_app/uploads/39761_Viavi-Top-5-Android-Apps-Bundle.pdf', 0, '0', 7, 1),
(35, 3, 14, 0, 'Odd man in societies of deviants in america ', '<pre>\r\nLorem Ipsum &nbsp;is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry&#39;s standard dummy text ever since the 1500s, when an unknown printer took a galley of type and scrambled it to make a type specimen book. It has survived not only five centuries, but also the leap into electronic typesetting, remaining essentially unchanged. It was popularised in the 1960s with the release of Letraset sheets containing Lorem Ipsum passages, and more recently with desktop publishing software like Aldus PageMaker including versions of Lorem Ipsum.</pre>\r\n', '5044_download.jpg', '42503_place_finder_banner.png', 'local', 'http://www.viaviweb.in/envato/cc/e_books_app/uploads/1371_Viavi-Top-5-Android-Apps-Bundle.pdf', 0, '0', 25, 1),
(36, 3, 14, 0, 'Sky is the not the limit ', '<pre>\r\nLorem Ipsum &nbsp;is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry&#39;s standard dummy text ever since the 1500s, when an unknown printer took a galley of type and scrambled it to make a type specimen book. It has survived not only five centuries, but also the leap into electronic typesetting, remaining essentially unchanged. It was popularised in the 1960s with the release of Letraset sheets containing Lorem Ipsum passages, and more recently with desktop publishing software like Aldus PageMaker including versions of Lorem Ipsum.</pre>\r\n', '3781_ec9653728c8946ebe7494ce5e15b4d65--bible-studies-premade-book-covers.jpg', '48689_Quotes-Diary-Apps_Banner.jpg', 'local', 'http://www.viaviweb.in/envato/cc/e_books_app/uploads/77414_All-In-One-Videos.pdf', 1, '5', 18, 1),
(37, 7, 25, 0, 'Host the ultimate dinner party', '<p>Lorem Ipsum &nbsp;is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry&#39;s standard dummy text ever since the 1500s, when an unknown printer took a galley of type and scrambled it to make a type specimen book. It has survived not only five centuries, but also the leap into electronic typesetting, remaining essentially unchanged. It was popularised in the 1960s with the release of Letraset sheets containing Lorem Ipsum passages, and more recently with desktop publishing software like Aldus PageMaker including versions of Lorem Ipsum.</p>\r\n', '82954_Cookbook-Page_text.png', '36574_Food_Delivery_Banner.jpg', 'local', 'http://www.viaviweb.in/envato/cc/e_books_app/uploads/84674_All-In-One-Videos.pdf', 0, '0', 0, 1),
(38, 7, 25, 0, 'The indin kitchen', '<p>Lorem Ipsum &nbsp;is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry&#39;s standard dummy text ever since the 1500s, when an unknown printer took a galley of type and scrambled it to make a type specimen book. It has survived not only five centuries, but also the leap into electronic typesetting, remaining essentially unchanged. It was popularised in the 1960s with the release of Letraset sheets containing Lorem Ipsum passages, and more recently with desktop publishing software like Aldus PageMaker including versions of Lorem Ipsum.</p>\r\n', '67575_cvr9780857831637_9780857831637_hr.jpg', '78452_image_answer_banner.jpg', 'local', 'http://www.viaviweb.in/envato/cc/e_books_app/uploads/65583_Android-Live-TV-with-Material-Design.pdf', 0, '0', 9, 1),
(39, 7, 25, 0, 'Cooking for all', '<p>Lorem Ipsum &nbsp;is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry&#39;s standard dummy text ever since the 1500s, when an unknown printer took a galley of type and scrambled it to make a type specimen book. It has survived not only five centuries, but also the leap into electronic typesetting, remaining essentially unchanged. It was popularised in the 1960s with the release of Letraset sheets containing Lorem Ipsum passages, and more recently with desktop publishing software like Aldus PageMaker including versions of Lorem Ipsum.</p>\r\n', '33300_images.jpg', '97972_Yoga_App_Banner.jpg', 'local', 'http://www.viaviweb.in/envato/cc/e_books_app/uploads/43466_Android-Online-MP3-with-Material-Design.pdf', 2, '2', 10, 1),
(40, 9, 27, 0, 'The broad view  introduction to book history', '<p>Lorem Ipsum &nbsp;is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry&#39;s standard dummy text ever since the 1500s, when an unknown printer took a galley of type and scrambled it to make a type specimen book. It has survived not only five centuries, but also the leap into electronic typesetting, remaining essentially unchanged. It was popularised in the 1960s with the release of Letraset sheets containing Lorem Ipsum passages, and more recently with desktop publishing software like Aldus PageMaker including versions of Lorem Ipsum.</p>\r\n', '65573_9781554810871.jpg', '30771_Bundle_Banner_New.jpg', 'local', 'http://www.viaviweb.in/envato/cc/e_books_app/uploads/6610_Android-Online-MP3-with-Material-Design.pdf', 3, '2', 7, 1),
(41, 9, 27, 0, 'The puffin history of India ', '<p>Lorem Ipsum &nbsp;is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry&#39;s standard dummy text ever since the 1500s, when an unknown printer took a galley of type and scrambled it to make a type specimen book. It has survived not only five centuries, but also the leap into electronic typesetting, remaining essentially unchanged. It was popularised in the 1960s with the release of Letraset sheets containing Lorem Ipsum passages, and more recently with desktop publishing software like Aldus PageMaker including versions of Lorem Ipsum.</p>\r\n', '83494_dalal_081115033741.jpg', '92603_news_app_combo_banner.jpg', 'local', 'http://www.viaviweb.in/envato/cc/e_books_app/uploads/16719_Online-Radio-With-Material-Design.pdf', 1, '3', 4, 1),
(42, 9, 27, 0, 'The angry black south', '<p>Lorem Ipsum &nbsp;is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry&#39;s standard dummy text ever since the 1500s, when an unknown printer took a galley of type and scrambled it to make a type specimen book. It has survived not only five centuries, but also the leap into electronic typesetting, remaining essentially unchanged. It was popularised in the 1960s with the release of Letraset sheets containing Lorem Ipsum passages, and more recently with desktop publishing software like Aldus PageMaker including versions of Lorem Ipsum.</p>\r\n', '41922_MacPhee_BlackPowerCovers_Nov2015-4.jpg', '34762_Single_Hotel_Banner.jpg', 'local', 'http://www.viaviweb.in/envato/cc/e_books_app/uploads/97899_Viavi-Top-5-Android-Apps-Bundle.pdf', 1, '2', 13, 1),
(43, 10, 28, 0, 'The nature of technology', '<p>Lorem Ipsum &nbsp;is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry&#39;s standard dummy text ever since the 1500s, when an unknown printer took a galley of type and scrambled it to make a type specimen book. It has survived not only five centuries, but also the leap into electronic typesetting, remaining essentially unchanged. It was popularised in the 1960s with the release of Letraset sheets containing Lorem Ipsum passages, and more recently with desktop publishing software like Aldus PageMaker including versions of Lorem Ipsum.</p>\r\n', '72789_6321234.jpg', '19957_kids_education_app_banner.jpg', 'local', 'http://www.viaviweb.in/envato/cc/e_books_app/uploads/73167_All-In-One-Videos.pdf', 0, '0', 5, 1),
(44, 10, 28, 0, '2030 Technology that will change the world ', '<p>Lorem Ipsum &nbsp;is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry&#39;s standard dummy text ever since the 1500s, when an unknown printer took a galley of type and scrambled it to make a type specimen book. It has survived not only five centuries, but also the leap into electronic typesetting, remaining essentially unchanged. It was popularised in the 1960s with the release of Letraset sheets containing Lorem Ipsum passages, and more recently with desktop publishing software like Aldus PageMaker including versions of Lorem Ipsum.</p>\r\n', '31627_cover.jpg', '62902_kids_education_app_banner.jpg', 'local', 'http://www.viaviweb.in/envato/cc/e_books_app/uploads/57033_Android-Live-TV-with-Material-Design.pdf', 1, '4', 2, 1),
(45, 10, 28, 0, 'Technology at the margins ', '<p>Lorem Ipsum &nbsp;is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry&#39;s standard dummy text ever since the 1500s, when an unknown printer took a galley of type and scrambled it to make a type specimen book. It has survived not only five centuries, but also the leap into electronic typesetting, remaining essentially unchanged. It was popularised in the 1960s with the release of Letraset sheets containing Lorem Ipsum passages, and more recently with desktop publishing software like Aldus PageMaker including versions of Lorem Ipsum.</p>\r\n', '94291_technology.jpg', '34680_List-of_Top10_Banner.jpg', 'local', 'http://www.viaviweb.in/envato/cc/e_books_app/uploads/36048_Android-Online-MP3-with-Material-Design.pdf', 2, '4', 17, 1),
(46, 11, 29, 0, 'Pokemon  alola region adventure guide ', '<p>Lorem Ipsum &nbsp;is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry&#39;s standard dummy text ever since the 1500s, when an unknown printer took a galley of type and scrambled it to make a type specimen book. It has survived not only five centuries, but also the leap into electronic typesetting, remaining essentially unchanged. It was popularised in the 1960s with the release of Letraset sheets containing Lorem Ipsum passages, and more recently with desktop publishing software like Aldus PageMaker including versions of Lorem Ipsum.</p>\r\n', '95490_176689-ml-1644788.jpg', '39926_All-In-One-Videos_Banner.jpg', 'server_url', 'http://www.viaviweb.in/envato/cc/e_books_app/uploads/36048_Android-Online-MP3-with-Material-Design.pdf', 0, '0', 13, 1),
(47, 11, 29, 0, 'Pokemon origami ', '<p>Lorem Ipsum &amp;nbsp;is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry&amp;#39;s standard dummy text ever since the 1500s, when an unknown printer took a galley of type and scrambled it to make a type specimen book. It has survived not only five centuries, but also the leap into electronic typesetting, remaining essentially unchanged. It was popularised in the 1960s with the release of Letraset sheets containing Lorem Ipsum passages, and more recently with desktop publishing software like Aldus PageMaker including versions of Lorem Ipsum.</p>\r\n', '3583_23397493.jpg', '11453_kids_education_app_banner.jpg', 'server_url', 'http://www.viaviweb.in/envato/cc/e_books_app/uploads/36048_Android-Online-MP3-with-Material-Design.pdf', 2, '3', 9, 1),
(48, 11, 29, 0, 'Danger girl', '<p>Lorem Ipsum &amp;nbsp;is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry&amp;#39;s standard dummy text ever since the 1500s, when an unknown printer took a galley of type and scrambled it to make a type specimen book. It has survived not only five centuries, but also the leap into electronic typesetting, remaining essentially unchanged. It was popularised in the 1960s with the release of Letraset sheets containing Lorem Ipsum passages, and more recently with desktop publishing software like Aldus PageMaker including versions of Lorem Ipsum.</p>\r\n', '12667_DG_Renegade_02-pr-page-001-e1445269933611.jpg', '51020_List-of_Top10_Banner.jpg', 'server_url', 'http://www.viaviweb.in/envato/cc/e_books_app/uploads/36048_Android-Online-MP3-with-Material-Design.pdf', 2, '3', 42, 1),
(49, 8, 26, 0, 'Sardar the sovereign saint', '<p>Lorem Ipsum &nbsp;is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry&#39;s standard dummy text ever since the 1500s, when an unknown printer took a galley of type and scrambled it to make a type specimen book. It has survived not only five centuries, but also the leap into electronic typesetting, remaining essentially unchanged. It was popularised in the 1960s with the release of Letraset sheets containing Lorem Ipsum passages, and more recently with desktop publishing software like Aldus PageMaker including versions of Lorem Ipsum.</p>\r\n', '66201_41hPa5vF5uL._SX322_BO1,204,203,200_.jpg', '99982_All-In-One-Videos_Banner.jpg', 'server_url', 'http://www.viaviweb.in/envato/cc/e_books_app/uploads/36048_Android-Online-MP3-with-Material-Design.pdf', 2, '5', 36, 1),
(51, 8, 26, 0, 'Sardar vallabhbahi patel', '<p>Lorem Ipsum &nbsp;is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry&#39;s standard dummy text ever since the 1500s, when an unknown printer took a galley of type and scrambled it to make a type specimen book. It has survived not only five centuries, but also the leap into electronic typesetting, remaining essentially unchanged. It was popularised in the 1960s with the release of Letraset sheets containing Lorem Ipsum passages, and more recently with desktop publishing software like Aldus PageMaker including versions of Lorem Ipsum.</p>\r\n', '38884_41037wk1ApL._SX324_BO1,204,203,200_.jpg', '4581_List-of_Top10_Banner.jpg', 'server_url', 'http://www.viaviweb.in/envato/cc/e_books_app/uploads/36048_Android-Online-MP3-with-Material-Design.pdf', 3, '5', 72, 1),
(52, 8, 26, 0, 'Mahatma vs gandhi ', '<p>Lorem Ipsum &nbsp;is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry&#39;s standard dummy text ever since the 1500s, when an unknown printer took a galley of type and scrambled it to make a type specimen book. It has survived not only five centuries, but also the leap into electronic typesetting, remaining essentially unchanged. It was popularised in the 1960s with the release of Letraset sheets containing Lorem Ipsum passages, and more recently with desktop publishing software like Aldus PageMaker including versions of Lorem Ipsum.</p>\r\n', '86998_51z5IxdRwyL._SX322_BO1204203200_.jpg', '53616_All-In-One-Videos_Banner.jpg', 'server_url', 'http://www.viaviweb.in/envato/cc/e_books_app/uploads/36048_Android-Online-MP3-with-Material-Design.pdf', 3, '4', 53, 1);

-- --------------------------------------------------------

--
-- Table structure for table `tbl_category`
--

CREATE TABLE IF NOT EXISTS `tbl_category` (
  `cid` int(11) NOT NULL AUTO_INCREMENT,
  `category_name` varchar(255) NOT NULL,
  PRIMARY KEY (`cid`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 AUTO_INCREMENT=12 ;

--
-- Dumping data for table `tbl_category`
--

INSERT INTO `tbl_category` (`cid`, `category_name`) VALUES
(1, 'Life'),
(2, 'Success'),
(3, 'Inspirational'),
(6, 'Kids'),
(7, 'Recipe'),
(8, 'Noval'),
(9, 'History'),
(10, 'Technology'),
(11, 'comic');

-- --------------------------------------------------------

--
-- Table structure for table `tbl_comments`
--

CREATE TABLE IF NOT EXISTS `tbl_comments` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `book_id` int(11) NOT NULL,
  `user_name` varchar(255) NOT NULL,
  `comment_text` text NOT NULL,
  `dt_rate` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 AUTO_INCREMENT=37 ;

--
-- Dumping data for table `tbl_comments`
--

INSERT INTO `tbl_comments` (`id`, `book_id`, `user_name`, `comment_text`, `dt_rate`) VALUES
(1, 20, 'kuldip', 'test', '2018-03-26 10:53:12'),
(3, 20, 'kuldip', 'test', '2018-03-26 11:20:48'),
(4, 20, 'kuldip', 'test', '2018-03-26 11:56:05'),
(5, 0, '', '', '2018-03-26 11:56:09'),
(6, 20, 'kuldip', 'test', '2018-03-26 12:02:24'),
(7, 20, 'kuldip', 'test', '2018-03-26 12:02:28'),
(8, 20, 'kuldi', 'test', '2018-03-26 12:02:37'),
(9, 20, 'a', 'test', '2018-03-26 12:02:53'),
(10, 21, 'a', 'test', '2018-03-26 12:03:01'),
(11, 21, 'b', 'test', '2018-03-26 12:03:36'),
(12, 10, 'b', 'test', '2018-03-26 12:03:47'),
(13, 20, 'kuldip', 'test', '2018-03-26 12:06:35'),
(14, 20, 'ssss', 'test', '2018-03-26 12:07:09'),
(15, 23, 'kishan', 'kishan', '2018-03-27 05:50:39'),
(16, 24, 'kishan', '', '2018-03-27 06:11:03'),
(17, 24, 'kishan', 'nice', '2018-03-27 07:17:49'),
(18, 23, 'kishan', 'qqqq', '2018-03-27 07:24:52'),
(19, 23, 'vishal', 'Great Application...', '2018-03-27 08:41:23'),
(20, 23, 'vishal', 'nice app', '2018-03-27 08:49:26'),
(21, 36, 'kishan', 'nice', '2018-03-27 11:46:13'),
(22, 36, 'kishan', 'hhhh', '2018-03-27 11:46:26'),
(23, 35, 'kishan', 'nicccc', '2018-03-27 11:52:54'),
(24, 35, 'kishan', 'good', '2018-03-27 11:53:09'),
(25, 35, 'kishan', 'done', '2018-03-27 11:56:36'),
(26, 35, 'kishan', 'qq', '2018-03-27 11:57:54'),
(27, 50, 'vishal', 'great', '2018-03-29 08:27:46'),
(28, 50, 'vishal', 'nice', '2018-03-29 08:28:11'),
(29, 51, 'kishan', 'nice', '2018-03-29 10:15:59'),
(30, 51, 'kishan', 'nice book', '2018-03-29 10:16:08'),
(31, 51, 'vishal', 'good', '2018-03-29 10:18:26'),
(32, 25, 'vishal', 'good', '2018-03-29 10:19:09'),
(33, 52, 'Demo', 'demo', '2018-03-30 04:55:43'),
(34, 52, 'Demo', 'nice app', '2018-03-30 04:56:16'),
(35, 52, 'Demo', 'nice', '2018-03-30 05:28:23'),
(36, 51, 'Demo', 'demo', '2018-03-30 05:32:03');

-- --------------------------------------------------------

--
-- Table structure for table `tbl_rating`
--

CREATE TABLE IF NOT EXISTS `tbl_rating` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `book_id` int(11) NOT NULL,
  `user_id` int(11) NOT NULL,
  `ip` varchar(40) NOT NULL,
  `rate` int(11) NOT NULL,
  `dt_rate` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 AUTO_INCREMENT=53 ;

--
-- Dumping data for table `tbl_rating`
--

INSERT INTO `tbl_rating` (`id`, `book_id`, `user_id`, `ip`, `rate`, `dt_rate`) VALUES
(1, 20, 2, '123', 4, '2018-03-16 09:32:56'),
(2, 20, 7, '123', 4, '2018-03-19 11:24:28'),
(3, 6, 2, '123', 4, '2018-03-19 11:28:31'),
(4, 21, 2, '1234', 4, '2018-03-19 12:15:19'),
(5, 21, 6, '1234', 4, '2018-03-19 12:15:51'),
(6, 20, 0, 'b28f34ac41deaa6e', 0, '2018-03-20 06:48:19'),
(7, 21, 10, '6375da2c32482a58', 0, '2018-03-23 08:47:45'),
(8, 20, 4, '1234', 4, '2018-03-23 10:15:46'),
(9, 21, 11, '9e3a15042d670ea9', 4, '2018-03-23 10:39:26'),
(10, 20, 11, '9e3a15042d670ea9', 4, '2018-03-23 10:45:50'),
(11, 22, 11, '9e3a15042d670ea9', 4, '2018-03-23 10:55:29'),
(12, 22, 6, '9e3a15042d670ea9', 4, '2018-03-23 10:59:13'),
(13, 20, 6, '9e3a15042d670ea9', 4, '2018-03-23 11:00:40'),
(14, 22, 12, '9e3a15042d670ea9', 4, '2018-03-23 12:15:12'),
(15, 21, 12, '9e3a15042d670ea9', 4, '2018-03-23 12:21:43'),
(16, 20, 12, '9e3a15042d670ea9', 5, '2018-03-23 12:26:02'),
(17, 24, 6, '9e3a15042d670ea9', 4, '2018-03-26 09:59:20'),
(18, 23, 6, '9e3a15042d670ea9', 3, '2018-03-26 10:06:00'),
(19, 23, 10, '6375da2c32482a58', 5, '2018-03-27 08:41:05'),
(20, 36, 6, '9e3a15042d670ea9', 5, '2018-03-27 12:18:44'),
(21, 25, 10, '6375da2c32482a58', 5, '2018-03-29 10:19:17'),
(22, 51, 10, '6375da2c32482a58', 5, '2018-03-29 10:19:50'),
(23, 40, 2, '1244', 4, '2018-03-29 10:29:47'),
(24, 51, 6, '9e3a15042d670ea9', 5, '2018-03-29 10:57:45'),
(25, 49, 6, '9e3a15042d670ea9', 4, '2018-03-29 10:57:53'),
(26, 52, 6, '9e3a15042d670ea9', 4, '2018-03-29 11:46:33'),
(27, 48, 6, '9e3a15042d670ea9', 4, '2018-03-29 12:07:42'),
(28, 49, 10, '6375da2c32482a58', 5, '2018-03-29 12:11:15'),
(29, 47, 6, '9e3a15042d670ea9', 4, '2018-03-29 12:17:02'),
(30, 26, 6, 'b28f34ac41deaa6e', 4, '2018-03-30 03:59:42'),
(31, 27, 6, '9e3a15042d670ea9', 4, '2018-03-30 05:10:47'),
(32, 27, 13, '9e3a15042d670ea9', 3, '2018-03-30 05:25:33'),
(33, 25, 13, '9e3a15042d670ea9', 3, '2018-03-30 05:26:48'),
(34, 45, 2, '123', 4, '2018-03-30 05:40:18'),
(35, 52, 13, '9e3a15042d670ea9', 4, '2018-03-30 05:55:10'),
(36, 51, 13, '9e3a15042d670ea9', 4, '2018-03-30 05:55:21'),
(37, 26, 13, '9e3a15042d670ea9', 3, '2018-03-30 05:55:34'),
(38, 48, 13, '9e3a15042d670ea9', 2, '2018-03-30 05:57:21'),
(39, 47, 13, '9e3a15042d670ea9', 2, '2018-03-30 05:57:31'),
(40, 31, 13, '9e3a15042d670ea9', 2, '2018-03-30 05:58:18'),
(41, 29, 13, '9e3a15042d670ea9', 2, '2018-03-30 06:11:42'),
(42, 28, 13, '9e3a15042d670ea9', 2, '2018-03-30 06:14:57'),
(43, 52, 10, '6375da2c32482a58', 4, '2018-03-30 06:50:01'),
(44, 27, 10, '6375da2c32482a58', 5, '2018-03-30 06:51:00'),
(45, 45, 6, 'b28f34ac41deaa6e', 3, '2018-03-30 07:01:29'),
(46, 44, 6, 'b28f34ac41deaa6e', 4, '2018-03-30 07:04:27'),
(47, 42, 6, 'b28f34ac41deaa6e', 2, '2018-03-30 07:08:31'),
(48, 41, 6, 'b28f34ac41deaa6e', 3, '2018-03-30 07:15:32'),
(49, 40, 6, 'b28f34ac41deaa6e', 1, '2018-03-30 07:19:38'),
(50, 39, 6, 'b28f34ac41deaa6e', 2, '2018-03-30 07:23:58'),
(51, 40, 13, '9e3a15042d670ea9', 2, '2018-03-30 07:26:55'),
(52, 39, 13, '9e3a15042d670ea9', 2, '2018-03-30 07:27:42');

-- --------------------------------------------------------

--
-- Table structure for table `tbl_settings`
--

CREATE TABLE IF NOT EXISTS `tbl_settings` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `app_name` varchar(255) NOT NULL,
  `app_logo` varchar(255) NOT NULL,
  `app_email` varchar(255) NOT NULL,
  `app_version` varchar(255) NOT NULL,
  `app_author` varchar(255) NOT NULL,
  `app_contact` varchar(255) NOT NULL,
  `app_website` varchar(255) NOT NULL,
  `app_description` text NOT NULL,
  `api_latest_limit` int(3) NOT NULL,
  `api_cat_order_by` varchar(255) NOT NULL,
  `api_cat_post_order_by` varchar(255) NOT NULL,
  `api_author_order_by` varchar(255) NOT NULL,
  `api_author_post_order_by` varchar(255) NOT NULL,
  `app_privacy_policy` text NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 AUTO_INCREMENT=2 ;

--
-- Dumping data for table `tbl_settings`
--

INSERT INTO `tbl_settings` (`id`, `app_name`, `app_logo`, `app_email`, `app_version`, `app_author`, `app_contact`, `app_website`, `app_description`, `api_latest_limit`, `api_cat_order_by`, `api_cat_post_order_by`, `api_author_order_by`, `api_author_post_order_by`, `app_privacy_policy`) VALUES
(1, 'Android E Book', 'logo.png', 'info@viaviweb.in', '1.1.2', 'viaviwebtech', '+91 1234567891', 'www.viaviweb.com', '<p>E Book app is an android application.E Book App has user-friendly interface with easy to manage. The Quotes Pro are stored in Server Side for easy editing and better performance. You can create apps Different types of Category and Author.The application is specially optimized to be extremely easy to configure and detailed documentation is provided.</p>\r\n', 10, 'category_name', 'DESC', 'author_name', 'DESC', '<p><strong>We are committed to protecting your privacy</strong></p>\r\n\r\n<p>We collect the minimum amount of information about you that is commensurate with providing you with a satisfactory service. This policy indicates the type of processes that may result in data being collected about you. Your use of this website gives us the right to collect that information.&nbsp;</p>\r\n\r\n<p><strong>Information Collected</strong></p>\r\n\r\n<p>We may collect any or all of the information that you give us depending on the type of transaction you enter into, including your name, address, telephone number, and email address, together with data about your use of the website. Other information that may be needed from time to time to process a request may also be collected as indicated on the website.</p>\r\n\r\n<p><strong>Information Use</strong></p>\r\n\r\n<p>We use the information collected primarily to process the task for which you visited the website. Data collected in the UK is held in accordance with the Data Protection Act. All reasonable precautions are taken to prevent unauthorised access to this information. This safeguard may require you to provide additional forms of identity should you wish to obtain information about your account details.</p>\r\n\r\n<p><strong>Cookies</strong></p>\r\n\r\n<p>Your Internet browser has the in-built facility for storing small files - &quot;cookies&quot; - that hold information which allows a website to recognise your account. Our website takes advantage of this facility to enhance your experience. You have the ability to prevent your computer from accepting cookies but, if you do, certain functionality on the website may be impaired.</p>\r\n\r\n<p><strong>Disclosing Information</strong></p>\r\n\r\n<p>We do not disclose any personal information obtained about you from this website to third parties unless you permit us to do so by ticking the relevant boxes in registration or competition forms. We may also use the information to keep in contact with you and inform you of developments associated with us. You will be given the opportunity to remove yourself from any mailing list or similar device. If at any time in the future we should wish to disclose information collected on this website to any third party, it would only be with your knowledge and consent.&nbsp;</p>\r\n\r\n<p>We may from time to time provide information of a general nature to third parties - for example, the number of individuals visiting our website or completing a registration form, but we will not use any information that could identify those individuals.&nbsp;</p>\r\n\r\n<p>In addition Dummy may work with third parties for the purpose of delivering targeted behavioural advertising to the Dummy website. Through the use of cookies, anonymous information about your use of our websites and other websites will be used to provide more relevant adverts about goods and services of interest to you. For more information on online behavioural advertising and about how to turn this feature off, please visit youronlinechoices.com/opt-out.</p>\r\n\r\n<p><strong>Changes to this Policy</strong></p>\r\n\r\n<p>Any changes to our Privacy Policy will be placed here and will supersede this version of our policy. We will take reasonable steps to draw your attention to any changes in our policy. However, to be on the safe side, we suggest that you read this document each time you use the website to ensure that it still meets with your approval.</p>\r\n\r\n<p><strong>Contacting Us</strong></p>\r\n\r\n<p>If you have any questions about our Privacy Policy, or if you want to know what information we have collected about you, please email us at hd@dummy.com. You can also correct any factual errors in that information or require us to remove your details form any list under our control.</p>\r\n');

-- --------------------------------------------------------

--
-- Table structure for table `tbl_users`
--

CREATE TABLE IF NOT EXISTS `tbl_users` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `user_type` varchar(255) NOT NULL,
  `name` varchar(255) NOT NULL,
  `email` varchar(255) NOT NULL,
  `password` varchar(255) NOT NULL,
  `phone` varchar(255) NOT NULL,
  `confirm_code` varchar(255) NOT NULL,
  `status` varchar(255) NOT NULL DEFAULT '1',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 AUTO_INCREMENT=14 ;

--
-- Dumping data for table `tbl_users`
--

INSERT INTO `tbl_users` (`id`, `user_type`, `name`, `email`, `password`, `phone`, `confirm_code`, `status`) VALUES
(10, 'Normal', 'John Doe', 'johndeo@gmail.com', 'vishal', '1234567891', '', '1'),
(13, 'Normal', 'Demo', 'demo@gmail.com', 'demo', '123456', '', '1');

-- --------------------------------------------------------

--
-- Table structure for table `tbl_version`
--

CREATE TABLE IF NOT EXISTS `tbl_version` (
  `vid` int(11) NOT NULL AUTO_INCREMENT,
  `version_code` varchar(255) NOT NULL,
  `version_messages` varchar(255) NOT NULL,
  `version_url` varchar(255) NOT NULL,
  PRIMARY KEY (`vid`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 AUTO_INCREMENT=2 ;

--
-- Dumping data for table `tbl_version`
--

INSERT INTO `tbl_version` (`vid`, `version_code`, `version_messages`, `version_url`) VALUES
(1, '2', 'New Update Available please download it.', 'https://www.google.co.in/');

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
