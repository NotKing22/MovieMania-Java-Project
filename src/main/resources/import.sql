-- ADMIN DEFAULT ADMIN_MODEL
INSERT INTO ADMIN_MODEL(cargo, email, nome_completo, senha) VALUES ('Trainee', 'Trainee@email.com', 'Trainee', '1234');
INSERT INTO ADMIN_MODEL(cargo, email, nome_completo, senha) VALUES ('AJUDANTE', 'helper@email.com', 'Helper', '1234');
INSERT INTO ADMIN_MODEL(cargo, email, nome_completo, senha) VALUES ('MODERADOR', 'mod@email.com', 'Mod', '1234');
INSERT INTO ADMIN_MODEL(cargo, email, nome_completo, senha) VALUES ('ADMIN', 'admin@email.com', 'Administrador', '1234');
INSERT INTO ADMIN_MODEL(cargo, email, nome_completo, senha) VALUES ('GERENTE', 'gerente@email.com', 'Gerente', '1234');

-- USERS DEFAULT USER_MODEL
INSERT INTO USER_MODEL(id, email, nome_completo, senha) VALUES (9999, 'teste@email.com', 'tester', '1234');
INSERT INTO USER_MODEL(email, nome_completo, senha) VALUES ('user@email.com', 'Rodolfo', '1234');
INSERT INTO USER_MODEL(email, nome_completo, senha) VALUES ('user2@email.com', 'Mariana', 'abcd');
INSERT INTO USER_MODEL(email, nome_completo, senha) VALUES ('user3@email.com', 'Carlos', 'pass123');
INSERT INTO USER_MODEL(email, nome_completo, senha) VALUES ('user4@email.com', 'Fernanda', 'senha123');

-- REVIEWS DO RODOLFO (USER_ID = 1)
INSERT INTO USER_REVIEW (USER_ID, NOTA, FILME_ID) VALUES (1, 5, 3);       -- Sombras no Paraíso
INSERT INTO USER_REVIEW (USER_ID, NOTA, FILME_ID) VALUES (1, 2, 5);       -- Grande Hotel
INSERT INTO USER_REVIEW (USER_ID, NOTA, FILME_ID) VALUES (1, 1, 1311032);  -- I Thought You Knew

-- REVIEWS DA MARIANA (USER_ID = 2)
INSERT INTO USER_REVIEW (USER_ID, NOTA, FILME_ID) VALUES (2, 2, 3);       -- Sombras no Paraíso
INSERT INTO USER_REVIEW (USER_ID, NOTA, FILME_ID) VALUES (2, 4, 5);       -- Grande Hotel
INSERT INTO USER_REVIEW (USER_ID, NOTA, FILME_ID) VALUES (2, 4, 1311032);  -- I Thought You Knew

-- REVIEWS DO CARLOS (USER_ID = 3)
INSERT INTO USER_REVIEW (USER_ID, NOTA, FILME_ID) VALUES (3, 1, 3);       -- Sombras no Paraíso
INSERT INTO USER_REVIEW (USER_ID, NOTA, FILME_ID) VALUES (3, 1, 5);       -- Grande Hotel
INSERT INTO USER_REVIEW (USER_ID, NOTA, FILME_ID) VALUES (3, 2, 1311032);  -- I Thought You Knew

-- REVIEWS DA FERNANDA (USER_ID = 4)
INSERT INTO USER_REVIEW (USER_ID, NOTA, FILME_ID) VALUES (4, 3, 3);       -- Sombras no Paraíso
INSERT INTO USER_REVIEW (USER_ID, NOTA, FILME_ID) VALUES (4, 4, 5);       -- Grande Hotel
INSERT INTO USER_REVIEW (USER_ID, NOTA, FILME_ID) VALUES (4, 1, 1311032);  -- I Thought You Knew