-- phpMyAdmin SQL Dump
-- version 4.0.4
-- http://www.phpmyadmin.net
--
-- Host: localhost
-- Generation Time: May 29, 2021 at 02:04 AM
-- Server version: 5.6.12-log
-- PHP Version: 5.4.12

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;

--
-- Database: `smart_shopie`
--
CREATE DATABASE IF NOT EXISTS `smart_shopie` DEFAULT CHARACTER SET latin1 COLLATE latin1_swedish_ci;
USE `smart_shopie`;

-- --------------------------------------------------------

--
-- Table structure for table `cart`
--

CREATE TABLE IF NOT EXISTS `cart` (
  `cart_id` int(10) NOT NULL AUTO_INCREMENT,
  `user_id` int(10) DEFAULT NULL,
  `product_id` int(10) DEFAULT NULL,
  `quantity` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`cart_id`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8mb4 AUTO_INCREMENT=5 ;

--
-- Dumping data for table `cart`
--

INSERT INTO `cart` (`cart_id`, `user_id`, `product_id`, `quantity`) VALUES
(1, 5, 11, '2'),
(2, 5, 12, '1'),
(4, 2, 13, '1');

-- --------------------------------------------------------

--
-- Table structure for table `complaint`
--

CREATE TABLE IF NOT EXISTS `complaint` (
  `complaint_id` int(10) NOT NULL AUTO_INCREMENT,
  `user_id` int(10) DEFAULT NULL,
  `store_id` int(10) DEFAULT NULL,
  `complaint_description` varchar(100) DEFAULT NULL,
  `reply_description` varchar(100) DEFAULT 'pending',
  `complaint_date` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`complaint_id`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8mb4 AUTO_INCREMENT=8 ;

--
-- Dumping data for table `complaint`
--

INSERT INTO `complaint` (`complaint_id`, `user_id`, `store_id`, `complaint_description`, `reply_description`, `complaint_date`) VALUES
(1, 2, 3, 'your shop is not at all', 'oke i will do', '2021-09-21'),
(2, 2, 3, 'you know what to doo', 'ok i know what to do', '2021-02-04'),
(3, 2, 3, 'so i am so late', 'pending', '2020-2-5'),
(4, 2, 2, 'oke by', 'pending', '2021-05-18'),
(5, 8, 2, 'this shop is bad delivery is not good', 'oke i will update u', '2021-05-19'),
(6, 9, 5, 'delivery is not good', 'we will talk about this to the store owner', '2021-05-21'),
(7, 9, 4, 'bad shop really really bad', 'pending', '2021-05-21');

-- --------------------------------------------------------

--
-- Table structure for table `departmentemployees`
--

CREATE TABLE IF NOT EXISTS `departmentemployees` (
  `employee_id` int(10) NOT NULL AUTO_INCREMENT,
  `store_id` int(10) DEFAULT NULL,
  `login_id` int(10) DEFAULT NULL,
  `first_name_employee` varchar(100) DEFAULT NULL,
  `last_name_employee` varchar(100) DEFAULT NULL,
  `place` varchar(100) DEFAULT NULL,
  `phone` varchar(12) DEFAULT NULL,
  `email` varchar(50) DEFAULT NULL,
  `gender` varchar(10) DEFAULT NULL,
  `dob` date DEFAULT NULL,
  PRIMARY KEY (`employee_id`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8mb4 AUTO_INCREMENT=5 ;

--
-- Dumping data for table `departmentemployees`
--

INSERT INTO `departmentemployees` (`employee_id`, `store_id`, `login_id`, `first_name_employee`, `last_name_employee`, `place`, `phone`, `email`, `gender`, `dob`) VALUES
(1, 3, 10, 'rajan', 'mathew', 'thrikakara', '9854741451', 'rajan@gmail.com', 'Male', '1985-05-03'),
(2, 3, 13, 'fready', 'wilson', 'Aluva', '9852147888', 'fredy@gmail.com', 'Male', '2000-05-09'),
(3, 5, 14, 'amal', 'vs', 'Aluva', '8454545457', 'amalvs33@gmail.com', 'Male', '2000-04-03'),
(4, 6, 16, 'johnny ', 'sing', 'america ', '985551451451', 'johny@gmail.com', 'Male', '1997-06-25');

-- --------------------------------------------------------

--
-- Table structure for table `login`
--

CREATE TABLE IF NOT EXISTS `login` (
  `login_id` int(10) NOT NULL AUTO_INCREMENT,
  `username` varchar(100) DEFAULT NULL,
  `password` varchar(100) DEFAULT NULL,
  `usertype` varchar(100) DEFAULT NULL,
  `status` int(10) NOT NULL DEFAULT '1',
  PRIMARY KEY (`login_id`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8mb4 AUTO_INCREMENT=24 ;

--
-- Dumping data for table `login`
--

INSERT INTO `login` (`login_id`, `username`, `password`, `usertype`, `status`) VALUES
(1, 'admin', 'admin', 'admin', 1),
(2, 'paragon@gmail.com', 'paragon', 'store', 1),
(3, 'paragon@gmail.com', 'paragon', 'store', 0),
(4, 'connect@gmail.com', 'connect', 'store', 1),
(5, 'amalvs@gmail.com', 'amal', 'user', 1),
(6, 'wilfred@gmail.com', 'fredy', 'user', 1),
(7, 'dominos@gmail.com', 'dominos', 'store', 1),
(8, 'burgerhut@gmail.com', 'burgerhut', 'store', 1),
(9, 'starmalabar@gmail.com', 'starmalabar', 'store', 1),
(10, 'rajan@gmail.com', 'rajan', 'stock', 1),
(11, 'amalvs33@gmail.com', '852147', 'employee', 1),
(12, 'fredy@gmail.com', '852147', 'employee', 1),
(13, 'fredy@gmail.com', '852147', 'employee', 1),
(14, 'amalvs33@gmail.com', '/74410', 'employee', 1),
(15, 'crocs@gmail.com', 'crocs', 'store', 0),
(16, 'johny@gmail.com', 'johny', 'employee', 1),
(17, 'amal@gmail.com', 'amal', 'user', 1),
(18, 'devu@gmail.com', 'devu', 'user', 1),
(19, 'deepa@gmail.com', 'deepa', 'user', 1),
(20, 'shaja@gmail.com', 'shaja', 'user', 1),
(21, 'devu@gmail.com', 'devu', 'user', 1),
(22, 'fasal@gmail.com', 'fasal', 'user', 1),
(23, 'valeeth@gmail.com', 'valeeth', 'user', 1);

-- --------------------------------------------------------

--
-- Table structure for table `orderstatus`
--

CREATE TABLE IF NOT EXISTS `orderstatus` (
  `status_id` int(10) NOT NULL AUTO_INCREMENT,
  `sm_id` int(10) DEFAULT NULL,
  `update_description` varchar(100) DEFAULT 'pending',
  `date_time` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`status_id`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8mb4 AUTO_INCREMENT=15 ;

--
-- Dumping data for table `orderstatus`
--

INSERT INTO `orderstatus` (`status_id`, `sm_id`, `update_description`, `date_time`) VALUES
(1, 1, 'Deliverd', '2021-04-02'),
(2, 8, 'Paid', '2021-05-19'),
(3, 9, 'Paid', '2021-05-19'),
(4, 10, 'Paid', '2021-05-19'),
(5, 11, 'Paid', '2021-05-21'),
(6, 12, 'Paid', '2021-05-21'),
(7, 13, 'pending', '2021-05-21'),
(8, 14, 'pending', '2021-05-21'),
(9, 15, 'pending', '2021-05-21'),
(10, 16, 'Paid', '2021-05-21'),
(11, 17, 'pending', '2021-05-21'),
(12, 18, 'pending', '2021-05-21'),
(13, 19, 'Paid', '2021-05-21'),
(14, 20, 'Paid', '2021-05-21');

-- --------------------------------------------------------

--
-- Table structure for table `payment`
--

CREATE TABLE IF NOT EXISTS `payment` (
  `payment_id` int(10) NOT NULL AUTO_INCREMENT,
  `sm_id` int(10) DEFAULT NULL,
  `amount` varchar(100) DEFAULT NULL,
  `payment_mode` varchar(100) DEFAULT NULL,
  `payment_date` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`payment_id`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8mb4 AUTO_INCREMENT=11 ;

--
-- Dumping data for table `payment`
--

INSERT INTO `payment` (`payment_id`, `sm_id`, `amount`, `payment_mode`, `payment_date`) VALUES
(2, 8, '250', 'online', '2021-05-19'),
(3, 9, '125', 'online', '2021-05-19'),
(4, 10, '998', 'online', '2021-05-19'),
(7, 12, '14999', 'online', '2021-05-21'),
(8, 16, '125', 'online', '2021-05-21'),
(9, 19, '499', 'online', '2021-05-21'),
(10, 20, '1998', 'online', '2021-05-21');

-- --------------------------------------------------------

--
-- Table structure for table `products`
--

CREATE TABLE IF NOT EXISTS `products` (
  `product_id` int(10) NOT NULL AUTO_INCREMENT,
  `product_name` varchar(100) DEFAULT NULL,
  `description` varchar(200) DEFAULT NULL,
  `image` varchar(500) DEFAULT NULL,
  `amount` varchar(100) DEFAULT NULL,
  `store_id` int(10) DEFAULT NULL,
  `status` int(10) NOT NULL DEFAULT '1',
  PRIMARY KEY (`product_id`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8mb4 AUTO_INCREMENT=16 ;

--
-- Dumping data for table `products`
--

INSERT INTO `products` (`product_id`, `product_name`, `description`, `image`, `amount`, `store_id`, `status`) VALUES
(1, 'T V', 'TV is a steamed rice served with various choices of pre-cooked dishes originated from West Sumatra, Indonesia. It is known across Indonesia as TV.', '/static/upload/97607694-9f03-47f5-9c69-70c4644e8b44632fd866-25d2-482f-8e54-41da869081betv.webp', '350000', 3, 1),
(2, 'pc', 'PC is a steamed rice served with various choices of pre-cooked dishes originated from West Sumatra, Indonesia. It is known across Indonesia as PC.', 'static/upload/00cd881e-ccce-4fde-916c-ceb34c4dda04pc.jpg', '450000', 3, 1),
(3, 'red tape', 'Red Tape is a steamed rice served with various choices of pre-cooked dishes originated from West Sumatra, Indonesia. It is known across Indonesia as RED Tape.', '/static/upload/aac6a595-83f5-43a2-bd41-aa4d7b56c4310b400e9e-e5ef-4e20-a06b-5fa58a32d780redtape.jpg', '1499', 3, 1),
(4, 'converse', 'Converse is a steamed rice served with various choices of pre-cooked dishes originated from West Sumatra, Indonesia. It is known across Indonesia as Converse.', '/static/upload/ccfa6e11-d14d-480e-9b0d-5b80eb4d4e8c22af0243-5144-41ec-961a-b39cc9735777converse3.jpg', '1599', 3, 1),
(11, 'Biscut', 'Biscut is a steamed rice served with various choices of pre-cooked dishes originated from West Sumatra, Indonesia. It is known across Indonesia as Biscut.', '/static/upload/040c9837-b537-41ea-a45a-f64b95420cc72049331d-a7dc-427f-b033-ad40d52610a5biscut.jpeg', '25', 3, 1),
(12, 'jeans', 'jeans is a steamed rice served with various choices of pre-cooked dishes originated from West Sumatra, Indonesia. It is known across Indonesia as jeans.', 'static/upload/9aea7ce5-da29-43e6-a167-6b673c8d4541pexels-arina-krasnikova-5418928.jpg', '599', 3, 0),
(13, 'converse', 'Converse is a steamed rice served with various choices of pre-cooked dishes originated from West Sumatra, Indonesia. It is known across Indonesia as Converse.', '/static/upload/9bf09aa8-ebf3-4ab7-9869-f1be484049e2pexels-andre-moura-2562992.jpg', '499', 6, 1),
(14, 'fan', 'Fan is a steamed rice served with various choices of pre-cooked dishes originated from West Sumatra, Indonesia. It is known across Indonesia as fan.', '/static/upload/d8621b71-744a-4554-81b9-3c344c2c890aheadway-5QgIuuBxKwM-unsplash.jpg', '999', 3, 1),
(15, 'Redmi K20 Pro', 'Redmi is a steamed rice served with various choices of pre-cooked dishes originated from West Sumatra, Indonesia. It is known across Indonesia as Redmi.', '/static/upload/6b4a9e25-2842-48f6-8205-065f4ed01a4dpexels-pixabay-4158.jpg', '14000', 6, 1);

-- --------------------------------------------------------

--
-- Table structure for table `rating`
--

CREATE TABLE IF NOT EXISTS `rating` (
  `rating_id` int(10) NOT NULL AUTO_INCREMENT,
  `store_id` int(10) DEFAULT NULL,
  `user_id` int(10) DEFAULT NULL,
  `rated_point` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`rating_id`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8mb4 AUTO_INCREMENT=2 ;

--
-- Dumping data for table `rating`
--

INSERT INTO `rating` (`rating_id`, `store_id`, `user_id`, `rated_point`) VALUES
(1, 3, 2, '5.0');

-- --------------------------------------------------------

--
-- Table structure for table `salesdetails`
--

CREATE TABLE IF NOT EXISTS `salesdetails` (
  `sd_id` int(10) NOT NULL AUTO_INCREMENT,
  `sm_id` int(10) DEFAULT NULL,
  `product_id` int(10) DEFAULT NULL,
  `quantity` varchar(50) DEFAULT NULL,
  `amount` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`sd_id`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8mb4 AUTO_INCREMENT=27 ;

--
-- Dumping data for table `salesdetails`
--

INSERT INTO `salesdetails` (`sd_id`, `sm_id`, `product_id`, `quantity`, `amount`) VALUES
(9, 9, 11, '5', '125'),
(10, 10, 13, '2', '998'),
(19, 15, 14, '1', '999'),
(20, 15, 15, '1', '14000'),
(21, 16, 11, '5', '125'),
(22, 17, 11, '10', '250'),
(23, 18, 11, '3', '75'),
(24, 19, 13, '1', '499'),
(25, 20, 14, '2', '1998'),
(26, 21, 13, '5', '2500');

-- --------------------------------------------------------

--
-- Table structure for table `salesmaster`
--

CREATE TABLE IF NOT EXISTS `salesmaster` (
  `sm_id` int(10) NOT NULL AUTO_INCREMENT,
  `user_id` int(10) DEFAULT NULL,
  `total_amount` varchar(100) DEFAULT NULL,
  `sales_date` varchar(100) DEFAULT NULL,
  `store_id` int(10) DEFAULT NULL,
  `status` varchar(100) NOT NULL DEFAULT '1',
  PRIMARY KEY (`sm_id`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8mb4 AUTO_INCREMENT=22 ;

--
-- Dumping data for table `salesmaster`
--

INSERT INTO `salesmaster` (`sm_id`, `user_id`, `total_amount`, `sales_date`, `store_id`, `status`) VALUES
(9, 8, '125', '2021-05-19', 3, '0'),
(10, 8, '998', '2021-05-19', 6, '0'),
(15, 8, '14999', '2021-05-21', 3, '1'),
(16, 8, '125', '2021-05-21', 3, '0'),
(17, 9, '250', '2021-05-21', 3, '1'),
(18, 9, '75', '2021-05-21', 3, '1'),
(19, 9, '499', '2021-05-21', 6, '1'),
(20, 9, '1998', '2021-05-21', 3, '1'),
(21, 6, '2500', '2021-05-06', 6, '1');

-- --------------------------------------------------------

--
-- Table structure for table `stock`
--

CREATE TABLE IF NOT EXISTS `stock` (
  `stock_id` int(10) NOT NULL AUTO_INCREMENT,
  `product_id` int(10) DEFAULT NULL,
  `avaliable_quantity` varchar(100) DEFAULT NULL,
  `unit` varchar(100) DEFAULT NULL,
  `store_id` int(10) DEFAULT NULL,
  PRIMARY KEY (`stock_id`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8mb4 AUTO_INCREMENT=10 ;

--
-- Dumping data for table `stock`
--

INSERT INTO `stock` (`stock_id`, `product_id`, `avaliable_quantity`, `unit`, `store_id`) VALUES
(1, 1, '20', 'number', 3),
(2, 2, '50', 'number', 3),
(3, 3, '20', 'number', 3),
(4, 4, '20', 'number', 3),
(5, 11, '32', 'number', 3),
(6, 12, '50', 'number', 3),
(7, 13, '17', 'number', 6),
(8, 14, '13', 'number', 3),
(9, 15, '45', 'number', 6);

-- --------------------------------------------------------

--
-- Table structure for table `stores`
--

CREATE TABLE IF NOT EXISTS `stores` (
  `store_id` int(10) NOT NULL AUTO_INCREMENT,
  `login_id` int(10) DEFAULT NULL,
  `store_name` varchar(100) DEFAULT NULL,
  `phone` varchar(12) DEFAULT NULL,
  `email` varchar(50) DEFAULT NULL,
  `latitude` varchar(200) DEFAULT NULL,
  `longitude` varchar(200) DEFAULT NULL,
  PRIMARY KEY (`store_id`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8mb4 AUTO_INCREMENT=8 ;

--
-- Dumping data for table `stores`
--

INSERT INTO `stores` (`store_id`, `login_id`, `store_name`, `phone`, `email`, `latitude`, `longitude`) VALUES
(2, 3, 'paragon', '8945345665', 'paragon@gmail.com', '9.971859505830913', '76.29844754934311'),
(3, 4, 'Connect', '8907338043', 'connect@gmail.com', '9.981589195941316', '76.30245108477784'),
(4, 7, 'Dominos', '9877664322', 'dominos@gmail.com', '9.977546285776178', '76.29989802179901'),
(5, 8, 'Burger Hut', '7845963214', 'burgerhut@gmail.com', '9.977546285776178', '76.29989802179901'),
(6, 9, 'Star Malabar', '98745454541', 'starmalabar@gmail.com', '9.975460386927724', '76.28042102558784'),
(7, 15, 'Crocs', '9874545454', 'crocs@gmail.com', '9.967384950546261', '76.31841559283448');

-- --------------------------------------------------------

--
-- Table structure for table `users`
--

CREATE TABLE IF NOT EXISTS `users` (
  `user_id` int(10) NOT NULL AUTO_INCREMENT,
  `login_id` int(10) DEFAULT NULL,
  `first_name` varchar(100) DEFAULT NULL,
  `last_name` varchar(100) DEFAULT NULL,
  `phone` varchar(12) DEFAULT NULL,
  `email` varchar(50) DEFAULT NULL,
  `latitude` varchar(200) DEFAULT NULL,
  `longitude` varchar(200) DEFAULT NULL,
  `house` varchar(100) DEFAULT NULL,
  `place` varchar(100) DEFAULT NULL,
  `pincode` int(10) DEFAULT NULL,
  PRIMARY KEY (`user_id`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8mb4 AUTO_INCREMENT=10 ;

--
-- Dumping data for table `users`
--

INSERT INTO `users` (`user_id`, `login_id`, `first_name`, `last_name`, `phone`, `email`, `latitude`, `longitude`, `house`, `place`, `pincode`) VALUES
(1, 5, 'Amal', 'veliyath sunilkumar', '985471246', 'amalvs@gmail.com', '3.25235235235', '84.7984654644', 'veliyath', 'aluva', 658754),
(2, 6, 'wilfred', 'wilson', '97951456418', 'wilfred@gmail.com', '2.6797464464', '3.6949846349749674', 'thaykattukara', 'paravoor', 657774),
(6, 20, 'shaja', 'khan', '7852456312', 'shaja@gmail.com', '37.421998333333335', '-122.08400000000002', 'tajmahal', 'delhi', 75339),
(7, 21, 'devu', 'devu', '9874563214', 'devu@gmail.com', '10.102743', '76.3798927', 'kodavath', 'aluva', 655478),
(8, 22, 'fasal', 'ps', '9845612583', 'fasal@gmail.com', '10.098045', '76.3707827', 'kunjattavettil', 'aluva', 684123),
(9, 23, 'valeeth', 'bin', '8109742531', 'valeeth@gmail.com', '10.0958584', '76.367527', 'kodavath', 'aluva', 683101);

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
