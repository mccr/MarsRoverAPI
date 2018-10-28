package com.nasa.marsroverproblem;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor(staticName = "with")
class MarsRover {
    private String direction;
    private Long positionX;
    private Long positionY;

    public String move(String commands) {
        String[] commandsParse = commands.split("");

        for (String command : commandsParse) {
            switch (command) {
                case "L":
                    moveLeft();
                    break;
                case "R":
                    moveRight();
                    break;
                default:
                    moveForward();
                    break;
            }
        }

        return getCurrentPosition();
    }

    private String getCurrentPosition() {
        return getDirection() +" "+ getPositionX() +" "+ getPositionY();
    }

    private void moveForward() {
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

    public void setPositionX(Long positionX) {
        if (positionX > 5 || positionX < 0) {
            throw new RuntimeException("you fall off");
        } else {
            this.positionX = positionX;
        }
    }

    public void setPositionY(Long positionY) {
        if (positionY > 5 || positionY < 0) {
            throw new RuntimeException("you fall off");
        } else {
            this.positionY = positionY;
        }
    }

    private void moveRight() {
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

    private void moveLeft() {
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
}
