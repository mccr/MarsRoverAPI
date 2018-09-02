package com.nasa.marsroverproblem;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.json.Json;
import javax.json.JsonObject;


@Controller
public class MarsroverproblemController {

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
    String newMarsRover() {
        JsonObject result = Json.createObjectBuilder()
                .add("direction", "N")
                .add("positionX", 0)
                .add("positionY", 0)
                .build();
        return result.toString();
    }



}