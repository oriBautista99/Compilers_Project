package ve.edu.unet.nodosAST;

public class NodoDeclaracionVector extends NodoDeclaracion {

    private NodoBase size;

    public NodoDeclaracionVector(String tipo, String identificador, NodoBase size) {
        super(tipo, identificador, 0);
        this.size = size;
    }

    public NodoBase getSizeVector() {
        return size;
    }
}
