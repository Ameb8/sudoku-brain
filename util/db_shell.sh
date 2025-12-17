#!/usr/bin/env bash
set -e

SERVICE=db_dev

docker compose exec \
  -e MYSQL_PWD="$MYSQL_PASSWORD" \
  "$SERVICE" \
  mysql -u "$MYSQL_USER" "$MYSQL_DATABASE"
