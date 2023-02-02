#include "CQMultiChoice.h"
#include "messages.h"
#include "headers.h"

using namespace std;

CQMultiChoice::CQMultiChoice( const shared_ptr<CInterface> & interface, const string & question, const string & type )
        : CQuestion( interface, question, type ) {
}

shared_ptr<CQuestion> CQMultiChoice::Clone() const {
    return make_shared<CQMultiChoice>( *this );
}

CQuestion & CQMultiChoice::Ref_answer() {
    // adding options to question
    ostream & out = m_interface->Get_ostream();
    size_t options_count = 0;

    // asks for how many options will this question have
    while( true ) {
        out << PROMPT_OPTIONS_AMOUNT << MAX_OPTIONS << ": " << endl;
        options_count = m_interface->Prompt_id( MAX_OPTIONS );

        if( options_count != 0 )
            break;
    }

    // asks for options
    m_interface->Print_to_console( PROMPT_ADD_OPTION );
    for( size_t i = 1; i <= options_count; ++i ) {
        out << i << ". " << flush;
        m_options.emplace_back( m_interface->Prompt_string() );
    }

    out << "Your question looks like this:" << endl;
    Print( out );

    // asks for a reference answer until it gets a valid one and
    // then until user says he doesn't want another ref answer
    while( true ) {
        m_interface->Print_to_console( PROMPT_REF_ANSWER_NUM );
        size_t id = m_interface->Prompt_id( m_options.size() );

        if( id != 0 ) {
            if( m_ref_answer.count( id ) == 1 ) {
                m_interface->Print_to_console( ERROR_INVALID_NUMBER );

            } else {
                m_ref_answer.emplace( id );

                // checks if user wants to add another reference answer
                if( m_interface->Prompt_yes_no( PROMPT_REF_ANSWER_NEXT ) == false )
                    break;
            }
        }

        // breaks the loop if all answers where checked as a reference one
        if( m_ref_answer.size() == m_options.size() ) {
            m_interface->Print_to_console( PROMPT_MC_ALL_ANSWERS );
            break;
        }
    }

    return *this;
}

CQuestion & CQMultiChoice::User_answer() {
    // we need to erase previous answers if somebody fills out quiz more times
    m_user_answer.clear();

    // asks for a reference answer until it gets a valid one and
    // then until user says he doesn't want to add another one
    while( true ) {
        m_interface->Print_to_console( PROMPT_USER_ANSWER_NUM );
        size_t answer_id = m_interface->Prompt_id( m_options.size() );

        if( answer_id != 0 ) {
            if( m_user_answer.count( answer_id ) == 1 ) {
                m_interface->Print_to_console( ERROR_INVALID_NUMBER );

            } else {
                m_user_answer.emplace( answer_id );

                // checks if user wants to add another correct answer
                if( m_interface->Prompt_yes_no( PROMPT_USER_MC_ANSWER_NEXT ) == false )
                    break;
            }
        }

        // if user already checked all possible options, the asking for answer won't continue
        if( m_user_answer.size() == m_options.size() ) {
            m_interface->Print_to_console( PROMPT_MC_ALL_ANSWERS );
            break;
        }
    }

    return *this;
}

ifstream & CQMultiChoice::Import( ifstream & file ) {
    string input;
    if( !getline( file, input ) || input != H_OPTIONS ) throw logic_error( "Incorrect header." );

    // imports control count so we know how many options should we import
    size_t control_count = 0;
    file >> control_count;
    file.ignore();

    if( ! Is_in_boundaries( control_count, MAX_OPTIONS ) ) throw logic_error( "Incorrect amount of options." );

    // imports the options
    for( size_t i = 0; i < control_count; ++i ) {
        if( !getline( file, input ) || input.empty() ) throw logic_error( "Empty line.");
        m_options.emplace_back( input );
    }

    if( !getline( file, input ) || input != H_OPTIONS_E ) throw logic_error( "Incorrect header." );

    if( !getline( file, input ) || input != H_REFERENCE ) throw logic_error( "Incorrect header." );

    // how many reference answers will be imported
    file >> control_count;
    if( ! Is_in_boundaries( control_count, m_options.size() ) ) throw logic_error( "Incorrect amount of reference answers." );

    // imports the reference answers
    size_t ref_answer = 0;
    for( size_t i = 0; i < control_count; ++i ) {
        file >> ref_answer;

        if( ! Is_in_boundaries( ref_answer, m_options.size() ) ) throw logic_error( "Incorrect reference option." );
        if( m_ref_answer.count( ref_answer ) == 1 ) throw logic_error( "Duplicite reference answer." );

        m_ref_answer.emplace( ref_answer );
    }
    file.ignore();

    if( !getline( file, input ) || input != H_REFERENCE_E ) throw logic_error( "Incorrect header." );

    return file;
}


std::ofstream & CQMultiChoice::Export( ofstream & file ) const {
    // writes initial information about the question into file
    CQuestion::Export( file );

    // writes every option on a new line and how many options it have
    file << H_OPTIONS << endl << m_options.size() << endl;
    for( const auto & o : m_options )
        file << o << endl;
    file << H_OPTIONS_E << endl;

    // writes reference answers on a new line and how many ref answers it have
    file << H_REFERENCE << endl << m_ref_answer.size() << endl;
    for( const auto & r : m_ref_answer )
        file << r << endl;
    file << H_REFERENCE_E << endl;

    return file;
}


ostream & CQMultiChoice::Print( ostream & out ) const {
    CQuestion::Print( out );

    for( size_t i = 0; i < m_options.size(); ++i )
        out << i + 1 << ". " << m_options[i] << endl;

    return out;
}

bool CQMultiChoice::Is_correct() const {
    // checks if all of the reference answers user inputted
    for( const auto & x : m_ref_answer )
        if( m_user_answer.count( x ) == 0 )
            return false;

    return true;
}

bool CQMultiChoice::Has_rules() const {
    return false;
}

bool CQMultiChoice::Is_in_boundaries( size_t number, size_t max ) const {
    if( number < 1 || number > max )
        return false;

    return true;
}
