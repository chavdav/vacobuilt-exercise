-- Could be enum?
CREATE TABLE IF NOT EXISTS category(
    id   SERIAL PRIMARY KEY NOT NULL,
    name VARCHAR(255)       NOT NULL
);

INSERT INTO category (name)
VALUES
    ('General'),
    ('Technology'),
    ('Staff'),
    ('Random');

CREATE TABLE IF NOT EXISTS post(
    id   SERIAL PRIMARY KEY NOT NULL,
    title VARCHAR(255)       NOT NULL,
    contents VARCHAR(255)       NOT NULL,
    time_stamp TIMESTAMP       NOT NULL,
    category_id INT       NOT NULL,
    CONSTRAINT fk_category FOREIGN KEY(category_id) REFERENCES category(id)
);
