CREATE TABLE company (
                         id BIGSERIAL PRIMARY KEY,
                         name VARCHAR(100),
                         email VARCHAR(100),
                         website VARCHAR(100),
                         phone VARCHAR(13),
                         address_id BIGINT
);

CREATE TABLE address (
                         id BIGSERIAL PRIMARY KEY,
                         street VARCHAR(50),
                         city VARCHAR(50),
                         home_number VARCHAR(10),
                         country VARCHAR(50),
                         company_id BIGINT
);

CREATE TABLE schedule (
                          id BIGSERIAL PRIMARY KEY,
                          company_id BIGINT,
                          user_id VARCHAR(255),
                          time TIMESTAMP,
                          is_free BOOLEAN
);

ALTER TABLE company
    ADD CONSTRAINT fk_company_address
        FOREIGN KEY (address_id) REFERENCES address(id);

ALTER TABLE address
    ADD CONSTRAINT fk_address_company
        FOREIGN KEY (company_id) REFERENCES company(id);

ALTER TABLE schedule
    ADD CONSTRAINT fk_schedule_company
        FOREIGN KEY (company_id) REFERENCES company(id);