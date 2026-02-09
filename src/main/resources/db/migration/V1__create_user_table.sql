CREATE TABLE tb_users (
    id        BIGSERIAL PRIMARY KEY,
    user_type VARCHAR(20)  NOT NULL,
    name      VARCHAR(255) NOT NULL,
    email     VARCHAR(255) NOT NULL UNIQUE,
    password  VARCHAR(255) NOT NULL,
    cpf       VARCHAR(14),
    cnpj      VARCHAR(18),

    CONSTRAINT chk_user_type
        CHECK (user_type IN ('COMMON', 'MERCHANT')),

    CONSTRAINT chk_user_document
        CHECK (
            (user_type = 'COMMON' AND cpf IS NOT NULL AND cnpj IS NULL)
                OR
            (user_type = 'MERCHANT' AND cnpj IS NOT NULL AND cpf IS NULL)
            )
);

CREATE UNIQUE INDEX uq_users_cpf
    ON tb_users (cpf) WHERE cpf IS NOT NULL;

CREATE UNIQUE INDEX uq_users_cnpj
    ON tb_users (cnpj) WHERE cnpj IS NOT NULL;