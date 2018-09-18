package com.nasa.marsroverproblem;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class MarsroverproblemApplicationTests {
    @Autowired
    private MockMvc mockMvc;

    @Test
    public void shouldReturnANewPlateau() throws Exception {
        this.mockMvc.perform(get("/newPlateau"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("New Plateau")));
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
    public void shouldReceiveAStringOfCommands() throws Exception {
        String command = "LMLMLMLMM";
        this.mockMvc.perform(post("/command/" + command))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    public void shouldBeAbleToModifyRoverDirectionAndReturnItsFinalPosition() throws Exception {
        this.mockMvc.perform(get("/deployMarsRover?dir=N&posx=1&posy=2"))
                .andDo(print());

        this.mockMvc.perform(post("/LMLMLMLMM"))
                .andDo(print())
                .andExpect(status().isOk());
    }
}
