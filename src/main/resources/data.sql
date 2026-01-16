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
