package com.nasa.marsroverproblem;

import com.nasa.marsroverproblem.exceptions.RoverDontWantToDieException;
import org.junit.Assert;
import org.junit.jupiter.api.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class GroundControlTest {
    private GroundControl groundControl = new GroundControl();

    @Test
    void shouldCreateANewPlateauAndReturnTheSizeOfTheGridCreatedAsAString() {
        Assert.assertThat(groundControl.createNewPlateau(10L), is("10"));
    }

    @Test
    void shouldDeployANewMarsRoverAndReturnTrue() {
        groundControl.createNewPlateau(10L);

        Boolean deployedMarsRover = groundControl.deployMarsRover("N",0L,5L);

        assertEquals(true, deployedMarsRover);
    }

    @Test
    void shouldReturnFalseIfTryingToDeployOutsideBoundaries() {
        groundControl.createNewPlateau(5L);

        Boolean isDeployed = groundControl.deployMarsRover("N",0L,6L);

        assertEquals(false, isDeployed);
    }

    @Test
    void shouldCheckForRoverMoveForwardIsInsideBoundaries() {
        groundControl.createNewPlateau(10L);
        groundControl.deployMarsRover("N", 1L, 2L);

        String finalMarsRoverPosition = groundControl.processCommands("M");

        assertThat(finalMarsRoverPosition, is("N 1 3"));
    }

    @Test
    void shouldThrowExceptionWhenRoverIsOutOfBoundaries() {
        groundControl.createNewPlateau(5L);
        groundControl.deployMarsRover("N", 1L, 5L);

        Throwable exception = assertThrows(RoverDontWantToDieException.class, () -> groundControl.processCommands("M"));
        assertEquals("cannot move forward and last position is: N 1 5", exception.getMessage());
    }
}