--changeset Luiz:20240905-01-01
INSERT INTO usuarios (nome, login, email, password, role, status)
VALUES
       ('ADMIN', 'admin', 'admin@exemplo.com',
        '$2a$10$lSrI2h./WOgFdyM7vkO9..gq50bfPluo0HyAHA7ySVldjaYCERMpu', '0', true);