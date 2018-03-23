import java.util.Stack;
/**
 * Clase para implementar jugadores al juego
 * 
 * @author (Gian Carlo) 
 * @version (23/03/2018)
 */
public class Player
{
    // instance variables - replace the example below with your own
    private Room currentRoom;
    private Stack<Room> pila;

    /**
     * Constructor for objects of class Player
     */
    public Player(Room habitacion)
    {
        currentRoom = habitacion;
        pila = new Stack<>();
    }
    
    /**
     * Metodo que devuelve la posicion actual del jugador
     */
    public Room getCurrentRoom(){
        return currentRoom;
    }

    /** 
     * Try to go in one direction. If there is an exit, enter
     * the new room, otherwise print an error message.
     */
    public void goRoom(Command command) 
    {
        if(!command.hasSecondWord()) {
            // if there is no second word, we don't know where to go...
            System.out.println("Go where?");
            return;
        }

        String direction = command.getSecondWord();

        // Try to leave current room.
        Room nextRoom = null;
        nextRoom = currentRoom.getExit(direction);

        if (nextRoom == null) {
            System.out.println("There is no door!");
        }
        else {
            pila.push(currentRoom);
            currentRoom = nextRoom;
        }
    }
    
    /**
     * Metodo para ver la habitacion en la que estamos y los objetos que hay alrededor
     */
    public void look() 
    {
        System.out.println(currentRoom.getLongDescription());
    }

    /**
     * Metodo para dar de comer al jugador, hasta ahora se imprime por pantalla una respuesta que no tiene mas hambre
     */
    public void eat()
    {
        System.out.println("You have eaten now and you are not hungry any more");
    }

    /**
     * Metodo para volver a la habitacion anterior
     */
    public void back(){
        if(!pila.isEmpty()){
            currentRoom = pila.pop();
        }
    }
}
