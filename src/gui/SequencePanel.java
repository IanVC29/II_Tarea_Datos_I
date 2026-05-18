package gui;

import benchmark.BenchmarkConfig;
import structures.ArrayStructure;
import structures.AVLTree;
import structures.BSTree;
import structures.BenchmarkStructure;
import structures.RedBlackTree;
import structures.SinglyLinkedList;
import structures.SplayTree;

import javax.swing.JPanel;
import javax.swing.JComboBox;
import javax.swing.JButton;
import javax.swing.JTextArea;
import javax.swing.JScrollPane;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.BorderFactory;
import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Font;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

/**
 * Panel para mostrar la construccion de una estructura paso a paso.
 *
 * Permite escoger una estructura, generar una secuencia de insercion
 * usando N y semilla, y avanzar o retroceder entre los estados.
 */
public class SequencePanel extends JPanel {

    private BenchmarkConfig config;

    private JComboBox<String> structureComboBox;

    private JButton generateButton;
    private JButton previousButton;
    private JButton nextButton;

    private JLabel stepLabel;
    private JTextArea stateArea;

    private List<String> states;
    private int currentIndex;

    public SequencePanel(BenchmarkConfig config) {
        this.config = config;
        this.states = new ArrayList<>();
        this.currentIndex = 0;

        setLayout(new BorderLayout());

        createComponents();
    }

    /**
     * Crea los componentes graficos del panel.
     */
    private void createComponents() {
        JPanel topPanel = new JPanel(new FlowLayout());

        structureComboBox = new JComboBox<>(
                new String[]{"Array", "List", "BST", "AVL", "Splay", "Red-Black"}
        );

        generateButton = new JButton("Generar secuencia");
        previousButton = new JButton("Anterior");
        nextButton = new JButton("Siguiente");

        stepLabel = new JLabel("Paso: 0 / 0");

        generateButton.addActionListener(event -> generateSequence());
        previousButton.addActionListener(event -> previousStep());
        nextButton.addActionListener(event -> nextStep());

        topPanel.add(new JLabel("Estructura:"));
        topPanel.add(structureComboBox);
        topPanel.add(generateButton);
        topPanel.add(previousButton);
        topPanel.add(nextButton);
        topPanel.add(stepLabel);

        stateArea = new JTextArea();
        stateArea.setEditable(false);
        stateArea.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 14));
        stateArea.setBorder(BorderFactory.createTitledBorder("Estado actual"));

        JScrollPane scrollPane = new JScrollPane(stateArea);

        add(topPanel, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);

        updateButtons();
    }

    /**
     * Genera todos los estados de insercion de la estructura seleccionada.
     */
    private void generateSequence() {
        try {
            String structureName = (String) structureComboBox.getSelectedItem();

            List<Integer> insertionData = generateInsertionData(config.getN(), config.getSeed());

            states = buildStates(structureName, insertionData);
            currentIndex = 0;

            showCurrentState();

        } catch (Exception exception) {
            JOptionPane.showMessageDialog(
                    this,
                    "Error al generar la secuencia:\n" + exception.getMessage(),
                    "Error",
                    JOptionPane.ERROR_MESSAGE
            );
        }
    }

    /**
     * Avanza al siguiente estado.
     */
    private void nextStep() {
        if (currentIndex < states.size() - 1) {
            currentIndex++;
            showCurrentState();
        }
    }

    /**
     * Retrocede al estado anterior.
     */
    private void previousStep() {
        if (currentIndex > 0) {
            currentIndex--;
            showCurrentState();
        }
    }

    /**
     * Muestra el estado actual en pantalla.
     */
    private void showCurrentState() {
        if (states == null || states.isEmpty()) {
            stateArea.setText("");
            stepLabel.setText("Paso: 0 / 0");
            updateButtons();
            return;
        }

        stateArea.setText(states.get(currentIndex));
        stateArea.setCaretPosition(0);

        stepLabel.setText("Paso: " + (currentIndex + 1) + " / " + states.size());

        updateButtons();
    }

    /**
     * Activa o desactiva botones segun el estado actual.
     */
    private void updateButtons() {
        boolean hasStates = states != null && !states.isEmpty();

        previousButton.setEnabled(hasStates && currentIndex > 0);
        nextButton.setEnabled(hasStates && currentIndex < states.size() - 1);
    }

    /**
     * Genera la misma secuencia aleatoria reproducible.
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
     * Construye los estados paso a paso.
     */
    private List<String> buildStates(String structureName, List<Integer> insertionData) {
        List<String> generatedStates = new ArrayList<>();

        BenchmarkStructure structure = createStructure(structureName);

        List<Integer> insertedValues = new ArrayList<>();

        generatedStates.add(
                "Estado inicial\n" +
                "Estructura: " + structureName + "\n" +
                "N = " + insertionData.size() + "\n" +
                "Semilla = " + config.getSeed() + "\n\n" +
                "(estructura vacia)"
        );

        for (int i = 0; i < insertionData.size(); i++) {
            int value = insertionData.get(i);

            structure.insert(value);
            insertedValues.add(value);

            String stateText =
                    "Paso " + (i + 1) + "\n" +
                    "Accion: insertar " + value + "\n" +
                    "Estructura: " + structureName + "\n" +
                    "Elementos insertados: " + insertedValues.size() + "\n\n" +
                    structureToText(structure, insertedValues);

            generatedStates.add(stateText);
        }

        return generatedStates;
    }

    /**
     * Crea la estructura seleccionada.
     */
    private BenchmarkStructure createStructure(String structureName) {
        switch (structureName) {
            case "Array":
                return new ArrayStructure();

            case "List":
                return new SinglyLinkedList();

            case "BST":
                return new BSTree();

            case "AVL":
                return new AVLTree();

            case "Splay":
                return new SplayTree();

            case "Red-Black":
                return new RedBlackTree();

            default:
                throw new IllegalArgumentException("Estructura no reconocida: " + structureName);
        }
    }

    /**
     * Convierte la estructura actual a texto.
     */
    private String structureToText(BenchmarkStructure structure, List<Integer> insertedValues) {
        if (structure instanceof BSTree) {
            return ((BSTree) structure).toStructuredString();
        }

        if (structure instanceof AVLTree) {
            return ((AVLTree) structure).toStructuredString();
        }

        if (structure instanceof SplayTree) {
            return ((SplayTree) structure).toStructuredString();
        }

        if (structure instanceof RedBlackTree) {
            return ((RedBlackTree) structure).toStructuredString();
        }

        return buildLinearText(structure, insertedValues);
    }

    /**
     * Representacion simple para arreglo y lista.
     */
    private String buildLinearText(BenchmarkStructure structure, List<Integer> insertedValues) {
        StringBuilder builder = new StringBuilder();

        builder.append(structure.getName())
                .append("\n")
                .append("Tamano actual: ")
                .append(structure.getHeightOrSize())
                .append("\n\n");

        builder.append("[ ");

        for (int i = 0; i < insertedValues.size(); i++) {
            builder.append(insertedValues.get(i));

            if (i < insertedValues.size() - 1) {
                builder.append(", ");
            }
        }

        builder.append(" ]");

        return builder.toString();
    }
}