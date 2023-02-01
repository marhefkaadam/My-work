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
