package com.nasa.marsroverproblem;

import com.nasa.marsroverproblem.exceptions.RoverDontWantToDieException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class GroundControl {
    private Plateau plateau;
    private MarsRover marsRover;

    private final MarsRoverObjectRepository marsRoverObjectRepository;

    private static void moveLeft(MarsRover marsRover) {
        switch (marsRover.direction) {
            case "N":
                marsRover.setDirection("W");
                break;
            case "W":
                marsRover.setDirection("S");
                break;
            case "S":
                marsRover.setDirection("E");
                break;
            case "E":
                marsRover.setDirection("N");
                break;
        }
    }

    private static void moveRight(MarsRover marsRover) {
        switch (marsRover.direction) {
            case "N":
                marsRover.setDirection("E");
                break;
            case "E":
                marsRover.setDirection("S");
                break;
            case "S":
                marsRover.setDirection("W");
                break;
            case "W":
                marsRover.setDirection("N");
                break;
        }
    }

    private static void moveForward(MarsRover marsRover) {
        switch (marsRover.direction) {
            case "N":
                marsRover.setPositionY(marsRover.positionY + 1);
                break;
            case "E":
                marsRover.setPositionX(marsRover.positionX + 1);
                break;
            case "S":
                marsRover.setPositionY(marsRover.positionY - 1);
                break;
            case "W":
                marsRover.setPositionX(marsRover.positionX - 1);
                break;
        }
    }

    public static void move(MarsRover marsRover, String command) {
        switch (command) {
            case "L":
                moveLeft(marsRover);
                break;
            case "R":
                moveRight(marsRover);
                break;
            case "M":
                moveForward(marsRover);
                break;
        }
    }

    public static String getCurrentPosition(MarsRover marsRover) {
        return marsRover.getDirection() + " " + marsRover.getPositionX() + " " + marsRover.getPositionY();
    }

    public String createNewPlateau(Long size) {
        plateau = new Plateau(size > 0 ? size : 5);
        return plateau.getSize().toString();
    }

    public Boolean deployMarsRover(String name, String direction, Long positionX, Long positionY) {
        Boolean isDeployed = false;

        if (!isOutOfBoundaries(positionX, positionY)) {
            marsRover = MarsRover.with( UUID.randomUUID().toString(), name, direction, positionX, positionY);
            marsRoverObjectRepository.save(marsRover);
            isDeployed = true;
        }
        return isDeployed;
    }

    public String processCommands(String marsRoverName, String commands) {
        String[] commandsParse = commands.split("");

        Optional<MarsRover> currentMarsRover = marsRoverObjectRepository.findByName(marsRoverName);
        for (String command : commandsParse) {
            if (command.matches("M")) checkBoundaries();
            else move(currentMarsRover.get(), command);
        }
        return getCurrentPosition(marsRover);
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
        String message = "cannot move forward and last position is: "+ getCurrentPosition(marsRover);

        if (marsRoverCondition) throw new RoverDontWantToDieException(message);
        else move(marsRover, "M");
    }

    private Boolean isOutOfBoundaries(Long positionX, Long positionY) {
        return (positionX < 0) || (positionX > plateau.getSize()) || (positionY < 0) || (positionY > plateau.getSize());
    }
}
