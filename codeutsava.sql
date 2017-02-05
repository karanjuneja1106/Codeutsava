-- phpMyAdmin SQL Dump
-- version 4.5.4.1deb2ubuntu2
-- http://www.phpmyadmin.net
--
-- Host: localhost
-- Generation Time: Feb 05, 2017 at 11:29 AM
-- Server version: 5.7.17-0ubuntu0.16.04.1
-- PHP Version: 7.0.13-0ubuntu0.16.04.1

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `codeutsava`
--

-- --------------------------------------------------------

--
-- Table structure for table `APPOINTMENT`
--

CREATE TABLE `APPOINTMENT` (
  `DOC_ID` varchar(200) NOT NULL,
  `MAIL_ID` varchar(200) NOT NULL,
  `Prefer_date` text NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `APPOINTMENT`
--

INSERT INTO `APPOINTMENT` (`DOC_ID`, `MAIL_ID`, `Prefer_date`) VALUES
('ramsingh@gmail.com', 'Madhuri@gmail.com', '12/02/2017'),
('ramsingh@gmail.com', 'radhaben@gmail.com', '19/02/2017'),
('ramsingh@gmail.com', 'GangaDevi@gmail.com', '5/02/2017'),
('ramsingh@gmail.com', 'chandani@yahoo.in', '15/02/2017'),
('ramsingh@gmail.com', 'shamili@rediffmail.com', '28/02/2017');

-- --------------------------------------------------------

--
-- Table structure for table `CLIENT_DETAILS`
--

CREATE TABLE `CLIENT_DETAILS` (
  `_id` int(11) NOT NULL,
  `Name` text NOT NULL,
  `Mail_id` varchar(200) NOT NULL,
  `Contact` text NOT NULL,
  `Weeks` varchar(11) NOT NULL,
  `City` text NOT NULL,
  `State` text NOT NULL,
  `Password` text NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `CLIENT_DETAILS`
--

INSERT INTO `CLIENT_DETAILS` (`_id`, `Name`, `Mail_id`, `Contact`, `Weeks`, `City`, `State`, `Password`) VALUES
(15, 'Madhuri', 'Madhuri@gmail.com', '9824259166', '5', 'Bombay', 'Maharashtra', '123456'),
(16, 'Radhaben', 'radhaben@gmail.com', '9876543333', '2', 'Mumbai', 'Maharashtra', '123456'),
(17, 'Chandani', 'chandani@yahoo.in', '9988776655', '1', 'Navi Mumbai', 'Maharashtra', '123456'),
(19, 'Shamili Devi', 'shamili@rediffmail.com', '9988006611', '12', 'Mumbai', 'Maharashtra', '123456');

-- --------------------------------------------------------

--
-- Table structure for table `CLIENT_MEDICAL_HISTORY`
--

CREATE TABLE `CLIENT_MEDICAL_HISTORY` (
  `Mail_id` varchar(50) NOT NULL,
  `Symptoms` varchar(500) NOT NULL,
  `Medical_Test` varchar(500) NOT NULL,
  `Medicines_Prescribed` varchar(500) NOT NULL,
  `Date_Time_Show` varchar(500) NOT NULL,
  `Date_Time_Store` varchar(500) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Table structure for table `DOCTOR_DETAILS`
--

CREATE TABLE `DOCTOR_DETAILS` (
  `_id` int(11) NOT NULL,
  `User_id` varchar(200) NOT NULL,
  `Name` text NOT NULL,
  `Password` text NOT NULL,
  `Contact` text NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `DOCTOR_DETAILS`
--

INSERT INTO `DOCTOR_DETAILS` (`_id`, `User_id`, `Name`, `Password`, `Contact`) VALUES
(3, 'ramsingh@gmail.com', 'Ram Singh', '123456', '9874563210'),
(4, 'radheShyam@ymail.com', 'Radhe Shyam', '123456', '9874673210'),
(5, 'GangaDevi@gmail.com', 'Ganga Devi', '123456', '9874123650'),
(6, 'dubeyRasika@gmail.com', 'Rasika Dubey', '123456', '9847123650');

--
-- Indexes for dumped tables
--

--
-- Indexes for table `CLIENT_DETAILS`
--
ALTER TABLE `CLIENT_DETAILS`
  ADD PRIMARY KEY (`_id`),
  ADD UNIQUE KEY `UNIQUE` (`Mail_id`);

--
-- Indexes for table `DOCTOR_DETAILS`
--
ALTER TABLE `DOCTOR_DETAILS`
  ADD PRIMARY KEY (`_id`),
  ADD UNIQUE KEY `UNIQUE` (`User_id`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `CLIENT_DETAILS`
--
ALTER TABLE `CLIENT_DETAILS`
  MODIFY `_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=23;
--
-- AUTO_INCREMENT for table `DOCTOR_DETAILS`
--
ALTER TABLE `DOCTOR_DETAILS`
  MODIFY `_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=7;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
