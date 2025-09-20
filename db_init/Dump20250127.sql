CREATE DATABASE  IF NOT EXISTS `sudoku_brain` /*!40100 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci */ /*!80016 DEFAULT ENCRYPTION='N' */;
USE `sudoku_brain`;
-- MySQL dump 10.13  Distrib 8.0.38, for Win64 (x86_64)
--
-- Host: localhost    Database: sodoku_brain
-- ------------------------------------------------------
-- Server version	8.0.38

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `attempted`
--

DROP TABLE IF EXISTS `attempted`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `attempted` (
  `user_id` BIGINT NOT NULL,
  `puzzle_id` int unsigned NOT NULL,
  `current_state` varchar(567) NOT NULL,
  `seconds_worked_on` int unsigned NOT NULL,
  `hints_used` int unsigned NOT NULL,
  `started_on` date NOT NULL,
  PRIMARY KEY (`user_id`,`puzzle_id`),
  KEY `attempted-puzzle_idx` (`puzzle_id`),
  CONSTRAINT `attempted-puzzle` FOREIGN KEY (`puzzle_id`) REFERENCES `puzzle` (`puzzle_id`),
  CONSTRAINT `attempted-user` FOREIGN KEY (`user_id`) REFERENCES `users` (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;



--
-- Table structure for table `puzzle`
--

DROP TABLE IF EXISTS `puzzle`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `puzzle` (
  `puzzle_id` int unsigned NOT NULL AUTO_INCREMENT,
  `puzzle_vals` char(81) NOT NULL,
  `solution_vals` char(81) NOT NULL,
  `num_clues` tinyint unsigned NOT NULL,
  PRIMARY KEY (`puzzle_id`),
  UNIQUE KEY `puzzle_id_UNIQUE` (`puzzle_id`),
  UNIQUE KEY `puzzle_vals_UNIQUE` (`puzzle_vals`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;


--
-- Table structure for table `puzzle_template`
--

DROP TABLE IF EXISTS `puzzle_template`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `puzzle_template` (
  `template_id` int unsigned NOT NULL AUTO_INCREMENT,
  `vals` char(81) NOT NULL,
  `generation_date` date NOT NULL,
  PRIMARY KEY (`template_id`),
  UNIQUE KEY `vals_UNIQUE` (`vals`),
  UNIQUE KEY `template_id_UNIQUE` (`template_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `puzzle_template`
--

LOCK TABLES `puzzle_template` WRITE;
/*!40000 ALTER TABLE `puzzle_template` DISABLE KEYS */;
/*!40000 ALTER TABLE `puzzle_template` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `solved`
--

DROP TABLE IF EXISTS `solved`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `solved` (
  `user_id` BIGINT NOT NULL,
  `puzzle_id` int unsigned NOT NULL,
  `current_state` char(81) NOT NULL,
  `seconds_worked_on` int unsigned NOT NULL,
  `hints_used` int unsigned NOT NULL,
  `started_on` date NOT NULL,
  `solved_on` date NOT NULL,
  `rating` tinyint unsigned DEFAULT NULL,
  PRIMARY KEY (`user_id`,`puzzle_id`),
  KEY `puzzle_idx` (`puzzle_id`),
  KEY `user_idx` (`user_id`),
  CONSTRAINT `puzzle` FOREIGN KEY (`puzzle_id`) REFERENCES `puzzle` (`puzzle_id`),
  CONSTRAINT `user` FOREIGN KEY (`user_id`) REFERENCES `users` (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;


--
-- Table structure for table `users`
--


DROP TABLE IF EXISTS `users`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `users` (
  `user_id` BIGINT NOT NULL AUTO_INCREMENT,
  `auth_provider` VARCHAR(50),
  `auth_id` VARCHAR(100),
  `email` VARCHAR(100) UNIQUE,
  `username` VARCHAR(50) UNIQUE NOT NULL,
  `profile_picture` VARCHAR(255),
  `created_on` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;
/*!40101 SET character_set_client = @saved_cs_client */;


--
-- Dumping routines for database 'sodoku_brain'
--
/*!50003 DROP PROCEDURE IF EXISTS `GetAttemptedPuzzle` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb4 */ ;
/*!50003 SET character_set_results = utf8mb4 */ ;
/*!50003 SET collation_connection  = utf8mb4_0900_ai_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` PROCEDURE `GetAttemptedPuzzle`(IN in_username VARCHAR(20), in_puzzle_id INT)
BEGIN
	SELECT p.puzzle_id,
		p.puzzle_vals,
        p.solution_vals,
        a.current_state,
        a.username,
        a.seconds_worked_on,
        a.hints_used,
        a.started_on
	FROM attempted a
		JOIN puzzle p
			ON a.puzzle_id = p.puzzle_id
	WHERE a.username = in_username
		AND a.puzzle_id = in_puzzle_id
	;
END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `GetAttemptedPuzzles` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb4 */ ;
/*!50003 SET character_set_results = utf8mb4 */ ;
/*!50003 SET collation_connection  = utf8mb4_0900_ai_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` PROCEDURE `GetAttemptedPuzzles`(IN username VARCHAR(20))
BEGIN
	SELECT p.puzzle_id,
		p.puzzle_vals,
        p.solution_vals,
        a.seconds_worked_on,
        a.hints_used,
        a.started_on
	FROM users u
		JOIN attempted a
			ON u.user_id = a.user_id
		JOIN puzzle p
			ON a.puzzle_id = p.puzzle_id
	WHERE u.username = username
    ;
END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `GetLeaderboard` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb4 */ ;
/*!50003 SET character_set_results = utf8mb4 */ ;
/*!50003 SET collation_connection  = utf8mb4_0900_ai_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` PROCEDURE `GetLeaderboard`(IN page_offset INT, IN page_size INT)
BEGIN
	SELECT u.username,
		COALESCE(COUNT(s.puzzle_id), 0)			AS puzzles_solved,
        COALESCE(AVG(s.seconds_worked_on), 0) 	AS avg_solve_time,
        COALESCE(AVG(s.hints_used), 0)			AS avg_hints_used
	FROM users u
		LEFT JOIN solved s
			ON u.user_id = s.user_id
	GROUP BY u.username
    ORDER BY puzzles_solved DESC
    LIMIT page_size
    OFFSET page_offset
    ;
END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `GetNumSolved` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb4 */ ;
/*!50003 SET character_set_results = utf8mb4 */ ;
/*!50003 SET collation_connection  = utf8mb4_0900_ai_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` PROCEDURE `GetNumSolved`(
	IN puzzle_id INT,
    OUT numSolved INT
)
BEGIN
	SELECT COUNT(*)
		INTO numSolved
	FROM puzzle p
		JOIN solved s
			ON p.puzzle_id = s.puzzle_id
	WHERE p.puzzle_id = puzzle_id
	GROUP BY p.puzzle_id;
END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `GetNumUsers` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb4 */ ;
/*!50003 SET character_set_results = utf8mb4 */ ;
/*!50003 SET collation_connection  = utf8mb4_0900_ai_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` PROCEDURE `GetNumUsers`()
BEGIN
	SELECT COUNT(*) AS num_users
    FROM users
    ;
END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `GetPuzzle` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb4 */ ;
/*!50003 SET character_set_results = utf8mb4 */ ;
/*!50003 SET collation_connection  = utf8mb4_0900_ai_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` PROCEDURE `GetPuzzle`(IN puzzle_id INT)
BEGIN
	WITH attempted_by AS (
		SELECT COUNT(*) AS num_attempted
        FROM puzzle p
			JOIN attempted a
				ON p.puzzle_id = puzzle_id
		WHERE p.puzzle_id = puzzle_id
        GROUP BY p.puzzle_id
    )
    
    SELECT p.puzzle_id,
		p.puzzle_vals,
        p.solution_vals,
        a.num_attempted AS attempted_by,
        COUNT(*) AS solved_by,
        AVG(s.rating) AS avg_rating
	FROM puzzle p
		JOIN attempted_by a
			ON p.puzzle_id = a.puzzle_id
		JOIN solved s
			ON p.puzzle_id = s.puzzle_id
	WHERE p.puzzle_id = puzzle_id
    GROUP BY p.puzzle_id, 
		p.puzzle_vals,
		p.solution_vals
    ;
END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `GetPuzzleLeaders` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb4 */ ;
/*!50003 SET character_set_results = utf8mb4 */ ;
/*!50003 SET collation_connection  = utf8mb4_0900_ai_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` PROCEDURE `GetPuzzleLeaders`(IN in_puzzle_id INT)
BEGIN
	SELECT username,
		seconds_to_solve,
        solved_on
	FROM solved
    WHERE hints_used = 0
    ORDER BY seconds_to_solve DESC
    LIMIT 3
    ;
END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `GetPuzzleMetrics` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb4 */ ;
/*!50003 SET character_set_results = utf8mb4 */ ;
/*!50003 SET collation_connection  = utf8mb4_0900_ai_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` PROCEDURE `GetPuzzleMetrics`(IN in_puzzle_id INT)
BEGIN
	WITH attempted_by AS (
		SELECT p.puzzle_id,
		CAST(COUNT(*) AS UNSIGNED)					AS num_attempted,
		CAST(SUM(a.seconds_worked_on) AS UNSIGNED) 	AS total_seconds,
        CAST(SUM(a.hints_used) AS UNSIGNED) 		AS total_hints_used
        FROM puzzle p
			JOIN attempted a
				ON p.puzzle_id = a.puzzle_id
		WHERE p.puzzle_id = in_puzzle_id
        GROUP BY p.puzzle_id
    ),
	solved_by AS (
		SELECT puzzle_id,
			CAST(COUNT(*) AS UNSIGNED) 				AS num_solved,
			AVG(rating) 							AS avg_rating,
            CAST(COUNT(rating) AS UNSIGNED) 		AS num_rated,
			CAST(SUM(seconds_worked_on) AS UNSIGNED) AS time_worked_on,
            AVG(seconds_worked_on)					AS avg_solve_time,
            AVG(hints_used)							AS avg_hints_used,
            CAST(SUM(hints_used) AS UNSIGNED)		AS total_hints_used
		FROM solved
        WHERE puzzle_id = in_puzzle_id
        GROUP BY puzzle_id
    ),
    record AS (
		SELECT s.puzzle_id,
			u.username,
			s.seconds_worked_on,
			s.solved_on
		FROM solved s
			JOIN users u
				ON s.user_id = u.user_id
        WHERE s.hints_used = 0 AND s.puzzle_id = in_puzzle_id
        ORDER BY s.seconds_worked_on
        LIMIT 1
    )
    
    SELECT COALESCE(s.num_solved, 0)										AS num_solved,
		(COALESCE(a.num_attempted, 0) + COALESCE(s.num_solved, 0))			AS num_attempted,
        COALESCE(s.avg_rating, -1)											AS avg_rating,
        COALESCE(s.num_rated, 0)											AS num_rated,
        COALESCE(s.avg_solve_time, -1)										AS avg_solve_time,
		(COALESCE(s.time_worked_on, 0) + COALESCE(a.total_seconds, 0))		AS total_seconds,
		COALESCE(s.avg_hints_used, -1)										AS avg_hints_used,
		(COALESCE(s.total_hints_used, 0) + COALESCE(a.total_hints_used, 0))	AS total_hints_used,
        r.seconds_worked_on											AS record,
		r.username		 													AS record_holder,
        r.solved_on															AS record_date
        
	FROM puzzle p
		LEFT JOIN attempted_by a
			ON p.puzzle_id = a.puzzle_id
		LEFT JOIN record r
			ON p.puzzle_id = r.puzzle_id
		LEFT JOIN solved_by s
			ON p.puzzle_id = s.puzzle_id
    WHERE in_puzzle_id = p.puzzle_id
    ;
END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `GetSolvedPuzzles` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb4 */ ;
/*!50003 SET character_set_results = utf8mb4 */ ;
/*!50003 SET collation_connection  = utf8mb4_0900_ai_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` PROCEDURE `GetSolvedPuzzles`(IN username VARCHAR(20))
BEGIN
	SELECT p.puzzle_id,
		p.puzzle_vals,
        p.solution_vals,
        s.seconds_to_solve,
        s.hints_used,
		s.rating,
        s.started_on,
        s.solved_on
	FROM users u
		JOIN solved s
			ON u.username = s.username
		JOIN puzzle p
			ON s.puzzle_id = p.puzzle_id
	WHERE u.username = username
    ;
END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2025-01-27  2:18:17
