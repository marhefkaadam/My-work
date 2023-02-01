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
