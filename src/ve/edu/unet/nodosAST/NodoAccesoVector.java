package ve.edu.unet.nodosAST;

public class NodoAccesoVector extends NodoBase {
    private String identificador;
    private NodoBase indice;

    public NodoAccesoVector(String identificador, NodoBase indice) {
        this.identificador = identificador;
        this.indice = indice;
    }

    public String getIdentificador() {
        return identificador;
    }

    public NodoBase getIndice() {
        return indice;
    }

}
