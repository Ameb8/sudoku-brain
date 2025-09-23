CREATE TABLE `daily_attempt` (
  `date` date NOT NULL,
  `user_id` bigint NOT NULL,
  `puzzle_state` char(81) NOT NULL,
  `solved_at` datetime DEFAULT NULL,
  `hints_used` int unsigned DEFAULT NULL,
  `daily_attemptcol` varchar(45) DEFAULT NULL,
  `seconds_worked` int unsigned DEFAULT NULL,
  PRIMARY KEY (`date`,`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;