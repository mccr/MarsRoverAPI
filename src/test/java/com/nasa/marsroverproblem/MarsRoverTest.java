package com.nasa.marsroverproblem;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.mock;

class MarsRoverTest {
    private MarsRover marsRover;

    private GroundControl groundControl = mock(GroundControl.class);

    @BeforeEach
    void setUp() {
        marsRover = MarsRover.with( UUID.randomUUID().toString(), "MarsRover1", "N", 0L,0L);
    }

    @Test
    void shouldWeDeployANewMarsRoverWithDirectionAndPosition() {
        assertEquals("N", marsRover.getDirection());
        assertThat(marsRover.getPositionX(), is(0L));
        assertThat(marsRover.getPositionY(), is(0L));
    }

    @Test
    void shouldBeAbleToMoveLeft() {
        GroundControl.move(marsRover, "L");

        assertEquals("W 0 0", marsRover.getCurrentPosition());
    }

    @Test
    void shouldBeAbleToMoveRight() {
        GroundControl.move(marsRover, "R");

        assertEquals("E 0 0", marsRover.getCurrentPosition());
    }

    @Test
    void shouldBeAbleToMoveForward() {
        GroundControl.move(marsRover, "M");

        assertEquals("N 0 1", marsRover.getCurrentPosition());
    }
}