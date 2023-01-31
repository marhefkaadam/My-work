# Rule 110 implementation

Rule 110 is one of the easiest Turing complete systems. The task is to understand the rules of this system and to implement a program which simulates Rule 110 on an array with unlimited length.
- Definition of **Rule 110** - https://en.wikipedia.org/wiki/Rule_110

## Example usage

The main implementation file is "semestral.rkt". There is a function called **rule-110** which is the function which starts the process of simulation.

Function call example:
``` 
(rule-110 '(1 0 1 0 0 1 1 0 1 0 1 0) 20)
```
The first argument is a list, from which the derivations defined by rules of Rule 110 are made. The second argument is the amount of derivations of the list for program to create. 

Result of the example function call is:
```
'((1 0 1 0 0 1 1 0 1 0 1 0)
  (1 1 1 0 1 1 1 1 1 1 1 1)
  (0 0 1 1 1 0 0 0 0 0 0 0)
  (0 1 1 0 1 0 0 0 0 0 0 0)
  (1 1 1 1 1 0 0 0 0 0 0 0)
  (1 0 0 0 1 0 0 0 0 0 0 1)
  (1 0 0 1 1 0 0 0 0 0 1 1)
  (1 0 1 1 1 0 0 0 0 1 1 0)
  (1 1 1 0 1 0 0 0 1 1 1 1)
  (0 0 1 1 1 0 0 1 1 0 0 0)
  (0 1 1 0 1 0 1 1 1 0 0 0)
  (1 1 1 1 1 1 1 0 1 0 0 0)
  (1 0 0 0 0 0 1 1 1 0 0 1)
  (1 0 0 0 0 1 1 0 1 0 1 1)
  (1 0 0 0 1 1 1 1 1 1 1 0)
  (1 0 0 1 1 0 0 0 0 0 1 1)
  (1 0 1 1 1 0 0 0 0 1 1 0)
  (1 1 1 0 1 0 0 0 1 1 1 1)
  (0 0 1 1 1 0 0 1 1 0 0 0)
  (0 1 1 0 1 0 1 1 1 0 0 0)
  (1 1 1 1 1 1 1 0 1 0 0 0))
```
There are visible triangular shapes made by zeros just like the white triangles showed in the example on wiki.

------
Tests are stored in file "tests.rkt". In the file "helper.rkt" there are helper functions defined which are then used in the main implementation file.
