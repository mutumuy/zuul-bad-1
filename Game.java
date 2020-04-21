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
        
    /**
     * Create the game and initialise its internal map.
     */
    public Game() 
    {
        createRooms();
        parser = new Parser();
    }

    /**
     * Create all the rooms and link their exits together.
     */
    private void createRooms()
    {
        Room Mercado, BaseCT, Plaza, Oscuro, Medio, Foso, Terraza, BaseT;
      
        // create the rooms
        Mercado = new Room("en un gran mercado");
        BaseCT = new Room("en el lugar de salida de los sanos");
        Plaza = new Room("en una plaza");
        Oscuro = new Room("en una callejuela entre los edificios");
        Medio = new Room("en la calle principal de la ciudad");
        Foso = new Room("en la rampa de acceso a un garaje");
        Terraza = new Room(" en la terraza de un bar");
        BaseT = new Room("en el lugar de salida de los infectados");
        
        // initialise room exits
        Mercado.setExit("east", BaseCT);
        Mercado.setExit("south", Oscuro);
        Mercado.setExit("pasadizo", Plaza);
        
        BaseCT.setExit("east", Plaza);
        BaseCT.setExit("west", Mercado);
        BaseCT.setExit("southEast", Foso);
        
        Plaza.setExit("south", Foso);
        Plaza.setExit("west", BaseCT);
        Plaza.setExit("pasadizo", Mercado);
        
        Oscuro.setExit("north", Mercado);
        Oscuro.setExit("east", Medio);
        Oscuro.setExit("south", Terraza);
        
        Medio.setExit("east", Foso);
        Medio.setExit("south", BaseT);
        Medio.setExit("west", Oscuro);
        
        Foso.setExit("north", Plaza);
        Foso.setExit("west", Medio);
        
        Terraza.setExit("north", Oscuro);
        Terraza.setExit("east", BaseT);
        
        BaseT.setExit("north", Medio);
        BaseT.setExit("west", Terraza);

        currentRoom = BaseT;  // start game outside
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
        System.out.println("Gracias por jugar.  Good bye.");
    }

    /**
     * Print out the opening message for the player.
     */
    private void printWelcome()
    {
        System.out.println();
        System.out.println("Bienvenido a The World of Zuul!");
        System.out.println("World of Zuul es un nuevo juego de aventuras increiblemente aburrido.");
        System.out.println("Escribe 'help' si necesitas ayuda.");
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
            System.out.println("No se que me quieres decir...");
            return false;
        }

        String commandWord = command.getCommandWord();
        if (commandWord.equals("help")) {
            printHelp();
        }
        else if (commandWord.equals("go")) {
            goRoom(command);
        }
        else if(commandWord.equals("look")){
            look();
        }
        else if(commandWord.equals("eat")){
            eat();
        }
        else if (commandWord.equals("quit")) {
            wantToQuit = quit(command);
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
        System.out.println("You are lost. You are alone. You wander");
        System.out.println("around at the university.");
        System.out.println();
        System.out.println("Your command words are:");
        System.out.println("   go quit help");
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
        Room nextRoom = currentRoom.getExit(direction);

        if (nextRoom == null) {
            System.out.println("There is no door!");
        }
        else {
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
    
    private void look() {
        System.out.println(currentRoom.getLongDescription());
    }
    
    private void eat() {
        System.out.println("You have eaten now and you are not hungry any more");
    }
    
    public void printLocationInfo(){
         System.out.println(currentRoom.getLongDescription());
         System.out.println();
    }
    
}
