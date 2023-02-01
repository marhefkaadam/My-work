#include "CQText.h"
#include "messages.h"
#include "headers.h"
using namespace std;

CQText::CQText( const shared_ptr<CInterface> & interface, const string & question,
        const string & type, const string & answer_type )
        : CQuestion( interface, question, type ), m_answer_type( answer_type ) {
}

shared_ptr<CQuestion> CQText::Clone() const {
    return std::make_shared<CQText>( *this );
}

CQuestion & CQText::Ref_answer() {
    m_interface->Get_ostream() << "Your question looks like this:" << endl;
    Print( m_interface->Get_ostream() );

    // asks for a reference answer
    m_interface->Print_to_console( PROMPT_REF_ANSWER_STR );
    m_ref_answer.emplace_back( m_interface->Prompt_string() );

    // the "set" type of answer type can have more reference answers so it loops
    // until the user wants to add another reference answers
    if( m_answer_type == A_SET ) {
        m_interface->Get_ostream() << "This question has the \"set\" type of correction of user's answers." << endl;

        while( true ) {
            if( m_interface->Prompt_yes_no( PROMPT_REF_ANSWER_NEXT ) == false )
                break;

            // asks for a reference answer
            m_interface->Print_to_console( PROMPT_REF_ANSWER_STR );
            string ref_answer = m_interface->Prompt_string();

            for( auto & x : m_ref_answer )
                if( x == ref_answer ) {
                    m_interface->Print_to_console( ERROR_DUPLICATION );
                    continue;
                }

            m_ref_answer.emplace_back( ref_answer );
        }
    }

    return *this;
}

CQuestion & CQText::User_answer() {
    // we need to erase previous answer if somebody fills out quiz more times
    m_user_answer.erase();

    m_interface->Print_to_console( PROMPT_USER_ANSWER_STR );
    m_user_answer = m_interface->Prompt_string();

    return *this;
}

ifstream & CQText::Import( std::ifstream & file ) {
    string input;

    // if answer type is "set" than the question can have more reference answers
    if( m_answer_type != A_SET ) {
        if( !getline( file, input ) || input.empty() ) throw logic_error( "Empty line.");
        m_ref_answer.emplace_back( input );

    } else {
        if( !getline( file, input ) || input != H_REFERENCE ) throw logic_error( "Incorrect header." );

        // imports control count so we know how many reference answers should we import
        size_t control_count = 0;
        file >> control_count;
        file.ignore();

        for( size_t i = 0; i < control_count; ++i ) {
            // imports reference answer
            if( !getline( file, input ) || input.empty() ) throw logic_error( "Empty line.");

            for( auto & x : m_ref_answer )
                if( x == input )
                    throw logic_error( "Duplicate reference answer." );

            m_ref_answer.emplace_back( input );
        }

        if( !getline( file, input ) || input != H_REFERENCE_E ) throw logic_error( "Incorrect header." );
    }

    return file;
}

ofstream & CQText::Export( ofstream & file ) const {
    // writes initial information about the question into file
    CQuestion::Export( file );

    // writes the correction type to file
    file << m_answer_type << endl;

    // writes reference answers on a new line
    if( m_answer_type != A_SET ) {
        file << m_ref_answer[0] << endl;
    } else {
        file << H_REFERENCE << endl << m_ref_answer.size() << endl;
        for( const auto & r : m_ref_answer )
            file << r << endl;
        file << H_REFERENCE_E << endl;
    }

    return file;
}

ostream & CQText::Print( ostream & out ) const {
    return CQuestion::Print( out ) << "Required answer type: " << m_answer_type << endl;
}

bool CQText::Is_correct() const {
    bool correct = false;

    if( m_answer_type == A_EXACT_MATCH )
        correct = Correction_exact_match();
    else if( m_answer_type == A_SET )
        correct = Correction_set();
    else
        correct = Correction_contains();

    return correct;
}

bool CQText::Has_rules() const {
    return false;
}

bool CQText::Correction_exact_match() const {
    return m_ref_answer[0] == m_user_answer;
}

bool CQText::Correction_set() const {
    for( auto & x : m_ref_answer )
        if( x == m_user_answer )
            return true;

    return false;
}

bool CQText::Correction_contains() const {
    // if the reference answer string was not found in the user's answer the it returns npos
    // which is equal to the maximum value representable by the type string
    if( m_user_answer.find( m_ref_answer[0] ) != m_user_answer.npos )
        return true;

    return false;
}
