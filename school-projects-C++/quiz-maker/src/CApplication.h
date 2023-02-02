#pragma once
#include "CQuiz.h"
#include "CInterface.h"
#include "CInteraction.h"
#include <memory>

class CApplication {
    public:

        /**
         * Constructor adds interface which will be used throughout the program and
         * adds every command which is used in the program to a map of commands in interface.
         * @param interface - is used to communicate with user
         */
        explicit CApplication( const std::shared_ptr<CInterface> & interface );

        /** Implicit copy-const and operator are deleted because application can't be copied. */
        CApplication( const CApplication & ) = delete;
        CApplication & operator = ( const CApplication & ) = delete;
        ~CApplication() = default;

        /**
         * Starts the whole program, prints introduction and
         * while m_is_running is true, communicates with the user.
         */
        void Run();

        /**
         * Sets the m_is_running to false which immediately stops the whole program.
         */
        void Stop();

    private:
        /** Used for further communication and running of methods of commands which are used. */
        CInteraction m_interaction;
        /** Interface which is used for communication with the user. */
        std::shared_ptr<CInterface> m_interface;
        bool m_is_running;
};



