CREATE TABLE IF NOT EXISTS weather_history
(
    id         UUID DEFAULT gen_random_uuid() NOT NULL,
    latitude   NUMERIC(9, 6)                  NOT NULL,
    longitude  NUMERIC(9, 6)                  NOT NULL,
    weather    VARCHAR(256)                   NOT NULL,
    query_date TIME WITHOUT TIME ZONE         NOT NULL,

    CONSTRAINT weather_history_pkey PRIMARY KEY (id)
);
