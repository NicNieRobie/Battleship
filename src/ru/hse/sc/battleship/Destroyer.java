package ru.hse.sc.battleship;

public class Destroyer extends Ship {
    public Destroyer() {
        this.shipSize = 2;
        this.shipClass = "destroyer";
        this.healthPoints = shipSize;
    }
}
