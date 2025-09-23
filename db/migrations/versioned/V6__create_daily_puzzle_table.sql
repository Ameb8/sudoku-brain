CREATE TABLE `daily_puzzle` (
  `day` date NOT NULL,
  `puzzle_id` int unsigned NOT NULL,
  PRIMARY KEY (`day`),
  UNIQUE KEY `iddaily_puzzle_UNIQUE` (`day`),
  KEY `puzzle_idx` (`puzzle_id`),
  CONSTRAINT `daily_puzzle-puzzle` FOREIGN KEY (`puzzle_id`) REFERENCES `puzzle` (`puzzle_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;