-- Tipos de recursos
INSERT INTO tipos_recursos (nome, descricao) VALUES
  ('Baias de Trabalho', 'Baias individuais para trabalho'),
  ('Sala de Reunião', 'Salas para reuniões pequenas'),
  ('Auditório', 'Grande espaço para apresentações');

-- Recursos
INSERT INTO recursos (nome, localizacao, ativo, tipo_recurso_id) VALUES
  ('Baia 1', 'Andar 3 - Ala A', TRUE, (SELECT id FROM tipos_recursos WHERE nome = 'Baias de Trabalho')),
  ('Baia 2', 'Andar 3 - Ala A', TRUE, (SELECT id FROM tipos_recursos WHERE nome = 'Baias de Trabalho')),
  ('Sala Reunião 101', 'Andar 2 - Sala 101', TRUE, (SELECT id FROM tipos_recursos WHERE nome = 'Sala de Reunião')),
  ('Auditório Principal', 'Térreo', FALSE, (SELECT id FROM tipos_recursos WHERE nome = 'Auditório'));

-- Usuários (senha: password123)
INSERT INTO usuarios (nome, email, senha, perfil) VALUES
  ('Administrador', 'admin@example.com', '$2a$10$ksAFEcHy/jARVp2iIGD4SuJi.u2vvARhYU1Mdl3l/kY9yAvhOnbI.', 'ADMIN'),
  ('Alice Silva', 'alice@example.com', '$2a$10$ksAFEcHy/jARVp2iIGD4SuJi.u2vvARhYU1Mdl3l/kY9yAvhOnbI.', 'USER'),
  ('Bob Santos', 'bob@example.com', '$2a$10$ksAFEcHy/jARVp2iIGD4SuJi.u2vvARhYU1Mdl3l/kY9yAvhOnbI.', 'USER');

-- Reservas de exemplo
INSERT INTO reservas (recurso_id, usuario_id, data_hora_inicio, data_hora_fim, status) VALUES
  ((SELECT id FROM recursos WHERE nome = 'Baia 1'), (SELECT id FROM usuarios WHERE email = 'alice@example.com'), '2026-01-30 09:00:00', '2026-01-30 10:00:00', 1),
  ((SELECT id FROM recursos WHERE nome = 'Baia 1'), (SELECT id FROM usuarios WHERE email = 'bob@example.com'), '2026-01-30 11:00:00', '2026-01-30 12:00:00', 1),
  ((SELECT id FROM recursos WHERE nome = 'Baia 2'), (SELECT id FROM usuarios WHERE email = 'alice@example.com'), '2026-01-30 09:30:00', '2026-01-30 10:30:00', 1),
  ((SELECT id FROM recursos WHERE nome = 'Sala Reunião 101'), (SELECT id FROM usuarios WHERE email = 'admin@example.com'), '2026-01-30 14:00:00', '2026-01-30 15:00:00', 2);
