#include "CInterface.h"
#include "messages.h"
#include <limits>
#include <iomanip>
using namespace std;

CInterface::CInterface( istream & in, ostream & out )
        : m_in( in ), m_out( out ) {
    m_in.exceptions( ios::badbit | ios::eofbit );
    m_out.exceptions( ios::failbit | ios::badbit | ios::eofbit );
}

CInterface & CInterface::Add_command( const string & command, const string & help ) {
    m_commands.emplace( command, help );

    return *this;
}

string CInterface::Prompt_command() const {
    bool found = false;
    string command;

    while( !found ) {
        Print_to_console( MAIN_MENU );

        m_in >> command;

        if( m_commands.count( command ) == 1 )
            found = true;
        else
            Print_to_console( ERROR_INVALID_COMMAND );

        Clear_istream();
    }

    return command;
}

size_t CInterface::Prompt_id( size_t max_id ) const {
    if( max_id == 0 )
        return 0;

    size_t id = 0;
    m_in >> id;
    Clear_istream();

    if( id < 1 || id > max_id ) {
        Print_to_console( ERROR_INVALID_NUMBER );
        return 0;
    }

    return id;
}

string CInterface::Prompt_string() const {
    string input;

    while( input.empty() )
        getline( m_in, input );

    Trim_string( input );

    return input;
}

bool CInterface::Prompt_yes_no( const std::string & message ) const {
    Print_to_console( message );
    string answer = Prompt_string();

    if( answer == "Y" || answer == "Yes" || answer == "y" || answer == "yes" )
        return true;

    return false;
}

void CInterface::Print_to_console( const std::string & message ) const {
    m_out << message << endl;
}

std::ostream & CInterface::Get_ostream() const {
    return m_out;
}

void CInterface::Clear_istream() const {
    m_in.clear();
    m_in.ignore( numeric_limits<streamsize>::max(), '\n' );
}

void CInterface::Print_help() const {
    m_out << "List of usable commands:" << endl;
    for( const auto & x : m_commands )
        m_out << setw(4) << " " << "-> " << x.first << " - " << x.second << endl;
}

void CInterface::Trim_string( string & str ) const {
    size_t start = str.find_first_not_of( ' ' ), end = str.find_last_not_of( ' ' );

    str = str.substr( start, end - start + 1 );
}
