package com.nasa.marsroverproblem;

public class GroundControl {

    private Plateau plateau;
    private MarsRover marsRover;

    public String createNewPlateau(Long size) {
        plateau = new Plateau(size > 0 ? size : 5);
        return plateau.getSize().toString();
    }

    public Long getPlateauBoundaries() {
        return plateau.getSize();
    }

    public MarsRover deployMarsRover(String direction, Long positionX, Long positionY) {
        marsRover = MarsRover.with(direction, positionX, positionY);
        return marsRover;
    }

    public String processCommands(String commands) {
        return marsRover.move(commands);
    }
}
