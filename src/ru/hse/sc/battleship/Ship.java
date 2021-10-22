package ru.hse.sc.battleship;

public abstract class Ship {
    protected String shipClass;
    protected Integer shipSize;
    protected Integer healthPoints;

    protected boolean isSunk() {
        return healthPoints == 0;
    }

    protected boolean onDamageSunk(boolean isTorpedoDamage) {
        healthPoints -= isTorpedoDamage ? healthPoints : 1;

        if (healthPoints == 0) {
            System.out.println("You just have sunk a " + shipClass + ".");
            return true;
        }

        System.out.println("Hit!");
        return false;
    }
}
