package Code;

import java.util.LinkedList;
import java.util.Queue;
import java.util.Stack;
import javax.swing.JOptionPane;

public class Lg {

    private Nodo punta;
    private Nodo ultimo;
    public StringBuilder sinExpandir = new StringBuilder();

    public Lg() {
        this.punta = null;
        this.ultimo = null;
    }

    public Nodo getPunta() {
        return punta;
    }

    public void setPunta(Nodo punta) {
        this.punta = punta;
    }

    public Nodo getUltimo() {
        return ultimo;
    }

    public void setUltimo(Nodo ultimo) {
        this.ultimo = ultimo;
    }

    public void consLg(String s) {
        sinExpandir.append(s);
        Stack<Nodo> pila = new Stack<>();
        Nodo x = new Nodo();
        punta = x;
        ultimo = x;
        int n = s.length();
        for (int i = 0; i < n; i++) {
            switch (s.charAt(i)) {
                case '(':
                    pila.push(ultimo);
                    x = new Nodo();
                    ultimo.setSwiche(1);
                    ultimo.setDato(x);
                    ultimo = x;
                    break;
                case ',':
                    x = new Nodo();
                    ultimo.setLigaSig(x);
                    ultimo = x;
                    break;
                case ')':
                    ultimo = pila.pop();
                    break;
                default:
                    if (s.charAt(i) == Character.toLowerCase(s.charAt(i))) {
                        ultimo.setSwiche(0);
                        ultimo.setDato(s.charAt(i));
                    } else {
                        String subL = JOptionPane.showInputDialog("Ingrese sublista " + s.charAt(i));
                        Nodo sub = new Nodo();
                        ultimo.setSwiche(1);
                        ultimo.setDato(sub);
                        Stack<Nodo> nuevaPila = new Stack<>();
                        Nodo nuevoUltimo = sub;
                        for (char nC : subL.toCharArray()) {
                            if (nC == '(') {
                                nuevaPila.push(nuevoUltimo);
                                Nodo y = new Nodo();
                                nuevoUltimo.setSwiche(1);
                                nuevoUltimo.setDato(y);
                                nuevoUltimo = y;
                            } else if (nC == ',') {
                                Nodo y = new Nodo();
                                nuevoUltimo.setLigaSig(y);
                                nuevoUltimo = y;
                            } else if (nC == ')') {
                                nuevoUltimo = nuevaPila.pop();
                            } else {
                                nuevoUltimo.setSwiche(0);
                                nuevoUltimo.setDato(nC);
                            }
                        }
                    }
                    break;
            }
        }
    }

    public String mostrarLgSinExpandir() {
        return sinExpandir.toString();
    }

    public String mostrarLgExpandida() {
        if (punta == null) {
            return "vacio";
        } else {
            return mostrarLgExpandida(punta);
        }
    }

    private String mostrarLgExpandida(Nodo p) {
        StringBuilder resultado = new StringBuilder();
        while (p != null) {
            if (p.getSwiche() == 0) {
                resultado.append(p.getDato()).append(",");
            } else if (p.getSwiche() == 1) {
                resultado.append("(");
                resultado.append(mostrarLgExpandida((Nodo) p.getDato()));
                resultado.append("),");
            }
            p = p.getLigaSig();
        }
        return resultado.toString().replaceAll(",$", "");
    }

    public String mostrarListaLigada() {
        if (punta == null) {
            return "Lista vacía";
        } else {
            return mostrarListaLigada(punta);
        }
    }

    private String mostrarListaLigada(Nodo p) {
        StringBuilder resultado = new StringBuilder();
        while (p != null) {
            if (p.getSwiche() == 0) {
                resultado.append(p.getDato());
            } else if (p.getSwiche() == 1) {
                resultado.append("(Sublista: ");
                resultado.append(mostrarListaLigada((Nodo) p.getDato()));
                resultado.append(")");
            }
            p = p.getLigaSig();
            if (p != null) {
                resultado.append(" -> ");
            }
        }
        return resultado.toString();
    }

//    public Nodo crearListaLigada() {
//        return crearListaLigada(punta);
//    }
//
//    private Nodo crearListaLigada(Nodo nodo) {
//        if (nodo == null) {
//            return null;
//        }
//
//        Nodo cabeza = new Nodo();
//        Nodo actual = cabeza;
//
//        while (nodo != null) {
//            if (nodo.getSwiche() == 0) {
//                actual.setSwiche(0);
//                actual.setDato(nodo.getDato());
//            } else if (nodo.getSwiche() == 1) {
//                actual.setSwiche(1);
//                actual.setDato(crearListaLigada((Nodo) nodo.getDato()));
//            }
//
//            nodo = nodo.getLigaSig();
//            if (nodo != null) {
//                Nodo siguiente = new Nodo();
//                actual.setLigaSig(siguiente);
//                actual = siguiente;
//            }
//        }
//
//        return cabeza;
//    }

//    public Nodo crearArbol() {
//        return crearArbol(punta);
//    }
//
//    private Nodo crearArbol(Nodo nodo) {
//        if (nodo == null) {
//            return null;
//        }
//
//        Nodo raiz = new Nodo();
//        raiz.setSwiche(nodo.getSwiche());
//        raiz.setDato(nodo.getDato());
//
//        if (nodo.getSwiche() == 1) {
//            Nodo hijoIzquierdo = crearArbol((Nodo) nodo.getDato());
//            raiz.setLigaSig(hijoIzquierdo);
//
//            Nodo hermanoDerecho = crearArbol(nodo.getLigaSig());
//            if (hijoIzquierdo != null) {
//                hijoIzquierdo.setLigaSig(hermanoDerecho);
//            } else {
//                raiz.setLigaSig(hermanoDerecho);
//            }
//        }
//
//        return raiz;
//    }

    public String mostrarArbolALoAncho() {
        if (punta == null) {
            return "Árbol vacío";
        } else {
            return mostrarArbolALoAncho(punta);
        }
    }

    private String mostrarArbolALoAncho(Nodo nodo) {
        StringBuilder resultado = new StringBuilder();
        Queue<Nodo> cola = new LinkedList<>();
        cola.add(nodo);

        while (!cola.isEmpty()) {
            Nodo actual = cola.poll();
            if (actual.getSwiche() == 0) {
                resultado.append(actual.getDato()).append(" ");
            } else {
                Nodo sublista = (Nodo) actual.getDato();
                cola.add(sublista);
            }

            if (actual.getLigaSig() != null) {
                cola.add(actual.getLigaSig());
            }
        }

        return resultado.toString().trim();
    }

}
