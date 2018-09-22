package com.nasa.marsroverproblem;

import lombok.*;

@EqualsAndHashCode
@ToString
@Setter
@Getter
@AllArgsConstructor(staticName = "with")
class Plateau {
    private final int axisX;
    private final int axisY;

    public String getSize() {
        return getAxisX() +"x"+ getAxisY();
    }
}
