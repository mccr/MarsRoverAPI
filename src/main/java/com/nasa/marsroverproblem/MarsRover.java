package com.nasa.marsroverproblem;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Data
@AllArgsConstructor(staticName = "with")
@NoArgsConstructor
@Table(name = "marsroverobject")
class MarsRover {
    @Id
    private String id;
    @Column(name = "name")
    private String name;
    @Column(name = "direction")
    public String direction;
    @Column(name = "positionx")
    public Long positionX;
    @Column(name = "positiony")
    public Long positionY;

    public String getCurrentPosition() {
        return getDirection() + " " + getPositionX() + " " + getPositionY();
    }

}
