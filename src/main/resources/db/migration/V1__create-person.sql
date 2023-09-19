CREATE TABLE IF NOT EXISTS person(
    id   UUID primary key not null,
    name varchar(255)       not null,
    age  int                not null
)
