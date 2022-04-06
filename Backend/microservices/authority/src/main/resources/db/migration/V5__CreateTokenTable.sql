CREATE TABLE cmiyc_authority_db.Token (
  token BINARY NOT NULL,
  user_id INT NOT NULL,
  exp_date DATE NOT NULL,
  email VARCHAR(50) NOT NULL,
  CONSTRAINT token_pk PRIMARY KEY (token),
  CONSTRAINT token_user_id_fk
  FOREIGN KEY (user_id) REFERENCES users (id)
  )