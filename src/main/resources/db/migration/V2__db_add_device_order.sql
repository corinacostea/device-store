CREATE TABLE IF NOT EXISTS device_order (
    id                              BIGSERIAL PRIMARY KEY NOT NULL,
    status                          VARCHAR(255),
    customer_id                     BIGINT,
    delivery_address                VARCHAR(255),
    delivery_person                 VARCHAR(255),
    delivery_phone                  VARCHAR(15),
    delivery_details                VARCHAR(2000),
    delivery_price                  NUMERIC(7,2),
    delivery_date                   TIMESTAMP,
    merchant_transaction_id         VARCHAR(50),
    reservation_time                TIMESTAMP,
    device_id                       BIGSERIAL CONSTRAINT device_id_fk REFERENCES device,
    creation_date                   TIMESTAMP,
    last_update_date                TIMESTAMP
    );