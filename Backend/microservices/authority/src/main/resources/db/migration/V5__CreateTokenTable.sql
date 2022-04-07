CREATE TABLE cmiyc_authority_db.token (
  token BINARY(16) NOT NULL,
  user_id INT NOT NULL,
  exp_date DATE NOT NULL,
  CONSTRAINT token_pk PRIMARY KEY (token),
  CONSTRAINT token_user_id_fk
  FOREIGN KEY (user_id) REFERENCES users (id)
  )