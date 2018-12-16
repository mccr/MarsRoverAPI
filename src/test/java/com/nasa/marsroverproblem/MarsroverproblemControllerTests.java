package com.nasa.marsroverproblem;

import com.nasa.marsroverproblem.exceptions.RoverDontWantToDieException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.containsString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
public class MarsroverproblemControllerTests {
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    GroundControl groundControl;

    @Test
    public void shouldReturnANewPlateau() throws Exception {
        when(groundControl.createNewPlateau(10L))
                .thenReturn("New Plateau with 10x10 Grid created");

        this.mockMvc.perform(get("/newPlateau?size=10"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("New Plateau with 10x10 Grid created")));
    }

    @Test
    public void shouldReturnAMessageOfDeploySuccessWithPosition() throws Exception {
        when(groundControl.deployMarsRover("MarsRover1", "N", 0L, 0L))
                .thenReturn(true);

        this.mockMvc.perform(get("/deployMarsRover?name=MarsRover1&dir=N&posx=0&posy=0"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("New Mars Rover MarsRover1 deployed in N 0 0")));
    }

    @Test
    public void shouldReturnAnExceptionWhenTryingToDeployOutsideGrid() throws Exception {
        when(groundControl.deployMarsRover("MarsRover1", "N", 0L, 6L))
                .thenReturn(false);

        this.mockMvc.perform(get("/deployMarsRover?name=MarsRover1&dir=N&posx=1&posy=6"))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(content().string(containsString("cannot deploy outside the grid boundaries")));
    }

    @Test
    public void shouldBeAbleToReceiveCommandsAndReturnRoversFinalPosition() throws Exception {
        when(groundControl.processCommands("MarsRover2", "LMLMLMLMM"))
                .thenReturn("N 1 3");

        this.mockMvc.perform(post("/command/MarsRover2/LMLMLMLMM"))
                .andDo(print())
                .andExpect(content().string(containsString("N 1 3")))
                .andExpect(status().isOk());
    }

    @Test
    public void shouldReturnErrorMessageAndRoverLastPositionIfRoverCannotMoveForwardInThePlateau() throws Exception {
        when(groundControl.processCommands("MarsRover1", "MM"))
                .thenThrow(new RoverDontWantToDieException("cannot move forward and last position is: N 1 5"));

        this.mockMvc.perform(post("/command/MarsRover1/MM"))
                .andDo(print())
                .andExpect(status().isIAmATeapot())
                .andExpect(content().string(containsString("cannot move forward and last position is: N 1 5")));
    }
}
