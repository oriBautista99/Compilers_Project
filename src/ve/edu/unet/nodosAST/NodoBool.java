package ve.edu.unet.nodosAST;

public class NodoBool extends NodoBase {
    private Boolean valor;

    public NodoBool(Boolean valor) {
        super();
        this.valor = valor;
    }

    public NodoBool() {
        super();
    }

    public Boolean getValor() {
        return valor;
    }
}
