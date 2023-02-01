# ARails - backend implementation (server)

 The database used in this project is a database created in my previous school lecture. The database stores information about trains (id, usage, height, length...), mechanics who repair trains and manufacturers of the train units. The application handles information about trains and their repairs.

- examples of HTTP requests are in the file "generated-requests.http" (requests can be run in IntellijIDEA)
- M:N relation is between entities *Train* and *Mechanic* - it holds information about train repairs - who repaired which train
- 1:N relations are between entities:
  - *Train* and *Manufacturer* - each train must have its own manufacturer, the manufacturer can produce multiple trains
  - *Mechanic* and *Manufacturer* - each mechanic has his own employer, the manufacturer employs several mechanics
- a complex query is e.g. HTTP request ```GET http://localhost:8080/api/manufacturers/trains/2010```, which will respond with a list of manufacturers that produced trains that were produced in 2010 (productionYear == 2010)
- a complex bussiness operation is represented in /api/trains PUT request when it is possible to modify the revisionValidity of the train only if the productionYear of the train is not older than 20 years from now
- server is run by typing ```gradle build``` in the console