# Handling database connections

This work is a part of a project on a subject Software Project 1 (SP1). This project was made by other students in a group as well. Files which are in this directory are just parts selected from the whole project, so these codes don't work on their own. Most of the code here was written by me. 

The project which we were programming is a part of a system called "DBS portál" (Database systems portal). This portal is part of the teaching of the DBS (Database systems) subject. Our task was to rewrite one part of it, namely a microservice that is in charge of managing student databases, running SQL commands on database connections and managing database rights. Student databases are used for creating student's semestral work. The microservice also uses external services for parsing SQL queries, according to which it can verify whether the user does not make unauthorized changes to read-only databases, etc. Another functionality is the conversion of relational algebra (RA) to SQL.

PHP is used to implement these features along with vendor, DBALConnection and external personal projects such as Oracle query parser, RA to SQL translator etc.
