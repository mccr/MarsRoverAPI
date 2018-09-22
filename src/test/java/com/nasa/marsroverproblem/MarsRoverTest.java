package com.nasa.marsroverproblem;

import org.junit.Before;
import org.junit.Test;

import static java.util.function.Predicate.isEqual;
import static org.junit.Assert.*;

public class MarsRoverTest {
    private MarsRover marsRover;

    @Before
    public void setUp() throws Exception {
        marsRover = MarsRover.with("N", 0,0);
    }

    @Test
    public void shouldWeDeployANewMarsRoverWithDirectionAndPosition() {
        assertEquals(marsRover.getDirection(), "N");
        assertEquals(marsRover.getPositionX(), 0);
        assertEquals(marsRover.getPositionY(), 0);
    }

    @Test
    public void shouldReturnFinalPositionWhenExecutingCommand() {
        assertEquals("W 0 0", marsRover.move("L"));
    }

    @Test
    public void shouldBeAbleToMoveLeft() {
        assertEquals("W 0 0", marsRover.move("L"));
    }

    @Test
    public void shouldBeAbleToMoveRight() {
        assertEquals("E 0 0", marsRover.move("R"));
    }

    @Test
    public void shouldBeAbleToMoveForward() {
        assertEquals("N 0 1", marsRover.move("M"));
    }

    @Test
    public void shouldReturnFinalPositionWhenExecutingASequenceOfCommands() {
        marsRover.setPositionX(1);
        marsRover.setPositionY(2);

        assertEquals("N 1 3", marsRover.move("LMLMLMLMM"));
    }

    @Test
    public void shouldReturnCorrectFinalPositionWhenExecutingASequenceOfCommands() {
        marsRover.setPositionX(3);
        marsRover.setPositionY(3);
        marsRover.setDirection("E");

        assertEquals("E 5 1", marsRover.move("MMRMMRMRRM"));
    }

}