
3D map development tool.
This project designed as a plugable dependency that provides an access to a map and 3DO via http protocol.
To use it, you should add worlds-api dependency, configure database properties in jpa.properties, add and configure logger dependency (for example logback), add front-end dependency: ---
If you are developing a web application then create AnnotationConfigWebApplicationContext, register configurational bean config.web.WebConfiguration from the main dependency and add DispatcherServlet.
A sample of launcher is awailable in repository: .........

What is inside?
The project subdivided to a several subdependencies: (map-loader, object-loader, object-loader-interface, earth-localization).
Dependency object-loader-interface contains base interfaces and classes.
Dependency map-loader contains entities and repositories to access to objects locations on a map.
Dependency object-loader contains entities and repositories to access to 3DOs and sprites stored in database.
The object-loader is a basic dependency for storing 3DOs in a relational database, farthere will be developed another dependencies which will allow to store 3DOs in document oriented databases like (Mongo, Casandra, etc...).
Dependency earth-localization contains a strategy that calculates a square of a visible area by an observers location.
In addition to this strategy there couldbe a numerous other strategies placed in their own dependencies and even custom users strategies.

The main dependency is worlds-api that uses map-loader, object-loader and earth-localization.
Dependencies object-loader and earth-localization can be excluded and replaced by more convenient one via pom of a chief project that uses the tool.
Thus the tool can be adopted for any kind of 3D map would it be a global map or depots map or airports map or may be even map for some game.
worlds-api contains a rest controller inside that allows to get an objects location and to load objects geometry (see package api).

Liquibase and database configuring.
Dependencies map-loader and object-loader have liquibase scripts inside. The main dependency has a master.xml that combines scripts of sub dependencies (see liquibase folder in the main/resources).
The main dependency loads properties from files jpa.properties, application.properties.
Database is configured by following properties: hibernate.dialect, jdbc.driver.name, jdbc.url, jdbc.username, jdbc.password.

Help files.
A controller in the main dependency supports help queries via OPTIONS requests.
To configure custom help files you should anable it via property custom-specification-enabled=true and then specify a path to a custom help file via special property (see service.simple.implementation.SimpleApiService)


