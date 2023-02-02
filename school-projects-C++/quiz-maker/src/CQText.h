#pragma once
#include "CQuestion.h"
#include <functional>
#include <vector>

class CQText : public CQuestion {
    public:
        CQText( const std::shared_ptr<CInterface> & interface, const std::string & question,
                const std::string & type, const std::string & answer_type );

        CQText( const CQText & ) = default;
        CQText & operator = ( const CQText & ) = default;
        virtual ~CQText() override = default;

        /**
         * Makes a clone of the shared_ptr of *this.
         * @returns shared_ptr<CQuestion>
         */
        virtual std::shared_ptr<CQuestion> Clone() const override;

        /**
         * Method asks for reference answer which will be used to determine whether the user
         * inputted a correct answer or not. The "set" type of correction can have more reference answers.
         * @return itself
         */
        virtual CQuestion & Ref_answer() override;

        /**
         * Method clears previous users answers and then asks for the users answer to a question.
         * @return itself
         */
        virtual CQuestion & User_answer() override;

        /**
         * Imports reference answer from a file and if the correction type is "set" then imports more of them.
         * @param file - input file stream
         * @throws logic_error if the structure of file isn't correct
         * @return input file stream
         */
        virtual std::ifstream & Import( std::ifstream & file ) override;

        /**
         * Exports type of a answer correction and reference answers to the file in specified format.
         * @param file - output file stream
         * @return output file stream
         */
        virtual std::ofstream & Export( std::ofstream & file ) const override;

        virtual std::ostream & Print( std::ostream & out ) const override;

        /**
         * Runs correction method according to variable m_answer_type because every answer type
         * has different correction style.
         * @return true if the users answer matches the reference answer, false otherwise
         */
        virtual bool Is_correct() const override;

        /** Always returns false because text questions don't have this functionality. */
        virtual bool Has_rules() const override;

    private:
        /** It is a type of how the users answer will be corrected, it can be exact-match, set or contains-word. */
        std::string m_answer_type;
        std::vector<std::string> m_ref_answer;
        std::string m_user_answer;

        /**
         * Determining if the user inputted an answer which is a reference one.
         * Users answer must be exactly the same as reference one from the first element of vector m_ref_answer.
         * @return true if it is, false otherwise
         */
        bool Correction_exact_match() const;

        /**
         * Determining if the user inputted an answer which is a reference one.
         * Users answer must be exactly the same as some from the set of reference answers.
         * @return true if it is, false otherwise
         */
        bool Correction_set() const;

        /**
         * Determining if the user inputted an answer which is a reference one.
         * Users answer must contain a word which is stored in first element of m_ref_answer vector.
         * @return true if it contains reference answer, false otherwise
         */
        bool Correction_contains() const;
};


