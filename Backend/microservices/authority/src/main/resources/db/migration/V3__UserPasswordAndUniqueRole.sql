ALTER TABLE cmiyc_authority_db.users ADD COLUMN password VARCHAR(72) NOT NULL;
ALTER TABLE cmiyc_authority_db.roles ADD CONSTRAINT uq_role UNIQUE(role);