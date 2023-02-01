#include "CQuiz.h"
#include "CQSingleChoice.h"
#include "CQText.h"
#include "CQMultiChoice.h"
#include "messages.h"
#include "headers.h"
using namespace std;

CQuiz::CQuiz( const string & name, const shared_ptr<CInterface> & interface )
        : m_name( name ), m_interface( interface ), m_page_number( 1 ) {
}

/**
 * This method goes through created questions and asks for user answers.
 * It is using polymorphism by calling method User_answer() which is implemented differently in different classes.
 * @return itself
 */
CQuiz & CQuiz::Run_quiz() {
    ostream & out = m_interface->Get_ostream();

    for( size_t i = 1; i <= m_pages.size(); ++i ) {
        out << "Page number - " << i << endl;
        for( const auto & x : m_pages.at(i) ) {
            out << *x;
            x->User_answer();

            // if the question doesn't have the right answer it can be a "jump" question
            // so we need to know to which page to jump to depending on the user's answer
            if( x->Has_rules() ) {
                // only single-choice questions can have this functionality
                int jump_to = dynamic_cast<CQSingleChoice &>(*x).Jump_to_page();

                if( jump_to != 0 ) {
                    i = jump_to - 1;
                    out << "You have been redirected to another page according to your answer." << endl;
                    break;
                }
            }
        }
    }

    m_interface->Print_to_console( SUCCESS_RUN );
    m_is_filled = true;

    return *this;
}

CQuiz & CQuiz::Create_quiz() {
    m_interface->Print_to_console( PROMPT_Q_TYPES );

    // functions creates questions until the user input that he doesn't want to create another one
    while( true ) {
        m_interface->Print_to_console( PROMPT_CREATE_TYPE );
        string q_type = m_interface->Prompt_string();
        shared_ptr<CQuestion> question;

        // depending on question_type the method asks for further info and creates appropriate quiz question
        if( q_type == Q_TEXT ) {
            m_interface->Print_to_console( PROMPT_ANSWER_TEXT_Q );
            string answer_type  = m_interface->Prompt_string();

            // answer correction type must be from the supported ones
            if( answer_type != A_EXACT_MATCH && answer_type != A_SET && answer_type != A_CONTAINS ) {
                m_interface->Print_to_console( ERROR_A_TYPE );
                continue;
            }

            if( answer_type == COMMAND_QUIT ) {
                m_quit = true;
                break;
            }

            m_interface->Print_to_console( PROMPT_CREATE_Q );
            question = CQText{ m_interface, m_interface->Prompt_string(), q_type, answer_type }.Clone();

        } else if( q_type == Q_SINGLE_CHOICE ) {
            m_interface->Print_to_console( PROMPT_CREATE_Q );
            question = CQSingleChoice{ m_interface, m_interface->Prompt_string(), q_type }.Clone();

        } else if( q_type == Q_MULTI_CHOICE ) {
            m_interface->Print_to_console( PROMPT_CREATE_Q );
            question = CQMultiChoice{ m_interface, m_interface->Prompt_string(), q_type }.Clone();

        } else if( q_type == COMMAND_QUIT ) {
            m_quit = true;
            break;

        } else {
            m_interface->Print_to_console( ERROR_INVALID_Q );
            continue;
        }

        Add_question( question );

        // asks if the user want to create another question and if yes than
        // whether it should be on a new page or not
        if( m_interface->Prompt_yes_no( PROMPT_ADD_QUESTION ) == false )
            break;
        if( m_interface->Prompt_yes_no( PROMPT_CREATE_PAGE ) == true )
            m_page_number++;
    }

    if( m_quit == false ) {
        Add_rules();
        m_interface->Print_to_console( SUCCESS_CREATE );
    }

    return *this;
}

ifstream & CQuiz::Import( ifstream & file ) {
    string input;
    if( !getline( file, input ) || input != H_PAGES ) throw logic_error( "Incorrect header." );

    while( true ) {
        // we will iterate through until we hit the </pages> header, then it's the end of pages
        getline( file, input );
        if( input == H_PAGES_E )
            break;

        // if it's not the end of pages then we should get a new page imported
        if( input != H_PAGE ) throw logic_error( "Incorrect header." );

        // header <question> or </page> must continue, if it's <question> the we import questions
        // if not the it should be </page>
        while( getline( file, input ) && input == H_QUESTION ) {
            shared_ptr<CQuestion> question;
            string q_type, q_asked;
            getline( file, q_type );

            // we decide by the imported string which type of question will be imported and then import additional info
            if( q_type == Q_TEXT ) {
                if( !getline( file, q_asked ) || q_asked.empty() ) throw logic_error( "Empty line.");

                string answer_type;
                getline( file, answer_type );
                if( answer_type != A_SET && answer_type != A_CONTAINS && answer_type != A_EXACT_MATCH )
                    throw logic_error( "Incorrect correction type of text question." );

                question = CQText { m_interface, q_asked, q_type, answer_type }.Clone();

            } else if( q_type == Q_SINGLE_CHOICE ) {
                if( !getline( file, q_asked ) || q_asked.empty() ) throw logic_error( "Empty line.");
                question = CQSingleChoice { m_interface, q_asked, q_type }.Clone();

            } else if( q_type == Q_MULTI_CHOICE ) {
                if( !getline( file, q_asked ) || q_asked.empty() ) throw logic_error( "Empty line.");
                question = CQMultiChoice { m_interface, q_asked, q_type }.Clone();

            } else {
                throw logic_error( "Incorrect type of question." );
            }

            // stores the question in a map and calls input functions on them as well
            Import_question( file, question );

            if( !getline( file, input ) || input != H_QUESTION_E ) throw logic_error( "Incorrect header." );
        }

        // if it's a </page> than we just increase the number of page where we will store another questions
        // if not, there was something wrong with the file
        if( input == H_PAGE_E )
            m_page_number++;
        else
            throw logic_error( "Incorrect header." );
    }

    Import_rules( file );

    return file;
}

ofstream & CQuiz::Export( ofstream & file ) const {
    file << H_QUIZ << endl << Name() << endl;

    // exports pages and the every question individually
    file << H_PAGES << endl;
    for( const auto & page : m_pages ) {
        file << H_PAGE << endl;
        for( const auto & quiz : page.second ) {
            file << H_QUESTION << endl;
            quiz->Export( file );
            file << H_QUESTION_E << endl;
        }

        file << H_PAGE_E << endl;
    }
    file << H_PAGES_E << endl;

    // exports rules if some exist for the exported quiz
    file << H_RULES << endl;
    for( size_t i = 1; i <= m_pages.size(); ++i ) {
        for( size_t j = 0; j < m_pages.at(i).size(); ++j ) {
            auto quiz = m_pages.at(i)[j];
            if( quiz->Has_rules() ) {
                file << H_RULE << endl << i << endl << j + 1 << endl;
                // only single-choice question have rules
                dynamic_cast<CQSingleChoice &>(*quiz).Export_rules( file );
                file << H_RULE_E << endl;
            }
        }
    }
    file << H_RULES_E << endl;

    file << H_QUIZ_E << endl;
    return file;
}


int CQuiz::Count_correct() const {
    int correct = 0;
    for( const auto & page : m_pages )
        for( const auto & quiz : page.second )
            if( quiz->Is_correct() == true )
                correct++;

    return correct;
}

int CQuiz::Count_total() const {
    int sum = 0;
    for( const auto & page : m_pages )
        for( const auto & quiz : page.second )
            if( quiz->Has_rules() == false )
                sum++;

    return sum;
}

string CQuiz::Name() const {
    return m_name;
}

bool CQuiz::Quit_used() const {
    return m_quit;
}

bool CQuiz::Is_filled() const {
    return m_is_filled;
}

void CQuiz::Add_question( const shared_ptr<CQuestion> & question ) {
    question->Ref_answer();

    m_pages[m_page_number].emplace_back( question );
}

void CQuiz::Import_question( ifstream & file, const shared_ptr<CQuestion> & question ) {
    question->Import( file );

    m_pages[m_page_number].emplace_back( question );
}

void CQuiz::Add_rules() const {
    for( const auto & page : m_pages )
        for( const auto & quiz : page.second )
            if( quiz->Has_rules() == true )
                dynamic_cast<CQSingleChoice &>(*quiz).Add_rule( m_pages.size() );
}

void CQuiz::Import_rules( ifstream & file ) const {
    string input;
    if( !getline( file, input ) || input != H_RULES ) throw logic_error( "Incorrect header." );

    // loops through until the header </rules> is read
    while( getline( file, input ) && input != H_RULES_E ) {
        if( input != H_RULE ) throw logic_error( "Incorrect header." );

        size_t page_number = 0, question_number = 0;
        file >> page_number >> question_number;
        file.ignore();

        if( page_number < 1 || page_number > m_pages.size() )
            throw logic_error( "Invalid page number." );

        auto page = m_pages.at( page_number );

        if( question_number < 1 || question_number > page.size() )
            throw logic_error( "Invalid question number." );

        auto question = m_pages.at( page_number )[ question_number - 1 ];

        if( ! question->Has_rules() )
            throw logic_error( "Incorrect rule." );
        else
            dynamic_cast<CQSingleChoice &>( *question ).Import_rules( file, m_pages.size() );


        if( !getline( file, input ) || input != H_RULE_E ) throw logic_error( "Incorrect header." );
    }
}


