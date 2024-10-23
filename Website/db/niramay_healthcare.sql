-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Host: localhost
-- Generation Time: Oct 23, 2024 at 05:36 PM
-- Server version: 10.4.28-MariaDB
-- PHP Version: 8.2.4

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `niramay_healthcare`
--

-- --------------------------------------------------------

--
-- Table structure for table `contactform`
--

CREATE TABLE `contactform` (
  `id` int(11) NOT NULL,
  `name` varchar(200) NOT NULL,
  `email` varchar(200) NOT NULL,
  `subject` text NOT NULL,
  `message` text NOT NULL,
  `timestamp` timestamp NOT NULL DEFAULT current_timestamp()
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Table structure for table `disease`
--

CREATE TABLE `disease` (
  `disease_id` int(11) NOT NULL,
  `disease_name` varchar(50) NOT NULL,
  `disease_symptoms` text NOT NULL,
  `modes_of_transmission` text NOT NULL,
  `disease_detail` text NOT NULL,
  `disease_precaution` text NOT NULL,
  `test_name` varchar(255) NOT NULL DEFAULT '0',
  `test_price` varchar(255) NOT NULL DEFAULT '0',
  `test_details` text NOT NULL DEFAULT '0',
  `vaccine_name` varchar(255) NOT NULL DEFAULT '0',
  `vaccine_price` varchar(255) NOT NULL DEFAULT '0'
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

--
-- Dumping data for table `disease`
--

INSERT INTO `disease` (`disease_id`, `disease_name`, `disease_symptoms`, `modes_of_transmission`, `disease_detail`, `disease_precaution`, `test_name`, `test_price`, `test_details`, `vaccine_name`, `vaccine_price`) VALUES
(1, 'Corona', 'Most common symptoms:\r\nfever\r\ndry cough\r\ntiredness.\r\nLess common symptoms:\r\naches and pains\r\nsore throat\r\ndiarrhoea\r\nconjunctivitis\r\nheadache\r\nloss of taste or smell\r\na rash on skin, or discolouration of fingers or toes', 'touch, air, close contact with infected person.', 'Coronaviruses are a large family of viruses that cause illness ranging from the common cold to more severe diseases such as Middle East Respiratory Syndrome and Severe Acute Respiratory Syndrome. 2019-nCoV is a new strain that has not been previously identified in humans and causes COVID19/coronavirus disease.', 'To prevent the spread of COVID-19:\r\nClean your hands often. Use soap and water, or an alcohol-based hand rub.\r\nMaintain a safe distance from anyone who is coughing or sneezing.\r\nWear a mask when physical distancing is not possible.\r\nDon’t touch your eyes, nose or mouth.\r\nCover your nose and mouth with your bent elbow or a tissue when you cough or sneeze.\r\nStay home if you feel unwell.\r\nIf you have a fever, cough and difficulty breathing, seek medical attention.\r\nCalling in advance allows your healthcare provider to quickly direct you to the right health facility. This protects you, and prevents the spread of viruses and other infections.\r\n\r\nMasks can help prevent the spread of the virus from the person wearing the mask to others. Masks alone do not protect against COVID-19, and should be combined with physical distancing and hand hygiene. Follow the advice provided by your local health authority', 'sample', 'sample', 'sample', 'CoviShield', 'Free'),
(2, 'Sample', 'abc', 'abc', 'abc', 'abc', '0', '0', '0', '0', '0');

-- --------------------------------------------------------

--
-- Table structure for table `facilities`
--

CREATE TABLE `facilities` (
  `fac_id` int(11) NOT NULL,
  `fac_name` varchar(50) NOT NULL,
  `fac_city` varchar(20) NOT NULL,
  `fac_address` varchar(255) NOT NULL,
  `fac_time` varchar(50) DEFAULT NULL,
  `fac_contact` varchar(50) NOT NULL,
  `beds_occupacy` varchar(255) NOT NULL DEFAULT '0',
  `data_last_updated` varchar(255) NOT NULL DEFAULT '0',
  `cities` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

-- --------------------------------------------------------

--
-- Table structure for table `medicines`
--

CREATE TABLE `medicines` (
  `disease` varchar(250) NOT NULL DEFAULT '0',
  `med_name` varchar(250) NOT NULL DEFAULT '0',
  `med_price` varchar(250) NOT NULL DEFAULT '0'
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

--
-- Dumping data for table `medicines`
--

INSERT INTO `medicines` (`disease`, `med_name`, `med_price`) VALUES
('corona', 'Remdecivir', '2000'),
('corona', 'sample', '100'),
('sample', 'test', '100');

-- --------------------------------------------------------

--
-- Table structure for table `user`
--

CREATE TABLE `user` (
  `user_id` int(11) NOT NULL,
  `name` varchar(50) DEFAULT NULL,
  `username` varchar(50) DEFAULT NULL,
  `email` varchar(50) DEFAULT NULL,
  `phone` varchar(10) DEFAULT NULL,
  `city` int(11) NOT NULL DEFAULT 0,
  `password` varchar(16) DEFAULT NULL,
  `profile_pic` varchar(255) NOT NULL DEFAULT 'https://niramayhealthcare.000webhostapp.com/images/profile_icon.jpg',
  `weight` varchar(10) NOT NULL DEFAULT '0',
  `height` varchar(10) NOT NULL DEFAULT '0',
  `age` varchar(255) NOT NULL DEFAULT 'NA',
  `gender` int(11) NOT NULL DEFAULT 0,
  `disease` int(11) NOT NULL DEFAULT 0,
  `status` int(11) NOT NULL DEFAULT 1,
  `isadmin` int(11) NOT NULL DEFAULT 0,
  `isauth` int(11) NOT NULL DEFAULT 0
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

--
-- Indexes for dumped tables
--

--
-- Indexes for table `contactform`
--
ALTER TABLE `contactform`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `disease`
--
ALTER TABLE `disease`
  ADD PRIMARY KEY (`disease_id`);

--
-- Indexes for table `facilities`
--
ALTER TABLE `facilities`
  ADD PRIMARY KEY (`fac_id`);

--
-- Indexes for table `user`
--
ALTER TABLE `user`
  ADD PRIMARY KEY (`user_id`),
  ADD KEY `user_id` (`user_id`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `contactform`
--
ALTER TABLE `contactform`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT for table `disease`
--
ALTER TABLE `disease`
  MODIFY `disease_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=3;

--
-- AUTO_INCREMENT for table `facilities`
--
ALTER TABLE `facilities`
  MODIFY `fac_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=6;

--
-- AUTO_INCREMENT for table `user`
--
ALTER TABLE `user`
  MODIFY `user_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=51;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
