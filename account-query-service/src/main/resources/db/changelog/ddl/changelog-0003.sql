-- liquibase formatted sql

-- changeset kong-sisovandara:changelog-0003-1
ALTER TABLE accounts
    ADD COLUMN IF NOT EXISTS account_type_code VARCHAR(10);

