import socket

IP = '127.0.0.1'
PORT = 6666
def main():
    # open socket with the server
    my_socket = socket.socket(socket.AF_INET, socket.SOCK_STREAM)
    my_socket.connect((IP, PORT))

    request = raw_input("Please enter command:\n")
    print my_socket.send(request)
    # rec = my_socket.recv(1024)
    # print rec
    my_socket.close()

if __name__ == '__main__':
    main()
