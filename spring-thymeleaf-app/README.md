# ARails - client-server application with the use of Spring and Thymeleaf frameworks

### Task
Write a client-server application which (**server** side):
- is a three-layer application and uses appropriate Java technologies and libraries (Spring framework)
- works with at least 3 entities from a relational database (all CRUD operations, min. 1 many-to-many link, i.e. there will be 4 tables in the relational database; using ORM; the relational database can be arbitrary)
- with the use of ORM, implement an extra query (beyond CRUD and manipulation of the M:N binding, a query implemented ideally in JPQL)
- the application logic layer enables all operations of the data layer (CRUD over all entities, M:N binding manipulation, extra query)
- a properly designed REST API exposes all application logic layer operations (including CRUD over all entities)
- is properly versioned
- all methods (except simple getters, setters and constructors) are covered by some unit test
- the REST API is fully tested
- it is properly built (Gradle) and tests are run and evaluated within the build

Write a client-server application which (**client** side):
- uses any programming/scripting language
- you can create any interface (GUI, web application, interactive console application, CLI)
- client side accesses a RESTful web service exposed by the server part
- allows the use of all CRUD operations on at least one entity, another data layer query, and other application logic operations
- is versioned and properly compiled

### Implementation
Further information about the implementation is in the subdirectories.
