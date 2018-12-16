package com.nasa.marsroverproblem;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
public class MarsroverproblemController {
    private static final String CANNOT_DEPLOY_OUTSIDE_THE_GRID_BOUNDARIES = "cannot deploy outside the grid boundaries";

    @Autowired
    private GroundControl groundControl;

    @GetMapping("/newPlateau")
    public String newPlateau(@RequestParam("size") Long size) {
        String gridSizeCreated = groundControl.createNewPlateau(size);
        return "New Plateau with " + gridSizeCreated +"x"+ gridSizeCreated + " Grid created";
    }


    @GetMapping(value = "deployMarsRover", produces = "application/json; charset=utf-8")
    public ResponseEntity<String> newMarsRover(@RequestParam("name") String name, @RequestParam("dir") String direction, @RequestParam("posx") Long positionX, @RequestParam("posy") Long positionY) {
        ResponseEntity<String> response = ResponseEntity.ok().body(getMarsRoverDeployedSuccessMessage(name, direction, positionX, positionY));

        if (!groundControl.deployMarsRover(name, direction, positionX, positionY)){
            response = ResponseEntity.status(HttpStatus.BAD_REQUEST).body(CANNOT_DEPLOY_OUTSIDE_THE_GRID_BOUNDARIES);
        }

        return response;
    }

    @PostMapping(value = "command/{name}/{stringOfCommands}")
    public ResponseEntity<String> takeCommands(@PathVariable("name") String name, @PathVariable("stringOfCommands") String commands) {
        try {
            return ResponseEntity.ok().body(groundControl.processCommands(name, commands));
        } catch (Exception exception) {
            return ResponseEntity.status(HttpStatus.I_AM_A_TEAPOT).body(exception.getMessage());
        }
    }


    private String getMarsRoverDeployedSuccessMessage(String name, String direction, Long positionX, Long positionY) {
        return "New Mars Rover "+ name +" deployed in " + direction +" "+ positionX +" "+ positionY;
    }
}