
package Code;

public class Main {
    public static void main(String[] args) {
        Lg lista = new Lg();
        String s = "(a,C,(e,b),B,d)";
        lista.consLg(s);
        System.out.println(lista.mostrarRecursivo());
    }
}
