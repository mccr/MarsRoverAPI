package com.nasa.marsroverproblem;

import com.nasa.marsroverproblem.exceptions.RoverDontWantToDieException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;

class GroundControlTest {
    private GroundControl groundControl;

    @BeforeEach
    void setUp() {
        MarsRoverObjectRepository marsRoverObjectRepository = mock(MarsRoverObjectRepository.class);
        groundControl = new GroundControl(marsRoverObjectRepository);
    }

    @Test
    void shouldCreateANewPlateauAndReturnTheSizeOfTheGridCreatedAsAString() {
        assertEquals("10", groundControl.createNewPlateau(10L));
    }

    @Test
    void shouldDeployANewMarsRoverAndReturnTrue() {
        groundControl.createNewPlateau(10L);

        Boolean deployedMarsRover = groundControl.deployMarsRover("MarsRover1", "N",0L,5L);

        assertEquals(true, deployedMarsRover);
    }

    @Test
    void shouldReturnFalseIfTryingToDeployOutsideBoundaries() {
        groundControl.createNewPlateau(5L);

        Boolean isDeployed = groundControl.deployMarsRover("MarsRover1", "N", 0L, 6L);

        assertEquals(false, isDeployed);
    }

    @Test
    void shouldCheckForRoverMoveForwardIsInsideBoundaries() {
        groundControl.createNewPlateau(10L);
        groundControl.deployMarsRover("MarsRover1", "N", 1L, 2L);

        String finalMarsRoverPosition = groundControl.processCommands("MarsRover1", "M");

        assertEquals("N 1 3", finalMarsRoverPosition);
    }

    @Test
    void shouldThrowExceptionWhenRoverIsOutOfBoundaries() {
        groundControl.createNewPlateau(5L);
        groundControl.deployMarsRover("MarsRover1", "N", 1L, 5L);

        Throwable exception = assertThrows(RoverDontWantToDieException.class, () -> groundControl.processCommands("MarsRover1", "M"));
        assertEquals("cannot move forward and last position is: N 1 5", exception.getMessage());
    }
}