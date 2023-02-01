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
