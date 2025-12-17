#!/usr/bin/env bash
set -e

docker compose exec -T \
  -e MYSQL_PWD="$MYSQL_PASSWORD" \
  db_dev \
  mysql -u "$MYSQL_USER" "$MYSQL_DATABASE" \
  < dev_data.sql
