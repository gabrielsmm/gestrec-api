INSERT INTO tipos_recursos (nome, descricao) VALUES ('Baias de Trabalho', 'Baias individuais para trabalho');
INSERT INTO tipos_recursos (nome, descricao) VALUES ('Sala de Reunião', 'Salas para reuniões pequenas');
INSERT INTO tipos_recursos (nome, descricao) VALUES ('Auditório', 'Grande espaço para apresentações');

INSERT INTO recursos (nome, localizacao, ativo, tipo_recurso_id) VALUES
  ('Baia 1', 'Andar 3 - Ala A', TRUE,  (SELECT id FROM tipos_recursos WHERE nome = 'Baias de Trabalho'));
INSERT INTO recursos (nome, localizacao, ativo, tipo_recurso_id) VALUES
  ('Baia 2', 'Andar 3 - Ala A', TRUE,  (SELECT id FROM tipos_recursos WHERE nome = 'Baias de Trabalho'));
INSERT INTO recursos (nome, localizacao, ativo, tipo_recurso_id) VALUES
  ('Sala Reunião 101', 'Andar 2 - Sala 101', TRUE, (SELECT id FROM tipos_recursos WHERE nome = 'Sala de Reunião'));
INSERT INTO recursos (nome, localizacao, ativo, tipo_recurso_id) VALUES
  ('Auditório Principal', 'Térreo', FALSE, (SELECT id FROM tipos_recursos WHERE nome = 'Auditório'));

INSERT INTO usuarios (nome, email, senha, perfil) VALUES ('Administrador', 'admin@example.com', '$2a$10$ksAFEcHy/jARVp2iIGD4SuJi.u2vvARhYU1Mdl3l/kY9yAvhOnbI.', 'ADMIN');
INSERT INTO usuarios (nome, email, senha, perfil) VALUES ('Alice Silva', 'alice@example.com', '$2a$10$ksAFEcHy/jARVp2iIGD4SuJi.u2vvARhYU1Mdl3l/kY9yAvhOnbI.', 'USER');
INSERT INTO usuarios (nome, email, senha, perfil) VALUES ('Bob Santos', 'bob@example.com', '$2a$10$ksAFEcHy/jARVp2iIGD4SuJi.u2vvARhYU1Mdl3l/kY9yAvhOnbI.', 'USER');

INSERT INTO reservas (recurso_id, usuario_id, data_hora_inicio, data_hora_fim, status) VALUES
  ((SELECT id FROM recursos WHERE nome = 'Baia 1'), (SELECT id FROM usuarios WHERE email = 'alice@example.com'), '2026-01-16 09:00:00', '2026-01-16 10:00:00', 1);

INSERT INTO reservas (recurso_id, usuario_id, data_hora_inicio, data_hora_fim, status) VALUES
  ((SELECT id FROM recursos WHERE nome = 'Baia 1'), (SELECT id FROM usuarios WHERE email = 'bob@example.com'), '2026-01-16 11:00:00', '2026-01-16 12:00:00', 1);

INSERT INTO reservas (recurso_id, usuario_id, data_hora_inicio, data_hora_fim, status) VALUES
  ((SELECT id FROM recursos WHERE nome = 'Baia 2'), (SELECT id FROM usuarios WHERE email = 'alice@example.com'), '2026-01-16 09:30:00', '2026-01-16 10:30:00', 1);

INSERT INTO reservas (recurso_id, usuario_id, data_hora_inicio, data_hora_fim, status) VALUES
  ((SELECT id FROM recursos WHERE nome = 'Sala Reunião 101'), (SELECT id FROM usuarios WHERE email = 'admin@example.com'), '2026-01-16 14:00:00', '2026-01-16 15:00:00', 2);
