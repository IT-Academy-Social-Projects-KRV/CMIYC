--Run script with root privileges
-----------------------------------------------------------------------------
--Create user for database

create user 'admin'@'localhost' identified by 'admin';

--Create database

create database db_workers;

--Grants on db_workers to user

grant all privileges on db_workers.* to 'admin'@'localhost' with grant option;
flush privileges;
-----------------------------------------------------------------------------