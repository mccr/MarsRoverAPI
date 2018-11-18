package com.nasa.marsroverproblem;

import com.nasa.marsroverproblem.exceptions.CannotDeployOutOfBoundariesException;
import com.nasa.marsroverproblem.exceptions.RoverDontWantToDieException;

public class GroundControl {

    private static final String CANNOT_DEPLOY_OUTSIDE_THE_GRID_BOUNDARIES = "cannot deploy outside the grid boundaries";
    private Plateau plateau;
    private MarsRover marsRover;

    public String createNewPlateau(Long size) {
        plateau = new Plateau(size > 0 ? size : 5);
        return plateau.getSize().toString();
    }

    public String deployMarsRover(String direction, Long positionX, Long positionY) {

        if (checkDeployBoundaries(positionX, positionY))
            throw new CannotDeployOutOfBoundariesException(CANNOT_DEPLOY_OUTSIDE_THE_GRID_BOUNDARIES);
        else
            marsRover = MarsRover.with(direction, positionX, positionY);

        return "New Mars Rover deployed in " + marsRover.getCurrentPosition();
    }

    public String processCommands(String commands) {
        String[] commandsParse = commands.split("");

        for (String command : commandsParse) {
            if (command.matches("M")) checkBoundaries();
            else marsRover.move(command);
        }
        return marsRover.getCurrentPosition();
    }

    public void checkBoundaries() {
        Long targetPosition;

        switch (marsRover.getDirection()) {
            case "N":
                targetPosition = marsRover.getPositionY() + 1;
                moveMarsRover(targetPosition > plateau.getSize());
                break;
            case "E":
                targetPosition = marsRover.getPositionX() + 1;
                moveMarsRover(targetPosition > plateau.getSize());
                break;
            case "S":
                targetPosition = marsRover.getPositionY() - 1;
                moveMarsRover(targetPosition < 0);
                break;
            case "W":
                targetPosition = marsRover.getPositionX() - 1;
                moveMarsRover(targetPosition < 0);
                break;
        }
    }

    private void moveMarsRover(Boolean marsRoverCondition) {
        String message = "cannot move forward and last position is: "+ marsRover.getCurrentPosition();

        if (marsRoverCondition) throw new RoverDontWantToDieException(message);
        else marsRover.move("M");
    }

    private Boolean checkDeployBoundaries(Long positionX, Long positionY) {
        return (positionX < 0) || (positionX > plateau.getSize()) || (positionY < 0) || (positionY > plateau.getSize());
    }
}
