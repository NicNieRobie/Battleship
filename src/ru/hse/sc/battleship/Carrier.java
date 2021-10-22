package ru.hse.sc.battleship;

public class Carrier extends Ship {
    public Carrier() {
        this.shipSize = 5;
        this.shipClass = "destroyer";
        this.healthPoints = shipSize;
    }
}
