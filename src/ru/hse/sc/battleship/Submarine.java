package ru.hse.sc.battleship;

public class Submarine extends Ship {
    public Submarine() {
        this.shipSize = 1;
        this.shipClass = "submarine";
        this.healthPoints = shipSize;
    }
}
