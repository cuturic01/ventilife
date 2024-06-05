-- roles

INSERT INTO ROLE (name) VALUES ('ROLE_USER');
INSERT INTO ROLE (name) VALUES ('ROLE_ADMIN');

-- users

INSERT INTO USERS (name, surname, email, password) VALUES ('Marko', 'Janosevic', 'marko@mail.com', 'aaa');
INSERT INTO USERS (name, surname, email, password) VALUES ('Milos', 'Cuturic', 'milos@mail.com', 'bbb');

-- user-role

INSERT INTO USER_ROLE (user_id, role_id) VALUES (1, 1);
INSERT INTO USER_ROLE (user_id, role_id) VALUES (2, 2);