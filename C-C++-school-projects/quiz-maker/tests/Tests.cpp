#include "Tests.h"
#include "../src/CQMultiChoice.h"
#include "../src/CQSingleChoice.h"
#include "../src/CQText.h"
#include "../src/CQuiz.h"
#include "../src/CInteraction.h"
#include "../src/CInterface.h"
#include "../src/CApplication.h"
#include <memory>
#include <iostream>
#include <cassert>
#include <sstream>
#include <exception>

using namespace std;

void Tests::Run() const {
    Test_CQMultiChoice();
    Test_CQSingleChoice();
    Test_CQText();
    Test_CQuiz();
    Test_CInteraction();
}

void Tests::Test_CQMultiChoice() const {
    ostringstream oss;
    istringstream iss ( "4\n"
                        "A\n"
                        "B\n"
                        "C\n"
                        "D\n"
                        "4\n"
                        "n\n");

    shared_ptr<CInterface> interface = make_shared<CInterface>( CInterface { iss, oss } );

    CQMultiChoice test { interface, "Test question", Q_MULTI_CHOICE };
    test.Ref_answer();

    oss.str( "" );
    test.Print( oss );
    assert( oss.str() == "multi-choice question: Test question\n"
                         "1. A\n"
                         "2. B\n"
                         "3. C\n"
                         "4. D\n" );

    iss.str( "1\n"
             "y\n"
             "2\n"
             "n\n" );

    test.User_answer();
    assert( test.Is_correct() == false );
    assert( test.Has_rules() == false );

    CQMultiChoice test2 { interface, "Test question", Q_MULTI_CHOICE };
    iss.str( "" );
    iss.str( "16\n"
             "3\n"
             "A\n"
             "B\n"
             "C\n"
             "5\n"
             "2\n"
             "y\n"
             "3\n"
             "n\n" );
    test2.Ref_answer();

    oss.str( "" );
    test2.Print( oss );
    assert( oss.str() == "multi-choice question: Test question\n"
                         "1. A\n"
                         "2. B\n"
                         "3. C\n");
    iss.str( "2\n"
             "y\n"
             "3\n"
             "n\n" );

    test2.User_answer();
    assert( test2.Is_correct() == true );

    try {
        iss.str( "abeceda\n"
                 "y\n"
                 "3\n"
                 "y\n" );
        test2.User_answer();

        assert( "No exception thrown!" == nullptr );
    } catch( const ios::failure & ex ) {
    } catch( ... ) {
        assert( "Invalid exception thrown!" == nullptr );
    }
    iss.clear();

    iss.str( "2\n"
             "y\n"
             "1\n"
             "n\n" );

    test2.User_answer();
    assert( test2.Is_correct() == false );
}

void Tests::Test_CQSingleChoice() const {
    ostringstream oss;
    istringstream iss ( "4\n"
                        "A\n"
                        "B\n"
                        "C\n"
                        "D\n"
                        "y\n"
                        "4\n");

    shared_ptr<CInterface> interface = make_shared<CInterface>( CInterface { iss, oss } );

    CQSingleChoice test { interface, "Test question", Q_SINGLE_CHOICE };
    test.Ref_answer();

    oss.str( "" );
    test.Print( oss );
    assert( oss.str() == "single-choice question: Test question\n"
                         "1. A\n"
                         "2. B\n"
                         "3. C\n"
                         "4. D\n" );

    iss.str( "5\n"
             "2\n" );
    test.User_answer();

    assert( test.Is_correct() == false );

    iss.str( "4\n" );
    test.User_answer();

    assert( test.Is_correct() == true );
    assert( test.Has_rules() == false );

    CQSingleChoice test2 { interface, "Test question", Q_SINGLE_CHOICE };
    iss.str( "0\n"
             "4\n"
             "A\n"
             "B\n"
             "C\n"
             "D\n"
             "n\n" );
    test2.Ref_answer();

    oss.str( "" );
    test2.Print( oss );
    assert( oss.str() == "single-choice question: Test question\n"
                         "1. A\n"
                         "2. B\n"
                         "3. C\n"
                         "4. D\n" );

    assert( test2.Is_correct() == false );
    assert( test2.Has_rules() == true );

    try {
        iss.str( "abeceda\n"
                 "3" );
        test2.User_answer();

        assert( "No exception thrown!" == nullptr );
    } catch( const ios::failure & ex ) {
    } catch( ... ) {
        assert( "Invalid exception thrown!" == nullptr );
    }

    iss.clear();
    iss.str( "2\n" );
    test2.User_answer();
    assert( test2.Is_correct() == false );

    assert( test2.Jump_to_page() == 0 );

    iss.str( "0\n"
             "1\n"
             "4\n"
             "y\n"
             "2\n"
             "5\n"
             "n\n" );
    test2.Add_rule( 5 );
    assert( test2.Jump_to_page() == 5 );

    iss.str( "1\n" );
    test2.User_answer();
    assert( test2.Jump_to_page() == 4 );

    iss.str( "3\n" );
    test2.User_answer();
    assert( test2.Jump_to_page() == 0 );
}

void Tests::Test_CQText() const {
    ostringstream oss;
    istringstream iss ( "ref1\n"
                        "y\n"
                        "ref2\n"
                        "y\n"
                        "ref answ\n"
                        "y\n"
                        "ref2\n"
                        "n\n" );

    shared_ptr<CInterface> interface = make_shared<CInterface>( CInterface { iss, oss } );

    CQText test { interface, "Test question", Q_TEXT, A_SET };
    test.Ref_answer();

    oss.str( "" );
    test.Print( oss );
    assert( oss.str() == "text question: Test question\n"
                         "Required answer type: set\n" );

    assert( test.Has_rules() == false );

    iss.str( "ref1\n" );
    test.User_answer();
    assert( test.Is_correct() == true );
    iss.str( "ref answ\n" );
    test.User_answer();
    assert( test.Is_correct() == true );
    iss.str( "incorrect\n" );
    test.User_answer();
    assert( test.Is_correct() == false );

    CQText test2 { interface, "Test question", Q_MULTI_CHOICE, A_EXACT_MATCH };
    iss.str( "" );
    iss.str( "exact-ref\n" );
    test2.Ref_answer();

    oss.str( "" );
    test2.Print( oss );
    assert( oss.str() == "multi-choice question: Test question\n"
                         "Required answer type: exact-match\n" );

    assert( test2.Has_rules() == false );

    iss.str( "ref1\n" );
    test2.User_answer();
    assert( test2.Is_correct() == false );
    iss.str( "exact-ref \n" );
    test2.User_answer();
    assert( test2.Is_correct() == true );
    iss.str( "exat-ref\n" );
    test2.User_answer();
    assert( test2.Is_correct() == false );

    try {
        iss.str( "abeceda" );
        test2.User_answer();

        assert( "No exception thrown!" == nullptr );
    } catch( const ios::failure & ex ) {
    } catch( ... ) {
        assert( "Invalid exception thrown!" == nullptr );
    }
    iss.clear();

    CQText test3 { interface, "Test question", Q_TEXT, A_CONTAINS };
    iss.str( "contains-ref\n" );
    test3.Ref_answer();

    oss.str( "" );
    test3.Print( oss );
    assert( oss.str() == "text question: Test question\n"
                         "Required answer type: contains-word\n" );

    assert( test3.Has_rules() == false );

    iss.str( "ref1\n" );
    test3.User_answer();
    assert( test3.Is_correct() == false );
    iss.str( "message contains-ref x \n" );
    test3.User_answer();
    assert( test3.Is_correct() == true );
    iss.str( "Contains-ref xdx\n" );
    test3.User_answer();
    assert( test3.Is_correct() == false );

    try {
        iss.str( "abeceda" );
        test3.User_answer();

        assert( "No exception thrown!" == nullptr );
    } catch( const ios::failure & ex ) {
    } catch( ... ) {
        assert( "Invalid exception thrown!" == nullptr );
    }

    iss.clear();
}

void Tests::Test_CQuiz() const {
    ostringstream oss;
    istringstream iss;

    shared_ptr<CInterface> interface = make_shared<CInterface>( CInterface { iss, oss } );
    CQuiz test { "Name of the quiz", interface };
    assert( test.Count_correct() == 0 );
    assert( test.Count_total() == 0 );
    assert( test.Name() == "Name of the quiz" );
    assert( test.Quit_used() == false );
    assert( test.Is_filled() == false );

    try {
        iss.str( "abeceda" );
        test.Create_quiz();

        assert( "No exception thrown!" == nullptr );
    } catch( const ios::failure & ex ) {
    } catch( ... ) {
        assert( "Invalid exception thrown!" == nullptr );
    }

    iss.clear();
}

void Tests::Test_CInteraction() const {
    ostringstream oss;
    istringstream iss;

    shared_ptr<CInterface> interface = make_shared<CInterface>( CInterface { iss, oss } );
    CApplication app { interface };
    CInteraction test { interface };

    iss.str( "quit\n" );
    test.Run();
    assert( oss.str() == "Please input command, for list of commands type \"help\":\n"
                         "Thanks for using you local quiz maker! See you soon!\n" );

    oss.str( "" );
    iss.str( "list\n" );
    test.Run();
    assert( oss.str() == "Please input command, for list of commands type \"help\":\n"
                         "No quizzes found, try making one by typing \"create_quiz\".\n" );

    oss.str( "" );
    iss.str( "help\n" );
    test.Run();
    assert( oss.str() == "Please input command, for list of commands type \"help\":\n"
                         "List of usable commands:\n"
                         "    -> create_quiz - starts process of making a quiz\n"
                         "    -> export - exports quiz with specified id\n"
                         "    -> help - lists all commands and what they do\n"
                         "    -> import - imports quiz from file\n"
                         "    -> list - lists all available quizzes\n"
                         "    -> quit - quits the application\n"
                         "    -> result - counts result (correct answers) of quiz with specified id\n"
                         "    -> run_quiz - runs desired quiz from list of quizzes to answer the questions\n" );

    oss.str( "" );
    iss.str( "result\n" );
    test.Run();
    assert( oss.str() == "Please input command, for list of commands type \"help\":\n"
                         "No quizzes found, try making one by typing \"create_quiz\".\n" );

}
