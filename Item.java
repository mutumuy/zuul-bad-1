
public class Item
{
    private String descripcion;
    private int peso;

    public Item(String descripcion, int peso){
     this.descripcion = descripcion;
     this.peso = peso;
    }

    public String getDescripcion(){
        return descripcion;
    }

     public int getPeso(){
        return peso;
    }
    
    public String toString() {
        return getDescripcion() + "con un peso de " + getPeso() + " gramos";
    } 
}