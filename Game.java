
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
    private Player jugador;

    /**
     * Create the game and initialise its internal map.
     */
    public Game() 
    {
        parser = new Parser();
        jugador = new Player(createRooms());
    }

    /**
     * Create all the rooms and link their exits together.
     */
    private Room createRooms()
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

        entrada.addItem("folletos de productos","folletos",2, true, false);
        dormitorios.addItem("cama de matrimonio","cama", 70, true, false);
        salones.addItem("sofa de piel","sofa", 85, true,  false);
        decoracion.addItem("cuadro de Picasso","cuadro", 5, false, false);
        dormitorios.addItem("armario para la entrada","armario", 30, true, false);
        cafeteria.addItem("cafe de Starbucks", "cafe", 1, true, true);

        return entrada;  // start game en la entrada
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
        CommandWord commandWord = command.getCommandWord();
        switch (commandWord) {
            case UNKNOWN:
            System.out.println("I don't know what you mean...");
            break;
            case HELP:
            printHelp();
            break;
            case GO:
            jugador.goRoom(command);
            jugador.look();
            break;
            case LOOK:
            jugador.look();
            break;
            case EAT:
            jugador.eat();
            break;
            case QUIT:
            wantToQuit = quit(command);
            break;
            case BACK:
            jugador.back();
            jugador.look();
            break;
            case TAKE:
            jugador.take(command);
            jugador.look();
            break;
            case ITEMS:
            jugador.items();
            break;
            case DROP:
            jugador.drop(command);
            jugador.look();
            break;
            case DRINK:
            jugador.drink(command);
            break;
        }

        if(command.isUnknown()) {
            System.out.println("I don't know what you mean...");
            return false;
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
        System.out.println(jugador.getCurrentRoom().getLongDescription());
        System.out.println();
    }

}
