-- liquibase formatted sql

-- changeset frozik6k:1
CREATE TABLE notification_task (
    id SERIAL,
    chat_id BIGINT,
    text_notification VARCHAR,
    time_date_notification TIMESTAMP WITHOUT TIME ZONE
)