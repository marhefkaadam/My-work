# ARails - frontend implementation (client)

- it is a web client that allows you to use the server part of the ARails application
- uses a framework for creating a web client - [Thymeleaf](https://www.thymeleaf.org/)
- the web client can be found at ```localhost:8081```
- the main page is a list of available trains - it has the functionality to add/edit/delete trains from the table
- on the main page there is also a search in which the user enters the year of the train production - the search returns a table of manufacturers that produced at least one train in the specified year 
- more complex logic of the server application is used when EDITing trains, when it is not possible to edit "End of revision validity"
if the train was manufactured more than 20 years ago
- client is run by typing ```gradle build``` in the console
