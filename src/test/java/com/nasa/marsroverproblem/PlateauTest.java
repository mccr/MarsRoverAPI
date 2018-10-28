package com.nasa.marsroverproblem;

import org.junit.jupiter.api.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class PlateauTest {

    @Test
    public void shouldBeAbleToCreateANewPlateauWithAxisXandAxisY() {
        Plateau plateau = new Plateau(10L);

        assertThat(plateau.getSize(), is(10L));
    }
}