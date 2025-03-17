package ve.edu.unet.nodosAST;

public class NodoAsignacionVector extends NodoBase {
    private String identificador;
    private NodoBase index;
    private NodoBase expresion;

    public NodoAsignacionVector(String identificador) {
        super();
        this.identificador = identificador;
        this.expresion = null;
        this.index = null;
    }

    public NodoAsignacionVector(String identificador, NodoBase index, NodoBase expresion) {
        super();
        this.identificador = identificador;
        this.index = index;
        this.expresion = expresion;
    }

    public String getIdentificador() {
        return identificador;
    }
    public void setIdentificador(String identificador) {
        this.identificador = identificador;
    }
    public NodoBase getIndex() {
        return index;
    }
    public void setIndex(NodoBase index) {
        this.index = index;
    }
    public NodoBase getExpresion() {
        return expresion;
    }
    public void setExpresion(NodoBase expresion) {
        this.expresion = expresion;
    }
}
