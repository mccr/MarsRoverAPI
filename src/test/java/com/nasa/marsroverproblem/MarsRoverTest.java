package com.nasa.marsroverproblem;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class MarsRoverTest {
    private MarsRover marsRover;

    private GroundControl groundControl = mock(GroundControl.class);

    @BeforeEach
    void setUp() {
        marsRover = MarsRover.with("N", 0L,0L);
    }

    @Test
    void shouldWeDeployANewMarsRoverWithDirectionAndPosition() {
        assertEquals("N", marsRover.getDirection());
        assertThat(marsRover.getPositionX(), is(0L));
        assertThat(marsRover.getPositionY(), is(0L));
    }

    @Test
    void shouldReturnFinalPositionWhenExecutingCommand() {
        assertEquals("W 0 0", marsRover.move("L"));
    }

    @Test
    void shouldBeAbleToMoveLeft() {
        assertEquals("W 0 0", marsRover.move("L"));
    }

    @Test
    void shouldBeAbleToMoveRight() {
        assertEquals("E 0 0", marsRover.move("R"));
    }

    @Test
    void shouldBeAbleToMoveForward() {
        assertEquals("N 0 1", marsRover.move("M"));
    }

    @Test
    void shouldReturnFinalPositionWhenExecutingASequenceOfCommands() {
        marsRover.setPositionX(1L);
        marsRover.setPositionY(2L);

        assertEquals("N 1 3", marsRover.move("LMLMLMLMM"));
    }

    @Test
    void shouldReturnCorrectFinalPositionWhenExecutingASequenceOfCommands() {
        marsRover.setPositionX(3L);
        marsRover.setPositionY(3L);
        marsRover.setDirection("E");

        assertEquals("E 5 1", marsRover.move("MMRMMRMRRM"));
    }

    @Test
    void shouldCheckWeatherItsFinalPositionIsOutOfThePlateauAndThrowAnException() {
        marsRover.setPositionX(4L);
        marsRover.setPositionY(4L);
        marsRover.setDirection("E");

//        when(groundControl.plateauBoundarie()).thenReturn(5);

        Throwable exception = assertThrows(RuntimeException.class, () -> marsRover.move("MM"));
        assertEquals("you fall off", exception.getMessage());
    }
}