#!/bin/bash
# Creates one database + one owner user per service.
# Runs ONLY ONCE: when the Postgres data volume is empty (first start).
set -euo pipefail

psql -v ON_ERROR_STOP=1 --username "$POSTGRES_USER" --dbname "$POSTGRES_DB" <<EOSQL
CREATE USER product_user WITH PASSWORD '${PRODUCT_DB_PASSWORD}';
CREATE DATABASE productdb OWNER product_user;
REVOKE ALL ON DATABASE productdb FROM PUBLIC;

CREATE USER order_user WITH PASSWORD '${ORDER_DB_PASSWORD}';
CREATE DATABASE orderdb OWNER order_user;
REVOKE ALL ON DATABASE orderdb FROM PUBLIC;

CREATE USER auth_user WITH PASSWORD '${AUTH_DB_PASSWORD}';
CREATE DATABASE authdb OWNER auth_user;
REVOKE ALL ON DATABASE authdb FROM PUBLIC;
EOSQL