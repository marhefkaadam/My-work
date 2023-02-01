#pragma once
#include "CQuestion.h"
#include <map>
#include <vector>

class CQuiz {
    public:
        CQuiz( const std::string & name, const std::shared_ptr<CInterface> & interface );

        /**
         * Iterates through every page and every question in the quiz and runs the method
         * "User_answer" on them which asks for user's answers for the question. This is a polymorphic
         * call. If the question has rules (single-choice can) than it checks if it should jump to
         * another page depending on the user's answer.
         * @return itself
         */
        CQuiz & Run_quiz();

        /**
         * Method creates question of a certain type inputted by the user and the runs the creation
         * process on the created question which asks the user for options and reference
         * answer for the question. If user inputs "quit" the creation was exited so the m_quit is set to true.
         * The method uses polymorphic calls on questions.
         * @return itself
         */
        CQuiz & Create_quiz();

        /**
         * Method parses information from the file and creates appropriate questions depending on the information.
         * The method uses polymorphic calls on questions.
         * Method uses constants from file headers.h to keep the format correct.
         * @param file - file from which the info is being read
         * @throws logic_error if the structure of file isn't correct
         * @return input file stream
         */
        std::ifstream & Import( std::ifstream & file );

        /**
         * Method exports quiz information in the wanted format to file stream from parameter.
         * The method uses polymorphic calls on questions.
         * Method uses constants from file headers.h to create the wanted format.
         * @param file - file where the quiz is exported
         * @return output file stream
         */
        std::ofstream & Export( std::ofstream & file ) const;

        /**
         * Method count how many question did user answer correctly.
         * @return amount of correctly answered questions
         */
        int Count_correct() const;

        /**
         * Method count how many questions which have a correct answer quiz have.
         * The questions which don't have rules have some correct answer (are not "jump" type).
         * @return this sum
         */
        int Count_total() const;

        /** Public getter of a name of a quiz. */
        std::string Name() const;

        /** Public getter of a variable m_quit. */
        bool Quit_used() const;

        /** Public getter of a variable m_is_filled.
         * @return true/false
         */
        bool Is_filled() const;

    private:
        std::string m_name;

        /** Used for further communication and running of methods of commands which are used. */
        std::shared_ptr<CInterface> m_interface;
        std::map<size_t, std::vector<std::shared_ptr<CQuestion>>> m_pages;

        /** The m_page_number is used when adding questions to pages to know to which page to add the question. */
        size_t m_page_number, m_is_filled = false;
        /** If user used quit command in the creation process than it gets switched to true. */
        bool m_quit = false;

        /**
         * Adds question from the parameter to the accurate page depending on variable m_page_number.
         * Uses polymorphic call to get the options and reference answer for a question.
         * @param question - question added to the quiz
         */
        void Add_question( const std::shared_ptr<CQuestion> & question );

        /**
         * Adds question from the parameter to the accurate page depending on variable m_page_number.
         * Uses polymorphic call to import the options and reference answer from a file.
         * @param file - input file stream
         * @param question - question added to the quiz
         */
        void Import_question( std::ifstream & file, const std::shared_ptr<CQuestion> & question );

        /**
         * Loops through the pages and questions and calls
         * an Add_rule method on a question which should have them.
         */
        void Add_rules() const;

        /**
         * Loops through the pages and questions and tries to import rules from the file
         * to appropriate question if some rules exists.
         * @throws logic_error if the structure of file isn't correct
         */
        void Import_rules( std::ifstream & file ) const;
};



