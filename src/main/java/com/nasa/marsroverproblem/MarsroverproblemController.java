package com.nasa.marsroverproblem;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;


@Controller
public class MarsroverproblemController {

    private MarsRover marsRover;
    private Plateau plateau;

    @RequestMapping("/newPlateau")
    public @ResponseBody
    String newPlateau() {
        plateau = Plateau.with(5,5);
        return "New Plateau with "+ plateau.getSize() +" Grid created";
    }


    @RequestMapping(value = "deployMarsRover", produces = "application/json; charset=utf-8", method = RequestMethod.GET)
    public @ResponseBody
    MarsRover newMarsRover(@RequestParam("dir") String direction, @RequestParam("posx") int positionX, @RequestParam("posy") int positionY) {
        marsRover = MarsRover.with(direction, positionX, positionY);
        return marsRover;
    }

    @RequestMapping(value = "command/{stringOfCommands}", method = RequestMethod.POST)
    public @ResponseBody 
    String takeCommands(@PathVariable("stringOfCommands") String commands) {
        System.out.println(commands);
        return marsRover.move(commands);
    }

}