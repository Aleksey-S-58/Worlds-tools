-- create table that will store objects geometry
create table if not exists ThreeDMap.geometry (
	name varchar(50) Primary Key, 
	bytes bytea
);
-- add description
comment on table ThreeDMap.geometry is 'This table is intendet to store 3DO geometry.';
-- create table that will store objects geometry
create table if not exists ThreeDMap.material (
	name varchar(50) Primary Key, 
	bytes bytea
);
-- add description
comment on table ThreeDMap.material is 'This table is intendet to store 3DO textures.';