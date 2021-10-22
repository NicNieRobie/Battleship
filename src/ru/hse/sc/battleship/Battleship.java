package ru.hse.sc.battleship;

public class Battleship extends Ship {
    public Battleship() {
        this.shipSize = 4;
        this.shipClass = "destroyer";
        this.healthPoints = shipSize;
    }
}
