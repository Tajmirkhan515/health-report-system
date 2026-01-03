-- phpMyAdmin SQL Dump
-- version 4.7.4
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Generation Time: Oct 03, 2018 at 02:37 PM
-- Server version: 10.1.30-MariaDB
-- PHP Version: 7.2.1

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET AUTOCOMMIT = 0;
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;

--
-- Database: `test_lab`
--

-- --------------------------------------------------------

--
-- Table structure for table `appointment`
--

CREATE TABLE `appointment` (
  `apm_id` int(11) NOT NULL,
  `report_date_time` datetime DEFAULT NULL,
  `annoced_date_time` datetime DEFAULT NULL,
  `state` varchar(50) DEFAULT NULL,
  `dr_id` varchar(50) DEFAULT NULL,
  `ptn_id` varchar(50) DEFAULT NULL
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Table structure for table `app_detail`
--

CREATE TABLE `app_detail` (
  `apm_dtl_id` int(11) NOT NULL,
  `apm_id` varchar(100) NOT NULL,
  `tst_id` varchar(100) NOT NULL,
  `result` varchar(200) DEFAULT NULL,
  `tdet_id` varchar(100) DEFAULT '0'
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Table structure for table `doctor`
--

CREATE TABLE `doctor` (
  `dr_id` int(11) NOT NULL,
  `dr_name` text NOT NULL,
  `dr_gender` varchar(10) NOT NULL,
  `dr_phone` varchar(50) DEFAULT NULL,
  `dr_specialization` varchar(50) DEFAULT NULL,
  `dr_address` varchar(100) DEFAULT NULL
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Table structure for table `patient`
--

CREATE TABLE `patient` (
  `ptn_id` int(11) NOT NULL,
  `ptn_name` text NOT NULL,
  `ptn_age` varchar(50) DEFAULT NULL,
  `ptn_gender` varchar(50) DEFAULT NULL,
  `ptn_address` varchar(100) DEFAULT NULL
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Table structure for table `test`
--

CREATE TABLE `test` (
  `tst_id` int(11) NOT NULL,
  `tst_name` varchar(100) NOT NULL,
  `tst_units` varchar(50) DEFAULT '%',
  `tst_price` varchar(50) DEFAULT NULL,
  `tst_normal_range` varchar(100) DEFAULT NULL,
  `note` text NOT NULL
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Table structure for table `tst_details`
--

CREATE TABLE `tst_details` (
  `tdet_id` int(11) NOT NULL,
  `tdet_name` varchar(200) DEFAULT NULL,
  `tdet_units` varchar(50) DEFAULT '-',
  `tdet_price` varchar(100) DEFAULT '0',
  `tdet_normal_range` varchar(50) DEFAULT NULL,
  `tst_id` varchar(50) NOT NULL
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

--
-- Indexes for dumped tables
--

--
-- Indexes for table `appointment`
--
ALTER TABLE `appointment`
  ADD PRIMARY KEY (`apm_id`);

--
-- Indexes for table `app_detail`
--
ALTER TABLE `app_detail`
  ADD PRIMARY KEY (`apm_dtl_id`);

--
-- Indexes for table `doctor`
--
ALTER TABLE `doctor`
  ADD PRIMARY KEY (`dr_id`);

--
-- Indexes for table `patient`
--
ALTER TABLE `patient`
  ADD PRIMARY KEY (`ptn_id`);

--
-- Indexes for table `test`
--
ALTER TABLE `test`
  ADD PRIMARY KEY (`tst_id`),
  ADD UNIQUE KEY `tst_name` (`tst_name`);

--
-- Indexes for table `tst_details`
--
ALTER TABLE `tst_details`
  ADD PRIMARY KEY (`tdet_id`),
  ADD UNIQUE KEY `tdet_name` (`tdet_name`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `appointment`
--
ALTER TABLE `appointment`
  MODIFY `apm_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=54;

--
-- AUTO_INCREMENT for table `app_detail`
--
ALTER TABLE `app_detail`
  MODIFY `apm_dtl_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=168;

--
-- AUTO_INCREMENT for table `doctor`
--
ALTER TABLE `doctor`
  MODIFY `dr_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=13;

--
-- AUTO_INCREMENT for table `test`
--
ALTER TABLE `test`
  MODIFY `tst_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=23;

--
-- AUTO_INCREMENT for table `tst_details`
--
ALTER TABLE `tst_details`
  MODIFY `tdet_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=34;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
