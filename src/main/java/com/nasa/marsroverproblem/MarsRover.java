package com.nasa.marsroverproblem;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor(staticName = "with")
class MarsRover {
    private String direction;
    private Long positionX;
    private Long positionY;

    public void move(String command) {
        switch (command) {
            case "L":
                moveLeft();
                break;
            case "R":
                moveRight();
                break;
            case "M":
                moveForward();
                break;
        }
    }

    public String getCurrentPosition() {
        return getDirection() + " " + getPositionX() + " " + getPositionY();
    }

    private void moveForward() {
        switch (this.direction) {
            case "N":
                setPositionY(this.positionY + 1);
                break;
            case "E":
                setPositionX(this.positionX + 1);
                break;
            case "S":
                setPositionY(this.positionY - 1);
                break;
            case "W":
                setPositionX(this.positionX - 1);
                break;
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
