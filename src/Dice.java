import java.util.Random;

public class Dice {

    private int value;

    public Dice() {

        Random num = new Random();

        value = num.nextInt(6) + 1;

    }

    /**
     * Rolls the dice
     */
    public void rollDice() {

        Random num = new Random();
        value = num.nextInt(6) + 1;
    }

    /**
     * Get the value of the dice
     * @return the value of the dice
     */
    public int getValue() {

        return value;

    }
}
