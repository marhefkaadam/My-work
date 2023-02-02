#pragma once
#include <map>
#include <string>
#include <iostream>

class CInterface {
    public:
        /**
         * Stores streams used for communicating with the user and sets that
         * for some stream indicators (badbit, eofbit) it will throw an exception.
         * @param in - input stream which takes input from the user
         * @param out - output stream which prints output to the user
         */
        CInterface( std::istream & in, std::ostream & out );

        /**
         * Function adds command and it's help message into the map of used commands.
         * @param command - usable command
         * @param help - explanation of the command
         * @return itself
         */
        CInterface & Add_command( const std::string & command, const std::string & help );

        /**
         * Method reads input and determines if some usable command was entered.
         * Loops through until correct command is inputted.
         * @return inputted command
         */
        std::string Prompt_command() const;

        /**
         * Method reads input for a numeric input where the number can be from a range <1, max_id>.
         * If the number is not in the bounds it prints error message to the user.
         * @param max_id - upper limit of correct numeric input
         * @return 0 if max_id == 0 or input is not in the bounds, inputted id otherwise.
         */
        std::size_t Prompt_id( size_t max_id ) const;

        /**
         * Method reads the whole line and trims the read line
         * from whitespaces from the start and end of a string.
         * The returned string can't be empty.
         * @return trimmed string of one inputted line
         */
        std::string Prompt_string() const;

        /**
         * Method prints message to the user and detect if the user answered with y/Y/yes/Yes.
         * If user answered otherwise the method will return false
         * @param message - message printed to user
         * @return true if yes was inputted, false otherwise
         */
        bool Prompt_yes_no( const std::string & message ) const;

        /** Prints message from parameter to the user. */
        void Print_to_console( const std::string & message ) const;

        std::ostream & Get_ostream() const;

        /** Method clears input stream until it hits \n and also clear state bits of a stream. */
        void Clear_istream() const;

        /** Method prints all usable commands and it's explanation to the user. */
        void Print_help() const;

    private:
        std::istream & m_in;
        std::ostream & m_out;
        /** Map of usable commands and their explanations for command "help". */
        std::map<std::string, std::string> m_commands;

        /** Method trims string from parameter for excess whitespaces in front and back of the string. */
        void Trim_string( std::string & str ) const;
};



