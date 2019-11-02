alter table ThreeDMap.object_location add column name varchar(50);
-- change primary key
alter table ThreeDMap.object_location drop constraint object_location_pkey;
alter table ThreeDMap.object_location add primary key (name);
-- drop column
alter table ThreeDMap.object_location drop column id;
alter table ThreeDMap.object_location drop column object_id;
-- drop table
drop table if exists ThreeDMap.object_description;
-- drop sequence
drop sequence if exists ThreeDMap.description_sequence;
drop sequence if exists ThreeDMap.location_sequence;