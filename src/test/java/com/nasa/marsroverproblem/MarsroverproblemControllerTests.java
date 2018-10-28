package com.nasa.marsroverproblem;

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

    @Test
    public void shouldReturnANewPlateau() throws Exception {
        this.mockMvc.perform(get("/newPlateau?size=5"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("New Plateau with 5x5 Grid created")));
    }

    @Test
    public void shouldReturnANewMarsRoverDeployedWithPositionAndDirection() throws Exception {
        this.mockMvc.perform(get("/deployMarsRover?dir=N&posx=0&posy=0"))
                .andDo(print())
                .andExpect(content().contentType("application/json;charset=UTF-8"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("direction").value("N"))
                .andExpect(jsonPath("positionX").value(0))
                .andExpect(jsonPath("positionY").value(0));
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
    public void shouldReturnErrorMessageIfRoverFallsOffThePlateau() throws Exception {
        this.mockMvc.perform(get("/newPlateau?size=5"));

        this.mockMvc.perform(get("/deployMarsRover?dir=N&posx=1&posy=4"));

        this.mockMvc.perform(post("/command/MM"))
                .andDo(print())
                .andExpect(status().isIAmATeapot())
                .andExpect(content().string(containsString("you fall off")));
    }
}
