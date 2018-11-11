package com.nasa.marsroverproblem;

public class GroundControl {

    public static final String YOU_FALL_OFF = "you fall off";
    private Plateau plateau;
    private MarsRover marsRover;

    public String createNewPlateau(Long size) {
        plateau = new Plateau(size > 0 ? size : 5);
        return plateau.getSize().toString();
    }

    public MarsRover deployMarsRover(String direction, Long positionX, Long positionY) {
        marsRover = MarsRover.with(direction, positionX, positionY);
        return marsRover;
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
                if (targetPosition > plateau.getSize()) throw new RuntimeException(YOU_FALL_OFF);
                else marsRover.move("M");
                break;
            case "E":
                targetPosition = marsRover.getPositionX() + 1;
                if (targetPosition > plateau.getSize()) throw new RuntimeException(YOU_FALL_OFF);
                else marsRover.move("M");
                break;
            case "S":
                targetPosition = marsRover.getPositionY() - 1;
                if (targetPosition < 0) throw new RuntimeException(YOU_FALL_OFF);
                else marsRover.move("M");
                break;
            case "W":
                targetPosition = marsRover.getPositionX() - 1;
                if (targetPosition < 0) throw new RuntimeException(YOU_FALL_OFF);
                else marsRover.move("M");
                break;
        }
    }
}
