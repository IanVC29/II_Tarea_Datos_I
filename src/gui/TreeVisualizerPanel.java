package gui;

import benchmark.BenchmarkConfig;
import structures.AVLTree;
import structures.BSTree;
import structures.RedBlackTree;
import structures.SplayTree;

import javax.swing.JPanel;
import javax.swing.JComboBox;
import javax.swing.JButton;
import javax.swing.JTextArea;
import javax.swing.JScrollPane;
import javax.swing.JLabel;
import javax.swing.BorderFactory;
import javax.swing.JOptionPane;
import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.FlowLayout;
import java.awt.Font;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

/**
 * Panel para visualizar dos arboles lado a lado.
 *
 * Permite escoger dos estructuras entre:
 * - BST
 * - AVL
 * - Splay
 * - Red-Black
 *
 * Luego inserta la misma secuencia de datos en ambos arboles
 * y muestra su estructura final.
 */
public class TreeVisualizerPanel extends JPanel {

    private BenchmarkConfig config;

    private JComboBox<String> leftTreeComboBox;
    private JComboBox<String> rightTreeComboBox;

    private JTextArea leftTreeArea;
    private JTextArea rightTreeArea;

    private JButton visualizeButton;

    /**
     * Constructor del visualizador.
     *
     * @param config configuracion actual del benchmark.
     */
    public TreeVisualizerPanel(BenchmarkConfig config) {
        this.config = config;

        setLayout(new BorderLayout());

        createComponents();
    }

    /**
     * Crea los componentes visuales del panel.
     */
    private void createComponents() {
        JPanel topPanel = new JPanel(new FlowLayout());

        leftTreeComboBox = new JComboBox<>(new String[]{"BST", "AVL", "Splay", "Red-Black"});
        rightTreeComboBox = new JComboBox<>(new String[]{"BST", "AVL", "Splay", "Red-Black"});

        rightTreeComboBox.setSelectedItem("AVL");

        visualizeButton = new JButton("Visualizar");

        visualizeButton.addActionListener(event -> visualizeTrees());

        topPanel.add(new JLabel("Arbol izquierdo:"));
        topPanel.add(leftTreeComboBox);

        topPanel.add(new JLabel("Arbol derecho:"));
        topPanel.add(rightTreeComboBox);

        topPanel.add(visualizeButton);

        leftTreeArea = createTreeTextArea();
        rightTreeArea = createTreeTextArea();

        JScrollPane leftScrollPane = new JScrollPane(leftTreeArea);
        JScrollPane rightScrollPane = new JScrollPane(rightTreeArea);

        leftScrollPane.setBorder(BorderFactory.createTitledBorder("Arbol izquierdo"));
        rightScrollPane.setBorder(BorderFactory.createTitledBorder("Arbol derecho"));

        JPanel centerPanel = new JPanel(new GridLayout(1, 2));
        centerPanel.add(leftScrollPane);
        centerPanel.add(rightScrollPane);

        add(topPanel, BorderLayout.NORTH);
        add(centerPanel, BorderLayout.CENTER);
    }

    /**
     * Crea un area de texto para mostrar un arbol.
     *
     * @return area de texto configurada.
     */
    private JTextArea createTreeTextArea() {
        JTextArea textArea = new JTextArea();
        textArea.setEditable(false);
        textArea.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 14));
        return textArea;
    }

    /**
     * Inserta los datos en los dos arboles seleccionados y los muestra.
     */
    private void visualizeTrees() {
        try {
            String leftType = (String) leftTreeComboBox.getSelectedItem();
            String rightType = (String) rightTreeComboBox.getSelectedItem();

            List<Integer> insertionData = generateInsertionData(config.getN(), config.getSeed());

            String leftTreeText = buildTreeText(leftType, insertionData);
            String rightTreeText = buildTreeText(rightType, insertionData);

            leftTreeArea.setText(leftTreeText);
            rightTreeArea.setText(rightTreeText);

            leftTreeArea.setCaretPosition(0);
            rightTreeArea.setCaretPosition(0);

        } catch (Exception exception) {
            JOptionPane.showMessageDialog(
                    this,
                    "Error al visualizar arboles:\n" + exception.getMessage(),
                    "Error",
                    JOptionPane.ERROR_MESSAGE
            );
        }
    }

    /**
     * Genera la misma secuencia aleatoria usada para insertar.
     *
     * @param n cantidad de claves.
     * @param seed semilla.
     * @return lista de claves en orden aleatorio reproducible.
     */
    private List<Integer> generateInsertionData(int n, long seed) {
        List<Integer> data = new ArrayList<>();

        for (int i = 1; i <= n; i++) {
            data.add(i);
        }

        Collections.shuffle(data, new Random(seed));

        return data;
    }

    /**
     * Construye un arbol del tipo indicado y devuelve su texto estructurado.
     *
     * @param treeType tipo de arbol.
     * @param insertionData datos a insertar.
     * @return representacion textual del arbol.
     */
    private String buildTreeText(String treeType, List<Integer> insertionData) {
        switch (treeType) {
            case "BST":
                BSTree bst = new BSTree();

                for (int value : insertionData) {
                    bst.insert(value);
                }

                return "BST\nN = " + insertionData.size()
                        + "\nSemilla = " + config.getSeed()
                        + "\n\n" + bst.toStructuredString();

            case "AVL":
                AVLTree avl = new AVLTree();

                for (int value : insertionData) {
                    avl.insert(value);
                }

                return "AVL\nN = " + insertionData.size()
                        + "\nSemilla = " + config.getSeed()
                        + "\n\n" + avl.toStructuredString();

            case "Splay":
                SplayTree splay = new SplayTree();

                for (int value : insertionData) {
                    splay.insert(value);
                }

                return "Splay\nN = " + insertionData.size()
                        + "\nSemilla = " + config.getSeed()
                        + "\n\n" + splay.toStructuredString();

            case "Red-Black":
                RedBlackTree redBlack = new RedBlackTree();

                for (int value : insertionData) {
                    redBlack.insert(value);
                }

                return "Red-Black\nN = " + insertionData.size()
                        + "\nSemilla = " + config.getSeed()
                        + "\n\n" + redBlack.toStructuredString();

            default:
                throw new IllegalArgumentException("Tipo de arbol no reconocido: " + treeType);
        }
    }
}