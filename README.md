## Using SQLancer

This repository is not meant to be used for typical sqlancer use, but meant as a refactoring sandbox for a course project for NUS CS3213.

The goal is to break down the monolithic project into multiple submodules.

For using SQLancer, check the upstream repository at [sqlancer.com](sqlancer.com) (yes it is a github link.)

## Project Structure explained

Initially, SQLancer is a monolithic program that compiles every DBMS and downloads their dependencies. The downsides should be obvious when you only want to test 1 DBMS.

```
src/sqlancer
|- Java files
|- DBMS packages
    |- various packages and java files
|- Common packages
```

Therefore as part of the course, one of the goals is to allow SQLancer to compile and download dependencies for the specified DBMS.

A few approaches were considered:
1. Specify DBMS and add it to the `include` path when compiling
   - It turns out that most of the bloat comes from the dependencies, and specifying dependencies for each DBMS in the POM will cause the `pom.xml` to be bloated and unworkable.
2. Use child POMs to manage each DBMS
   - It is not simple to get a child POM to compile with the parent POM, there needs to be an additional POM to get the base functions.
3. Extract the base functions as a dependency and use it for other DBMS.
   - It is feasible, but I encountered limitations, which may or may not be attributed to my unfamilarity with the POM files.

The 3rd approach breaks down the project this way (may be dynamic, as long as POM file stays consistent)
```
src/sqlancer
|- base
    |- .settings
    |- src
        |- files and packages for the base
    |- pom.xml
|- DBMS_1
    |- .settings
    |- src
        |- files and packages for the DBMS_1
    |- pom.xml
|- ...
```

In this case, only sqlite3 is properly implemented. 

## Other changes
Submodules are added to POM.xml

## Example
At project root,
```shell
mvn clean package -pl src/sqlancer/sqlite3 -am

```
```shell
cd src/sqlancer/sqlite3/target
```
```shell
java -jar sqlite3-2.0.0.jar sqlite3
```