package com.nasa.marsroverproblem;

import lombok.Builder;
import lombok.Builder.Default;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Builder
@ToString
@Setter
@Getter
class MarsRover {
    @Default
    private String direction = "N";
    @Default
    private int positionX = 0;
    @Default
    private int positionY = 0;
}
