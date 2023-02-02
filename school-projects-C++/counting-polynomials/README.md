# Counting polynomials

This is a school assignment from class Programming and algorithmization 2 (PA2). In this class we were learning C++, good programming skills and algorithmization practises. This particular task is focused on operator overloading in C++.

### Task
The task is to implement the CPolynomial class, which will represent counting with polynomials.

The CPolynomial class stores a polynomial using the coefficients of its individual powers. We assume coefficients in the form of double decimal numbers. Using interfaces (most interfaces are in the form of overloaded operators) this class can work with polynomials. The implemented class must meet the following interfaces:

- constructor implicit
  - initializes an object that will represent a 0-valued polynomial.
- copy constructor
  - will be implemented if the internal structures of your class require it.
- destructor
  - will be implemented if the internal structures of your class require it.
- overloaded operator =
  - will allow polynomials to be copied (if the automatically generated operator = does not suit).
- operator <<
  - will allow object output to a C++ stream. The statement will be implemented in a compact form:
    - terms are displayed from the highest power,
    - terms with a zero coefficient will not be displayed,
    - terms with a coefficient of +1 or -1 will not display the number 1, it will only display the corresponding power of x,
    - there are no unnecessary signs in the statement - (ie, for example, x - 9 or - x^2 + 4 is correct, but x + (-9) is wrong),
    - the zero polynomial appears as a single number 0.
- operator +
  - allows adding two polynomials,
- operator -
  - allows to subtract two polynomials,
- operator *
  - allows multiplying a polynomial by a decimal number or two polynomials,
- the == and != operators
  - will allow you to compare polynomials for an exact match,
- operator []
  - allows setting/finding the value of the coefficient for the specified power of the polynomial. The reading variant must also work for constant polynomials,
- operator ()
  - will allow evaluating the value of the polynomial for the specified value x (x is a decimal number),
- method Degree()
  - method finds the degree of the polynomial (e.g. x^3+4 has degree 3, 5 has degree 0, 0 has degree 0).

Submit the source file that contains your implementation of the CPolynomial class. Do not include header files, your test function and the main function in the commit file. If you want to keep main or embed header files in the file, put them in a conditional translation block.