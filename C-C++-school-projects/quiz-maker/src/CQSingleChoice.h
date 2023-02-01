#pragma once
#include "CQuestion.h"
#include <vector>

class CQSingleChoice : public CQuestion {
    public:
        CQSingleChoice( const std::shared_ptr<CInterface> & interface, const std::string & question, const std::string & type );

        CQSingleChoice( const CQSingleChoice & ) = default;
        CQSingleChoice & operator = ( const CQSingleChoice & ) = default;
        virtual ~CQSingleChoice() override = default;

        /**
         * Makes a clone of the shared_ptr of *this.
         * @returns shared_ptr<CQuestion>
         */
        virtual std::shared_ptr<CQuestion> Clone() const override;

        /**
         * Method asks for reference answer which will be used to determine whether the user
         * inputted a correct answer or not.
         * Method also asks for options which the user can choose when filling a quiz.
         * If the user says that this question won't have a correct answer than it's the "jump" question
         * and then it won't have a reference answer.
         * @return itself
         */
        virtual CQuestion & Ref_answer() override;

        /**
         * Method clears previous users answer and then asks for the users answer to a question.
         * @return itself
         */
        virtual CQuestion & User_answer() override;

        /**
         * Imports options and a reference answer from a
         * file if it is noted without rules in the import file.
         * @param file - input file stream
         * @throws logic_error if the structure of file isn't correct
         * @return input file stream
         */
        virtual std::ifstream & Import( std::ifstream & file ) override;

        /**
         * Exports initial information, options and
         * whether the question has rules or not to the file in specified format.
         * @param file - output file stream
         * @return output file stream
         */
        virtual std::ofstream & Export( std::ofstream & file ) const override;

        virtual std::ostream & Print( std::ostream & out ) const override;

        /**
         * Determines if the question was correctly answered by the user.
         * @return true if the users answer matches the reference answer,
         * false this question has rules (it's the "jump" type) or the answers doen't match
         */
        virtual bool Is_correct() const override;

        /**
         * Public getter of variable m_has_rules. If it is true than this question is specified as a "jump" question.
         * @return true/false
         */
        virtual bool Has_rules() const override;

        /**
         * Method asks for rules for each option where should thw quiz jump depending on the users answer.
         * @param pages_amount - how many pages current quiz has
         */
        void Add_rule( size_t pages_sum );

        /**
         * Method finds rules for the users answer and returns them. If rule wasn't found returns 0.
         * @return where should we jump while filling up the quiz depending on the answer
         */
        size_t Jump_to_page() const;

        /**
         * Method export rules in wanted format to a file.
         * @param file - output file stream
         * @return output file stream
         */
        std::ofstream & Export_rules( std::ofstream & file ) const;

        /**
         * Method imports rules for this question and checks if they are logically correct.
         * @param file - input file stream
         * @param pages_amount
         * @return input file stream
         */
        std::ifstream & Import_rules( std::ifstream & file, size_t max_page );

    private:
        /** Determines how many options can user create in a question. */
        constexpr static const int MAX_OPTIONS = 15;

        struct SRule {
            size_t m_option;
            size_t m_jump_to_page;

            SRule() = default;

            SRule( size_t option_rule, size_t jump_to )
                    : m_option( option_rule ), m_jump_to_page( jump_to ) {
            }
        };

        std::vector<std::string> m_options;
        size_t m_ref_answer, m_user_answer;
        bool m_has_rules;
        std::vector<SRule> m_rules;
};
