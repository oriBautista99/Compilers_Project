package ve.edu.unet.nodosAST;

public class NodoFor extends NodoBase {

    private String variable;
    private NodoBase init;
    private NodoBase end;
    private NodoBase sent;

    public NodoFor(String variable, NodoBase init, NodoBase end, NodoBase sent) {
        super();
        this.variable = variable;
        this.init = init;
        this.end = end;
        this.sent = sent;
    }

    public NodoFor(){
        super();
        this.variable = null;
        this.init = null;
        this.end = null;
        this.sent = null;
    }

    public String getVariable() {
        return variable;
    }
    public void setVariable(String variable) {
        this.variable = variable;
    }
    public NodoBase getInit() {
        return init;
    }
    public void setInit(NodoBase init) {
        this.init = init;
    }
    public NodoBase getEnd() {
        return end;
    }
    public void setEnd(NodoBase end) {
        this.end = end;
    }
    public NodoBase getSent() {
        return sent;
    }
    public void setSent(NodoBase sent) {
        this.sent = sent;
    }
}
