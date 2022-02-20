-- -----------------------------------------------------
-- Schema db_workers
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS db_workers DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci ;
USE db_workers ;

-- -----------------------------------------------------
-- Table db_workers.roles
-- -----------------------------------------------------
DROP TABLE IF EXISTS db_workers.roles ;

CREATE TABLE IF NOT EXISTS db_workers.roles (
  id_role INT NOT NULL AUTO_INCREMENT,
  role VARCHAR(25) DEFAULT "User",
  role_desc VARCHAR(150) DEFAULT NULL,
  CONSTRAINT pk_roles PRIMARY KEY (id_role)
  )
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table db_workers.users
-- -----------------------------------------------------
DROP TABLE IF EXISTS db_workers.users;

CREATE TABLE IF NOT EXISTS db_workers.users (
  id_user INT NOT NULL AUTO_INCREMENT,
  first_name VARCHAR(50) NOT NULL,
  last_name VARCHAR(50) NOT NULL,
  email VARCHAR(50) NOT NULL,
  register_date DATE DEFAULT (CURRENT_DATE),
  is_active BOOLEAN DEFAULT false,
  CONSTRAINT pk_users PRIMARY KEY (id_user),
  CONSTRAINT uq_users UNIQUE (email)
  )
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table db_workers.user_roles
-- -----------------------------------------------------
DROP TABLE IF EXISTS db_workers.users_roles;

CREATE TABLE IF NOT EXISTS db_workers.users_roles (
  id_user INT NOT NULL,
  id_role INT NOT NULL,
  CONSTRAINT pk_users_roles PRIMARY KEY (id_user, id_role),
  CONSTRAINT fk_users_roles_id_role_roles
  FOREIGN KEY (id_role) REFERENCES roles (id_role),
  CONSTRAINT fk_users_roles_id_user_users
  FOREIGN KEY (id_user) REFERENCES users (id_user)
  )
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;

