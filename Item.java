
public class Item
{
    private String id;
    private String descripcion;
    private int peso;
    private boolean equipable;

    public Item(String id, String descripcion, int peso, boolean equipable){
     this.descripcion = descripcion;
     this.peso = peso;
     this.id = id;
     this.equipable = equipable;
    }

    public String getDescripcion(){
        return descripcion;
    }

    public int getPeso(){
        return peso;
    }
    
    public String getId(){
        return id;
    }
    
    public boolean getEquipable(){
        return equipable;
    }
    
    public String toString() {
        return getDescripcion() + "con un peso de " + getPeso() + " gramos";
    } 
}