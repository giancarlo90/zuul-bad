import java.util.Stack;
import java.util.ArrayList;

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
    private ArrayList<Item> bag;

    /**
     * Constructor for objects of class Player
     */
    public Player(Room habitacion)
    {
        currentRoom = habitacion;
        pila = new Stack<>();
        bag = new ArrayList<>();
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

    /**
     * Metodo para coger objetos
     */
    public void take(Command command){
        if(!command.hasSecondWord()) {
            // if there is no second word, we don't know what to take...
            System.out.println("Take what?");
            return;
        }

        String item = command.getSecondWord();
        if(currentRoom.lookForItems(item).getCoger() == true){
            bag.add(currentRoom.lookForItems(item));
            currentRoom.removeItem(currentRoom.lookForItems(item));
        }
        else{
            System.out.println(">>Este objeto no se puede coger");
        }
    }

    /**
     * Metodo que muestra los objetos que lleva el jugador en la mochila
     */
    public void items(){
        if(bag.size() > 0){ 
            for(int i = 0; i < bag.size(); i++){
                System.out.println("Tienes un/a(s) " + bag.get(i).getDescription() + " de " + bag.get(i).getWeight() + " kg.");
            }
        }
        else{
            System.out.println("No tienes objetos en la mochila.");
        }
    }
}
