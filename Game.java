import java.util.Stack;
import java.util.ArrayList;
/**
 *  This class is the main class of the "World of Zuul" application. 
 *  "World of Zuul" is a very simple, text based adventure game.  Users 
 *  can walk around some scenery. That's all. It should really be extended 
 *  to make it more interesting!
 * 
 *  To play this game, create an instance of this class and call the "play"
 *  method.
 * 
 *  This main class creates and initialises all the others: it creates all
 *  rooms, creates the parser and starts the game.  It also evaluates and
 *  executes the commands that the parser returns.
 * 
 * @author  Michael Kölling and David J. Barnes
 * @version 2011.07.31
 */

public class Game 
{
    private Parser parser;
    private Room currentRoom;
    private Room previousRoom;
    private Stack<Room> pila;
    private ArrayList<Item> bag;
    private int pesoMaximo;
    private int pesoTransportado;

    /**
     * Create the game and initialise its internal map.
     */
    public Game() 
    {
        createRooms();
        parser = new Parser();
        pila = new Stack<>();
        bag = new ArrayList<>();
        pesoMaximo = 85;
        pesoTransportado = 0;
    }

    /**
     * Create all the rooms and link their exits together.
     */
    private void createRooms()
    {
        Room entrada, restaurante, cafeteria, dormitorios, salones, cocinas, banos,
        alfombras, sabanas, utensilios, decoracion, outlet, tienda, salida;

        // create the rooms
        entrada = new Room("Entrada de Ikea",null);
        restaurante = new Room("en el restaurante",null);
        cafeteria = new Room("en la cafeteria", null);
        dormitorios = new Room("en la seccion de dormitorios", null);
        salones = new Room("en la seccion de salones", null);
        cocinas = new Room("en la seccion de cocinas", null);
        banos = new Room("en la seccion de baños",null);
        alfombras = new Room("en la seccion de alfombras",null);
        sabanas = new Room("en la seccion de sabanas",null);
        utensilios = new Room("en la seccion de utensilios de cocina",null);
        decoracion = new Room("en la seccion de decoracion", null);
        outlet = new Room("en la seccion de productos rebajados",null);
        tienda = new Room("en la tienda de productos", null);
        salida = new Room("en la salida", null);

        // initialise room exits
        entrada.setExit("east", cafeteria);
        entrada.setExit("south", dormitorios);
        entrada.setExit("west",restaurante);
        restaurante.setExit("east", entrada);
        cafeteria.setExit("west", entrada);
        dormitorios.setExit("north", entrada);
        dormitorios.setExit("west",salones);
        salones.setExit("east", dormitorios);
        salones.setExit("south", cocinas);
        cocinas.setExit("north", salones);
        cocinas.setExit("east", banos);
        cocinas.setExit("south",utensilios);
        utensilios.setExit("north",cocinas);
        banos.setExit("east", alfombras);
        banos.setExit("south", decoracion);
        banos.setExit("west", cocinas);
        alfombras.setExit("south", outlet);
        alfombras.setExit("west", banos);
        alfombras.setExit("northWest", sabanas);
        sabanas.setExit("southEast", alfombras);
        outlet.setExit("north", alfombras);
        outlet.setExit("west",decoracion);
        outlet.setExit("southEast", tienda);
        tienda.setExit("nothWest", outlet);
        decoracion.setExit("north", banos);
        decoracion.setExit("east", outlet);
        decoracion.setExit("south", salida);
        salida.setExit("north", decoracion);

        entrada.addItem("folletos",2);
        dormitorios.addItem("cama", 70);
        salones.addItem("sofa", 85);
        decoracion.addItem("cuadro", 5);
        dormitorios.addItem("armario", 30);

        currentRoom = entrada;  // start game outside
    }

    /**
     *  Main play routine.  Loops until end of play.
     */
    public void play() 
    {            
        printWelcome();

        // Enter the main command loop.  Here we repeatedly read commands and
        // execute them until the game is over.

        boolean finished = false;
        while (! finished) {
            Command command = parser.getCommand();
            finished = processCommand(command);
        }
        System.out.println("Thank you for playing.  Good bye.");
    }

    /**
     * Print out the opening message for the player.
     */
    private void printWelcome()
    {
        System.out.println();
        System.out.println("Welcome to the World of Zuul!");
        System.out.println("World of Zuul is a new, incredibly boring adventure game.");
        System.out.println("Type 'help' if you need help.");
        System.out.println();
        printLocationInfo();
    }

    /**
     * Given a command, process (that is: execute) the command.
     * @param command The command to be processed.
     * @return true If the command ends the game, false otherwise.
     */
    private boolean processCommand(Command command) 
    {
        boolean wantToQuit = false;

        if(command.isUnknown()) {
            System.out.println("I don't know what you mean...");
            return false;
        }

        String commandWord = command.getCommandWord();
        if (commandWord.equals("help")) {
            printHelp();
        }
        else if (commandWord.equals("go")) {
            goRoom(command);
        }
        else if (commandWord.equals("look")) {
            look();
        }
        else if (commandWord.equals("eat")) {
            eat();
        }
        else if (commandWord.equals("quit")) {
            wantToQuit = quit(command);
        }
        else if (commandWord.equals("back")) {
            back();
        }
        else if (commandWord.equals("take")) {
            takeIntoBag(command);
        }
        else if (commandWord.equals("drop")) {
            drop(command);
        }
        else if (commandWord.equals("items")) {
            showItems();
        }

        return wantToQuit;
    }

    // implementations of user commands:

    /**
     * Print out some help information.
     * Here we print some stupid, cryptic message and a list of the 
     * command words.
     */
    private void printHelp() 
    {
        System.out.println("Your command words are:");
        System.out.println(parser.showCommands());
    }

    /** 
     * Try to go in one direction. If there is an exit, enter
     * the new room, otherwise print an error message.
     */
    private void goRoom(Command command) 
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
            printLocationInfo();
        }
    }

    /** 
     * "Quit" was entered. Check the rest of the command to see
     * whether we really quit the game.
     * @return true, if this command quits the game, false otherwise.
     */
    private boolean quit(Command command) 
    {
        if(command.hasSecondWord()) {
            System.out.println("Quit what?");
            return false;
        }
        else {
            return true;  // signal that we want to quit
        }
    }

    /**
     * Metodo para imprimir la informacion de la localizacion
     */
    private void printLocationInfo(){
        System.out.println(currentRoom.getLongDescription());
        System.out.println();
    }

    private void look() 
    {
        System.out.println(currentRoom.getLongDescription());
    }

    private void eat()
    {
        System.out.println("You have eaten now and you are not hungry any more");
    }

    private void back(){
        if(!pila.isEmpty()){
            currentRoom = pila.pop();
        }
        printLocationInfo();
    }

    /**
     * Metodo para coger objetos.
     * @param it    es el nombre del objeto a coger
     */
    private void takeIntoBag(Command command){
        if(!command.hasSecondWord()) {
            // if there is no second word, we don't know what to take...
            System.out.println("Take what?");
            return;
        }

        String item = command.getSecondWord();
        Item it = null;
        boolean objetoCogido = false; //variable para parar el bucle en caso de que coger el objeto
        boolean existeObjeto = false; //variable para comprobar si el objeto existe

        for(int i=0; i < currentRoom.getItems().size(); i++){
            if (currentRoom.getItems().get(i).getDescription().equals(item) && !objetoCogido) {
                it = currentRoom.getItems().get(i);
                int pesoObjetoActual = it.getWeight();
                existeObjeto = true;
                if((pesoTransportado + pesoObjetoActual) <= pesoMaximo){
                    bag.add(it);
                    pesoTransportado += pesoObjetoActual;
                    System.out.println("Has metido un/a(s) " + it.getDescription() + " de " + pesoObjetoActual +
                        " kg. Ahora tu mochila pesa: " + pesoTransportado + " kg.");
                    currentRoom.getItems().remove(it);
                    objetoCogido = true;
                    printLocationInfo();
                }
                else{
                    System.out.println("No puedes coger este objeto.");
                }
            }
        }
        if(existeObjeto == false)   {
            System.out.println("No existe este objeto en esta seccion.");
        }
    }

    /**
     * Metodo para soltar objetos.
     * @param it    es el nombre del objeto a coger
     */
    private void drop(Command command){
        if(!command.hasSecondWord()) {
            // if there is no second word, we don't know what to take...
            System.out.println("Drop what?");
            return;
        }
        String item = command.getSecondWord();
        Item it = null;
        boolean objetoSoltado = false;

        for(int i = 0; i < bag.size(); i++){
            if(bag.get(i).getDescription().equals(item) && !objetoSoltado){
                it = bag.get(i);
                int pesoObjetoActual = it.getWeight();
                pesoTransportado -= pesoObjetoActual;
                currentRoom.getItems().add(it);
                System.out.println("Has soltado un/a(s) " + it.getDescription() + " de " + pesoObjetoActual +
                    " kg. Ahora tu mochila pesa: " + pesoTransportado + " kg.");
                bag.remove(it);
                objetoSoltado = true;
                printLocationInfo();
            }
        }
    }

    /**
     * Metodo para mostrar los objetos que tenemos en la mochila
     */
    private void showItems(){
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
