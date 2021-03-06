import java.util.*;

/**
 * Risk Game, a turn-based world domination game where players take turn fighting each other to the death until only
 * one player remain conquering the whole world.
 *
 * @author Ryan. N
 * @version 10.25.2020
 *
 * @author Fareen. L
 * @version 11.09.2020
 *
 * @author Vis. K
 *
 * @author Ryan. N
 * @version 11.09.2020
 *
 */
public class Game
{
    private Dice die;

    private static ArrayList<Player> playerList;

    private static int numPlayers;

    private int playerIndex;

    private static Board board;

    private Player currentPlayer, enemyPlayer;

    private int numAtkArmy;

    private Country countryOwn, enemyCountry;

    private List <RiskView> riskViews;

    private String outcome = "\n", diceValue = "\n";


    /**
     * Creates a game and initialise its internal map.
     */
    public Game()
    {
        playerList = new ArrayList<>();
        board = new Board();
    }

    /**
     * Creates a game and initialise its internal map.
     *
     * @param playerCount The number of players playing the game.
     */
    public Game(int playerCount)
    {
        playerList = new ArrayList<>();
        board = new Board();
        riskViews = new ArrayList<>();

        this.numPlayers = playerCount;

        _retrievePlayers();
    }

    /**
     * Returns the game board.
     *
     * @return The board containing the world map.
     */
    public Board getBoardMap() {
        return board;
    }

    /**
     * Adds a RiskView object to the game.
     *
     * @param rv The RiskView object to add to the game.
     */
    public void addRiskView(RiskFrame rv) { riskViews.add(rv);}

    /**
     * Removes a RiskView object from the game.
     *
     * @param rv The RiskView object to remove from the game.
     */
    public void removeRiskView(RiskFrame rv) { riskViews.remove(rv);}

    /**
     * Returns the current player.
     *
     * @return The current player.
     */
    public Player getCurrentPlayer() {
        return currentPlayer;
    }

    /**
     * Gets the number of players from the user and sets them up.
     */
    private void _retrievePlayers()
    {
        playerList = new ArrayList<>();

        for (int i = 0; i < numPlayers; i++) {

            playerList.add(new Player("Player" + (i + 1)));

        }

        // Initialize the starting player.
        playerIndex = 0;
        currentPlayer = playerList.get(playerIndex);

        board.setupPlayers(playerList);
    }


    /**
     * Method which returns the state of the game.
     * The number of armies and countries owned by each player.
     */
    public String _getGameStatus()
    {
        String playersInfo = "";
        String pInfo = "";

        String currentTurn = "";

        for (Player p : playerList){
            playersInfo += p.toString();
            String ownedCountries = "";
            for (Country c : p.getOwnedCountries())
            {
                ownedCountries += c.getCountryName().name() + " ";
            }
            pInfo += p.getName() + " owns the following countries: " + ownedCountries + "\n";
        }

        currentTurn += "It's currently " + currentPlayer.getName() + "'s turn. \n";
        return playersInfo + "\n" + pInfo + "\n" + currentTurn;
    }

    /**
     * Attack action method.
     *
     * @param attacker The attacking player.
     * @param numArmy The number of army used to attack.
     * @param defender The defending player.
     * @return The result of the battle.
     */
    public String attackCMD(String attacker, int numArmy, String defender) {

        countryOwn = board.getCountry(CountryName.valueOf(attacker));

        List<CountryName> countryOwnAdj = countryOwn.getAdjCountries();

        enemyCountry = board.getCountry(CountryName.valueOf(defender));
        enemyPlayer = enemyCountry.getRuler();


        if (!currentPlayer.equals(enemyPlayer)) {

            numAtkArmy = numArmy;
            // Check if the country being attack has zero army
            // If so then takeover the country without commencing battlephase
            if (enemyCountry.getArmyOccupied() == 0) {

                enemyCountry.setRuler(currentPlayer);
                currentPlayer.addNewCountry(enemyCountry);
                enemyCountry.setArmyOccupied(numAtkArmy);
                countryOwn.setArmyOccupied(countryOwn.getArmyOccupied() - numAtkArmy);
                outcome += "You have claimed " + enemyCountry;

            }

            // Check to see if the current player has the military force to attack.
            else {

                // Onto war!!!
                battlePhase();

            }
        }

        else {
            outcome = "\n You own this country stupid!!!\n" + "Attack another country \n";
            return outcome;
        }

        return diceValue + outcome;
    }

    /**
     * This method handles the battle phase between players. By rolling a
     * dice based on the number of troops deployed.
     */
    private void battlePhase() {

        // An arraylist to store dice.
        ArrayList<Integer> atkList = new ArrayList<>();
        ArrayList<Integer> defList = new ArrayList<>();

        die = new Dice();

        int numDefArmy = 0;

        // Fill up the array with the values of the dice.
        for (int i = 0; i < Math.min(numAtkArmy, countryOwn.getArmyOccupied()); i++) {

            die.rollDice();
            atkList.add(die.getValue());
            diceValue += currentPlayer.getName() + " Dice: " + (i + 1) +  " Value: " + atkList.get(i);
            diceValue += "\n";

        }

        if (enemyCountry.getArmyOccupied() >= 2) {
            numDefArmy = 2;
        } else {
            numDefArmy = 1;
        }

        for (int i = 0; i < numDefArmy; i++) {

            die.rollDice();
            defList.add(die.getValue());
            diceValue += enemyPlayer.getName() + " Dice: " + (i + 1) + " Value: " + defList.get(i);
            diceValue += "\n";

        }

        // Arrange the dice value in descending order.
        if (numAtkArmy > 1) {
            Collections.sort(atkList, Collections.reverseOrder());
        }
        else {

        }
        if (numDefArmy > 1) {
            Collections.sort(defList, Collections.reverseOrder());
        }

        // Compare the values from the attacker side and defender side
        if (atkList.get(0) > defList.get(0)) {
            enemyPlayer.setArmyCount(enemyPlayer.getArmyCount() - 1); // sub an army from enemy player
            enemyCountry.setArmyOccupied(enemyCountry.getArmyOccupied() - 1); // sub an army from enemy country
            numDefArmy--;
        }

        else {
            currentPlayer.setArmyCount(currentPlayer.getArmyCount() - 1); // sub an army from current player
            countryOwn.setArmyOccupied(countryOwn.getArmyOccupied() - 1); // sub an army from current country
            numAtkArmy--;
        }

        if (atkList.size() >= 2 && defList.size() >= 2){

            if (atkList.get(1) > defList.get(1)) {
                enemyPlayer.setArmyCount(enemyPlayer.getArmyCount() - 1);
                enemyCountry.setArmyOccupied(enemyCountry.getArmyOccupied() - 1);
                numDefArmy--;
            }

            else {
                currentPlayer.setArmyCount(currentPlayer.getArmyCount() - 1);
                countryOwn.setArmyOccupied(countryOwn.getArmyOccupied() - 1);
                numAtkArmy--;
            }

        }

        // If there are no more troops in the country, player takes over the country.
        if (countryOwn.getArmyOccupied() == 0) {

            countryOwn.setRuler(enemyPlayer); // Set the new Ruler
            enemyPlayer.addNewCountry(countryOwn); // Add the country to the new Ruler
            countryOwn.setArmyOccupied(numDefArmy); // put the army that was fighting in the new country
            currentPlayer.removeNewCountry(countryOwn);
            enemyCountry.setArmyOccupied(enemyCountry.getArmyOccupied() - numDefArmy); // sub the num of army that was fighting

            // If the the current player total army count falls to zero, remove player from game.
            if (currentPlayer.getArmyCount() == 0) {
                removePlayer(currentPlayer);
            }

            outcome = "NEWS: " + currentPlayer.getName() + " has lost " + countryOwn.getCountryName() + " to " + enemyPlayer.getName() + ". \n";
            return;
        }

        // If there are no more troops in the country, player takes over the country.
        if (enemyCountry.getArmyOccupied() == 0) {

            enemyCountry.setRuler(currentPlayer);
            currentPlayer.addNewCountry(enemyCountry);
            enemyCountry.setArmyOccupied(numAtkArmy); // Should check to make sure at least one army in countryOwn
            enemyPlayer.removeNewCountry(enemyCountry);
            countryOwn.setArmyOccupied(countryOwn.getArmyOccupied() - numAtkArmy);

            // If the enemy total army count falls to zero, remove player from game.
            if (enemyPlayer.getArmyCount() == 0) {
                removePlayer(enemyPlayer);
            }

            outcome = "NEWS: " + currentPlayer.getName() + " has won " + enemyCountry.getCountryName() + " from " + enemyPlayer.getName() + ". \n";

        }

    }

    /**
     * Remove the player who has no more army from the game.
     *
     * todo: Fareen refactor.
     *
     * @param dead The player to remove from the game
     */
    private void removePlayer(Player dead){

        System.out.println("NEWS: " + dead + " has been eliminated from the game!");

        playerList.remove(dead);

    }


    /**
     * Updates the current player to next player.
     */
    public void nextPlayer() {

        playerIndex++;

        // If the index is bigger or equal to the player list go back to index 0
        if (playerIndex >= playerList.size()){
            playerIndex = 0;

        }

        // Player that is playing according to index.
        currentPlayer = playerList.get(playerIndex);
        _getGameStatus();
    }
}
