CREATE TABLE tb_wallets (
    id      BIGSERIAL PRIMARY KEY,
    balance DOUBLE PRECISION NOT NULL DEFAULT 0,
    user_id BIGINT         NOT NULL UNIQUE,

    CONSTRAINT fk_wallet_user
        FOREIGN KEY (user_id)
            REFERENCES tb_users (id)
);