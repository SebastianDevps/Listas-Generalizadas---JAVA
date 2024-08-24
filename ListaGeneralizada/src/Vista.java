import Code.Lg;
import Code.Nodo;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Vista extends JFrame {

    private Lg lg;
    private JTextArea inputArea;
    private JPanel outputPanel;

    public Vista() {
        lg = new Lg();
        setTitle("Visualización de Lista Generalizada");
        setSize(1000, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        JPanel namePanel = createNamePanel();
        JPanel inputPanel = createInputPanel();
        outputPanel = createOutputPanel();

        JScrollPane scrollPane = new JScrollPane(outputPanel);
        scrollPane.setPreferredSize(new Dimension(950, 400));

        add(namePanel, BorderLayout.NORTH);
        add(inputPanel, BorderLayout.CENTER);
        add(scrollPane, BorderLayout.SOUTH);
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

        inputArea = new JTextArea(3, 10);
        inputPanel.add(new JScrollPane(inputArea), BorderLayout.CENTER);

        JButton processButton = new JButton("Procesar");
        processButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                processInput();
            }
        });
        inputPanel.add(processButton, BorderLayout.SOUTH);
        return inputPanel;
    }

    private JPanel createOutputPanel() {
        JPanel panel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                drawListStructure(g, lg.getPunta(), 10, 30);
            }
        };
        panel.setBorder(BorderFactory.createTitledBorder("Salida"));
        panel.setBackground(Color.WHITE);
        return panel;
    }

    private void processInput() {
        String input = inputArea.getText().trim();
        if (input.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Por favor, ingrese una cadena.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        lg.consLg(input);
        outputPanel.repaint();
    }

    private void drawListStructure(Graphics g, Nodo current, int x, int y) {
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
                drawListStructure(g, sublist, nextX, y + 40);
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

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            Vista ui = new Vista();
            ui.setVisible(true);
        });
    }
}
