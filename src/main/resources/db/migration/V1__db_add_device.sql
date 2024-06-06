CREATE TABLE IF NOT EXISTS device (
    id                              BIGSERIAL PRIMARY KEY NOT NULL,
    external_id                     BIGINT NOT NULL,
    name                            VARCHAR(255) NOT NULL,
    information                     VARCHAR(255) NOT NULL,
    final_price                     NUMERIC(9,2) NOT NULL,
    reference_price                 NUMERIC(9,2) NOT NULL,
    reservation_expiration_date     TIMESTAMP,
    thumbnail                       VARCHAR(255),
    category                        VARCHAR(50) NOT NULL,
    stock                           VARCHAR(50),
    stock_number                    NUMERIC(1000),
    creation_date                   TIMESTAMP,
    last_update_date                TIMESTAMP
);