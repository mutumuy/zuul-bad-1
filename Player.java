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
    private int pesoMaximo = 1500;
    private int pesoDisponible = pesoMaximo;

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
    
    public void take(Command command) {
        if(!command.hasSecondWord()) {
            // if there is no second word, we don't know the item to take...
            System.out.println("No has indicado el ID del arma");
            return;
        }
        String positionItem = command.getSecondWord();
        Item itemToTake = currentRoom.getItem(positionItem);

        if (itemToTake != null && itemToTake.getPeso() < pesoDisponible){
            System.out.println("Has cogido " + "\n");
            System.out.println(itemToTake.getDescripcion() + "con un peso de " + itemToTake.getPeso()+ " gramos");
            objetos.add(itemToTake);
            pesoDisponible -= itemToTake.getPeso();
            currentRoom.removeItem(itemToTake);
        }

        else{
            if (itemToTake == null){
                System.out.println("No hay objetos en la habitacion");
            }
            else{
                System.out.println("No puedes llevar un objeto tan pesado");
            }
        }
    }
    
    public void items(){
        if (objetos.size() > 0){
            System.out.println("Llevas: ");
            for (int i = 0; i < objetos.size(); i++){
                System.out.println(objetos.get(i).getDescripcion() + "con un peso de " + objetos.get(i).getPeso()+ " gramos");
            }
        }
        else{
            System.out.println("No llevas ningun objeto");
        }
    }
    
        public void drop(Command command){
        if(!command.hasSecondWord()) {
            // if there is no second word, we don't know where to go...
            System.out.println("Drop what?");
            return;
        }
        else{
            int contador = 0;
            boolean encontrado = false;
            while(!encontrado && contador < objetos.size()){
                if(objetos.get(contador).getDescripcion().contains(command.getSecondWord())){
                    encontrado = true;
                    contador --;
                }
                contador ++;
            }
            if(!encontrado){
                System.out.println("No tienes ese objeto en el inventario");
                System.out.println();
            }
            else{
                System.out.println("Has soltado " + objetos.get(contador).toString());
                System.out.println();
                currentRoom.addItem(objetos.get(contador).getId(), objetos.get(contador).getDescripcion(), objetos.get(contador).getPeso(), objetos.get(contador).getEquipable());
                pesoDisponible += objetos.get(contador).getPeso();
                objetos.remove(contador);
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