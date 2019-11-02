-- create schema
create schema if not exists ThreeDMap;
-- create table that will store objects locations on a map
create table if not exists ThreeDMap.object_location (
	name varchar(50) Primary Key, 
	latitude double precision not null,
	longitude double precision not null,
	hight double precision not null,
	alphaX double precision not null,
	alphaY double precision not null,
	alphaZ double precision not null
);
-- add description
comment on table ThreeDMap.object_location is 'This table describes objects locations on a map.';
-- create indexes
create index latitude_index on ThreeDMap.object_location (latitude);
create index longitude_index on ThreeDMap.object_location (longitude);
create index hight_index on ThreeDMap.object_location (hight);