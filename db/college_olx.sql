-- phpMyAdmin SQL Dump
-- version 4.1.6
-- http://www.phpmyadmin.net
--
-- Host: 127.0.0.1
-- Generation Time: Mar 07, 2024 at 06:11 AM
-- Server version: 5.6.16
-- PHP Version: 5.5.9

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;

--
-- Database: `college_olx`
--

-- --------------------------------------------------------

--
-- Table structure for table `account_tbl`
--

CREATE TABLE IF NOT EXISTS `account_tbl` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `uid` varchar(100) NOT NULL,
  `accname` varchar(100) NOT NULL,
  `accountno` varchar(100) NOT NULL,
  `bank` varchar(100) NOT NULL,
  `pin` varchar(100) NOT NULL,
  `amount` varchar(100) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=3 ;

--
-- Dumping data for table `account_tbl`
--

INSERT INTO `account_tbl` (`id`, `uid`, `accname`, `accountno`, `bank`, `pin`, `amount`) VALUES
(1, '1', 'Anandu', '12345678', 'State Bank Of India', '12345', '36925'),
(2, '3', 'Sana', '12345666', 'South Indian Bank', '1234', '49715');

-- --------------------------------------------------------

--
-- Table structure for table `cart_tbl`
--

CREATE TABLE IF NOT EXISTS `cart_tbl` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `user_id` varchar(50) NOT NULL,
  `product_id` varchar(50) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=11 ;

--
-- Dumping data for table `cart_tbl`
--

INSERT INTO `cart_tbl` (`id`, `user_id`, `product_id`) VALUES
(5, '3', '1'),
(8, '3', '3');

-- --------------------------------------------------------

--
-- Table structure for table `history_tbl`
--

CREATE TABLE IF NOT EXISTS `history_tbl` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `uid` varchar(100) NOT NULL,
  `pname` varchar(100) NOT NULL,
  `price` varchar(100) NOT NULL,
  `image` tinytext NOT NULL,
  `date` varchar(100) NOT NULL,
  `order_id` varchar(100) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=8 ;

--
-- Dumping data for table `history_tbl`
--

INSERT INTO `history_tbl` (`id`, `uid`, `pname`, `price`, `image`, `date`, `order_id`) VALUES
(1, '1', 'WINGS OF FIRE: AUTOBIOGRAPHY OF ABDUL KALAM', '280', 'wingsoffire.jpg', '2023-10-31', '488850'),
(2, '1', 'IKIGAI : The Japanese secret to a long and happy life', '175', 'ikigai.jpg', '2023-10-31', '286295'),
(3, '1', 'IKIGAI : The Japanese secret to a long and happy life', '175', 'ikigai.jpg', '2023-10-31', '604599'),
(4, '1', 'Kelp Lunar Daypack', '1500', 'lunar.png', '2023-11-01', '878695'),
(5, '3', 'WINGS OF FIRE: AUTOBIOGRAPHY OF ABDUL KALAM', '285', 'wingsoffire.jpg', '2024-02-13', '791142'),
(6, '1', 'Kelp Lunar Daypack', '1500', 'lunar.png', '2024-03-07', '776203'),
(7, '1', 'WINGS OF FIRE: AUTOBIOGRAPHY OF ABDUL KALAM', '285', 'wingsoffire.jpg', '2024-03-07', '100667');

-- --------------------------------------------------------

--
-- Table structure for table `product_tbl`
--

CREATE TABLE IF NOT EXISTS `product_tbl` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `user_id` varchar(100) NOT NULL,
  `pname` varchar(100) NOT NULL,
  `description` text NOT NULL,
  `price` varchar(100) NOT NULL,
  `image` varchar(255) NOT NULL,
  `status` varchar(100) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=7 ;

--
-- Dumping data for table `product_tbl`
--

INSERT INTO `product_tbl` (`id`, `user_id`, `pname`, `description`, `price`, `image`, `status`) VALUES
(1, '1', 'WINGS OF FIRE: AUTOBIOGRAPHY OF ABDUL KALAM', 'Every common man who by his sheer grit and hard work achieves success should share his story with the rest for they may find inspiration and strength to go on, in his story. The ''Wings of Fire'' is one such autobiography by visionary scientist Dr. APJ Abdul Kalam, who from very humble beginnings rose to be the President of India. The book is full of insights, personal moments and life experiences of Dr. Kalam. It gives us an understanding on his journey of success.', '285', 'wingsoffire.jpg', 'sold'),
(2, '1', 'Kelp Lunar Daypack', 'Deskbound or travel-bound, Lunar is an all-time essential daypack, designed to meet most of your carry requirements with fitting storage solutions in a nice compact backpack. Made from 14 recycled PET bottles, the bag features high-utility, seamless functioning and clean aesthetic that lets you experience synergy with the world around you as you go about your day.', '1500', 'lunar.png', 'sold'),
(3, '3', 'IKIGAI : The Japanese secret to a long and happy life', 'Ikigai translates as a reason to live or a reason to jump out of bed in the morning. Its the place where your needs, desires, ambitions, and satisfaction meet.', '175', 'ikigai.jpg', 'available'),
(6, '3', 'meteor', 'badude polinja vandi\nbreak illa\nclutch potti\nexhaust vittu\nenginil vellam keeri', '180000', 'product6.jpg', 'available');

-- --------------------------------------------------------

--
-- Table structure for table `user_tbl`
--

CREATE TABLE IF NOT EXISTS `user_tbl` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(100) NOT NULL,
  `number` varchar(100) NOT NULL,
  `email` varchar(100) NOT NULL,
  `image` tinytext NOT NULL,
  `username` varchar(100) NOT NULL,
  `password` varchar(100) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=4 ;

--
-- Dumping data for table `user_tbl`
--

INSERT INTO `user_tbl` (`id`, `name`, `number`, `email`, `image`, `username`, `password`) VALUES
(1, 'Anandu', '8943409211', 'anandu@gmail.com', 'image1.jpg', 'anandu', '555'),
(2, 'nandu', '8921679456', 'n@gmail.com', 'image2.jpg', 'nandu', '666'),
(3, 'sana', '9999999999', 'sanabacker@gmail.com', 'image3.jpg', 'sana backer', 'sanasana');

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
