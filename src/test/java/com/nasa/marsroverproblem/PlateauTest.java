package com.nasa.marsroverproblem;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class PlateauTest {

    private Plateau plateau;

    @Before
    public void setUp() throws Exception {
        plateau = Plateau.with(10,10);
    }

    @Test
    public void shouldBeAbleToCreateANewPlateauWithAxisXandAxisY() {
        assertEquals(plateau.getAxisX(), 10);
        assertEquals(plateau.getAxisY(), 10);
    }

    @Test
    public void shouldBeAbleToReturnSizeOfThePlateau() {
        assertEquals(plateau.getSize(), "10x10");
    }

}