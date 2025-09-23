CREATE TABLE `puzzle_template` (
  `template_id` int unsigned NOT NULL AUTO_INCREMENT,
  `vals` char(81) NOT NULL,
  `generation_date` date NOT NULL,
  PRIMARY KEY (`template_id`),
  UNIQUE KEY `vals_UNIQUE` (`vals`),
  UNIQUE KEY `template_id_UNIQUE` (`template_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;