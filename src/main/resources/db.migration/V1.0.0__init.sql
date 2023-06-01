CREATE SEQUENCE account_seq INCREMENT BY 50;

CREATE TABLE account (
                         id bigint primary key default nextval('account_seq'),
                         username varchar(255),
                         password varchar(255),
                         email varchar(255),
                         age varchar(255),
                         role varchar(255)
);

INSERT INTO account (username, password, email, age, role)
VALUES ('user', '1111', 'qwer@gmail.com', '123', 'ROLE_USER'),
       ('manager', '1111', 'qwer@gmail.com', '123', 'ROLE_MANAGER'),
       ('admin', '1111', 'qwer@gmail.com', '123', 'ROLE_ADMIN');

