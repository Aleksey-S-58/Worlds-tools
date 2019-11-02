# map-loader
This dependency provides an access to objects locations which are stored in database.
The Location entity is intended to store coordinates and rotation of an object.
The Location binded to an objects description via field objectId.

The Description entity is intended to store additional information about objects view such as radius, objects type and objects name. Description binded to Location via objectId field that forms a relation OneToMany (Many descriptions lincs to the single location). 
Name is a linc to the 3DO or to the sprite, according to a type.

## Partitioning
In case of partitioning to distribute Locations between various tables columns: latitude, longitude and hight should be used. To distribute Descriptions between various tables an object_id column should be used.