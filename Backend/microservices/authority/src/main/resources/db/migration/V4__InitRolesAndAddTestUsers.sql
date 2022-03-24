-- Init roles
INSERT INTO cmiyc_authority_db.roles (role, role_desc) VALUES
    ('user', 'Simple user that can perform search'),
    ('admin_user', 'Admin that can register new users'),
    ('admin_schema', 'Admin that can upload new schemes');

-- Inserting three test users: user@gmail.com/pass (user), admin_u@gmail.com/pass (admin_user), admin_s@gmail.com/pass (admin_schema)
INSERT INTO cmiyc_authority_db.users (first_name, last_name, email, is_active, password) VALUES
    ('User', 'Foo', 'user@gmail.com', true, '$2a$08$j9ArZIiXVOwvolOkM2Q//uZl9Lx5K7IFlzEVQHizi.V/w22IlMbe6'),
    ('Admin', 'User', 'admin_u@gmail.com', true, '$2a$08$j9ArZIiXVOwvolOkM2Q//uZl9Lx5K7IFlzEVQHizi.V/w22IlMbe6'),
    ('Admin', 'Schema', 'admin_s@gmail.com', true, '$2a$08$j9ArZIiXVOwvolOkM2Q//uZl9Lx5K7IFlzEVQHizi.V/w22IlMbe6');

-- Adding roles to users
INSERT INTO cmiyc_authority_db.users_roles (id_user, id_role) VALUES
    ((SELECT id FROM cmiyc_authority_db.users WHERE email = 'user@gmail.com'), (SELECT id from cmiyc_authority_db.roles WHERE role = 'user')),
    ((SELECT id FROM cmiyc_authority_db.users WHERE email = 'admin_u@gmail.com'), (SELECT id from cmiyc_authority_db.roles WHERE role = 'admin_user')),
    ((SELECT id FROM cmiyc_authority_db.users WHERE email = 'admin_s@gmail.com'), (SELECT id from cmiyc_authority_db.roles WHERE role = 'admin_schema'));