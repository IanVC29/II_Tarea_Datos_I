package gui;

import benchmark.BenchmarkConfig;
import benchmark.BenchmarkResult;
import benchmark.BenchmarkRunner;
import benchmark.CSVExporter;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JTextArea;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.BorderFactory;
import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.FlowLayout;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Ventana principal de la aplicacion.
 *
 * Permite configurar el benchmark, ejecutarlo, ver la tabla
 * comparativa y exportar los resultados a CSV.
 */
public class MainWindow extends JFrame {

    private JTextField nField;
    private JTextField seedField;
    private JTextField warmupField;
    private JTextField measuredRunsField;
    private JTextField queryCountField;

    private JComboBox<String> searchModeComboBox;
    private JTextArea manualQueriesArea;

    private JCheckBox arrayCheckBox;
    private JCheckBox listCheckBox;
    private JCheckBox bstCheckBox;
    private JCheckBox avlCheckBox;
    private JCheckBox splayCheckBox;
    private JCheckBox redBlackCheckBox;

    private JButton exampleButton;
    private JButton runButton;
    private JButton exportButton;
    private JButton visualizeTreesButton;
    private JButton sequenceButton;

    private ResultsPanel resultsPanel;

    /**
     * Guarda los resultados de la ultima corrida.
     * El CSV se exporta usando esta lista para coincidir con la tabla.
     */
    private List<BenchmarkResult> lastResults;

    /**
     * Constructor de la ventana principal.
     */
    public MainWindow() {
        setTitle("Benchmark de estructuras de datos");
        setSize(1200, 700);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        lastResults = new ArrayList<>();

        createComponents();
        loadExampleValues();
    }

    /**
     * Crea y organiza los componentes de la interfaz.
     */
    private void createComponents() {
        setLayout(new BorderLayout());

        JPanel configPanel = createConfigPanel();

        resultsPanel = new ResultsPanel();

        add(configPanel, BorderLayout.WEST);
        add(resultsPanel, BorderLayout.CENTER);
    }

    /**
     * Crea el panel izquierdo con los parametros del benchmark.
     *
     * @return panel de configuracion.
     */
    private JPanel createConfigPanel() {
        JPanel configPanel = new JPanel();
        configPanel.setLayout(new BorderLayout());
        configPanel.setBorder(BorderFactory.createTitledBorder("Configuracion"));
        
        JPanel fieldsPanel = new JPanel();
        fieldsPanel.setLayout(new GridLayout(0, 2, 5, 5));

        nField = new JTextField();
        seedField = new JTextField();
        warmupField = new JTextField();
        measuredRunsField = new JTextField();
        queryCountField = new JTextField();

        searchModeComboBox = new JComboBox<>(new String[]{"Automatico", "Manual"});

        fieldsPanel.add(new JLabel("N:"));
        fieldsPanel.add(nField);

        fieldsPanel.add(new JLabel("Semilla:"));
        fieldsPanel.add(seedField);

        fieldsPanel.add(new JLabel("W warmup:"));
        fieldsPanel.add(warmupField);

        fieldsPanel.add(new JLabel("R iteraciones:"));
        fieldsPanel.add(measuredRunsField);

        fieldsPanel.add(new JLabel("Modo busqueda:"));
        fieldsPanel.add(searchModeComboBox);

        fieldsPanel.add(new JLabel("Cantidad consultas:"));
        fieldsPanel.add(queryCountField);

        JPanel structuresPanel = createStructuresPanel();

        manualQueriesArea = new JTextArea(5, 20);
        manualQueriesArea.setBorder(BorderFactory.createTitledBorder("Consultas manuales separadas por coma o espacio"));

        JScrollPane manualScrollPane = new JScrollPane(manualQueriesArea);

        JPanel buttonsPanel = createButtonsPanel();

        JPanel upperPanel = new JPanel();
        upperPanel.setLayout(new BorderLayout());
        upperPanel.add(fieldsPanel, BorderLayout.NORTH);
        upperPanel.add(structuresPanel, BorderLayout.CENTER);
        upperPanel.add(manualScrollPane, BorderLayout.SOUTH);

        configPanel.add(upperPanel, BorderLayout.NORTH);
        configPanel.add(buttonsPanel, BorderLayout.SOUTH);

        return configPanel;
    }

    /**
     * Crea el panel con las casillas de estructuras.
     *
     * @return panel de estructuras.
     */
    private JPanel createStructuresPanel() {
        JPanel structuresPanel = new JPanel();
        structuresPanel.setLayout(new GridLayout(0, 1));
        structuresPanel.setBorder(BorderFactory.createTitledBorder("Estructuras"));

        arrayCheckBox = new JCheckBox("Array");
        listCheckBox = new JCheckBox("List");
        bstCheckBox = new JCheckBox("BST");
        avlCheckBox = new JCheckBox("AVL");
        splayCheckBox = new JCheckBox("Splay");
        redBlackCheckBox = new JCheckBox("Red-Black");

        structuresPanel.add(arrayCheckBox);
        structuresPanel.add(listCheckBox);
        structuresPanel.add(bstCheckBox);
        structuresPanel.add(avlCheckBox);
        structuresPanel.add(splayCheckBox);
        structuresPanel.add(redBlackCheckBox);

        return structuresPanel;
    }

    /**
     * Crea el panel de botones principales.
     *
     * @return panel de botones.
     */
    private JPanel createButtonsPanel() {
        JPanel buttonsPanel = new JPanel();
        buttonsPanel.setLayout(new GridLayout(0, 1, 5, 5));

        exampleButton = new JButton("Experimento de ejemplo");
        runButton = new JButton("Ejecutar");
        exportButton = new JButton("Exportar CSV");
        visualizeTreesButton = new JButton("Visualizar arboles");
        sequenceButton = new JButton("Secuencia paso a paso");

        exampleButton.addActionListener(event -> loadExampleValues());
        runButton.addActionListener(event -> runBenchmark());
        exportButton.addActionListener(event -> exportCSV());
        visualizeTreesButton.addActionListener(event -> openTreeVisualizer());
        sequenceButton.addActionListener(event -> openSequencePanel());

        buttonsPanel.add(exampleButton);
        buttonsPanel.add(runButton);
        buttonsPanel.add(exportButton);
        buttonsPanel.add(visualizeTreesButton);
        buttonsPanel.add(sequenceButton);

        return buttonsPanel;
    }

    /**
     * Carga valores por defecto para ejecutar una corrida rapida.
     */
    private void loadExampleValues() {
        nField.setText("100");
        seedField.setText("12345");
        warmupField.setText("1");
        measuredRunsField.setText("3");
        queryCountField.setText("50");

        searchModeComboBox.setSelectedItem("Automatico");
        manualQueriesArea.setText("");

        arrayCheckBox.setSelected(true);
        listCheckBox.setSelected(true);
        bstCheckBox.setSelected(true);
        avlCheckBox.setSelected(true);
        splayCheckBox.setSelected(true);
        redBlackCheckBox.setSelected(true);
    }

    /**
     * Ejecuta el benchmark usando los datos ingresados por el usuario.
     */
    private void runBenchmark() {
        try {
            BenchmarkConfig config = buildConfigFromInputs();

            BenchmarkRunner runner = new BenchmarkRunner();

            lastResults = runner.run(config);

            resultsPanel.updateResults(lastResults);

            JOptionPane.showMessageDialog(
                    this,
                    "Benchmark ejecutado correctamente.",
                    "Resultado",
                    JOptionPane.INFORMATION_MESSAGE
            );

        } catch (Exception exception) {
            JOptionPane.showMessageDialog(
                    this,
                    "Error al ejecutar el benchmark:\n" + exception.getMessage(),
                    "Error",
                    JOptionPane.ERROR_MESSAGE
            );
        }
    }

    /**
     * Construye la configuracion del benchmark a partir de los campos de la interfaz.
     *
     * @return configuracion lista para usar.
     */
    private BenchmarkConfig buildConfigFromInputs() {
        BenchmarkConfig config = new BenchmarkConfig();

        config.setN(Integer.parseInt(nField.getText().trim()));
        config.setSeed(Long.parseLong(seedField.getText().trim()));
        config.setWarmupRuns(Integer.parseInt(warmupField.getText().trim()));
        config.setMeasuredRuns(Integer.parseInt(measuredRunsField.getText().trim()));

        boolean automaticMode = searchModeComboBox.getSelectedItem().equals("Automatico");
        config.setAutomaticSearchMode(automaticMode);

        config.setQueryCount(Integer.parseInt(queryCountField.getText().trim()));

        if (!automaticMode) {
            config.setManualQueries(parseManualQueries());
        }

        config.setEnabledStructures(getEnabledStructures());

        return config;
    }

    /**
     * Obtiene las estructuras seleccionadas por el usuario.
     *
     * @return lista de nombres de estructuras activas.
     */
    private List<String> getEnabledStructures() {
        List<String> enabledStructures = new ArrayList<>();

        if (arrayCheckBox.isSelected()) {
            enabledStructures.add("Array");
        }

        if (listCheckBox.isSelected()) {
            enabledStructures.add("List");
        }

        if (bstCheckBox.isSelected()) {
            enabledStructures.add("BST");
        }

        if (avlCheckBox.isSelected()) {
            enabledStructures.add("AVL");
        }

        if (splayCheckBox.isSelected()) {
            enabledStructures.add("Splay");
        }

        if (redBlackCheckBox.isSelected()) {
            enabledStructures.add("Red-Black");
        }

        return enabledStructures;
    }

    /**
     * Construye una configuracion basica para herramientas visuales.
     *
     * Estas herramientas solo necesitan N y semilla.
     */
    private BenchmarkConfig buildConfigForVisualTools() {
        BenchmarkConfig config = new BenchmarkConfig();

        config.setN(Integer.parseInt(nField.getText().trim()));
        config.setSeed(Long.parseLong(seedField.getText().trim()));
        config.setWarmupRuns(Integer.parseInt(warmupField.getText().trim()));
        config.setMeasuredRuns(Integer.parseInt(measuredRunsField.getText().trim()));
        config.setQueryCount(Integer.parseInt(queryCountField.getText().trim()));

        return config;
    }

    /**
     * Convierte el texto del area manual en una lista de enteros.
     *
     * Permite separar con comas, espacios o saltos de linea.
     *
     * @return lista de consultas manuales.
     */
    private List<Integer> parseManualQueries() {
        List<Integer> queries = new ArrayList<>();

        String text = manualQueriesArea.getText().trim();

        if (text.isEmpty()) {
            return queries;
        }

        String[] parts = text.split("[,\\s]+");

        for (String part : parts) {
            queries.add(Integer.parseInt(part));
        }

        return queries;
    }

    /**
     * Exporta a CSV los resultados de la ultima corrida.
     */
    private void exportCSV() {
        if (lastResults == null || lastResults.isEmpty()) {
            JOptionPane.showMessageDialog(
                    this,
                    "Primero debe ejecutar un benchmark.",
                    "Sin resultados",
                    JOptionPane.WARNING_MESSAGE
            );
            return;
        }

        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Guardar resultados CSV");
        fileChooser.setSelectedFile(new File("resultados_benchmark.csv"));

        int option = fileChooser.showSaveDialog(this);

        if (option == JFileChooser.APPROVE_OPTION) {
            File selectedFile = fileChooser.getSelectedFile();

            try {
                CSVExporter exporter = new CSVExporter();
                exporter.export(lastResults, selectedFile.getAbsolutePath());

                JOptionPane.showMessageDialog(
                        this,
                        "CSV exportado correctamente.",
                        "Exportacion completa",
                        JOptionPane.INFORMATION_MESSAGE
                );

            } catch (Exception exception) {
                JOptionPane.showMessageDialog(
                        this,
                        "Error al exportar CSV:\n" + exception.getMessage(),
                        "Error",
                        JOptionPane.ERROR_MESSAGE
                );
            }
        }
    }

    /**
     * Abre una ventana para visualizar dos arboles lado a lado.
     */
    private void openTreeVisualizer() {
        try {
            BenchmarkConfig config = buildConfigForVisualTools();

            JFrame visualizerFrame = new JFrame("Visualizador de dos arboles");
            visualizerFrame.setSize(1000, 700);
            visualizerFrame.setLocationRelativeTo(this);
            visualizerFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

            TreeVisualizerPanel treeVisualizerPanel = new TreeVisualizerPanel(config);

            visualizerFrame.add(treeVisualizerPanel);
            visualizerFrame.setVisible(true);

        } catch (Exception exception) {
            JOptionPane.showMessageDialog(
                    this,
                    "Error al abrir el visualizador:\n" + exception.getMessage(),
                    "Error",
                    JOptionPane.ERROR_MESSAGE
            );
        }
    }

    /**
     * Abre una ventana para inspeccionar la construccion paso a paso.
     */
    private void openSequencePanel() {
        try {
            BenchmarkConfig config = buildConfigForVisualTools();

            JFrame sequenceFrame = new JFrame("Secuencia paso a paso");
            sequenceFrame.setSize(1000, 700);
            sequenceFrame.setLocationRelativeTo(this);
            sequenceFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

            SequencePanel sequencePanel = new SequencePanel(config);

            sequenceFrame.add(sequencePanel);
            sequenceFrame.setVisible(true);

        } catch (Exception exception) {
            JOptionPane.showMessageDialog(
                    this,
                    "Error al abrir la secuencia:\n" + exception.getMessage(),
                    "Error",
                    JOptionPane.ERROR_MESSAGE
            );
        }
    }
}