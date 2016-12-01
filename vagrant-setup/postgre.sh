#!/usr/bin/env bash

unzip /vagrant/vagrant-setup/cities.zip -d /tmp

cat << EOF | sudo -u postgres psql

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

EOF