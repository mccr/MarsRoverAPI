package com.nasa.marsroverproblem;

public class GroundControl {

    public static final String CANNOT_MOVE_FORWARD = "cannot move forward";
    private Plateau plateau;
    private MarsRover marsRover;

    public String createNewPlateau(Long size) {
        plateau = new Plateau(size > 0 ? size : 5);
        return plateau.getSize().toString();
    }

    public String deployMarsRover(String direction, Long positionX, Long positionY) {
        if ((positionX >= 0) && (positionX <= plateau.getSize()) && (positionY >= 0) && (positionY <= plateau.getSize())) {
            marsRover = MarsRover.with(direction, positionX, positionY);
        } else {
            throw new RuntimeException("cannot deploy outside the grid boundaries");
        }
        return "New Mars Rover deployed in " + marsRover.getCurrentPosition();
    }

    public String processCommands(String commands) {
        String[] commandsParse = commands.split("");

        for (String command : commandsParse) {
            if (command.matches("M")) {
                checkBoundaries();
            } else {
                marsRover.move(command);
            }
        }
        return marsRover.getCurrentPosition();
    }

    public void checkBoundaries() {
        Long targetPosition;

        switch (marsRover.getDirection()) {
            case "N":
                targetPosition = marsRover.getPositionY() + 1;
                if (targetPosition > plateau.getSize()) throw new RuntimeException(CANNOT_MOVE_FORWARD);
                else marsRover.move("M");
                break;
            case "E":
                targetPosition = marsRover.getPositionX() + 1;
                if (targetPosition > plateau.getSize()) throw new RuntimeException(CANNOT_MOVE_FORWARD);
                else marsRover.move("M");
                break;
            case "S":
                targetPosition = marsRover.getPositionY() - 1;
                if (targetPosition < 0) throw new RuntimeException(CANNOT_MOVE_FORWARD);
                else marsRover.move("M");
                break;
            case "W":
                targetPosition = marsRover.getPositionX() - 1;
                if (targetPosition < 0) throw new RuntimeException(CANNOT_MOVE_FORWARD);
                else marsRover.move("M");
                break;
        }
    }
}
