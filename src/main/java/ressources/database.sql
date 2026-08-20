CREATE DATABASE bank_transaction_db;

CREATE TYPE account_type AS ENUM ('STANDARD', 'PREMIUM', 'GOLD');
CREATE TYPE transaction_type AS ENUM ('IN', 'OUT');


CREATE TABLE account (
                         id VARCHAR(50) PRIMARY KEY,
                         account_type account_type NOT NULL
);


CREATE TABLE transaction (
                             id VARCHAR(50) PRIMARY KEY,
                             created_at TIMESTAMP NOT NULL DEFAULT now(),
                             transaction_type transaction_type NOT NULL,
                             amount NUMERIC(15, 2) NOT NULL CHECK (amount > 0),
                             reason VARCHAR(255),
                             account_id VARCHAR(50) NOT NULL,

                             CONSTRAINT fk_transaction_account
                                 FOREIGN KEY (account_id)
                                     REFERENCES account (id)
                                     ON DELETE CASCADE
);