package com.nasa.marsroverproblem;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
public class MarsroverproblemController {

    private MarsRover marsRover;
    private GroundControl groundControl = new GroundControl();

    @GetMapping("/newPlateau")
    public String newPlateau(@RequestParam("size") Long size) {
        String gridSizeCreated = groundControl.createNewPlateau(size);
        return "New Plateau with " + gridSizeCreated +"x"+ gridSizeCreated + " Grid created";
    }


    @GetMapping(value = "deployMarsRover", produces = "application/json; charset=utf-8")
    public MarsRover newMarsRover(@RequestParam("dir") String direction, @RequestParam("posx") Long positionX, @RequestParam("posy") Long positionY) {
        return groundControl.deployMarsRover(direction, positionX, positionY);
    }

    @PostMapping(value = "command/{stringOfCommands}")
    public ResponseEntity<String> takeCommands(@PathVariable("stringOfCommands") String commands) {
        try {
            return ResponseEntity.ok().body(groundControl.processCommands(commands));
        } catch (Exception exception) {
            return ResponseEntity.status(HttpStatus.I_AM_A_TEAPOT).body("you fall off");
        }
    }

}