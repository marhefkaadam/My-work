#pragma once
#include "CInterface.h"
#include <memory>
#include <fstream>

class CQuestion {
    public:
        CQuestion( const std::shared_ptr<CInterface> & interface, const std::string & question, const std::string & type );
        CQuestion( const CQuestion & ) = default;
        CQuestion & operator = ( const CQuestion & ) = default;
        virtual ~CQuestion() = default;

        virtual std::shared_ptr<CQuestion> Clone() const = 0;
        virtual CQuestion & Ref_answer() = 0;
        virtual CQuestion & User_answer() = 0;
        virtual std::ifstream & Import( std::ifstream & file ) = 0;

        /**
         * Method exports type of the question and what has been asked to the file from parameter.
         * @param file - export file stream
         * @returns parameter file
         */
        virtual std::ofstream & Export( std::ofstream & file ) const;

        virtual std::ostream & Print( std::ostream & out ) const;
        virtual bool Is_correct() const = 0;
        virtual bool Has_rules() const = 0;
        friend std::ostream & operator << ( std::ostream & out, const CQuestion & x );

    protected:
        /** Used for further communication and running of methods of commands which are used. */
        std::shared_ptr<CInterface> m_interface;
        std::string m_type;
        std::string m_question;
};



