ALTER TABLE cmiyc_authority_db.users
    ADD tfa_secret VARCHAR (50);

UPDATE cmiyc_authority_db.users
    SET tfa_secret = "OFO3LVPJRZDLUCYZEZDUZRN65T26ZZAF"
    WHERE ID = 1;

UPDATE cmiyc_authority_db.users
    SET tfa_secret = "OFO3LVPJRZDLUCYZEZDUZRN65T26ZZAF"
    WHERE ID = 2;

UPDATE cmiyc_authority_db.users
    SET tfa_secret = "OFO3LVPJRZDLUCYZEZDUZRN65T26ZZAF"
    WHERE ID = 3;
