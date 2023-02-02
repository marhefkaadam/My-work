#pragma once
#include "CQuestion.h"
#include <vector>
#include <set>

class CQMultiChoice : public CQuestion {
    public:
        CQMultiChoice( const std::shared_ptr<CInterface> & interface, const std::string & question, const std::string & type );

        CQMultiChoice( const CQMultiChoice & ) = default;
        CQMultiChoice & operator = ( const CQMultiChoice & ) = default;
        virtual ~CQMultiChoice() override = default;

        /**
         * Makes a clone of the shared_ptr of *this.
         * @returns shared_ptr<CQuestion>
         */
        virtual std::shared_ptr<CQuestion> Clone() const override;

        /**
         * Method asks for reference answers (can have more) which will be used to
         * determine whether the user inputted a correct answer or not.
         * Method also asks for options which the user can choose when filling a quiz.
         * @return itself
         */
        virtual CQuestion & Ref_answer() override;

        /**
         * Method clears previous users answers and then asks for the users answers to a question.
         * @return itself
         */
        virtual CQuestion & User_answer() override;

        /**
         * Imports options and a reference answers from a import file.
         * @param file - input file stream
         * @throws logic_error if the structure of file isn't correct
         * @return input file stream
         */
        virtual std::ifstream & Import( std::ifstream & file ) override;

        /**
         * Exports initial information, options and reference answers.
         * @param file - output file stream
         * @return output file stream
         */
        virtual std::ofstream & Export( std::ofstream & file ) const override;

        virtual std::ostream & Print( std::ostream & out ) const override;

        /**
         * Determines if the question was correctly answered by the user.
         * @return true if the users answer matches the all reference answers, false otherwise
         */
        virtual bool Is_correct() const override;

        /** Always returns false because text questions don't have this functionality. */
        virtual bool Has_rules() const override;

    private:
        /** Determines how many options can user create in a question. */
        constexpr static const int MAX_OPTIONS = 15;

        std::vector<std::string> m_options;
        std::set<size_t> m_ref_answer;
        std::set<size_t> m_user_answer;

        /**
         * Checks if the number is in boundaries <1, max>.
         * @param number - number controlled
         * @param max - maximum boundary
         * @return true if yes, false otherwise
         */
        bool Is_in_boundaries( size_t number, size_t max ) const;
};
