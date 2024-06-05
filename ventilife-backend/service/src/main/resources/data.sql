-- roles

INSERT INTO ROLE (name) VALUES ('ROLE_USER');
INSERT INTO ROLE (name) VALUES ('ROLE_ADMIN');

-- users

INSERT INTO USERS (name, surname, email, password) VALUES ('Marko', 'Janosevic', 'marko@mail.com', '$2a$10$c1VQizq/ZqqOauIt0qP/9OSlXSFk7dPvBVGDW.oJCm760ZnLYL8uW');
INSERT INTO USERS (name, surname, email, password) VALUES ('Milos', 'Cuturic', 'milos@mail.com', '$2a$10$NZz/5OdUUs3Uf12cqm4cXe8azc3q83pjMRCtwiNXfWrIhJN0vujmq');

-- user-role

INSERT INTO USER_ROLE (user_id, role_id) VALUES (1, 1);
INSERT INTO USER_ROLE (user_id, role_id) VALUES (2, 2);