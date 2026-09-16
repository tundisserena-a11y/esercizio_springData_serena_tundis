-- INSERT INTO authors (firstname, lastname, email)
-- value ("Vincenzo","Bianchi","vincbianche@tin.it");

-- INSERT INTO authors (firstname, lastname, email)
-- value ("Antonio","Rossi","antonred@mail.it");


-- INSERT INTO posts (title, body, publish_date, author_id)
-- SELECT 'Lorem ipsum .....', 'Ciao', null, id
-- FROM authors
-- where firstname = 'Vincenzo'
-- and lastname = 'Bianchi';

-- INSERT INTO posts (title, body, publish_date, author_id)
-- SELECT 'Lorem ipsum .....', 'Non sono Ciao', null, id
-- FROM authors
-- where firstname = 'Vincenzo'
-- and lastname = 'Bianchi';

INSERT INTO comments(email, body, date, post_id)
value ("antonred@mail.it","Lorem ipsum....","20231315",1),
      ("antonred@mail.it","Lorem ipsum....","20231315",2);

