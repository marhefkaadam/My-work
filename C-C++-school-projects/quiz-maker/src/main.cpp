#include "CApplication.h"
#include <exception>
using namespace std;

int main() {
    try {
        CInterface interface { cin, cout };
        CApplication app { make_shared<CInterface>( interface ) };
        app.Run();

        return 0;
    } catch( const ios::failure & ex ) {
        if( cin.eof() ) {
            cerr << "EOF reached." << endl;
            return 1;
        }

        cerr << "Unexpected error occurred. Error message: " << ex.what() << endl;
        cerr << "Program finished with exit code " << ex.code() << endl;
        return 2;
    } catch( const runtime_error & ex ) {
        cerr << "Runtime error: " << ex.what() << endl;
        return 3;
    }
}
