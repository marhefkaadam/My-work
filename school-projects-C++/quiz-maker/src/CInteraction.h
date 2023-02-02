#pragma once
#include "CInterface.h"
#include "messages.h"
#include "CQuiz.h"
#include <memory>
#include <vector>
#include <map>
#include <functional>

class CInteraction {
    public:
        explicit CInteraction( const std::shared_ptr<CInterface> & interface );

        /**
         * Method tries to get a command from user depending on the command
         * runs the adequate method from map of static methods.
         * @return false if command quit was entered, true otherwise
         */
        bool Run() const;

    private:
        /** Used for further communication and running of methods of commands which are used. */
        static std::shared_ptr<CInterface> m_interface;
        static std::vector<CQuiz> m_quizzes;

        /**
         * Constant map of commands which can be used and static methods assigned to them.
         * Every command has it's own method except the "quit" command.
         */
        const std::map<std::string, std::function<void()>> COMMANDS = {
                { COMMAND_RUN_Q, Run_quiz },
                { COMMAND_CREATE_Q, Create_quiz },
                { COMMAND_RESULT_Q, Result },
                { COMMAND_IMPORT, Import },
                { COMMAND_EXPORT, Export },
                { COMMAND_LIST, List },
                { COMMAND_HELP, Help }
        };

        // for CLion purposes
        //constexpr static const char * const EXPORTS_DIR = "../semestral/marheada/examples/exports/";
        // for makefile purposes
        /** Constant which determines where the export and import files are store. Relative path. */
        constexpr static const char * const EXPORTS_DIR = "examples/exports/";

        /**
         * Starts the process of filling of a quiz. Asks for a id of a quiz which will be filled.
         * If there aren't any created quizzes than it doesn't start the filling process.
         */
        static void Run_quiz();

        /**
         * Starts the process of making of a quiz by asking for the quiz name
         * and then creating the instance of a quiz and running creation process on it.
         */
        static void Create_quiz();

        /**
         * Asks for a id of quiz which will be evaluated and if the quiz
         * was already filled at least once it evaluates how many points user earned and the success rate.
         * It only counts questions which have a correct answer.
         */
        static void Result();

        /**
         * Method prints files which can be imported from a directory from a constant.
         * Puts every filename in a directory of the constant into vector and then makes the user choose
         * which file will be imported and starts the process of importing of a quiz from the file.
         */
        static void Import();

        /**
         * Method exports quiz to directory specified in the constant. User can decide which quiz will be
         * exported and how the file where the quiz will be exported will be named. User can in the
         * future use the import command to import and fill the exported quiz.
         */
        static void Export();

        /**
         * Method lists all quizzes which exist in the program or says that no quizzes exist yet.
         */
        static void List();

        /** Prints help windows to the user. */
        static void Help();

        /**
         * Checks if file from path in parameter already exists.
         * @param path - file to check
         * @return true if file already exists, false otherwise
         */
        static bool File_exists( const std::string & path );
};
