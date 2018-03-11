import java.util.HashMap;
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

    /**
     * Create a room described "description". Initially, it has
     * no exits. "description" is something like "a kitchen" or
     * "an open court yard".
     * @param description The room's description.
     */
    public Room(String description) 
    {
        this.description = description;
        salidasConSecciones = new HashMap<>();
    }

    /**
     * Define the exits of this room.  Every direction either leads
     * to another room or is null (no exit there).
     * @param north The north exit.
     * @param east The east east.
     * @param south The south exit.
     * @param west The west exit.
     * @param southEast The south-east exit.
     * @param northWest The north-west exit.
     */
    public void setExits(Room north, Room east, Room south, Room west, Room southEast, Room northWest) 
    {
        if(north != null)
            salidasConSecciones.put("north",north);
        if(east != null)
            salidasConSecciones.put("east",east);
        if(south != null)
            salidasConSecciones.put("south",south);
        if(west != null)
            salidasConSecciones.put("west",west);
        if(southEast != null){
            salidasConSecciones.put("southEast",southEast);
        }
        if(northWest != null){
            salidasConSecciones.put("northWest",northWest);
        }
    }

    /**
     * @return The description of the room.
     */
    public String getDescription()
    {
        return description;
    }

    /**
     * 
     */
    public Room getExit(String direccion){
        Room exit = null; 
        if(direccion.equals("north")){
            exit = salidasConSecciones.get("north");
        }
        if(direccion.equals("east")){
            exit = salidasConSecciones.get("east");
        }
        if(direccion.equals("south")){
            exit = salidasConSecciones.get("south");
        }
        if(direccion.equals("west")){
            exit = salidasConSecciones.get("west");
        }
        if(direccion.equals("southEast")){
            exit = salidasConSecciones.get("southEast");
        }
        if(direccion.equals("northWest")){
            exit = salidasConSecciones.get("northWest");
        }
        return exit;
    }

    /**
     * Return a description of the room's exits.
     * For example: "Exits: north east west"
     *
     * @ return A description of the available exits.
     */
    public String getExitString()
    {
        String exits = "Exits: ";
        if(salidasConSecciones.get("north") != null) {
            exits += "north ";
        }
        if(salidasConSecciones.get("east") != null) {
            exits += "east ";
        }
        if(salidasConSecciones.get("south") != null) {
            exits += "south ";
        }
        if(salidasConSecciones.get("west") != null) {
            exits += "west ";
        }
        if(salidasConSecciones.get("southEast") != null) {
            exits += "southEast ";
        }
        if(salidasConSecciones.get("northWest") != null) {
            exits += "northWest ";
        }
        return exits;
    }
}
