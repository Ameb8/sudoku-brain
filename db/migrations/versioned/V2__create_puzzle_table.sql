CREATE TABLE `puzzle` (
  `puzzle_id` int unsigned NOT NULL AUTO_INCREMENT,
  `puzzle_vals` char(81) NOT NULL,
  `solution_vals` char(81) NOT NULL,
  `num_clues` tinyint unsigned NOT NULL,
  `difficulty` enum('EASY','MEDIUM','HARD','EXPERT') NOT NULL DEFAULT 'MEDIUM',
  PRIMARY KEY (`puzzle_id`),
  UNIQUE KEY `puzzle_id_UNIQUE` (`puzzle_id`),
  UNIQUE KEY `puzzle_vals_UNIQUE` (`puzzle_vals`)
) ENGINE=InnoDB AUTO_INCREMENT=57 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;