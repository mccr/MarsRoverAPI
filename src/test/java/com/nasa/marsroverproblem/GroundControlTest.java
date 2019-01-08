package com.nasa.marsroverproblem;

import com.nasa.marsroverproblem.exceptions.RoverDontWantToDieException;
import org.assertj.core.util.Arrays;
import org.junit.Assert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.stubbing.OngoingStubbing;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class GroundControlTest {
    private GroundControl groundControl;
    private MarsRoverObjectRepository marsRoverObjectRepository;

    @BeforeEach
    void setUp() {
        marsRoverObjectRepository = mock(MarsRoverObjectRepository.class);
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

        when(marsRoverObjectRepository.findByName("MarsRover1"))
                .thenReturn(Optional.ofNullable(MarsRover.with("1", "MarsRover1", "N", 1L, 2L)));

        String finalMarsRoverPosition = groundControl.processCommands("MarsRover1", "M");

        assertEquals("N 1 3", finalMarsRoverPosition);
    }

    @Test
    void shouldThrowExceptionWhenRoverIsOutOfBoundaries() {
        groundControl.createNewPlateau(5L);
        groundControl.deployMarsRover("MarsRover1", "N", 1L, 5L);

        when(marsRoverObjectRepository.findByName("MarsRover1"))
                .thenReturn(Optional.ofNullable(MarsRover.with("1", "MarsRover1", "N", 1L, 5L)));

        Throwable exception = assertThrows(RoverDontWantToDieException.class, () -> groundControl.processCommands("MarsRover1", "M"));
        assertEquals("cannot move forward and last position is: N 1 5", exception.getMessage());
    }

    @Test
    void shouldBeAbleToMoveLeft() {
        MarsRover marsRover = MarsRover.with("1", "MarsRover1", "N", 0L, 0L);

        groundControl.move(marsRover, "L");

        Assert.assertEquals("W 0 0", groundControl.getCurrentPosition(marsRover));
    }

    @Test
    void shouldBeAbleToMoveRight() {
        MarsRover marsRover = MarsRover.with("1", "MarsRover1", "N", 0L, 0L);

        groundControl.move(marsRover, "R");

        Assert.assertEquals("E 0 0", groundControl.getCurrentPosition(marsRover));
    }

    @Test
    void shouldBeAbleToMoveForward() {
        MarsRover marsRover = MarsRover.with("1", "MarsRover1", "N", 0L, 0L);

        groundControl.move(marsRover, "M");

        Assert.assertEquals("N 0 1", groundControl.getCurrentPosition(marsRover));
    }

    @Test
    void shouldBeAbleToReturnAListOfEveryRoverDeployed() {
        List<MarsRover> deployedMarsRoverList = new ArrayList<>();
        deployedMarsRoverList.add(MarsRover.with("1", "MarsRover1", "N", 0L, 5L));

        when(marsRoverObjectRepository.findAll()).thenReturn(deployedMarsRoverList);

        assertEquals(1, groundControl.getDeployedRoversList().size());
    }

    @Test
    void shouldBeAbleToMoveWithoutCrashingToAnyRover() {
        groundControl.createNewPlateau(10L);

        groundControl.deployMarsRover("MarsRover1", "N",0L,5L);
        groundControl.deployMarsRover("MarsRover2", "N",0L,2L);

        when(marsRoverObjectRepository.findByName("MarsRover2"))
                .thenReturn(Optional.of(MarsRover.with("2",
                        "MarsRover2",
                        "N",
                        0L,
                        2L)));

        List<MarsRover> marsRoverList = new ArrayList<>();
        marsRoverList.add(MarsRover.with("1",
                "MarsRover1",
                "N",
                0L,
                5L));
        marsRoverList.add(MarsRover.with("2",
                        "MarsRover2",
                        "N",
                        0L,
                        2L));

        when(marsRoverObjectRepository.findAll()).thenReturn(marsRoverList);

        Throwable exception = assertThrows(RoverDontWantToDieException.class, () -> groundControl.processCommands("MarsRover2", "MMMM"));
        assertEquals("cannot move forward and last position is: N 0 4. MarsRover1 on my way", exception.getMessage());
    }
}