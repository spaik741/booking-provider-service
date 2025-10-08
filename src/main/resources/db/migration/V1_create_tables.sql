CREATE TABLE schedule (
                          id BIGINT PRIMARY KEY AUTO_INCREMENT,
                          company_id BIGINT,
                          user_id VARCHAR(255),
                          time TIMESTAMP,
                          is_free BOOLEAN,
                          FOREIGN KEY (company_id) REFERENCES company(id)
);

CREATE TABLE company (
                         id BIGINT PRIMARY KEY AUTO_INCREMENT,
                         name VARCHAR(100),
                         email VARCHAR(100),
                         website VARCHAR(100),
                         phone VARCHAR(13),
                         address_id BIGINT,
                         FOREIGN KEY (address_id) REFERENCES address(id)
);

CREATE TABLE address (
                         id BIGINT PRIMARY KEY AUTO_INCREMENT,
                         street VARCHAR(50),
                         city VARCHAR(50),
                         home_number INT,
                         country VARCHAR(50),
                         company_id BIGINT,
                         FOREIGN KEY (company_id) REFERENCES company(id)
);