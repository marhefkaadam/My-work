# Server for communication with client using TCP protocol

This task was part of an assessment for Computer Networks subject on my university. It tries to mimic communication through TCP protocol which can result in communication errors or partition of messages.

### Task
Create a server for automatic control of remote robots. The robots themselves log in to the server and it guides them to the center of the coordinate system. For testing purposes, each robot starts at random coordinates and tries to reach the coordinate [0,0]. At the target coordinate, the robot must pick up the secret. On the way to the goal, the robots may encounter obstacles that they must bypass. The server can navigate multiple robots at the same time and should implement a flawless communication protocol.

#### **Definition of communication**
Communication between the server and the robots is realized by a fully text protocol. Each command is terminated by a pair of special symbols "\a\b". (These are the two characters '\a' and '\b'.) The server must follow the communication protocol in detail, but must allow for imperfect bot firmwares (see the Special Situations section). Commands are defined in file **constants.py**.

#### **Authentication**
The server and the client both know five pairs of authentication keys based on which communication must first be authenticated.

#### **Movement of robots**
The robot can only move straight (SERVER_MOVE) and is able to turn right (SERVER_TURN_RIGHT) and left (SERVER_TURN_LEFT) in place. After each movement command, it sends a confirmation (CLIENT_OK), which also includes the current coordinate. The position of the robot is not known to the server at the beginning of the communication.

After the robot reaches the target coordinate [0,0], it tries to pick up the secret message (SERVER_PICK_UP message). If the robot is asked to pick up a message and is not located at the target coordinate, the robot self-destructs and communication with the server is interrupted. When trying to pick up at the target coordinate, the robot responds with a CLIENT_MESSAGE message. The server must respond to this message with a SERVER_LOGOUT message.

#### **Tester**
An image of the Tiny Core Linux operating system, which includes a homework tester, is ready for testing. The image is compatible with VirtualBox. Download and unzip the image. Run the resulting file in VirtualBox. After starting and booting, a shell is immediately available. The tester is started with the tester command:
```bash
tester <port_number> <remote_address> [test_numbers]
```
If no test number is entered, all tests will be run one after the other. Tests can also be run individually. The following sample runs tests 2, 3, and 8:
```bash
tester 3999 10.0.2.2 2 3 8 | less
```
Needed files to run tests in tester environment:  
- tester/BI-PSI_tester_2021_vX.ova - virtual image with a tester for VirtualBox  
- tester/psi-tester-2021-vX_x64.bz2 - version for linux 64-bit 
- X is the number of version of the tester