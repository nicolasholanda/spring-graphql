INSERT INTO author (id, name, biography) VALUES
  (1, 'George Orwell', 'George Orwell was an English novelist and essayist.'),
  (2, 'J.R.R. Tolkien', 'J.R.R. Tolkien was an English writer, poet, and philologist.');

INSERT INTO book (id, title, isbn, published_year, author_id) VALUES
  (1, '1984', '9780451524935', 1949, 1),
  (2, 'Animal Farm', '9780451526342', 1945, 1),
  (3, 'The Hobbit', '9780547928227', 1937, 2);
