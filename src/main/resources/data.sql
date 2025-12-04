-- Usuários
INSERT INTO tb_users (user_type, name, email, password, cpf) VALUES ('COMMON', 'Edras Simões', 'edras@email.com', '123456', '12345678900');
INSERT INTO tb_users (user_type, name, email, password, cpf) VALUES ('COMMON', 'Maria Silva', 'maria@email.com', '456789', '98765432100');
INSERT INTO tb_users (user_type, name, email, password, cpf) VALUES ('COMMON', 'João Santos', 'joao@email.com', 'senha123', '11122233344');
INSERT INTO tb_users (user_type, name, email, password, cnpj) VALUES ('MERCHANT', 'Loja do João', 'loja@email.com', '789012', '12345678000199');
INSERT INTO tb_users (user_type, name, email, password, cnpj) VALUES ('MERCHANT', 'Mercado Central', 'mercado@email.com', 'abc123', '98765432000188');

-- Carteiras
INSERT INTO tb_wallets (balance, user_id) VALUES (1000.00, 1);
INSERT INTO tb_wallets (balance, user_id) VALUES (500.50, 2);
INSERT INTO tb_wallets (balance, user_id) VALUES (250.00, 3);
INSERT INTO tb_wallets (balance, user_id) VALUES (5000.00, 4);
INSERT INTO tb_wallets (balance, user_id) VALUES (10000.00, 5);

-- Transações
INSERT INTO tb_transactions (amount, payer_id, payee_id, timestamp, status) 
VALUES (100.00, 1, 4, '2024-12-01T10:30:00', 'COMPLETED');

INSERT INTO tb_transactions (amount, payer_id, payee_id, timestamp, status) 
VALUES (50.00, 2, 5, '2024-12-01T14:15:00', 'COMPLETED');

INSERT INTO tb_transactions (amount, payer_id, payee_id, timestamp, status) 
VALUES (75.50, 1, 2, '2024-12-02T09:00:00', 'COMPLETED');

INSERT INTO tb_transactions (amount, payer_id, payee_id, timestamp, status) 
VALUES (200.00, 3, 4, '2024-12-02T16:45:00', 'COMPLETED');

INSERT INTO tb_transactions (amount, payer_id, payee_id, timestamp, status) 
VALUES (150.00, 2, 1, '2024-12-03T11:20:00', 'FAILED');

INSERT INTO tb_transactions (amount, payer_id, payee_id, timestamp, status) 
VALUES (25.00, 1, 5, '2024-12-04T08:00:00', 'PENDING');
