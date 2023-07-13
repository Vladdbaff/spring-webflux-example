CREATE TABLE users (
        id SERIAL NOT NULL,
        username VARCHAR NOT NULL,
        password VARCHAR NOT NULL,
        enabled VARCHAR NOT NULL,
        role    VARCHAR NOT NULL
);

CREATE UNIQUE INDEX users_id_uindex ON users (id);

ALTER TABLE users ADD CONSTRAINT users_pk PRIMARY KEY (id);