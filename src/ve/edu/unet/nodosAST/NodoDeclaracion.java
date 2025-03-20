package ve.edu.unet.nodosAST;

public class NodoDeclaracion extends NodoBase{
    private String type;
    private String name;
    private int size;

    public NodoDeclaracion(String type, String name, int size){
        super();
        this.type = type;
        this.name = name;
        this.size = size;
    }

    public NodoDeclaracion(){
        super();
        this.type = null;
        this.name = null;
        this.size = 0;
    }

    public String getType() {
        return type;
    }
    public String getName() {
        return name;
    }
    public int getSize() {
        return size;
    }
    public void setType(String type) {
        this.type = type;
    }
    public void setName(String name) {
        this.name = name;
    }
    public void setSize(int size) {
        this.size = size;
    }
}
