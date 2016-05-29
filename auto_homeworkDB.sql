-- phpMyAdmin SQL Dump
-- version 4.0.10.15
-- http://www.phpmyadmin.net
--
-- Client: localhost
-- Généré le: Dim 29 Mai 2016 à 11:40
-- Version du serveur: 5.1.73
-- Version de PHP: 5.3.3

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;

--
-- Base de données: `auto_homeworkDB`
--

-- --------------------------------------------------------

--
-- Structure de la table `admin`
--

CREATE TABLE IF NOT EXISTS `admin` (
  `id` int(3) NOT NULL AUTO_INCREMENT,
  `name` varchar(20) CHARACTER SET utf8 COLLATE utf8_turkish_ci NOT NULL,
  `surname` varchar(20) CHARACTER SET utf8 COLLATE utf8_turkish_ci NOT NULL,
  `mail` varchar(60) CHARACTER SET utf8 COLLATE utf8_turkish_ci NOT NULL,
  `address` varchar(150) CHARACTER SET utf8 COLLATE utf8_turkish_ci NOT NULL,
  `birthdate` date NOT NULL,
  `univ_n` int(9) NOT NULL,
  `gender` varchar(1) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `mail` (`mail`),
  UNIQUE KEY `univ_n` (`univ_n`)
) ENGINE=MyISAM  DEFAULT CHARSET=latin1 AUTO_INCREMENT=2 ;

--
-- Contenu de la table `admin`
--

INSERT INTO `admin` (`id`, `name`, `surname`, `mail`, `address`, `birthdate`, `univ_n`, `gender`) VALUES
(1, 'Zoheir', 'Doudou', 'zoheirdoudou@marmara.tr', 'Istanbul,turkey', '1992-05-21', 199515452, 'M');

-- --------------------------------------------------------

--
-- Structure de la table `Course`
--

CREATE TABLE IF NOT EXISTS `Course` (
  `id` int(3) NOT NULL AUTO_INCREMENT,
  `name` varchar(40) CHARACTER SET utf8 COLLATE utf8_turkish_ci NOT NULL,
  `semester` varchar(20) CHARACTER SET utf8 COLLATE utf8_turkish_ci NOT NULL,
  `description` varchar(2000) CHARACTER SET utf8 COLLATE utf8_turkish_ci NOT NULL,
  `instructor_id` int(3) NOT NULL,
  `from_d` datetime DEFAULT NULL,
  `to_d` datetime DEFAULT NULL,
  `status` varchar(10) CHARACTER SET utf8 COLLATE utf8_turkish_ci NOT NULL,
  `code` varchar(10) CHARACTER SET utf8 COLLATE utf8_turkish_ci NOT NULL,
  `grader_id` int(3) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `code` (`code`),
  KEY `instructor_id` (`instructor_id`),
  KEY `grader_id` (`grader_id`)
) ENGINE=MyISAM  DEFAULT CHARSET=latin1 COMMENT='Stores the courses information created by instructors' AUTO_INCREMENT=3 ;

--
-- Contenu de la table `Course`
--

INSERT INTO `Course` (`id`, `name`, `semester`, `description`, `instructor_id`, `from_d`, `to_d`, `status`, `code`, `grader_id`) VALUES
(1, 'Internat Programming', 'spring', 'Learn about JSF', 6, NULL, NULL, 'Active', 'CSE123', 3),
(2, 'System Control', 'spring', 'You learn about control systems', 6, NULL, NULL, 'Active', 'CSE555', 2);

-- --------------------------------------------------------

--
-- Structure de la table `Course_students`
--

CREATE TABLE IF NOT EXISTS `Course_students` (
  `course_id` int(3) NOT NULL,
  `student_id` int(6) NOT NULL,
  `status` varchar(5) DEFAULT NULL,
  PRIMARY KEY (`course_id`,`student_id`),
  KEY `student_id` (`student_id`)
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

--
-- Contenu de la table `Course_students`
--

INSERT INTO `Course_students` (`course_id`, `student_id`, `status`) VALUES
(2, 17, NULL),
(1, 0, NULL),
(2, 18, NULL),
(1, 18, NULL);

-- --------------------------------------------------------

--
-- Structure de la table `graders`
--

CREATE TABLE IF NOT EXISTS `graders` (
  `id` int(3) NOT NULL AUTO_INCREMENT,
  `name` varchar(20) CHARACTER SET utf8 COLLATE utf8_turkish_ci NOT NULL,
  `surname` varchar(20) CHARACTER SET utf8 COLLATE utf8_turkish_ci NOT NULL,
  `birthdate` date DEFAULT NULL,
  `gender` varchar(1) CHARACTER SET utf8 COLLATE utf8_turkish_ci NOT NULL,
  `mail` varchar(60) CHARACTER SET utf8 COLLATE utf8_turkish_ci NOT NULL,
  `address` varchar(150) CHARACTER SET utf8 COLLATE utf8_turkish_ci DEFAULT NULL,
  `univ_n` int(9) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `mail` (`mail`),
  UNIQUE KEY `grader_n` (`univ_n`)
) ENGINE=MyISAM  DEFAULT CHARSET=latin1 AUTO_INCREMENT=4 ;

--
-- Contenu de la table `graders`
--

INSERT INTO `graders` (`id`, `name`, `surname`, `birthdate`, `gender`, `mail`, `address`, `univ_n`) VALUES
(2, 'Zoheir', 'Doudou', NULL, 'M', 'zoheirdoudou29@marmara.tr', NULL, 199999990),
(3, 'Zoheir', 'Doudou', NULL, 'M', 'zoheirdoudou29@gmail.com', NULL, 199651321);

-- --------------------------------------------------------

--
-- Structure de la table `homework`
--

CREATE TABLE IF NOT EXISTS `homework` (
  `id` int(9) NOT NULL AUTO_INCREMENT,
  `name` varchar(30) CHARACTER SET utf8 COLLATE utf8_turkish_ci NOT NULL,
  `description` varchar(2000) CHARACTER SET utf8 COLLATE utf8_turkish_ci NOT NULL,
  `from_d` datetime NOT NULL,
  `to_d` datetime NOT NULL,
  `state` varchar(10) NOT NULL,
  `number_f` int(1) NOT NULL,
  `course_id` int(3) NOT NULL,
  `creator` varchar(15) NOT NULL,
  `creator_id` int(3) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `course_id` (`course_id`)
) ENGINE=MyISAM  DEFAULT CHARSET=latin1 COMMENT='Stores the homework created either by instructors or graders' AUTO_INCREMENT=31 ;

--
-- Contenu de la table `homework`
--

INSERT INTO `homework` (`id`, `name`, `description`, `from_d`, `to_d`, `state`, `number_f`, `course_id`, `creator`, `creator_id`) VALUES
(25, 'Modified Homework', 'Your First Homework', '2016-05-17 00:00:00', '2016-05-24 00:00:00', 'Disable', 1, 2, 'Instructor', 6),
(26, 'JSF homework', 'Implement all the course ', '2016-05-18 00:00:00', '2016-05-31 00:00:00', 'Close', 3, 1, 'Instructor', 6),
(27, 'Second project2', 'implement design pattern', '2016-05-19 00:00:00', '2016-05-31 00:00:00', 'Close', 1, 1, 'Instructor', 6),
(28, 'Third Homework', 'Hii', '2016-05-25 00:00:00', '2016-05-30 00:00:00', 'Open', 1, 1, 'Instructor', 6),
(29, 'grader Homework', 'This is the grager homework', '2016-05-17 00:00:00', '2016-05-24 00:00:00', 'Open', 1, 2, 'Grader', 2),
(30, 'Test homework', 'ggg', '2016-05-21 05:00:00', '2016-05-26 00:00:00', 'Open', 1, 2, 'Instructor', 6);

-- --------------------------------------------------------

--
-- Structure de la table `homework_students`
--

CREATE TABLE IF NOT EXISTS `homework_students` (
  `homework_id` int(9) NOT NULL,
  `student_id` int(6) NOT NULL,
  `status` varchar(10) CHARACTER SET utf8 COLLATE utf8_turkish_ci DEFAULT NULL,
  `remarq` varchar(100) CHARACTER SET utf8 COLLATE utf8_turkish_ci DEFAULT NULL,
  `grade` int(3) DEFAULT '0',
  PRIMARY KEY (`homework_id`,`student_id`),
  KEY `student_id` (`student_id`)
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

--
-- Contenu de la table `homework_students`
--

INSERT INTO `homework_students` (`homework_id`, `student_id`, `status`, `remarq`, `grade`) VALUES
(28, 18, 'Graded', 'good', 60),
(26, 18, 'Graded', 'Not submitted at time', 5),
(27, 18, 'Submitted', 'Not Graded', 0),
(29, 17, 'Graded', 'Graded', 50);

-- --------------------------------------------------------

--
-- Structure de la table `h_passwd`
--

CREATE TABLE IF NOT EXISTS `h_passwd` (
  `id` int(9) NOT NULL AUTO_INCREMENT,
  `hash` varchar(512) CHARACTER SET utf8 NOT NULL,
  `status` varchar(6) NOT NULL,
  `user` varchar(11) NOT NULL,
  `user_id` int(6) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `user_id` (`user_id`)
) ENGINE=MyISAM  DEFAULT CHARSET=latin1 COMMENT='Stores the users hash passwd' AUTO_INCREMENT=26 ;

--
-- Contenu de la table `h_passwd`
--

INSERT INTO `h_passwd` (`id`, `hash`, `status`, `user`, `user_id`) VALUES
(1, '7aa2728dcd8ef4bd0a66b53c2a2f8c1489623141dd12c6b6cb396e0835deae90dce07c4658b4be55304cb75107f6ae58cef7b6238c986b4bf8ed8ac5084ab557', 'Active', 'admin', 1),
(19, '7aa2728dcd8ef4bd0a66b53c2a2f8c1489623141dd12c6b6cb396e0835deae90dce07c4658b4be55304cb75107f6ae58cef7b6238c986b4bf8ed8ac5084ab557', 'Active', 'instructors', 6),
(25, '7aa2728dcd8ef4bd0a66b53c2a2f8c1489623141dd12c6b6cb396e0835deae90dce07c4658b4be55304cb75107f6ae58cef7b6238c986b4bf8ed8ac5084ab557', 'Active', 'students', 18),
(22, '1a476518d2d422621f6457773c8f41b6648aaf8573e357e4e9cdcd44bf952bf3ef4f8d061138c72fec439a259bfed3a875d35f63ba52ec024cadc050cf45151c', 'Close', 'students', 17),
(23, '7aa2728dcd8ef4bd0a66b53c2a2f8c1489623141dd12c6b6cb396e0835deae90dce07c4658b4be55304cb75107f6ae58cef7b6238c986b4bf8ed8ac5084ab557', 'Active', 'graders', 2),
(24, '81e95ff1c99acfc454155387ce7b06d4da8af951dcd599550c160fef270fce91c945a0fa085ced5d55c6d9c0a92775924f44f72baf1936439ef8ef328666f045', 'Close', 'graders', 3);

-- --------------------------------------------------------

--
-- Structure de la table `instructors`
--

CREATE TABLE IF NOT EXISTS `instructors` (
  `id` int(3) NOT NULL AUTO_INCREMENT,
  `name` varchar(20) CHARACTER SET utf8 COLLATE utf8_turkish_ci NOT NULL,
  `surname` varchar(20) CHARACTER SET utf8 COLLATE utf8_turkish_ci NOT NULL,
  `mail` varchar(60) CHARACTER SET utf8 COLLATE utf8_turkish_ci NOT NULL,
  `gender` varchar(1) CHARACTER SET utf8 COLLATE utf8_turkish_ci NOT NULL,
  `address` varchar(150) CHARACTER SET utf8 COLLATE utf8_turkish_ci DEFAULT NULL,
  `birthdate` date DEFAULT NULL,
  `univ_n` int(9) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `mail` (`mail`),
  UNIQUE KEY `instructor_n` (`univ_n`)
) ENGINE=MyISAM  DEFAULT CHARSET=latin1 AUTO_INCREMENT=7 ;

--
-- Contenu de la table `instructors`
--

INSERT INTO `instructors` (`id`, `name`, `surname`, `mail`, `gender`, `address`, `birthdate`, `univ_n`) VALUES
(6, 'zoheir', 'doudou', 'zoheirdoudou29@gmail.com', 'M', NULL, NULL, 199515334);

-- --------------------------------------------------------

--
-- Structure de la table `students`
--

CREATE TABLE IF NOT EXISTS `students` (
  `id` int(6) NOT NULL AUTO_INCREMENT COMMENT 'Uniq student id, user in DB level',
  `name` varchar(20) COLLATE utf8_turkish_ci NOT NULL,
  `surname` varchar(20) COLLATE utf8_turkish_ci NOT NULL,
  `mail` varchar(60) COLLATE utf8_turkish_ci NOT NULL,
  `gender` varchar(1) COLLATE utf8_turkish_ci NOT NULL,
  `address` varchar(150) COLLATE utf8_turkish_ci DEFAULT NULL,
  `birthdate` date DEFAULT NULL,
  `class` tinyint(4) DEFAULT NULL,
  `status` varchar(5) COLLATE utf8_turkish_ci DEFAULT NULL,
  `univ_n` int(9) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `student_N` (`univ_n`),
  UNIQUE KEY `mail` (`mail`)
) ENGINE=MyISAM  DEFAULT CHARSET=utf8 COLLATE=utf8_turkish_ci COMMENT='This table stores the student''s information, either under/po' AUTO_INCREMENT=19 ;

--
-- Contenu de la table `students`
--

INSERT INTO `students` (`id`, `name`, `surname`, `mail`, `gender`, `address`, `birthdate`, `class`, `status`, `univ_n`) VALUES
(18, 'zoheir', 'doudou', 'zoheirdoudou29@gmail.com', 'M', NULL, NULL, NULL, NULL, 199554141),
(17, 'zzz', 'ssss', 'zzz@gmail.com', 'M', NULL, NULL, NULL, NULL, 199923241);

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
