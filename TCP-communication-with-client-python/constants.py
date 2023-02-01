# COMMUNICATION
IP_ADDRESS = '172.17.160.1'
PORT = 2222

# SERVER CONSTANTS
#SERVER_CONFIRMATION = ''  # <16-bitove cislo v decimalni notaci>\a\b	#Zprava s potvrzovacim kodem. Muze obsahovat maximalne 5 cisel a ukoncovaci sekvenci \a\b.
SERVER_MOVE = '102 MOVE\a\b'.encode('utf-8')  # Prikaz pro pohyb o jedno pole vpred
SERVER_TURN_LEFT = '103 TURN LEFT\a\b'.encode('utf-8')  # Prikaz pro otoceni doleva
SERVER_TURN_RIGHT = '104 TURN RIGHT\a\b'.encode('utf-8')  # Prikaz pro otoceni doprava
SERVER_PICK_UP = '105 GET MESSAGE\a\b'.encode('utf-8')  # Prikaz pro vyzvednuti zpravy
SERVER_LOGOUT = '106 LOGOUT\a\b'.encode('utf-8')  # Prikaz pro ukonceni spojeni po uspesnem vyzvednuti zpravy
SERVER_OK = '200 OK\a\b'.encode('utf-8')  # Kladne potvrzeni
SERVER_KEY_REQUEST = '107 KEY REQUEST\a\b'.encode('utf-8')  # Zadost serveru o Key ID pro komunikaci
SERVER_LOGIN_FAILED = '300 LOGIN FAILED\a\b'.encode('utf-8')  # Nezdarena autentizace
SERVER_SYNTAX_ERROR = '301 SYNTAX ERROR\a\b'.encode('utf-8')  # Chybna syntaxe zpravy
SERVER_LOGIC_ERROR = '302 LOGIC ERROR\a\b'.encode('utf-8')  # Zprava odeslana ve spatne situaci
SERVER_KEY_OUT_OF_RANGE_ERROR = '303 KEY OUT OF RANGE\a\b'.encode('utf-8')  # Key ID neni v ocekavanem rozsahu
END_A = '\a'
END_B = '\b'

# CLIENT CONSTANTS
CLIENT_RECHARGING = 'RECHARGING\a\b'  # Robot se zacal dobijet a prestal reagovat na zpravy.
CLIENT_FULL_POWER = 'FULL POWER\a\b'  # Robot doplnil energii a opet prijima prikazy.
CLIENT_OK = 'OK'

CLIENT_USERNAME_LEN = 20
CLIENT_KEY_ID_LEN = 5
CLIENT_CONFIRMATION_LEN = 7
CLIENT_OK_LEN = 12
CLIENT_RECHARGING_LEN = 12
CLIENT_FULL_POWER_LEN = 12
CLIENT_MESSAGE_LEN = 100

TIMEOUT = 1
TIMEOUT_RECHARGING = 5

SERVER_KEY_0 = 23019
CLIENT_KEY_0 = 32037
SERVER_KEY_1 = 32037
CLIENT_KEY_1 = 29295
SERVER_KEY_2 = 18789
CLIENT_KEY_2 = 13603
SERVER_KEY_3 = 16443
CLIENT_KEY_3 = 29533
SERVER_KEY_4 = 18189
CLIENT_KEY_4 = 21952

UNINITIALIZED = -1
UP = 0
RIGHT = 1
DOWN = 2
LEFT = 3
STRAIGHT = 5
