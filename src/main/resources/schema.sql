CREATE TABLE users(
                      id BIGINT AUTO_INCREMENT PRIMARY KEY,
                      user_name VARCHAR(200) NOT NULL UNIQUE,
                      email VARCHAR(200) NOT NULL UNIQUE ,
                      first_name VARCHAR(50) NOT NULL,
                      last_name VARCHAR(50) NOT NULL,
                      password VARCHAR(500) NOT NULL
);


CREATE TABLE role(
                     id BIGINT AUTO_INCREMENT PRIMARY KEY,
                     role VARCHAR(200) NOT NULL UNIQUE
);

CREATE TABLE users_roles(
                           id      BIGINT AUTO_INCREMENT PRIMARY KEY,
                           user_id BIGINT,
                           role_id BIGINT,
                           foreign key (user_id) REFERENCES users (id),
                           foreign key (role_id) REFERENCES role (id)
);
