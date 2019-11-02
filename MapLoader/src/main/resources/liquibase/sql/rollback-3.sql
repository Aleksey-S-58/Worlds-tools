-- Drop index.
drop index if exists object_description_object_id_index;
-- Drop columns.
alter table ThreeDMap.object_description drop column radius;
alter table ThreeDMap.object_description drop column object_id;