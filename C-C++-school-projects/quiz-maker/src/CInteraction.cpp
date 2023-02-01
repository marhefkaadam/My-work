#include "CInteraction.h"
#include "headers.h"
#include <iomanip>
#include <fstream>
#include <experimental/filesystem>
using namespace std;
using directory_iterator = experimental::filesystem::directory_iterator;

// definition of static variables
std::shared_ptr<CInterface> CInteraction::m_interface;
std::vector<CQuiz> CInteraction::m_quizzes;
const char * const CInteraction::EXPORTS_DIR;

CInteraction::CInteraction( const shared_ptr<CInterface> & interface ) {
    m_interface = interface;
}

bool CInteraction::Run() const {
    string command = CInteraction::m_interface->Prompt_command();
    if( command == COMMAND_QUIT ) {
        CInteraction::m_interface->Get_ostream() << "Thanks for using you local quiz maker! See you soon!" << endl;
        return false;
    }

    CInteraction::COMMANDS.at( command )();

    return true;
}

void CInteraction::Run_quiz() {
    List();
    if( CInteraction::m_quizzes.empty() )
        return;

    CInteraction::m_interface->Print_to_console( PROMPT_Q_NUMBER );
    size_t quiz_id = CInteraction::m_interface->Prompt_id( CInteraction::m_quizzes.size() );

    // if the quiz_id is valid, starts the run process on the adequate quiz
    if( quiz_id != 0 )
        CInteraction::m_quizzes[quiz_id - 1].Run_quiz();
}

void CInteraction::Create_quiz() {
    CInteraction::m_interface->Get_ostream() << "The number of this quiz will be - " << CInteraction::m_quizzes.size() + 1 << endl;
    CInteraction::m_interface->Print_to_console( PROMPT_Q_NAME );

    string quiz_name = CInteraction::m_interface->Prompt_string();
    CInteraction::m_interface->Get_ostream() << "The quiz_name of this quiz will be - " << quiz_name << endl;

    CQuiz quiz { quiz_name, CInteraction::m_interface };
    quiz.Create_quiz();

    if( quiz.Quit_used() )
        return;

    CInteraction::m_quizzes.emplace_back( quiz );
}

void CInteraction::Result() {
    List();
    if( CInteraction::m_quizzes.empty() )
        return;

    CInteraction::m_interface->Print_to_console( PROMPT_Q_N_RESULT );
    size_t quiz_id = CInteraction::m_interface->Prompt_id( CInteraction::m_quizzes.size() );

    // invalid quiz_id detected
    if( quiz_id == 0 )
        return;

    ostream & out = CInteraction::m_interface->Get_ostream();
    if( CInteraction::m_quizzes[quiz_id - 1].Is_filled() == false ) {
        out << "Quiz wasn't filled yet, fill it by using command \"run_quiz\"." << endl;
        return;
    }

    double points_correct = static_cast<double>( CInteraction::m_quizzes[quiz_id - 1].Count_correct() );
    double points_total = static_cast<double>( CInteraction::m_quizzes[quiz_id - 1].Count_total() );
    double result = 0;

    if( points_total != 0 )
        result = ( points_correct/points_total ) * 100;

    out << "You have correctly answered " << setprecision(0) << points_correct
        << " questions out of total of " << points_total << " questions." << endl;
    out << "Success rate: " << setprecision(2) << fixed << result << " %" << endl;
    out << "Thanks for playing!" << endl;
}

void CInteraction::Import() {
    vector<string> filenames;
    try {
        for( auto & dir_it : directory_iterator( EXPORTS_DIR ) )
            filenames.emplace_back( dir_it.path().filename() );
    } catch( ... ) {
        CInteraction::m_interface->Print_to_console( ERROR_OPEN_DIR );
        return;
    }

    // prints files which can be imported from the directory
    CInteraction::m_interface->Print_to_console( PROMPT_IMPORT );
    for( size_t i = 0; i < filenames.size(); ++i )
        CInteraction::m_interface->Get_ostream() << setw(4) << " " << i + 1 << ". " << filenames[i] << endl;

    // asks the user which file he wants to import
    CInteraction::m_interface->Print_to_console( PROMPT_Q_N_IMPORT );
    size_t file_id = CInteraction::m_interface->Prompt_id( filenames.size() );

    // incorrect file_id detected, end of a function
    if( file_id == 0 )
        return;

    ifstream file;
    // may throw an exception further in the program, used for controlling unwanted input
    // or unexpected problems of writing into file
    file.exceptions( ios::failbit );

    try {
        file.open( EXPORTS_DIR + filenames[ file_id - 1 ] );

        // checks if the file was successfully opened and error didn't happen
        if( file.bad() ) {
            CInteraction::m_interface->Print_to_console( ERROR_OPEN_FILE );
            return;
        }

        // logic_error exceptions are used throughout the proccess of importing if file is formatted incorrectly
        string input, quiz_name;
        getline( file, input );
        if( input != H_QUIZ ) throw logic_error( "Incorrect header." );
        if( !getline( file, quiz_name ) || quiz_name.empty() ) throw logic_error( "Empty line.");

        // sends the process of importing further into the program
        CQuiz quiz { quiz_name, CInteraction::m_interface };
        quiz.Import( file );

        if( !getline( file, input ) || input != H_QUIZ_E ) throw logic_error( "Incorrect header." );

        file.close();

        CInteraction::m_quizzes.emplace_back( quiz );
        CInteraction::m_interface->Print_to_console( SUCCESS_IMPORT );
    } catch( const ios::failure & ex ) {
        CInteraction::m_interface->Get_ostream() << "Unexpected failure happened while reading from file." << endl;
    } catch( const logic_error & ex ) {
        CInteraction::m_interface->Get_ostream() << "Unexpected failure happened. Exception message: " << ex.what() << endl;
    }
}

void CInteraction::Export() {
    List();
    if( CInteraction::m_quizzes.empty() )
        return;

    // asks which quiz will be exported
    CInteraction::m_interface->Print_to_console( PROMPT_Q_N_EXPORT );
    size_t quiz_id = CInteraction::m_interface->Prompt_id( CInteraction::m_quizzes.size() );

    // incorrect quiz_id detected, end of export
    if( quiz_id == 0 )
        return;

    CInteraction::m_interface->Print_to_console( PROMPT_EXPORT_NAME );
    string filename = EXPORTS_DIR + CInteraction::m_interface->Prompt_string();

    // if the file already exists and user says that he doesn't want to overwrite it the export ends
    if( File_exists( filename )
        && ! CInteraction::m_interface->Prompt_yes_no( ERROR_EXPORT_DUPLICATION ) ) {
            return;
    }

    ofstream file;
    // may throw an exception further in the program, used for controlling unwanted input
    // or unexpected problems of reading from file
    file.exceptions( ios::failbit );

    try {
        file.open( filename );

        // sends the process of exporting further into the program
        CInteraction::m_quizzes[ quiz_id - 1 ].Export( file );

        file.close();

        CInteraction::m_interface->Get_ostream() << SUCCESS_EXPORT << filename << endl;
    } catch( const ios::failure & ex ) {
        CInteraction::m_interface->Get_ostream() << "Unexpected failure happened while writing into file." << endl;
    }
}

void CInteraction::List() {
    ostream & out = CInteraction::m_interface->Get_ostream();

    if( CInteraction::m_quizzes.empty() ) {
        out << "No quizzes found, try making one by typing \"create_quiz\"." << endl;
    } else {
        out << "List of quizzes:" << endl;
        for( size_t i = 0; i < CInteraction::m_quizzes.size(); ++i )
            out << setw( 4 ) << " " << i + 1 << ". " << CInteraction::m_quizzes[i].Name() << endl;
    }
}

void CInteraction::Help() {
    CInteraction::m_interface->Print_help();
}

bool CInteraction::File_exists( const string & path ) {
    ifstream file { path };
    if( file.is_open() )
        return true;

    return false;
}
