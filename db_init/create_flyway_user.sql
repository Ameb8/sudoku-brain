CREATE USER IF NOT EXISTS 'flyway'@'%' IDENTIFIED WITH mysql_native_password BY 'flyway123';
GRANT ALL PRIVILEGES ON sudoku_brain.* TO 'flyway'@'%';
FLUSH PRIVILEGES;
