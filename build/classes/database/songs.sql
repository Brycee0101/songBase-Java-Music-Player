-- phpMyAdmin SQL Dump
-- version 5.2.0
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Generation Time: Jul 18, 2023 at 05:32 PM
-- Server version: 10.4.25-MariaDB
-- PHP Version: 8.1.10

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `songbase`
--

-- --------------------------------------------------------

--
-- Table structure for table `songs`
--

CREATE TABLE `songs` (
  `song_id` int(11) NOT NULL,
  `song_title` varchar(50) NOT NULL,
  `song_artist` varchar(250) NOT NULL,
  `song_duration` varchar(50) NOT NULL,
  `song_fpath` varchar(250) NOT NULL,
  `song_lyrfpath` varchar(250) NOT NULL,
  `song_imgfpath` varchar(255) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `songs`
--

INSERT INTO `songs` (`song_id`, `song_title`, `song_artist`, `song_duration`, `song_fpath`, `song_lyrfpath`, `song_imgfpath`) VALUES
(1, 'Wake Me Up When September Ends', 'Green Day', '04:45', 'src//resources//songs//Wake Me Up When September Ends.mp3', 'src//resources//lyrics//Wake Me Up When September Ends.txt', 'resources/img/wake-me-up-when-september-ends.png'),
(2, 'Candy Paint', 'Post Malone', '03:47', 'src//resources//songs//Candy Paint.mp3', 'src//resources//lyrics//Candy Paint.txt', 'resources/img/candy-paint.png'),
(4, 'Kiss Kiss', 'MGK', '02:24', 'src//resources//songs//kiss kiss.mp3', 'src//resources//lyrics//Kiss Kiss.txt', 'resources/img/kiss-kiss.png'),
(5, 'Halftime', 'Nas', '04:27', 'src//resources//songs//Halftime.mp3', 'src//resources//lyrics//Halftime.txt', 'resources/img/Halftime.png'),
(6, 'Humble', 'Kendrick Lamar', '02:56', 'src//resources//songs//Humble.mp3', 'src//resources//lyrics//Humble.txt', 'resources/img/Humble.png');

--
-- Indexes for dumped tables
--

--
-- Indexes for table `songs`
--
ALTER TABLE `songs`
  ADD PRIMARY KEY (`song_id`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `songs`
--
ALTER TABLE `songs`
  MODIFY `song_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=7;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
