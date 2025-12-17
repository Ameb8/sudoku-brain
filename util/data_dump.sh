#!/usr/bin/env bash
set -e

docker compose exec -T db_dev \
  sh -c 'MYSQL_PWD="$MYSQL_PASSWORD" mysqldump \
    -u "$MYSQL_USER" \
    --no-create-info \
    --skip-triggers \
    "$MYSQL_DATABASE"' \
  > dev_data.sql
