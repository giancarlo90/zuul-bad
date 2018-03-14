
/**
 * Write a description of class Item here.
 * 
 * @author (Gian Carlo) 
 * @version (2018/03/14)
 */
public class Item
{
    // instance variables
    private String itemDescription;
    private int itemWeight;

    /**
     * Constructor for objects of class Item
     */
    public Item(String itemDescription, int itemWeight)
    {
        // initialise instance variables
        this.itemDescription = itemDescription;
        this.itemWeight = itemWeight;
    }

    /**
     * Metodo que devulve el peso del item
     */
    public int getWeight()
    {
        return itemWeight;
    }

    /**
     * Metodo que devulve el peso del item
     */
    public String getDescription()
    {
        return itemDescription;
    }
}
