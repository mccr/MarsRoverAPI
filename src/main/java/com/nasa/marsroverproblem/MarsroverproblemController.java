package com.nasa.marsroverproblem;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.json.Json;
import javax.json.JsonObject;


@Controller
public class MarsroverproblemController {

    private MarsRover marsRover;

    @RequestMapping("/")
    public @ResponseBody
    String greeting() {
        return "Hello World";
    }

    @RequestMapping("/newPlateau")
    public @ResponseBody
    String newPlateau() {
        return "New Plateau";
    }


    @RequestMapping(value = "/deployMarsRover", produces = "application/json; charset=utf-8")
    public @ResponseBody
    MarsRover newMarsRover() {
        marsRover = new MarsRover("N", 0, 0);
        return marsRover;
    }

    @RequestMapping(value = "command/{stringOfCommands}", method = RequestMethod.POST)
    public void takeCommands(@PathVariable("stringOfCommands") String commands) {
        System.out.println(commands);
    }

    @RequestMapping(value = "roverModifyDirection/{newDirection}", method = RequestMethod.PUT)
    public void modifyRoverDirection(@PathVariable("newDirection") String direction) {
        marsRover.setDirection(direction);
    }

    @RequestMapping("/getMarsRoverLocation")
    public @ResponseBody
    String getMarsRoverLocation() {
        String marsRoverLocation = marsRover.getPositionX() + " " + marsRover.getPositionY() + " " + marsRover.getDirection();
        return marsRoverLocation;
    }

}