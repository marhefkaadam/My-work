#include "CQSingleChoice.h"
#include "messages.h"
#include "headers.h"

using namespace std;

CQSingleChoice::CQSingleChoice( const shared_ptr<CInterface> & interface, const string & question, const string & type )
        : CQuestion( interface, question, type ), m_has_rules( false ) {
}

shared_ptr<CQuestion> CQSingleChoice::Clone() const {
    return make_shared<CQSingleChoice>( *this );
}

CQuestion & CQSingleChoice::Ref_answer() {
    ostream & out = m_interface->Get_ostream();
    size_t options_amount = 0;

    // asks for how many options will this question have
    while( true ) {
        out << PROMPT_OPTIONS_AMOUNT << MAX_OPTIONS << ": " << endl;
        options_amount = m_interface->Prompt_id( MAX_OPTIONS );

        if( options_amount != 0 )
            break;
    }

    // asks for options
    m_interface->Print_to_console( PROMPT_ADD_OPTION );
    for( size_t i = 1; i <= options_amount; ++i ) {
        out << i << ". " << flush;
        m_options.emplace_back( m_interface->Prompt_string() );
    }

    // checks if this answer will have correct answer or not (usage of SRule)
    if( m_interface->Prompt_yes_no( PROMPT_SINGLE_CHOICE_ANSWER ) == false ) {
        m_has_rules = true;
        return *this;
    }

    out << "Your question looks like this:" << endl;
    Print( out );

    // asks for a reference answer until it gets a valid one
    while( true ) {
        m_interface->Print_to_console( PROMPT_REF_ANSWER_NUM );
        size_t answer_id = m_interface->Prompt_id( m_options.size() );

        if( answer_id != 0 ) {
            m_ref_answer = answer_id;
            break;
        }
    }

    return *this;
}

CQuestion & CQSingleChoice::User_answer() {
    // resets the answer to non-valid one if somebody fills quiz more times
    m_user_answer = 0;

    // asks for a users answer until it gets a valid one
    while( true ) {
        m_interface->Print_to_console( PROMPT_USER_ANSWER_NUM );
        size_t answer_id = m_interface->Prompt_id( m_options.size() );

        if( answer_id != 0 ) {
            m_user_answer = answer_id;
            break;
        }
    }

    return *this;
}

ifstream & CQSingleChoice::Import( std::ifstream & file ) {
    string input;
    if( !getline( file, input ) || input != H_OPTIONS ) throw logic_error( "Incorrect header." );

    // imports control count so we know how many options should we import
    size_t control_count = 0;
    file >> control_count;
    file.ignore();

    if( control_count < 1 || control_count > MAX_OPTIONS ) throw logic_error( "Incorrect amount of options." );

    // imports the options
    for( size_t i = 0; i < control_count; ++i ) {
        if( !getline( file, input ) || input.empty() ) throw logic_error( "Empty line.");
        m_options.emplace_back( input );
    }

    if( !getline( file, input ) || input != H_OPTIONS_E ) throw logic_error( "Incorrect header." );

    if( !getline( file, input ) || input != H_RULES_EXIST ) throw logic_error( "Incorrect header." );

    // if rules exist than we get from a file the "Y", if not then "N"
    // if "N" is entered then we import the reference answer, if not it means that this question is a "jump" question
    getline( file, input );
    if( input == "N" ) {
        size_t ref_answer = 0;
        file >> ref_answer;
        file.ignore();

        if( ref_answer < 1 || ref_answer > m_options.size() ) throw logic_error( "Incorrect reference option." );

        m_ref_answer = ref_answer;
    } else if( input == "Y" ) {
        m_has_rules = true;
    } else {
        throw logic_error( "Incorrect header." );
    }

    return file;
}

ofstream & CQSingleChoice::Export( ofstream & file ) const {
    // writes initial information about the question into file
    CQuestion::Export( file );

    // writes every option on a new line and writes how much options does question have
    file << H_OPTIONS << endl << m_options.size() << endl;
    for( const auto & o : m_options )
        file << o << endl;
    file << H_OPTIONS_E << endl;

    file << H_RULES_EXIST << endl;
    // writes reference answer if the question have correct answer
    if( Has_rules() )
        file << "Y" << endl;
    else
        file << "N" << endl << m_ref_answer << endl;

    return file;
}

std::ostream & CQSingleChoice::Print( std::ostream & out ) const {
    CQuestion::Print( out );

    for( size_t i = 0; i < m_options.size(); ++i )
        out << i + 1 << ". " << m_options[i] << endl;

    return out;
}

bool CQSingleChoice::Is_correct() const {
    if( m_has_rules )
        return false;

    return m_ref_answer == m_user_answer;
}

bool CQSingleChoice::Has_rules() const {
    return m_has_rules;
}

void CQSingleChoice::Add_rule( size_t pages_amount ) {
    m_interface->Get_ostream() << "Add rules to question, this question looks like this:" << endl;
    Print( m_interface->Get_ostream() );

    // while user wants to add rules the method asks for them
    while( true ) {
        m_interface->Print_to_console( PROMPT_ADD_RULE_A );
        size_t option = m_interface->Prompt_id( m_options.size() );
        if( option == 0 )
            continue;

        // checks if a rule for this option already exists
        bool found = false;
        for( const auto & x : m_rules )
            if( x.m_option == option ) {
                found = true;
                m_interface->Print_to_console( ERROR_DUPLICATION );
                break;
            }

        if( found )
            continue;

        // asks for a page_id where should option jump
        m_interface->Print_to_console( PROMPT_ADD_RULE_JUMP );
        size_t jump_to = m_interface->Prompt_id( pages_amount );
        if( jump_to == 0 )
            continue;

        m_rules.emplace_back( SRule { option, jump_to } );

        if( m_interface->Prompt_yes_no( PROMPT_ADD_RULE_NEXT ) == false )
            break;
    }
}

size_t CQSingleChoice::Jump_to_page() const {
    for( const auto & x : m_rules )
        if( x.m_option == m_user_answer )
            return x.m_jump_to_page;

    return 0;
}

ofstream & CQSingleChoice::Export_rules( ofstream & file ) const {
    file << m_rules.size() << endl;
    for( const auto & rule : m_rules )
        file << rule.m_option << endl << rule.m_jump_to_page << endl;

    return file;
}

ifstream & CQSingleChoice::Import_rules( ifstream & file, size_t pages_amount ) {
    // how many rules will be imported
    size_t control_count, option_id, jump_to_page;
    file >> control_count;

    if( control_count < 1 || control_count > m_options.size() ) throw logic_error( "Incorrect amount of rules." );

    for( size_t i = 0; i < control_count; ++i ) {
        // import which option has rule and where should quiz jump if this answer is checked
        file >> option_id >> jump_to_page;

        if( option_id < 1 || option_id > m_options.size()
            || jump_to_page < 1 || jump_to_page > pages_amount ) {
            throw logic_error( "Incorrect content of a rule." );
        }

        SRule rule { option_id, jump_to_page };
        m_rules.emplace_back( rule );
    }

    file.ignore();
    return file;
}

