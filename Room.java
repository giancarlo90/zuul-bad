import java.util.HashMap;
import java.util.Set;
import java.util.ArrayList;

/**
 * Class Room - a room in an adventure game.
 *
 * This class is part of the "World of Zuul" application. 
 * "World of Zuul" is a very simple, text based adventure game.  
 *
 * A "Room" represents one location in the scenery of the game.  It is 
 * connected to other rooms via exits.  The exits are labelled north, 
 * east, south, west.  For each direction, the room stores a reference
 * to the neighboring room, or null if there is no exit in that direction.
 * 
 * @author  Michael Kölling and David J. Barnes
 * @version 2011.07.31
 */
public class Room 
{
    private String description;
    private HashMap<String, Room> salidasConSecciones;
    private ArrayList<Item> it;

    /**
     * Create a room described "description". Initially, it has
     * no exits. "description" is something like "a kitchen" or
     * "an open court yard".
     * @param description The room's description.
     */
    public Room(String description, Item item) 
    {
        this.description = description;
        it = new ArrayList<>();
        salidasConSecciones = new HashMap<>();
    }

    /**
     * Define an exit from this room.
     * @param direction The direction of the exit.
     * @param neighbor The room in the given direction.
     */
    public void setExit(String direction, Room neighbor){
        salidasConSecciones.put(direction, neighbor);
    }

    /**
     * @return The description of the room.
     */
    public String getDescription()
    {
        return description;
    }

    /**
     * Devuelve la seccion que esta en la posicion introducida por parametro.
     */
    public Room getExit(String direccion){
        return salidasConSecciones.get(direccion);
    }

    /**
     * Return a description of the room's exits.
     * For example: "Exits: north east west"
     *
     * @ return A description of the available exits.
     */
    public String getExitString()
    {
        Set<String> cadenaDeSalidas = salidasConSecciones.keySet();
        String exits = "Exits: ";
        for(String cadena :cadenaDeSalidas){
            exits += cadena + " ";
        } 

        return exits;
    }

    /**
     * Return a long description of this room, of the form:
     *     You are in the 'name of room'
     *     Exits: north west southwest
     * @return A description of the room, including exits.
     */
    public String getLongDescription(){
        String descripcion = "You are in the " + description + ".\n"+ getExitString();
        if(it != null){
            for(int i=0; i < it.size(); i++){
                descripcion += ".\n" + "Hay un(a) " + it.get(i).getDescription() + " con un peso de: " + it.get(i).getWeight();
            }
        } 
        return descripcion;
    }

    public void addItem(String descripcion, int peso){
        it.add(new Item(descripcion,peso));
    }

    public ArrayList<Item> getItems(){
        return it;
    }
}
