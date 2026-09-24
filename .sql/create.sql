create table authors (
    id BIGINT auto_increment PRIMARY KEY,
    firstname VARCHAR(100),
    lastname VARCHAR(100),
    email VARCHAR(100)
);
create table posts(
    id BIGINT auto_increment PRIMARY KEY,
    title VARCHAR(100) not NULL,
    body VARCHAR(100) not NULL,
    publish_date CHAR(10), -- seguendo il formato a 8 caratteri yyyyMMdd
    author_id BIGINT,
    FOREIGN KEY (author_id) REFERENCES authors(id)
);
create table comments(
    id BIGINT auto_increment PRIMARY KEY,
    email VARCHAR(100) not null,
    body VARCHAR(200) not null,
    date CHAR(10), -- se dovesse dar problemi modificare in "date"
    post_id BIGINT,
    FOREIGN KEY (post_id) REFERENCES posts(id)
);
