import java.util.HashMap;
/**
 * This class is part of the "World of Zuul" application. 
 * "World of Zuul" is a very simple, text based adventure game.  
 * 
 * This class holds an enumeration of all command words known to the game.
 * It is used to recognise commands as they are typed in.
 *
 * @author  Michael Kölling and David J. Barnes
 * @version 2011.07.31
 */

public class CommandWords
{
    // a constant array that holds all valid command words
    private HashMap<String,CommandWord> validCommands;

    /**
     * Constructor - initialise the command words.
     */
    public CommandWords()
    {
        validCommands = new HashMap<>();
        validCommands.put("go", CommandWord.GO);
        validCommands.put("help", CommandWord.HELP);
        validCommands.put("quit", CommandWord.QUIT);
        validCommands.put("eat", CommandWord.EAT);
        validCommands.put("look", CommandWord.LOOK);
        validCommands.put("back", CommandWord.BACK);
        validCommands.put("take", CommandWord.TAKE);
        validCommands.put("items", CommandWord.ITEMS);
        validCommands.put("drop", CommandWord.DROP);
        validCommands.put("drink", CommandWord.DRINK);
        validCommands.put("unknown", CommandWord.UNKNOWN);
    }

    /**
     * Check whether a given String is a valid command word. 
     * @return true if a given string is a valid command,
     * false if it isn't.
     */
    public boolean isCommand(String aString)
    {
        for(int i = 0; i < validCommands.size(); i++) {
            if(validCommands.containsKey(aString))
                return true;
        }
        // if we get here, the string was not found in the commands
        return false;
    }

    /**
     * Imprime por pantalla todos los comandos válidos
     */
    public String getCommandList()
    {
        String aDevolver = "";
        for (String command : validCommands.keySet()) {
            aDevolver += command + " ";
        }
        return aDevolver;
    }

    /**
     * Return the CommandWord associated with a word.
     * @param commandWord The word to look up (as a string).
     * @return The CommandWord corresponding to the String commandWord, or UNKNOWN
     *         if it is not a valid command word.
     */
    public CommandWord getCommandWord(String commandWord){
        return validCommands.get(commandWord);
    }
}
