#include "CApplication.h"
using namespace std;

CApplication::CApplication( const shared_ptr<CInterface> & interface )
        : m_interaction( interface ), m_interface( interface ), m_is_running( true ) {
    m_interface->Add_command( COMMAND_RUN_Q, HELP_RUN_Q )
                .Add_command( COMMAND_CREATE_Q, HELP_CREATE_Q )
                .Add_command( COMMAND_RESULT_Q, HELP_RESULT_Q )
                .Add_command( COMMAND_IMPORT, HELP_IMPORT )
                .Add_command( COMMAND_EXPORT, HELP_EXPORT )
                .Add_command( COMMAND_LIST, HELP_LIST )
                .Add_command( COMMAND_HELP, HELP_HELP )
                .Add_command( COMMAND_QUIT, HELP_QUIT );
}

void CApplication::Run() {
    m_interface->Print_to_console( INTRODUCTION );
    m_interface->Print_help();

    while( m_is_running ) {
        bool quit = m_interaction.Run();
        if( quit == false )
            Stop();
    }
}

void CApplication::Stop() {
    m_is_running = false;
}
