CREATE TABLE recursos (
    id BIGSERIAL PRIMARY KEY,
    nome VARCHAR(150) NOT NULL UNIQUE,
    localizacao VARCHAR(300) NOT NULL,
    ativo BOOLEAN NOT NULL DEFAULT TRUE,
    tipo_recurso_id BIGINT NOT NULL,
    CONSTRAINT fk_tipo_recurso FOREIGN KEY (tipo_recurso_id) REFERENCES tipos_recursos(id) ON DELETE CASCADE
);