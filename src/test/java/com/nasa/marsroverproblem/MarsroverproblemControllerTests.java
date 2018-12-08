package com.nasa.marsroverproblem;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
public class MarsroverproblemControllerTests {
    @Autowired
    private MockMvc mockMvc;

    @BeforeEach
    void setUp() throws Exception {
        this.mockMvc.perform(get("/newPlateau?size=5"));
    }

    @Test
    public void shouldReturnANewPlateau() throws Exception {
        this.mockMvc.perform(get("/newPlateau?size=10"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("New Plateau with 10x10 Grid created")));
    }

    @Test
    public void shouldReturnAMessageOfDeploySuccessWithPosition() throws Exception {
        this.mockMvc.perform(get("/deployMarsRover?dir=N&posx=0&posy=0"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("New Mars Rover deployed in N 0 0")));
    }

    @Test
    public void shouldReturnAnExceptionWhenTryingToDeployOutsideGrid() throws Exception {
        this.mockMvc.perform(get("/deployMarsRover?dir=N&posx=1&posy=6"))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(content().string(containsString("cannot deploy outside the grid boundaries")));
    }

    @Test
    public void shouldBeAbleToReceiveCommandsAndReturnRoversFinalPosition() throws Exception {
        this.mockMvc.perform(get("/deployMarsRover?dir=N&posx=1&posy=2"));

        this.mockMvc.perform(post("/command/LMLMLMLMM"))
                .andDo(print())
                .andExpect(content().string(containsString("N 1 3")))
                .andExpect(status().isOk());
    }

    @Test
    public void shouldReturnErrorMessageAndRoverLastPositionIfRoverCannotMoveForwardInThePlateau() throws Exception {
        this.mockMvc.perform(get("/deployMarsRover?dir=N&posx=1&posy=4"));

        this.mockMvc.perform(post("/command/MM"))
                .andDo(print())
                .andExpect(status().isIAmATeapot())
                .andExpect(content().string(containsString("cannot move forward and last position is: N 1 5")));
    }
}
