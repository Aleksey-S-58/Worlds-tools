-- create table Description
create table if not exists ThreeDMap.object_description (
	id bigint Primary Key,
	name varchar(50) not null
);
create index name_index on ThreeDMap.object_description (name);
comment on table ThreeDMap.object_description is 'This table describes 3D objects.';
-- Change table object_location
-- add id field
alter table ThreeDMap.object_location add column id bigint;
-- add link to an object description
alter table ThreeDMap.object_location add column object_id bigint;
alter table ThreeDMap.object_location alter column object_id set not null;
-- change primary key
alter table ThreeDMap.object_location drop constraint object_location_pkey;
alter table ThreeDMap.object_location add primary key (id);
create index object_id_index on ThreeDMap.object_location (object_id);
-- drop column name
alter table ThreeDMap.object_location drop column name;
--alter table ThreeDMap.object_location alter column name set not null;
--create index name_index on ThreeDMap.object_location (name);
-- create sequence
create sequence ThreeDMap.location_sequence start 1;
create sequence ThreeDMap.description_sequence start 1;