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
