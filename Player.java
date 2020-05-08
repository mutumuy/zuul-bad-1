import java.util.Stack;
import java.util.ArrayList;

/**
 * Clase que representa al jugador, en ella se guardara toda la informacion
 * referente al jugador.
 */
public class Player {
    private ArrayList<Item> objetos;
    private Room currentRoom;
    private Stack<Room> previousRoom;

    public Player() {
        currentRoom = null;
        previousRoom = new Stack<>();
        objetos = new ArrayList<>();
    }

    public void setCurrentRoom(Room sala) {
        currentRoom = sala;
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
        if (currentRoom.getExit(direction) == null) {
            System.out.println("There is no door!");
        }
        else {
            previousRoom.add(currentRoom);
            currentRoom = currentRoom.getExit(direction);
            look();
        }
    }

    /**
     * Te envia a la sala en la que estabas, execpto al 
     * inicio del juego.
     */
    public void backRoom() {
        if(!previousRoom.empty()) {
            currentRoom = previousRoom.get(previousRoom.size()-1);
            System.out.println(currentRoom.getLongDescription());
            previousRoom.remove(previousRoom.size()-1);
        }
        else {
            System.out.println("No hay lugar al que volver.");
        }
    }

    /**
     * Imprime por pantalla el siguiente mensaje
     * You have eaten now and you are not hungry any more
     */
    public void eat() {
        System.out.println("You have eaten now and you are not hungry any more");
    }
    
        public void take(Command command) 
    {
        if(!command.hasSecondWord()) {
            // if there is no second word, we don't know the item to take...
            System.out.println("No has indicado el ID del arma");
            return;
        }
        String positionItem = command.getSecondWord();
        Item itemToTake = currentRoom.getItem(positionItem);

        if (itemToTake != null){
            System.out.println("Has cogido " + "\n");
            System.out.println(itemToTake.getDescripcion() + "con un peso de " + itemToTake.getPeso()+ " gramos");
            currentRoom.removeItem(itemToTake);
        }

        else{
            if (itemToTake == null){
                System.out.println("No hay armas en la habitacion");
            }
            else{
                System.out.println("Te has pasado del peso de la mochila");
            }
        }
    }

    /**
     * Inprime por pantalla la informacion completa de la sala
     * en la que te encuentras
     */
    public void look() {
        System.out.println(currentRoom.getLongDescription());
    }

} 