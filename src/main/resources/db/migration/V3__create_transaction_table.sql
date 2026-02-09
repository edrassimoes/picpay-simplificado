CREATE TABLE tb_transactions (
    id         BIGSERIAL PRIMARY KEY,
    amount     DOUBLE PRECISION NOT NULL,
    payer_id   BIGINT         NOT NULL,
    payee_id   BIGINT         NOT NULL,
    created_at TIMESTAMP      NOT NULL,
    status     VARCHAR(30)    NOT NULL,

    CONSTRAINT fk_transaction_payer
        FOREIGN KEY (payer_id) REFERENCES tb_users (id),

    CONSTRAINT fk_transaction_payee
        FOREIGN KEY (payee_id) REFERENCES tb_users (id),

    CONSTRAINT chk_transaction_users
        CHECK (payer_id <> payee_id)
);

CREATE INDEX idx_transactions_payer
    ON tb_transactions (payer_id);

CREATE INDEX idx_transactions_payee
    ON tb_transactions (payee_id);