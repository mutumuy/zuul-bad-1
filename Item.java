
public class Item
{
    private String id;
    private String descripcion;
    private int peso;

    public Item(String id, String descripcion, int peso){
     this.descripcion = descripcion;
     this.peso = peso;
     this.id = id;
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
    
    public String toString() {
        return getDescripcion() + "con un peso de " + getPeso() + " gramos";
    } 
}