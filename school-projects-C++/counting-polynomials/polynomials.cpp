#ifndef __PROGTEST__
#include <cstring>
#include <cstdlib>
#include <cstdio>
#include <cctype>
#include <climits>
#include <cmath>
#include <cfloat>
#include <cassert>
#include <unistd.h>
#include <iostream>
#include <iomanip>
#include <sstream>
#include <string>
#include <vector>
#include <algorithm>
#if defined ( __cplusplus ) && __cplusplus > 199711L /* C++ 11 */
#include <memory>
#endif /* C++ 11 */
using namespace std;
#endif /* __PROGTEST__ */

/* global constant to determine the deviation of counting with decimal numbers */
const long double DBL_EPS = DBL_EPSILON * 1e3;

/** Class represents an implementation of polynomials and basic counting with polynomials. */
class CPolynomial {
    public:
        /** Default constructor initializes the polynomial to degree 0. */
        CPolynomial( double value = 0 );

        /**
         * Copy constructor, operator = and destructor are default,
         * because the implementation doesn't require specific definition.
         */
        CPolynomial( const CPolynomial & ) = default;
        CPolynomial & operator = ( const CPolynomial & ) = default;
        ~CPolynomial() = default;

        /**
         * Operator + sums two polynomials.
         * Function finds the polynomial of higher degree of the two in the equation and then adds the other one to it.
         * @param right - polynomial to be added to left polynomial in the equation.
         * @returns new polynomial which was created.
         */
        CPolynomial   operator + ( const CPolynomial & right ) const;

        /**
         * Operator - subtracts one polynomial from each other.
         * Uses operator + and *(-1) to implement to avoid copying of code.
         * @param right - polynomial to be subtracted.
         * @returns new polynomial which was created.
         */
        CPolynomial   operator - ( const CPolynomial & right ) const;

        /**
         * Operator * multiplies two polynomials with each other.
         * Uses naive algorithm which multiplicates every coefficient of one polynomial by other, complexity O(n^2).
         * Could be faster and better implemented by FFT algorithm, complexity O(n*log(n)).
         * @param right - is the second polynomial in the equation.
         * @returns new polynomial which was created by multiplying of two polynomials.
         */
        CPolynomial   operator * ( const CPolynomial & right ) const;

        /**
         * Operator * multiplies every coefficient by given value of type double.
         * @param multiply - double value which is used to multiply coefficients.
         * @returns multiplied polynomial.
         */
        CPolynomial & operator * ( double multiply );

        /**
         * Operator == compares two polynomials by each coefficient. Every coefficient must be the same.
         * @param right - is the polynomial which is compared with object on which the == was called.
         * @returns true if polynomials are the same, false otherwise.
         */
        bool          operator == ( const CPolynomial & right ) const;

        /**
         * Operator != compares two polynomials by each coefficient. Every compared coefficient must be different.
         * Uses opposite of return value of operator == to implement this.
         * @param right - is the polynomial which is compared with object on which the != was called.
         * @returns true if polynomials differ, false otherwise.
         */
        bool          operator != ( const CPolynomial & right ) const;


        /**
         * Const operator [] is used for reading the value of certain coefficient in the polynomial.
         * @param index - is an index to be read.
         * @returns the coefficient of wanted index.
         */
        double        operator [] ( size_t index ) const;

        /**
         * Non-const operator [] is used for changing the value of certain coefficient in the polynomial.
         * If current size is smaller than the index to be changed, function inserts zeros into coefficients below which are not filled yet.
         * @param index - is an index to be changed.
         * @returns reference to value in m_coefficients to be changed.
         */
        double &      operator [] ( size_t index );

        /**
         * Operator () is used to determine the value of polynomial by
         * replacing unknown x in the polynomial for value "num" and evaluating it.
         * @param num - value to by replaced (our unknown x in the polynomial).
         * @returns value of the polynomial.
         */
        double        operator () ( double num ) const;

        /**
         * Method counts the degree of a polynomial on which this method was called.
         * Method goes through the vector from the end to the beginning and
         * @returns the index of the first non zero element in vector.
         */
        size_t Degree() const;

        friend ostream & operator << ( ostream & out, const CPolynomial & x );

        friend bool dumpMatch( const CPolynomial & x, const vector<double> & ref );

    private:
        /**
         * @var - vector of coefficients in one polynomial,
         * the index of coefficient in vector represents degree of the coefficient.
         */
        vector<double> m_coefficients;
};


//______________________________________________________________________________________________________________________
CPolynomial::CPolynomial( double value ) {
    m_coefficients.push_back( value );
}

//______________________________________________________________________________________________________________________
CPolynomial CPolynomial::operator + ( const CPolynomial & right ) const {
    CPolynomial result;
    size_t max_degree = Degree(), min_degree = right.Degree();

    if( min_degree > max_degree ) {
        result = right;
        swap( min_degree, max_degree );
        for( size_t i = 0; i <= min_degree; i++ )
            result.m_coefficients[i] += m_coefficients[i];
    } else {
        result = *this;
        for( size_t i = 0; i <= min_degree; i++ )
            result.m_coefficients[i] += right.m_coefficients[i];
    }

    return result;
}

//______________________________________________________________________________________________________________________
CPolynomial CPolynomial::operator - ( const CPolynomial & right ) const {
    CPolynomial right_copy = right;

    return *this + right_copy * (-1);
}

//______________________________________________________________________________________________________________________
CPolynomial CPolynomial::operator * ( const CPolynomial & right ) const {
    CPolynomial result;

    /* Initializes polynomial to maximum value which can be assigned (m+n). */
    result.m_coefficients.resize( Degree() + right.Degree() + 1, 0 );

    for( size_t i = 0; i <= Degree(); i++ )
        for( size_t j = 0; j <= right.Degree(); j++ )
            result.m_coefficients[i+j] += m_coefficients[i] * right.m_coefficients[j];

    return result;
}

//______________________________________________________________________________________________________________________
CPolynomial & CPolynomial::operator * ( double multiply ) {
    for( size_t i = 0; i <= Degree(); i++ )
        m_coefficients[i] *= multiply;

    return *this;
}

//______________________________________________________________________________________________________________________
bool CPolynomial::operator == ( const CPolynomial & right ) const {
    /* If the degrees of two polynomial doesn't match, they can't be the even. */
    if( Degree() != right.Degree() )
        return false;

    for( size_t i = 0; i <= Degree(); i++ )
        if( fabs( m_coefficients[i] - right.m_coefficients[i] ) > DBL_EPS * fabs( m_coefficients[i] ) )
            return false;

    return true;
}

//______________________________________________________________________________________________________________________
bool CPolynomial::operator != ( const CPolynomial & right ) const {
    return !( *this == right );
}

//______________________________________________________________________________________________________________________
double CPolynomial::operator [] ( size_t index ) const {
    if( index < 0 || index >= m_coefficients.size() )
        throw out_of_range( "Index out of range." );    /* There wasn't a specific way to handle this situation in the assignment. */

    return m_coefficients[index];
}

//______________________________________________________________________________________________________________________
double & CPolynomial::operator [] ( size_t index ) {
    if( index >= m_coefficients.size() - 1 )
        m_coefficients.resize( index + 1, 0 );

    return m_coefficients[index];
}

//______________________________________________________________________________________________________________________
double CPolynomial::operator () ( double num ) const {
    double result = 0;

    for( size_t i = 0; i <= Degree(); i++ )
        result += m_coefficients[i] * pow( num, i );

    return result;
}

//______________________________________________________________________________________________________________________
size_t CPolynomial::Degree() const {
    for( size_t i = m_coefficients.size() - 1; i > 0; i-- )
        if( fabs( m_coefficients[i] ) > DBL_EPS * fabs( m_coefficients[i] ) )
            return i;

    return 0;
}

//______________________________________________________________________________________________________________________
/**
 * Function prints polynomial in compact form e.g. "x^2 + 3*x^1 - 4".
 * @param out - is an output stream to which the polynomial is printed.
 * @param x - is the polynomial to be printed.
 * @returns ostream.
 */
ostream & operator << ( ostream & out, const CPolynomial & x ) {
    int degree = x.Degree();

    for( int i = degree; i >= 0; i-- ) {
        double value = x.m_coefficients[i];

        /* Coefficient with zero value are not shown in the printout. */
        if( fabs( value ) <= DBL_EPS * fabs( value ) )
            continue;

        if( i != degree )
            out << " ";

        if( i != degree && ( value >= DBL_EPS * value ) )
            out << "+ ";
        else if( !( value >= DBL_EPS * value ) )
            out << "- ";

        /* If the value is 1 or -1 we don't want the number 1 to be shown. */
        if( fabs( value ) - 1 >= DBL_EPS * fabs( value ) ) {
            out << fabs( value );
            if( i != 0 )
                out << "*";
        }

        if( i != 0 )
            out << "x^" << i;
    }

    /* Special case for polynomials with degree == 0, we show only the coefficient. */
    if( degree == 0 ) {
        if( !( x.m_coefficients[0] >= DBL_EPS * x.m_coefficients[0] ) )
            out << "- ";
        out << fabs( x.m_coefficients[0] );
    }

    return out;
}


//______________________________________________________________________________________________________________________
#ifndef __PROGTEST__

/**
 * Functions determines if a < b.
 * @param a
 * @param b
 * @returns true if a is lower, false otherwise.
 */
bool smallDiff( double a, double b ) {
    if( fabs( a - b ) < DBL_EPS * fabs( a ) )
        return true;
    return false;
}

/**
 * Function determines if the given polynomial coefficients match the ones from reference output.
 * @param x - polynomial to be compared.
 * @param ref - vector of coefficients of reference output.
 * @returns true if every coefficient matches, false otherwise.
 */
bool dumpMatch( const CPolynomial & x, const vector<double> & ref ) {
    for( size_t i = 0; i < ref.size(); i++ )
        if( fabs( x.m_coefficients[i] - ref[i] ) > DBL_EPS * fabs( ref[i] ) )
            return false;

    return true;
}

int main() {
    CPolynomial a, b, c;
    ostringstream out;

    const CPolynomial x;
    const double f = 3.5;
    c = b * x;
    c = x * f;

    a[0] = -10;
    a[1] = 3.5;
    a[3] = 1;
    assert ( smallDiff ( a ( 2 ), 5 ) );
    out . str ("");
    out << a;
    assert ( out . str () == "x^3 + 3.5*x^1 - 10" );
    a = a * -2;
    assert ( a . Degree () == 3
           && dumpMatch ( a, vector<double>{ 20.0, -7.0, -0.0, -2.0 } ) );

    out . str ("");
    out << a;
    assert ( out . str () == "- 2*x^3 - 7*x^1 + 20" );
    out . str ("");
    out << b;

    assert ( out . str () == "0" );
    b[5] = -1;
    out . str ("");
    out << b;

    assert ( out . str () == "- x^5" );
    c = a + b;
    assert ( c . Degree () == 5
           && dumpMatch ( c, vector<double>{ 20.0, -7.0, 0.0, -2.0, 0.0, -1.0 } ) );

    out . str ("");
    out << c;
    assert ( out . str () == "- x^5 - 2*x^3 - 7*x^1 + 20" );
    c = a - b;
    assert ( c . Degree () == 5
           && dumpMatch ( c, vector<double>{ 20.0, -7.0, -0.0, -2.0, -0.0, 1.0 } ) );

    out . str ("");
    out << c;
    assert ( out . str () == "x^5 - 2*x^3 - 7*x^1 + 20" );
    c = a * b;
    assert ( c . Degree () == 8
           && dumpMatch ( c, vector<double>{ -0.0, -0.0, 0.0, -0.0, 0.0, -20.0, 7.0, -0.0, 2.0 } ) );

    out . str ("");
    out << c;
    assert ( out . str () == "2*x^8 + 7*x^6 - 20*x^5" );
    assert ( a != b );
    b[5] = 0;
    assert ( !(a == b) );
    a = a * 0;
    assert ( a . Degree () == 0
           && dumpMatch ( a, vector<double>{ 0.0 } ) );

    assert ( a == b );

    return 0;
}

#endif /* __PROGTEST__ */
