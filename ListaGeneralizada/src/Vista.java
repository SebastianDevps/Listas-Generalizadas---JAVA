
import Code.Lg;
import Code.Nodo;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Vista extends JFrame {

    private Lg lg;
    private JTextArea inputArea;
    private JTabbedPane tabbedPane;
    private Nodo rootNode;

    public Vista() {
        lg = new Lg();
        setTitle("Visualización de Lista Generalizada");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        JPanel namePanel = createNamePanel();
        JPanel inputPanel = createInputPanel();
        tabbedPane = createTabbedPane();

        add(namePanel, BorderLayout.NORTH);
        add(inputPanel, BorderLayout.NORTH);
        add(tabbedPane, BorderLayout.CENTER);
    }

    private JPanel createNamePanel() {
        JPanel namePanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        JLabel nameLabel1 = new JLabel("Juan Sebastian Guerra Gonzalez   Y   ");
        JLabel nameLabel2 = new JLabel("Kaleth Alexander Lopez Atencia");
        nameLabel1.setFont(new Font("Arial", Font.BOLD, 14));
        nameLabel2.setFont(new Font("Arial", Font.BOLD, 14));
        namePanel.add(nameLabel1);
        namePanel.add(nameLabel2);
        return namePanel;
    }

    private JPanel createInputPanel() {
        JPanel inputPanel = new JPanel(new BorderLayout());
        inputPanel.setBorder(BorderFactory.createTitledBorder("Ingrese la cadena"));

        inputArea = new JTextArea(2, 30);
        inputPanel.add(new JScrollPane(inputArea), BorderLayout.CENTER);

        JButton processButton = new JButton("Procesar");
        processButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                processInput();
            }
        });
        inputPanel.add(processButton, BorderLayout.EAST);
        return inputPanel;
    }

    private JTabbedPane createTabbedPane() {
        JTabbedPane tabbedPane = new JTabbedPane();

        JPanel sinExpandirPanel = createDrawingPanel("SinExpandir");
        JPanel expandidaPanel = createDrawingPanel("Expandida");
        JPanel listaLigadaPanel = createDrawingPanel("ListaLigada");
        JPanel arbolPanel = createDrawingPanel("Arbol");

        tabbedPane.addTab("Sin Expandir", new JScrollPane(sinExpandirPanel));
        tabbedPane.addTab("Expandida", new JScrollPane(expandidaPanel));
        tabbedPane.addTab("Lista Ligada", new JScrollPane(listaLigadaPanel));
        tabbedPane.addTab("Árbol", new JScrollPane(arbolPanel));

        return tabbedPane;
    }

    private JPanel createDrawingPanel(String mode) {
        DrawingPanel panel = new DrawingPanel(mode);
        panel.setPreferredSize(new Dimension(1600, 1200));
        JScrollPane scrollPane = new JScrollPane(panel);
        scrollPane.setPreferredSize(new Dimension(880, 580));
        return panel;
    }

    private void processInput() {
        String input = inputArea.getText().trim();
        if (input.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Por favor, ingrese una cadena.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        lg.consLg(input);
        rootNode = lg.getPunta();
        updateViews();
    }

    private void updateViews() {
        for (int i = 0; i < tabbedPane.getTabCount(); i++) {
            Component component = tabbedPane.getComponentAt(i);
            if (component instanceof JScrollPane) {
                JScrollPane scrollPane = (JScrollPane) component;
                JViewport viewport = scrollPane.getViewport();
                if (viewport.getView() instanceof DrawingPanel) {
                    DrawingPanel panel = (DrawingPanel) viewport.getView();
                    panel.updateView();
                }
            }
        }
    }

    private class DrawingPanel extends JPanel {

        private String mode;

        public DrawingPanel(String mode) {
            this.mode = mode;
            setBackground(Color.WHITE);
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            if (rootNode != null) {
                switch (mode) {
                    case "SinExpandir":
                        drawSinExpandir(g, lg.sinExpandir, 50, 50);
                        break;
                    case "Expandida":
                        drawExpandida(g, rootNode, 50, 50);
                        break;
                    case "ListaLigada":
                        drawListaLigada(g, rootNode, 50, 50);
                        break;
                    case "Arbol":
                        drawArbol(g, rootNode, getWidth() / 2, 50, 0);
                        break;
                }
            }
        }

        public void updateView() {
            revalidate();
            repaint();
        }

        private void drawSinExpandir(Graphics g, StringBuilder sinExpandir, int x, int y) {
            Font font = new Font("Arial", Font.PLAIN, 14);
            g.setFont(font);

            int rectWidth = 80;
            int rectHeight = 30;
            int currentX = x;
            int currentY = y;

            String[] items = sinExpandir.toString().split(",");
            int itemCount = items.length;

            for (int i = 0; i < itemCount; i++) {
                String item = items[i].trim();
                int nextX = currentX + rectWidth + 80;

                if (isSublista(item)) {
                    g.setColor(Color.CYAN);
                    g.fillRect(currentX, currentY, rectWidth, rectHeight);
                    g.setColor(Color.BLACK);
                    g.drawRect(currentX, currentY, rectWidth, rectHeight);
                    g.drawString("Sublista: " + item, currentX + 5, currentY + 20);

                } else {
                    g.setColor(Color.YELLOW);
                    g.fillRect(currentX, currentY, rectWidth, rectHeight);
                    g.setColor(Color.BLACK);
                    g.drawRect(currentX, currentY, rectWidth, rectHeight);
                    g.drawString("Dato: " + item, currentX + 5, currentY + 20);
                }

                if (i < itemCount - 1) {
                    g.setColor(Color.BLACK);
                    g.drawLine(currentX + rectWidth, currentY + rectHeight / 2, nextX - 30, currentY + rectHeight / 2);
                    g.drawLine(nextX - 35, currentY + rectHeight / 2 - 5, nextX - 30, currentY + rectHeight / 2);
                    g.drawLine(nextX - 35, currentY + rectHeight / 2 + 5, nextX - 30, currentY + rectHeight / 2);
                }

                currentX = nextX;
            }
        }

        private boolean isSublista(String item) {
            return item.length() == 1 && Character.isUpperCase(item.charAt(0));
        }

        private void drawExpandida(Graphics g, Nodo current, int x, int y) {
            Font font = new Font("Arial", Font.PLAIN, 14);
            g.setFont(font);

            while (current != null) {
                int rectWidth = 80;
                int rectHeight = 30;
                int nextX = x + rectWidth + 80;

                if (current.getSwiche() == 0) {
                    g.setColor(Color.YELLOW);
                    g.fillRect(x, y, rectWidth, rectHeight);
                    g.setColor(Color.BLACK);
                    g.drawRect(x, y, rectWidth, rectHeight);
                    g.drawString("Dato: " + current.getDato(), x + 5, y + 20);
                } else if (current.getSwiche() == 1) {
                    g.setColor(Color.CYAN);
                    g.fillRect(x, y, rectWidth, rectHeight);
                    g.setColor(Color.BLACK);
                    g.drawRect(x, y, rectWidth, rectHeight);
                    g.drawString("Sublista", x + 5, y + 20);

                    g.setColor(Color.BLACK);
                    g.drawLine(x + rectWidth / 2, y + rectHeight, x + rectWidth / 2, y + 55);
                    g.drawLine(x + rectWidth / 2, y + 55, nextX, y + 55);

                    Nodo sublist = (Nodo) current.getDato();
                    drawExpandida(g, sublist, nextX, y + 40);
                }

                if (current.getLigaSig() != null) {
                    g.setColor(Color.BLACK);
                    g.drawLine(x + rectWidth, y + rectHeight / 2, nextX - 30, y + rectHeight / 2);
                    g.drawLine(nextX - 35, y + rectHeight / 2 - 5, nextX - 30, y + rectHeight / 2);
                    g.drawLine(nextX - 35, y + rectHeight / 2 + 5, nextX - 30, y + rectHeight / 2);
                }

                x = nextX;
                current = current.getLigaSig();
            }
        }

        private void drawListaLigada(Graphics g, Nodo current, int x, int y) {
            Font font = new Font("Arial", Font.PLAIN, 14);
            g.setFont(font);

            int rectWidth = 80;
            int rectHeight = 30;
            int baseX = x;
            int baseY = y;

            while (current != null) {
                int nextX = x + rectWidth + 80; 

                if (current.getSwiche() == 0) {
                    g.setColor(Color.YELLOW);
                    g.fillRect(x, y, rectWidth, rectHeight);
                    g.setColor(Color.BLACK);
                    g.drawRect(x, y, rectWidth, rectHeight);
                    g.drawString("Dato: " + current.getDato(), x + 5, y + 20);
                } else if (current.getSwiche() == 1) {
                    g.setColor(Color.CYAN);
                    g.fillRect(x, y, rectWidth, rectHeight);
                    g.setColor(Color.BLACK);
                    g.drawRect(x, y, rectWidth, rectHeight);
                    g.drawString("Sublista", x + 5, y + 20);

                    g.setColor(Color.BLACK);
                    g.drawLine(x + rectWidth / 2, y + rectHeight, x + rectWidth / 2, y + 55);
                    g.drawLine(x + rectWidth / 2, y + 55, nextX, y + 55);

                    Nodo sublist = (Nodo) current.getDato();
                    drawExpandida(g, sublist, nextX, y + 40);
                }

                if (current.getLigaSig() != null) {
                    g.setColor(Color.BLACK);
                    g.drawLine(x + rectWidth, y + rectHeight / 2, nextX - 30, y + rectHeight / 2);
                    g.drawLine(nextX - 35, y + rectHeight / 2 - 5, nextX - 30, y + rectHeight / 2);
                    g.drawLine(nextX - 35, y + rectHeight / 2 + 5, nextX - 30, y + rectHeight / 2);
                }

                x = nextX;
                current = current.getLigaSig();
            }
        }

       private void drawArbol(Graphics g, Nodo current, int x, int y, int level) {
    if (current == null) {
        return;
    }

    int rectWidth = 80;
    int rectHeight = 30;
    int childOffset = 120 / (level + 1);  

    // Convertir el dato a texto usando String.valueOf()
    String datoTexto = String.valueOf(current.getDato()); 

    // Dibuja el nodo actual
    g.setColor(Color.GREEN);
    g.fillRect(x - rectWidth / 2, y, rectWidth, rectHeight);
    g.setColor(Color.BLACK);
    g.drawRect(x - rectWidth / 2, y, rectWidth, rectHeight);
    g.drawString("Dato: " + datoTexto, x - rectWidth / 2 + 5, y + 20);

    if (current.getSwiche() == 1) {
        Nodo sublist = (Nodo) current.getDato();
        int childX = x - childOffset;

        Nodo child = sublist;
        while (child != null) {
            // Dibuja una línea de conexión desde el nodo actual al nodo hijo
            g.setColor(Color.BLACK);
            g.drawLine(x, y + rectHeight, childX, y + 70);

            // Dibuja el nodo hijo
            drawArbol(g, child, childX, y + 70, level + 1);

            // Ajusta la posición para el siguiente nodo hijo
            childX += childOffset;
            child = child.getLigaSig();
        }
    }
}

    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            Vista ui = new Vista();
            ui.setVisible(true);
        });
    }
}
