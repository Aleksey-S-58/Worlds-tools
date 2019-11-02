-- Add new columns to a table.
alter table ThreeDMap.object_description add column object_id bigint;
alter table ThreeDMap.object_description add column radius double precision;
-- Add constraint.
alter table ThreeDMap.object_description alter column object_id set not null;
alter table ThreeDMap.object_description alter column radius set not null;
-- Add index.
create index object_description_object_id_index on ThreeDMap.object_description (object_id);
-- Comment new field.
comment on column ThreeDMap.object_description.object_id is 'he same like object_id in ThreeDMap.object_location';
comment on column ThreeDMap.object_description.radius is 'The radius of outer line of sight.';