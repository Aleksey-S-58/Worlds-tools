-- create schema
create schema if not exists ThreeDMap;
-- create table that will store objects locations on a map
create table if not exists ThreeDMap.object_location (
	id bigint Primary Key,
	object_id bigint not null,
	latitude double precision not null,
	longitude double precision not null,
	hight double precision not null,
	alphaX double precision not null,
	alphaY double precision not null,
	alphaZ double precision not null
);

-- create table Description
create table if not exists ThreeDMap.object_description (
	id bigint Primary Key,
	object_id bigint not null,
	radius double precision not null,
	name varchar(50) not null,
	object_type varchar(50) not null
);
-- create sequence
create sequence ThreeDMap.location_sequence start 1;
create sequence ThreeDMap.description_sequence start 1;
create sequence ThreeDMap.object_id_sequence start 1;

-- create table that will store objects geometry (bytea)
create table if not exists ThreeDMap.geometry (
	name varchar(50) Primary Key, 
	bytes blob
);
-- create table that will store objects material
create table if not exists ThreeDMap.material (
	name varchar(50) Primary Key, 
	bytes blob
);
-- create table that will store sprite objects
create table if not exists ThreeDMap.sprite (
	name varchar(50) Primary Key, 
	bytes blob
);
-- insert test values
insert into ThreeDMap.object_description values (1, 1, 5, 'test', 'THREE_D_OBJECT');
insert into ThreeDMap.object_location values (1, 1, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0);
insert into ThreeDMap.geometry values ('test', rawtohex('test'));
insert into ThreeDMap.material values ('test', rawtohex('test'));
insert into ThreeDMap.sprite values ('test-sprite-1', rawtohex('test'));
