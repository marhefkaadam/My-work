from functions import *


class Robot:
    def __init__(self, client_connection):
        self.x = None
        self.y = None
        self.facing = UNINITIALIZED
        self.head_to = UNINITIALIZED
        self.client_connection = client_connection

    def get_coords(self):
        while True:
            self.client_connection.sendall(SERVER_TURN_LEFT)
            message = receive_message(self.client_connection, CLIENT_OK_LEN)
            (self.x, self.y) = parse_ok_message(message)
            self.facing = UNINITIALIZED
            (move_x, move_y) = self.move_robot(SERVER_MOVE)

            facing_found = self.get_facing(move_x, move_y)
            (self.x, self.y) = move_x, move_y

            if facing_found:
                break

    def get_facing(self, new_x, new_y):
        if new_x > self.x:
            self.facing = RIGHT
        elif new_x < self.x:
            self.facing = LEFT
        elif new_y > self.y:
            self.facing = UP
        elif new_y < self.y:
            self.facing = DOWN
        else:
            return False
        return True

    def set_head_to(self, coord):
        if coord == 'x':
            if self.x < 0:
                self.head_to = RIGHT
            elif self.x > 0:
                self.head_to = LEFT
            else:
                self.head_to = STRAIGHT
        if coord == 'y':
            if self.y < 0:
                self.head_to = UP
            elif self.y > 0:
                self.head_to = DOWN
            else:
                self.head_to = STRAIGHT

    def move_robot(self, command):
        self.client_connection.sendall(command)
        message = receive_message(self.client_connection, CLIENT_OK_LEN)

        if command == SERVER_TURN_LEFT:
            self.facing = (self.facing - 1) % 4
        if command == SERVER_TURN_RIGHT:
            self.facing = (self.facing + 1) % 4

        return parse_ok_message(message)

    def get_to_y_0(self):
        # set head to for going around an obstacle, x for heading in the x axis
        self.set_head_to('x')

        turn_times = 0
        negative = False
        # if y < 0 we want to go UP to hit the 0 coordinate
        if self.y < 0:
            if self.facing - UP < 0:
                negative = True
            turn_times = abs(self.facing - UP)

        # if y > 0 we want to go DOWN to hit the 0 coordinate
        if self.y > 0:
            if self.facing - DOWN < 0:
                negative = True
            turn_times = abs(self.facing - DOWN)

        for i in range(turn_times):
            if negative:
                self.move_robot(SERVER_TURN_RIGHT)
            else:
                self.move_robot(SERVER_TURN_LEFT)

        while self.y != 0:
            (new_x, new_y) = self.move_robot(SERVER_MOVE)

            # the robot didn't move -> obstacle hit
            if self.x == new_x and self.y == new_y:
                # True returned when while getting around obstacle the robot got to y == 0
                if self.get_around_obstacle('y'):
                    return

                # setting heading to for going around an obstacle, x for heading in the x axis
                self.set_head_to('x')
            else:
                (self.x, self.y) = new_x, new_y


    def get_to_x_0(self):
        # set head to for going around an obstacle, y for heading in the y axis
        self.set_head_to('y')

        turn_times = 0
        negative = False
        # if x < 0 we want to go RIGHT to hit the 0 coordinate
        if self.x < 0:
            if self.facing - RIGHT < 0:
                negative = True
            turn_times = abs(self.facing - RIGHT)
        # if x > 0 we want to go LEFT to hit the 0 coordinate
        if self.x > 0:
            if self.facing - LEFT < 0:
                negative = True
            turn_times = abs(self.facing - LEFT)

        # negative = True -> clockwise, negative = False -> anticlockwise
        for i in range(turn_times):
            if negative:
                self.move_robot(SERVER_TURN_RIGHT)
            else:
                self.move_robot(SERVER_TURN_LEFT)

        while self.x != 0:
            (new_x, new_y) = self.move_robot(SERVER_MOVE)

            # the robot didn't move -> obstacle hit
            if self.x == new_x and self.y == new_y:
                # True returned when while getting around obstacle the robot got to x == 0
                if self.get_around_obstacle('x'):
                    return

                # setting heading to for going around an obstacle, y for heading in the y axis
                self.set_head_to('y')

            else:
                (self.x, self.y) = new_x, new_y

    # Attribute get_to_0 means that the True will be returned only if the obstacle is being
    # overstep while getting to y or x 0 coordinate
    def get_around_obstacle(self, get_to_0):
        # RIGHT MOVE LEFT MOVE MOVE
        around_commands = (SERVER_TURN_RIGHT, SERVER_MOVE, SERVER_TURN_LEFT, SERVER_MOVE, SERVER_MOVE)
        if self.head_to == RIGHT or self.head_to == UP:
            for command in around_commands:
                (self.x, self.y) = self.move_robot(command)
                if (self.x == 0 and get_to_0 == 'x' and self.y == 0) or (self.y == 0 and get_to_0 == 'y'):
                    return True

        # LEFT MOVE RIGHT MOVE MOVE
        around_commands = (SERVER_TURN_LEFT, SERVER_MOVE, SERVER_TURN_RIGHT, SERVER_MOVE, SERVER_MOVE)
        if self.head_to == LEFT or self.head_to == DOWN:
            for command in around_commands:
                (self.x, self.y) = self.move_robot(command)
                if (self.x == 0 and get_to_0 == 'x' and self.y == 0) or (self.y == 0 and get_to_0 == 'y'):
                    return True

        # LEFT MOVE RIGHT MOVE MOVE RIGHT MOVE LEFT
        around_commands = (SERVER_TURN_LEFT, SERVER_MOVE, SERVER_TURN_RIGHT, SERVER_MOVE, SERVER_MOVE, SERVER_TURN_RIGHT, SERVER_MOVE, SERVER_TURN_LEFT)
        if self.head_to == STRAIGHT:
            for command in around_commands:
                (self.x, self.y) = self.move_robot(command)
                if (self.x == 0 and get_to_0 == 'x' and self.y == 0) or (self.y == 0 and get_to_0 == 'y'):
                    return True

        return False

    def get_message(self):
        self.client_connection.sendall(SERVER_PICK_UP)
        message = receive_message(self.client_connection, CLIENT_MESSAGE_LEN)
        print("Received message:", message)

        self.client_connection.sendall(SERVER_LOGOUT)
import socket
from threading import Thread
from Robot import *


class TCPCommunication:
    def __init__(self):
        self.threads = []

    def accept_communication(self, new_socket):
        while True:
            # print("starting")
            client_connection, client_address = new_socket.accept()
            client_connection.settimeout(TIMEOUT)

            thread = Thread(target=self.communicate, args=(new_socket, client_connection))
            thread.start()
            # print("starting thread, connection from", client_address)
            self.threads.append(thread)

    def communicate(self, new_socket, client_connection):
        print("connection established")
        robot = Robot(client_connection)

        try:
            # AUTHORIZATION
            if not authorization(client_connection):
                client_connection.close()
                return
            # NAVIGATION TO [0,0] & RECHARGING
            robot.get_coords()
            robot.get_to_y_0()
            robot.get_to_x_0()
            robot.get_message()

        except BufferError:
            print("buffer error")
            client_connection.sendall(SERVER_SYNTAX_ERROR)
        except ValueError:
            print("value error")
            client_connection.sendall(SERVER_SYNTAX_ERROR)
        except RuntimeError:
            print("logic error")
            client_connection.sendall(SERVER_LOGIC_ERROR)
        except socket.timeout:
            print("timeout")
        except Exception as err:
            print("error:", format(err).encode('utf-8'))
        finally:
            print("closing connection")

            client_connection.close()
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
from constants import *


def receive_message(client_connection, wanted_length, recharging=False):
    message = ''
    length = 0
    prev_symbol = ''

    # take at least length of recharging because that's not an expected message
    expected_length = 0
    expected_length = max(wanted_length, CLIENT_RECHARGING_LEN)

    while True:
        symbol = client_connection.recv(1).decode('utf-8')
        length += 1
        message += symbol

        if symbol == END_B and prev_symbol == END_A:
            if message == CLIENT_RECHARGING:
                print("recharging")
                client_recharging(client_connection)
                message = ''
                length = 0
                prev_symbol = ''
                continue

            if message == CLIENT_FULL_POWER and recharging is False:
                raise RuntimeError

            if length > wanted_length:
                raise BufferError

            return message[:-2]

        if length >= expected_length:
            raise BufferError

        prev_symbol = symbol


def parse_ok_message(message):
    if not message[-1].isnumeric():
        raise BufferError

    split_mess = message.split(' ')
    if len(split_mess) != 3 or split_mess[0] != CLIENT_OK:
        raise BufferError

    # if the value is not an integer -> ValueError raised
    x = int(split_mess[1])
    y = int(split_mess[2])

    return x, y


def authorization(client_connection):
    # print("receive_mess")
    client_username = receive_message(client_connection, CLIENT_USERNAME_LEN)
    # print("received client username")
    client_connection.sendall(SERVER_KEY_REQUEST)
    client_key_id = receive_message(client_connection, CLIENT_KEY_ID_LEN)

    if not client_key_id.isnumeric():
        raise BufferError

    if int(client_key_id) < 0 or int(client_key_id) > 4:
        client_connection.sendall(SERVER_KEY_OUT_OF_RANGE_ERROR)
        return False

    # get sum od ASCII values and count server hash
    ascii_sum = 0
    for el in client_username:
        ascii_sum += (ord(el))

    server_key_arr = (SERVER_KEY_0, SERVER_KEY_1, SERVER_KEY_2, SERVER_KEY_3, SERVER_KEY_4)
    client_key_arr = (CLIENT_KEY_0, CLIENT_KEY_1, CLIENT_KEY_2, CLIENT_KEY_3, CLIENT_KEY_4)

    ascii_hash = (ascii_sum * 1000) % 65536
    server_hash = (ascii_hash + server_key_arr[int(client_key_id)]) % 65536

    server_confirmation = str(server_hash) + END_A + END_B
    client_connection.sendall(server_confirmation.encode('utf-8'))

    client_hash_recv = receive_message(client_connection, CLIENT_CONFIRMATION_LEN)

    if not client_hash_recv.isnumeric() or len(client_hash_recv) + 2 > CLIENT_CONFIRMATION_LEN:
        raise BufferError

    client_hash = (ascii_hash + client_key_arr[int(client_key_id)]) % 65536
    # print("cl_hash_recv", client_hash_recv, "cl_hash_comp", client_hash)
    if client_hash != int(client_hash_recv):
        client_connection.sendall(SERVER_LOGIN_FAILED)
        return False

    client_connection.sendall(SERVER_OK)
    return True


def client_recharging(client_connection):
    client_connection.settimeout(TIMEOUT_RECHARGING)

    # True to indicate that FULL_POWER message is expected
    message = receive_message(client_connection, CLIENT_FULL_POWER_LEN, True)
    # expecting CLIENT_FULL_POWER but got different message
    if message != CLIENT_FULL_POWER[:-2]:
        raise RuntimeError

    client_connection.settimeout(TIMEOUT)
from TCPCommunication import *


def main():
    tcp = TCPCommunication()
    soc = socket.socket(socket.AF_INET, socket.SOCK_STREAM)

    # bind socket to the address
    print("hostname:", socket.gethostbyname(socket.gethostname()), "port:", PORT)
    address = (socket.gethostbyname(socket.gethostname()), PORT)
    soc.bind(address)

    # print("listening on socket")
    soc.listen(1)
    # print("got packet")
    tcp.accept_communication(soc)


if __name__ == '__main__':
    main()
