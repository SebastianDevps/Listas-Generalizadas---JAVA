package Code;

public class Nodo {

    private int swiche;
    private Object dato;
    private Nodo ligaSig;

    public Nodo() {
        this.ligaSig = null;
    }

    public int getSwiche() {
        return swiche;
    }

    public void setSwiche(int swiche) {
        this.swiche = swiche;
    }

    public Object getDato() {
        return dato;
    }

    public void setDato(Object dato) {
        this.dato = dato;
    }

    public Nodo getLigaSig() {
        return ligaSig;
    }

    public void setLigaSig(Nodo liga) {
        this.ligaSig = liga;
    }

}

