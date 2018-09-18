package com.nasa.marsroverproblem;

import lombok.*;

import java.util.ArrayList;

@EqualsAndHashCode
@ToString
@Setter
@Getter
@AllArgsConstructor(staticName = "with")
class MarsRover {
    private String direction;
    private int positionX;
    private int positionY;

    public String move(String commands) {
        String[] commandsParse = commands.split("");

        for (String command : commandsParse) {
            if (command.equals("L")) {
                switch (this.direction) {
                    case "N":
                        setDirection("W");
                        break;
                    case "W":
                        setDirection("S");
                        break;
                    case "S":
                        setDirection("E");
                        break;
                    case "E":
                        setDirection("N");
                        break;
                }
            }

            if (command.equals("R")) {
                switch (this.direction) {
                    case "N":
                        setDirection("E");
                        break;
                    case "E":
                        setDirection("S");
                        break;
                    case "S":
                        setDirection("W");
                        break;
                    case "W":
                        setDirection("N");
                        break;
                }
            }

            if (command.equals("M")) {
                switch (this.direction) {
                    case "N":
                        setPositionY(this.positionY+1);
                        break;
                    case "E":
                        setPositionX(this.positionX+1);
                        break;
                    case "S":
                        setPositionY(this.positionY-1);
                        break;
                    case "W":
                        setPositionX(this.positionX-1);
                        break;
                }
            }
        }

        return getDirection() +" "+ getPositionX() +" "+ getPositionY();
    }
}
