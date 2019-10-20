# Worlds-api

## About packages

package api contains controller that provides information about objects locations and geometry, also it provides a help information via OPTIONS method.

package config contains various configuration beans and conditions.

package dto contains data transfer object that is used during data transmition to a front-end.

package service contains base interfaces for services that are used by controller.

package service.database.implementation contains ORM based implementation of the MapService interface.

package service.simple.implementation contains implementation of ApiService interface that loads help information from resource files.

## Subdependencies

The worlds-api dependency designed as a plugable dependency and uses following subdependencies of the project: map-loader, object-loader, object-loader-interface, and earth-localization.
Dependencies object-loader and earth-localization are configurable and could be replaced either by another one from the project or by some custom users dependency.

## Resources and liquibase scripts
Help files are in the src/main/resource/specification folder.
Liquibase script that combines scripts from various dependencies is in src/main/resource/liquibase folder, also this folder contains a sample sql script to create database and user

## Main configuration bean
The main configuration bean that has to be scanned during the context loading is: config.web.WebConfiguration

## Properties
The project loads properties from two files: application.properties and jpa.properties

jpa.properties file is intended to configure a database and should contain following properties:
hibernate.dialect
jdbc.driver.name
jdbc.url
jdbc.username
jdbc.password

application.properties is intended to configure help files and other project behavior. To configure a custom users help files set properties:
custom-specification-enabled=true to enable custom help files.
Use property 
"api-info" to define a resource that will be load by api info request, 
"supported-types" to define a resource that will be load by supported object types OPTIONS request, 
"getObject-info" to define a resource that will be load by get object OPTIONS request, 
"getMaterial-info" to define a resource that will be load by get material OPTIONS request, 
"getSprite-info" to define a resource that will be load by get sprite OPTIONS request

If some of this properties was not configured then a default help file will be used. If property custom-specification-enabled=false only default help files will be used inspite of other settings.

Also project allows to load objects locations from json file via SimpleMapService, use the property "map" to configure path to the json file.
