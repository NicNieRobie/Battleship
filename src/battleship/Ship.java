package battleship;

public abstract class Ship {
    protected String shipClass;
    protected Integer shipSize;
    protected Integer healthPoints;

    protected boolean isSunk() {
        return healthPoints == 0;
    }

    protected boolean onDamageSunk(boolean isTorpedoDamage) {
        if (isSunk()) {
            System.out.println("  Ship already sunk!");
            return false;
        }

        healthPoints -= isTorpedoDamage ? healthPoints : 1;

        if (healthPoints == 0) {
            System.out.println("  You just have sunk a " + shipClass + ".");
            return true;
        }

        System.out.println("  Hit!");
        return false;
    }
}