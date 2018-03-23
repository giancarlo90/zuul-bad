
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
    private String id;
    private int itemWeight;

    /**
     * Constructor for objects of class Item
     */
    public Item(String itemDescription, String id, int itemWeight)
    {
        // initialise instance variables
        this.itemDescription = itemDescription;
        this.id = id;
        this.itemWeight = itemWeight;
    }

    /**
     * Metodo que devuelve el peso del item
     */
    public int getWeight()
    {
        return itemWeight;
    }

    /**
     * Metodo que devuelve la descripcion del item
     */
    public String getDescription()
    {
        return itemDescription;
    }

    /**
     * Metodo que devulve el peso del item
     */
    public String getId()
    {
        return id;
    }
}
