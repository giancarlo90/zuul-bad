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
    private int pesoTransportado;
    private int pesoMaximo; 

    /**
     * Constructor for objects of class Player
     */
    public Player(Room habitacion)
    {
        currentRoom = habitacion;
        pila = new Stack<>();
        bag = new ArrayList<>();
        pesoTransportado = 0;
        pesoMaximo = 80;
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
        if(currentRoom.lookForItems(item) != null){
            if((pesoTransportado + currentRoom.lookForItems(item).getWeight()) <= pesoMaximo){
                if(currentRoom.lookForItems(item).getCoger() == true){
                    bag.add(currentRoom.lookForItems(item));
                    pesoTransportado += currentRoom.lookForItems(item).getWeight();
                    currentRoom.removeItem(currentRoom.lookForItems(item));
                }
                else{
                    System.out.println(">>Este objeto no se puede coger");
                }
            }
            else{
                System.out.println("El objeto no cabe en tu mochila");
            }
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
        System.out.println("El peso total que llevas en tu mochila es de: " + pesoTransportado + " kg." );
    }

    /**
     * Metodo para coger objetos
     */
    public void drop(Command command){
        if(!command.hasSecondWord()) {
            // if there is no second word, we don't know what to drop...
            System.out.println("Drop what?");
            return;
        }

        String item = command.getSecondWord();
        Item itemActual = null;
        boolean existeObjeto = false; //variable para comprobar si el objeto existe
        int i = 0; //contador para el bucle

        while(i < bag.size() && !existeObjeto){
            if (bag.get(i).getId().equals(item)) {
                itemActual = bag.get(i);
                existeObjeto = true;
                bag.remove(itemActual);
                pesoTransportado -= itemActual.getWeight();
                currentRoom.addItems(itemActual);
            }
            i++;
        }
        if(existeObjeto == false)   {
            System.out.println("No existe este objeto en la mochila.");
        }
    }

    /**
     * Metodo para beber los objetos especiales
     */
    public void drink(Command command){
        if(!command.hasSecondWord()) {
            // if there is no second word, we don't know what to drink...
            System.out.println("Drink what?");
            return;
        }

        String item = command.getSecondWord();
        Item itemActual = null;
        boolean existeObjeto = false; //variable para comprobar si el objeto existe
        int i = 0; //contador para el bucle

        while(i < bag.size() && !existeObjeto){
            if (bag.get(i).getId().equals(item)) {
                itemActual = bag.get(i);
                existeObjeto = true;
                if(itemActual.getEspecial() == true){
                    bag.remove(itemActual);
                    pesoTransportado -= itemActual.getWeight();
                    pesoMaximo += 40;
                    System.out.println("El peso maximo que puedes transportar en tu mochila ha aumentado, ahora el peso maximo de tu mochila es de "+
                        pesoMaximo + " kg.");
                }
                else{
                    System.out.println("No se puede ejecutar el comando drink sobre este objeto.");
                }
            }
            i++;
        }
        if(existeObjeto == false)   {
            System.out.println("No existe este objeto en la mochila.");
        }
    }
}
