package com.nasa.marsroverproblem;

import com.nasa.marsroverproblem.exceptions.RoverDontWantToDieException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class GroundControl {
    private Plateau plateau;

    private final MarsRoverObjectRepository marsRoverObjectRepository;

    public String createNewPlateau(Long size) {
        plateau = new Plateau(size > 0 ? size : 5);
        return plateau.getSize().toString();
    }

    public Boolean deployMarsRover(String name, String direction, Long positionX, Long positionY) {
        Boolean isDeployed = false;

        if (!isOutOfBoundaries(positionX, positionY)) {
            MarsRover deployedMarsRover = MarsRover.with( UUID.randomUUID().toString(), name, direction, positionX, positionY);
            marsRoverObjectRepository.save(deployedMarsRover);
            isDeployed = true;
        }
        return isDeployed;
    }

    public String processCommands(String marsRoverName, String commands) {
        String[] commandsParse = commands.split("");

        Optional<MarsRover> currentMarsRover = marsRoverObjectRepository.findByName(marsRoverName);

        for (String command : commandsParse) {
            if (command.matches("M")) safelyMoveRoverIfInsidePlateau(currentMarsRover.get());
            else move(currentMarsRover.get(), command);
        }
        
        return getCurrentPosition(currentMarsRover.get());
    }

    public void move(MarsRover marsRover, String command) {
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

    public String getCurrentPosition(MarsRover marsRover) {
        return marsRover.getDirection() + " " + marsRover.getPositionX() + " " + marsRover.getPositionY();
    }

    public List<MarsRover> getDeployedRoversList() {
        return marsRoverObjectRepository.findAll();
    }

    private void safelyMoveRoverIfInsidePlateau(MarsRover marsRover) {
        Long targetPosition;

        switch (marsRover.getDirection()) {
            case "N":
                targetPosition = marsRover.getPositionY() + 1;
                moveMarsRover(targetPosition > plateau.getSize(), marsRover);
                break;
            case "E":
                targetPosition = marsRover.getPositionX() + 1;
                moveMarsRover(targetPosition > plateau.getSize(), marsRover);
                break;
            case "S":
                targetPosition = marsRover.getPositionY() - 1;
                moveMarsRover(targetPosition < 0, marsRover);
                break;
            case "W":
                targetPosition = marsRover.getPositionX() - 1;
                moveMarsRover(targetPosition < 0, marsRover);
                break;
        }
    }

    private void moveMarsRover(Boolean marsRoverCondition, MarsRover marsRover) {
        String message = "cannot move forward and last position is: "+ getCurrentPosition(marsRover);

        List<MarsRover> marsRoverList = getDeployedRoversList();
        marsRoverObjectRepository.save(marsRover);
        for (MarsRover rover : marsRoverList) {
            if (!rover.getName().equals(marsRover.getName()) && getCurrentPosition(rover).equals(getCurrentPosition(marsRover))) throw new RoverDontWantToDieException(message + ". " +rover.getName() + " on my way");
        }

        if (marsRoverCondition) throw new RoverDontWantToDieException(message);
        else move(marsRover, "M");
    }

    private Boolean isOutOfBoundaries(Long positionX, Long positionY) {
        return (positionX < 0) || (positionX > plateau.getSize()) || (positionY < 0) || (positionY > plateau.getSize());
    }

    private void moveLeft(MarsRover marsRover) {
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

    private void moveRight(MarsRover marsRover) {
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

    private void moveForward(MarsRover marsRover) {
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
}
