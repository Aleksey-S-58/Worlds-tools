-- add column object_type
alter table ThreeDMap.object_description add column object_type varchar(50);
alter table ThreeDMap.object_description alter column object_type set not null;
comment on column ThreeDMap.object_description.object_type is 'The type can be (THREE_D_OBJECT, SPRITE).';