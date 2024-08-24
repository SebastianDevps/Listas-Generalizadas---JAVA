package Code;

import java.util.Stack;
import javax.swing.JOptionPane;

public class Lg {

    private Nodo punta;
    private Nodo ultimo;

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
//                        String subLVal = "";
//                        for (char Validar : subL.toCharArray()) {
//                            if (!(Validar != Character.toLowerCase(Validar))) {
//                                subLVal += Validar;
//                            }
//                            System.out.println(subLVal);
//                        }
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

    public String mostrarRecursivo() {
        if (punta == null) {
            return "vacio";
        } else {
           return mostrarRecursivo(punta);
        }

    }

    private String mostrarRecursivo(Nodo p) {
        StringBuilder resultado = new StringBuilder();
        while (p != null) {
            if (p.getSwiche() == 0) {
                resultado.append(p.getDato()).append(",");
            } else if (p.getSwiche() == 1) {
                resultado.append("( ");
                resultado.append(mostrarRecursivo((Nodo) p.getDato()));
                resultado.append(")");
                if (p.getLigaSig() == null) {
                    break;
                }
            }
            p = p.getLigaSig();
        }
        return resultado.toString();
    }
}


