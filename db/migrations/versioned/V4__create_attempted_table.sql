CREATE TABLE `attempted` (
  `user_id` bigint NOT NULL,
  `puzzle_id` int unsigned NOT NULL,
  `current_state` varchar(567) NOT NULL,
  `seconds_worked_on` int unsigned NOT NULL,
  `hints_used` int unsigned NOT NULL,
  `started_on` date NOT NULL,
  PRIMARY KEY (`user_id`,`puzzle_id`),
  KEY `attempted-puzzle_idx` (`puzzle_id`),
  CONSTRAINT `attempted__puzzle` FOREIGN KEY (`puzzle_id`) REFERENCES `puzzle` (`puzzle_id`),
  CONSTRAINT `attempted__user` FOREIGN KEY (`user_id`) REFERENCES `users` (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;