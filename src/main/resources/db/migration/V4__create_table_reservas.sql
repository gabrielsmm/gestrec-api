CREATE TABLE reservas (
    id BIGSERIAL PRIMARY KEY,
    recurso_id BIGINT NOT NULL,
    data_hora_inicio TIMESTAMP NOT NULL,
    data_hora_fim TIMESTAMP NOT NULL,
    status INT NOT NULL CHECK (status IN (1, 2)),
    usuario_id BIGINT NOT NULL,
    CONSTRAINT fk_recurso FOREIGN KEY (recurso_id) REFERENCES recursos(id) ON DELETE CASCADE,
    CONSTRAINT fk_usuario FOREIGN KEY (usuario_id) REFERENCES usuarios(id) ON DELETE CASCADE
);