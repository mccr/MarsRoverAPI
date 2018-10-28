package com.nasa.marsroverproblem;

import org.junit.Assert;
import org.junit.jupiter.api.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.mock;

public class GroundControlTest {
    private GroundControl groundControl = new GroundControl();
    private MarsRover marsRover = mock(MarsRover.class);

    @Test
    void shouldCreateANewPlateauAndReturnTheSizeOfTheGridCreatedAsAString() {
        Assert.assertThat(groundControl.createNewPlateau(10L), is("10"));
    }

    @Test
    void shouldReturnTheBoundariesOfThePlateau() {
        groundControl.createNewPlateau(5L);

        Assert.assertThat(groundControl.getPlateauBoundaries(), is(5L));
    }

    @Test
    void shouldDeployANewMarsRoverAndReturnTheObject() {
        MarsRover deployedMarsRover = groundControl.deployMarsRover("N",0L,5L);

        assertEquals("N", deployedMarsRover.getDirection());
        assertThat(deployedMarsRover.getPositionX(), is(0L));
        assertThat(deployedMarsRover.getPositionY(), is(5L));

        //how do I mock the marsRover?
    }

    @Test
    void shouldMakeSureToCommandTheMarsRoverToMoveInsideTheBoundariesOfThePlateauAndReturnFinalPositionOfMarsRover() {
        groundControl.deployMarsRover("N", 1L, 2L);

        String finalMarsRoverPosition = groundControl.processCommands("LMLMLMLMM");

        assertThat(finalMarsRoverPosition, is("N 1 3"));
    }
}