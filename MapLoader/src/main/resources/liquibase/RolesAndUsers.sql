-- create database
create database map;
-- create user
create user map_api with password 'api-for-3dmap';
-- grant privileges to user
grant all privileges on database map to map_api;
