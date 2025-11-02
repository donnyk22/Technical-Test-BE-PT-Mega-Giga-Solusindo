-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Host: localhost
-- Generation Time: Nov 02, 2025 at 08:32 AM
-- Server version: 10.4.32-MariaDB
-- PHP Version: 8.0.30

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `bookstore`
--

-- --------------------------------------------------------

--
-- Table structure for table `books`
--

CREATE TABLE `books` (
  `id` int(11) NOT NULL,
  `title` varchar(255) NOT NULL,
  `author` varchar(100) NOT NULL,
  `price` decimal(10,2) NOT NULL,
  `stock` int(11) NOT NULL,
  `year` int(11) NOT NULL,
  `category_id` int(11) NOT NULL,
  `image_base64` text NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `books`
--

INSERT INTO `books` (`id`, `title`, `author`, `price`, `stock`, `year`, `category_id`, `image_base64`) VALUES
(1, 'The Great Gatsby', 'F. Scott Fitzgerald', 150000.00, 50, 1925, 1, ''),
(2, 'A Brief History of Time', 'Stephen Hawking', 200000.00, 30, 1988, 2, ''),
(3, 'Clean Code', 'Robert C. Martin', 350000.00, 20, 2008, 3, ''),
(4, 'Introduction to Algorithms', 'Thomas H. Cormen', 700000.00, 10, 2009, 3, ''),
(5, 'World War II History', 'Max Hastings', 280000.00, 15, 2011, 5, ''),
(6, 'Laskar Pelangi', 'Andrea Hirata', 95000.00, 100, 2005, 1, ''),
(7, '5 CM', 'Donny Dhirgantoro', 85000.00, 80, 2005, 1, ''),
(8, 'Dilan 1990', 'Pidi Baiq', 99000.00, 150, 2014, 1, ''),
(9, 'Negeri 5 Menara', 'Ahmad Fuadi', 90000.00, 60, 2009, 1, ''),
(10, 'Bumi', 'Tere Liye', 120000.00, 200, 2014, 1, ''),
(11, 'The Psychology of Money', 'Morgan Housel', 180000.00, 40, 2020, 4, ''),
(12, 'Atomic Habits', 'James Clear', 175000.00, 70, 2018, 4, ''),
(13, 'Deep Work', 'Cal Newport', 160000.00, 30, 2016, 4, ''),
(14, 'Rich Dad Poor Dad', 'Robert Kiyosaki', 140000.00, 90, 2000, 4, ''),
(15, 'The Power of Habit', 'Charles Duhigg', 150000.00, 70, 2012, 4, ''),
(16, 'Sapiens', 'Yuval Noah Harari', 200000.00, 25, 2011, 2, ''),
(17, 'Homo Deus', 'Yuval Noah Harari', 210000.00, 20, 2015, 2, ''),
(18, 'Brief Answers to the Big Questions', 'Stephen Hawking', 185000.00, 30, 2018, 2, ''),
(19, 'Cosmos', 'Carl Sagan', 195000.00, 18, 1980, 2, ''),
(20, 'The Selfish Gene', 'Richard Dawkins', 175000.00, 25, 1976, 2, ''),
(21, 'The Pragmatic Programmer', 'Andrew Hunt', 450000.00, 18, 1999, 3, ''),
(22, 'Design Patterns', 'Erich Gamma', 650000.00, 10, 1994, 3, ''),
(23, 'Refactoring', 'Martin Fowler', 550000.00, 12, 1999, 3, ''),
(24, 'Cracking the Coding Interview', 'Gayle Laakmann', 400000.00, 40, 2015, 3, ''),
(25, 'Java: The Complete Reference', 'Herbert Schildt', 500000.00, 15, 2018, 3, ''),
(26, 'Sejarah Indonesia', 'Kemdikbud', 60000.00, 200, 2016, 5, ''),
(27, 'Indonesia dalam Arus Sejarah', 'Tim Sejarah Indonesia', 250000.00, 30, 2012, 5, ''),
(28, 'Napoleon', 'Andrew Roberts', 220000.00, 15, 2014, 5, ''),
(29, 'Guns, Germs, and Steel', 'Jared Diamond', 190000.00, 40, 1997, 5, ''),
(30, 'The Silk Roads', 'Peter Frankopan', 210000.00, 18, 2015, 5, ''),
(31, 'Matematika SMA Kelas 12', 'Erlangga', 80000.00, 120, 2020, 4, ''),
(32, 'Bahasa Indonesia SMA', 'Erlangga', 75000.00, 110, 2020, 4, ''),
(33, 'Fisika SMA Kelas 12', 'Erlangga', 85000.00, 90, 2020, 4, ''),
(34, 'Kimia SMA Kelas 12', 'Erlangga', 82000.00, 95, 2020, 4, ''),
(35, 'Biologi SMA Kelas 12', 'Erlangga', 83000.00, 100, 2020, 4, ''),
(36, 'The Hobbit', 'J.R.R. Tolkien', 140000.00, 45, 1937, 1, ''),
(37, 'Harry Potter and the Sorcerer\'s Stone', 'J.K. Rowling', 160000.00, 60, 1997, 1, ''),
(38, 'The Catcher in the Rye', 'J.D. Salinger', 130000.00, 30, 1951, 1, ''),
(39, '1984', 'George Orwell', 145000.00, 50, 1949, 1, ''),
(40, 'To Kill a Mockingbird', 'Harper Lee', 135000.00, 55, 1960, 1, ''),
(41, 'Think and Grow Rich', 'Napoleon Hill', 120000.00, 70, 1937, 4, ''),
(42, 'The Subtle Art of Not Giving a F*ck', 'Mark Manson', 150000.00, 80, 2016, 4, ''),
(43, 'Ikigai', 'Héctor García', 130000.00, 100, 2016, 4, ''),
(44, 'Grit', 'Angela Duckworth', 160000.00, 40, 2016, 4, ''),
(45, 'Start With Why', 'Simon Sinek', 155000.00, 30, 2009, 4, ''),
(46, 'Digital Minimalism', 'Cal Newport', 165000.00, 35, 2019, 4, ''),
(47, 'Machine Learning', 'Tom Mitchell', 650000.00, 10, 1997, 3, ''),
(48, 'Artificial Intelligence', 'Stuart Russell', 800000.00, 8, 2021, 3, ''),
(49, 'Clean Architecture', 'Robert C. Martin', 480000.00, 25, 2017, 3, ''),
(50, 'Data Science from Scratch', 'Joel Grus', 520000.00, 22, 2015, 3, '');

-- --------------------------------------------------------

--
-- Table structure for table `categories`
--

CREATE TABLE `categories` (
  `id` int(11) NOT NULL,
  `name` varchar(100) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `categories`
--

INSERT INTO `categories` (`id`, `name`) VALUES
(1, 'Fiction'),
(2, 'Science'),
(3, 'Technology'),
(4, 'Education'),
(5, 'History');

-- --------------------------------------------------------

--
-- Table structure for table `orders`
--

CREATE TABLE `orders` (
  `id` int(11) NOT NULL,
  `user_id` int(11) NOT NULL,
  `total_price` decimal(10,2) NOT NULL,
  `status` enum('PENDING','PAID','CANCELLED') NOT NULL,
  `created_at` timestamp NOT NULL DEFAULT current_timestamp() ON UPDATE current_timestamp()
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `orders`
--

INSERT INTO `orders` (`id`, `user_id`, `total_price`, `status`, `created_at`) VALUES
(1, 2, 150000.00, 'PAID', '2025-11-01 05:28:43'),
(2, 3, 390000.00, 'PAID', '2025-11-01 05:28:43'),
(3, 2, 350000.00, 'PENDING', '2025-11-01 05:28:43'),
(4, 3, 280000.00, 'PAID', '2025-11-01 05:28:43'),
(5, 2, 95000.00, 'PAID', '2025-11-01 05:28:43'),
(6, 3, 520000.00, 'PENDING', '2025-11-01 05:28:43'),
(7, 2, 140000.00, 'CANCELLED', '2025-11-01 05:28:43'),
(8, 3, 700000.00, 'PAID', '2025-11-01 05:28:43'),
(9, 2, 175000.00, 'PENDING', '2025-11-01 05:28:43'),
(10, 3, 450000.00, 'PAID', '2025-11-01 05:28:43');

-- --------------------------------------------------------

--
-- Table structure for table `order_items`
--

CREATE TABLE `order_items` (
  `id` int(11) NOT NULL,
  `order_id` int(11) NOT NULL,
  `book_id` int(11) NOT NULL,
  `quantity` int(11) NOT NULL,
  `price` decimal(10,2) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `order_items`
--

INSERT INTO `order_items` (`id`, `order_id`, `book_id`, `quantity`, `price`) VALUES
(1, 1, 1, 1, 150000.00),
(2, 2, 12, 1, 175000.00),
(3, 2, 43, 1, 130000.00),
(4, 2, 41, 1, 120000.00),
(5, 3, 3, 1, 350000.00),
(6, 4, 5, 1, 280000.00),
(7, 5, 6, 1, 95000.00),
(8, 6, 50, 1, 520000.00),
(9, 7, 41, 1, 120000.00),
(10, 7, 32, 1, 75000.00),
(11, 8, 4, 1, 700000.00),
(12, 9, 12, 1, 175000.00),
(13, 10, 21, 1, 450000.00);

-- --------------------------------------------------------

--
-- Table structure for table `users`
--

CREATE TABLE `users` (
  `id` int(11) NOT NULL,
  `name` varchar(100) NOT NULL,
  `email` varchar(100) NOT NULL,
  `password` varchar(255) NOT NULL,
  `role` enum('user','admin') NOT NULL,
  `created_at` timestamp NOT NULL DEFAULT current_timestamp() ON UPDATE current_timestamp()
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `users`
--

INSERT INTO `users` (`id`, `name`, `email`, `password`, `role`, `created_at`) VALUES
(1, 'Admin', 'admin@gmail.com', '$2a$10$MOan/LI2GM2I3Sf7X0s5P.hHixsdcxPPQXO/srHbHNv5fpChUcBoi', 'admin', '2025-11-02 07:20:45'),
(2, 'Donny Kurniawan', 'donny@gmail.com', '$2a$10$Su9nlRjMTUqublvU1W2g2eQl4wj3OYmcXJi60ZFJtDDpBLGCXYPlW', 'user', '2025-11-02 07:22:31'),
(3, 'User', 'user@gmail.com', '$2a$10$2OIehvLLVTCApxEiPNKgjO62lMUa8VvfLbu7j6UMW9szztMwoi9fC', 'user', '2025-11-02 07:24:17');

--
-- Indexes for dumped tables
--

--
-- Indexes for table `books`
--
ALTER TABLE `books`
  ADD PRIMARY KEY (`id`),
  ADD KEY `fk_category_id` (`category_id`);

--
-- Indexes for table `categories`
--
ALTER TABLE `categories`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `orders`
--
ALTER TABLE `orders`
  ADD PRIMARY KEY (`id`),
  ADD KEY `fk_user_id` (`user_id`);

--
-- Indexes for table `order_items`
--
ALTER TABLE `order_items`
  ADD PRIMARY KEY (`id`),
  ADD KEY `fk_order_id` (`order_id`),
  ADD KEY `fk_book_id` (`book_id`);

--
-- Indexes for table `users`
--
ALTER TABLE `users`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `email` (`email`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `books`
--
ALTER TABLE `books`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=58;

--
-- AUTO_INCREMENT for table `categories`
--
ALTER TABLE `categories`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=8;

--
-- AUTO_INCREMENT for table `orders`
--
ALTER TABLE `orders`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=17;

--
-- AUTO_INCREMENT for table `order_items`
--
ALTER TABLE `order_items`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=20;

--
-- AUTO_INCREMENT for table `users`
--
ALTER TABLE `users`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=10;

--
-- Constraints for dumped tables
--

--
-- Constraints for table `books`
--
ALTER TABLE `books`
  ADD CONSTRAINT `fk_category_id` FOREIGN KEY (`category_id`) REFERENCES `categories` (`id`);

--
-- Constraints for table `orders`
--
ALTER TABLE `orders`
  ADD CONSTRAINT `fk_user_id` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`);

--
-- Constraints for table `order_items`
--
ALTER TABLE `order_items`
  ADD CONSTRAINT `fk_book_id` FOREIGN KEY (`book_id`) REFERENCES `books` (`id`),
  ADD CONSTRAINT `fk_order_id` FOREIGN KEY (`order_id`) REFERENCES `orders` (`id`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
