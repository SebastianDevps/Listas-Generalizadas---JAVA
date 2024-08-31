
package Code;
import javax.swing.JOptionPane;

public class Main {
    public static void main(String[] args) {
        
        Lg lista = new Lg();
        String s = JOptionPane.showInputDialog("Ingrese lo que le de la gana.");
        lista.consLg(s);
        System.out.println("Lg Sin expandir: "+lista.mostrarLgSinExpandir());
        System.out.println("Lg Expandida: "+lista.mostrarLgExpandida());
        System.out.println("Lista Ligada: "+lista.mostrarListaLigada());
        System.out.println("Arbol a lo ancho: "+ lista.mostrarArbolALoAncho());
    }
}
