import java.util.*;

/**
 * Represents the continents
 *
 * @author Vyasan.J
 * @version 10.25.2020
 */
public class Continent {
    ArrayList<Country> countries;
    private final ContinentName name;

    /**
     *Creates a new continent object
     *
     *@param name - the name of the continent
     */
    public Continent(ContinentName name){
        this.name = name;
        countries = new ArrayList<>();
    }

    /**
     *Adds a country to the continent
     *
     *@param c - country being added to continent
     */
    public void addCountry(Country c){
        countries.add(c);
    }

    /**
     *Returns the name of the continent
     *
     *@return name of the continent
     */
    public ContinentName getName(){
        return name;
    }

    /**
     *Returns the list of countries in the continent
     *
     *@return list of countries in the continent
     */
    public List<Country> getContinent(){
        return countries;
    }

}