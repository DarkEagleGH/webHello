#!/usr/bin/env bash

APP_DB_USER=webapp
APP_DB_PASS=dbpass
APP_DB_NAME=$APP_DB_USER

print_db_usage () {
  echo "Your PostgreSQL database has been setup and can be accessed on your local machine on the forwarded port (default: 15432)"
  echo "  Host: localhost"
  echo "  Port: 15432"
  echo "  Database: $APP_DB_NAME"
  echo "  Username: $APP_DB_USER"
  echo "  Password: $APP_DB_PASS"
  echo ""
  echo "Admin access to postgres user via VM:"
  echo "  vagrant ssh"
  echo "  sudo su - postgres"
  echo ""
  echo "psql access to app database user via VM:"
  echo "  vagrant ssh"
  echo "  sudo su - postgres"
  echo "  PGUSER=$APP_DB_USER PGPASSWORD=$APP_DB_PASS psql -h localhost $APP_DB_NAME"
  echo ""
  echo "Env variable for application development:"
  echo "  DATABASE_URL=postgresql://$APP_DB_USER:$APP_DB_PASS@localhost:15432/$APP_DB_NAME"
  echo ""
  echo "Local command to access the database via psql:"
  echo "  PGUSER=$APP_DB_USER PGPASSWORD=$APP_DB_PASS psql -h localhost -p 15432 $APP_DB_NAME"
}
#-------------------------------------------------------------

unzip /vagrant/vagrant-setup/cities.zip -d /tmp

cat << EOF | sudo -u postgres psql
-- Create the database user:
CREATE USER $APP_DB_USER WITH PASSWORD '$APP_DB_PASS';

-- Create the database:
CREATE DATABASE $APP_DB_NAME WITH OWNER=$APP_DB_USER
                                  LC_COLLATE='en_US.utf8'
                                  LC_CTYPE='en_US.utf8'
                                  ENCODING='UTF8'
                                  TEMPLATE=template0;

\connect webapp;

-- Create table
-- DROP TABLE public.contacts;

CREATE TABLE public.contacts
(
    id BIGSERIAL NOT NULL,
    name character varying COLLATE pg_catalog."default" NOT NULL,
    CONSTRAINT contacts_pkey PRIMARY KEY (id)
)
WITH (
    OIDS = FALSE
)
TABLESPACE pg_default;

ALTER TABLE public.contacts
    OWNER to webapp;

-- COPY "contacts" (name) FROM '/vagrant/vagrant-setup/cities.txt';
COPY "contacts" (name) FROM '/tmp/cities.txt';

CREATE INDEX id_idx
  ON contacts
  USING btree
  (id);
EOF

echo ""
print_db_usage
echo ""
echo "Successfully created PostgreSQL dev virtual machine."

