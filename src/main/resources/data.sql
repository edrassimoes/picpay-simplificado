-- Email: edras@email.com       | Senha: 123456
-- Email: maria@email.com       | Senha: 456789
-- Email: mercado@email.com     | Senha: abc123
-- Email: semtransacao@email.com | Senha: teste123

INSERT INTO tb_users (user_type, name, email, password, cpf)
VALUES ('COMMON', 'Edras Simões', 'edras@email.com',
'$2a$10$0VDT5n.8WOv9mkWKygMVL.XJfcjHcbCJ02HbXdPnUjMIXI2HAqMB.', '52998224725');

INSERT INTO tb_users (user_type, name, email, password, cpf)
VALUES ('COMMON', 'Maria Silva', 'maria@email.com',
'$2a$10$k2nCQURrPVNzo4uhwbm/6uCosq.Ch68Es18lqkS.KoJy1AucrgnjO', '11144477735');

INSERT INTO tb_users (user_type, name, email, password, cnpj)
VALUES ('MERCHANT', 'Mercado Central', 'mercado@email.com',
'$2a$10$4y4CniZ3Q8vKN3Hk/08wl.LUupwaVMpR1CLxe60ffL1a4Gn9LmXmG', '04252011000110');

-- Usuário sem transações para testes de exclusão
INSERT INTO tb_users (user_type, name, email, password, cpf)
VALUES ('COMMON', 'Usuário Sem Transação', 'semtransacao@email.com',
'$2a$10$r1zC/VOOpKU5UOuLvs2gXuab.AOyCTQlP4nT72CjXmUPkcWFn8e5O', '40688134000161');

-- Carteiras
INSERT INTO tb_wallets (balance, user_id) VALUES (1000.00, 1);
INSERT INTO tb_wallets (balance, user_id) VALUES (5000.50, 2);
INSERT INTO tb_wallets (balance, user_id) VALUES (2500.00, 3);
INSERT INTO tb_wallets (balance, user_id) VALUES (3000.00, 4);

-- Transações
INSERT INTO tb_transactions (amount, payer_id, payee_id, timestamp, status)
VALUES (100.00, 1, 2, '2024-12-01T10:30:00', 'COMPLETED');

INSERT INTO tb_transactions (amount, payer_id, payee_id, timestamp, status)
VALUES (50.00, 2, 1, '2024-12-01T14:15:00', 'COMPLETED');

INSERT INTO tb_transactions (amount, payer_id, payee_id, timestamp, status)
VALUES (75.50, 1, 3, '2024-12-02T09:00:00', 'COMPLETED');

INSERT INTO tb_transactions (amount, payer_id, payee_id, timestamp, status)
VALUES (150.00, 2, 3, '2024-12-03T11:20:00', 'FAILED');

INSERT INTO tb_transactions (amount, payer_id, payee_id, timestamp, status)
VALUES (25.00, 2, 3, '2024-12-04T08:00:00', 'PENDING');
