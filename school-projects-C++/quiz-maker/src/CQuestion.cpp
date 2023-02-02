#include "CQuestion.h"
#include "headers.h"
using namespace std;

CQuestion::CQuestion( const shared_ptr<CInterface> & interface, const string & question, const string & type )
    : m_interface( interface ), m_type( type ), m_question( question ) {
}

ofstream & CQuestion::Export( ofstream & file ) const {
    file << m_type << endl << m_question << endl;
    return file;
}

ostream & CQuestion::Print( ostream & out ) const {
    return out << m_type << " question: " << m_question << endl;
}

ostream & operator << ( ostream & out, const CQuestion & x ) {
    return x.Print( out );
}