package gui;

import benchmark.BenchmarkResult;

import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.JScrollPane;
import javax.swing.table.DefaultTableModel;
import java.awt.BorderLayout;
import java.util.List;

/**
 * Panel encargado de mostrar la tabla comparativa del benchmark.
 *
 * Esta tabla se actualiza cada vez que el usuario ejecuta una corrida.
 */
public class ResultsPanel extends JPanel {

    private JTable resultsTable;
    private DefaultTableModel tableModel;

    /**
     * Constructor del panel de resultados.
     */
    public ResultsPanel() {
        setLayout(new BorderLayout());

        String[] columns = {
                "Estructura",
                "Tiempo insercion ns",
                "Comparaciones insercion",
                "Tiempo busqueda ns",
                "Comparaciones busqueda",
                "Tiempo borrado ns",
                "Comparaciones borrado",
                "Complejidad insercion",
                "Complejidad busqueda",
                "Complejidad borrado",
                "Altura/Tamano"
        };

        tableModel = new DefaultTableModel(columns, 0);

        resultsTable = new JTable(tableModel);
        resultsTable.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        resultsTable.getColumnModel().getColumn(0).setPreferredWidth(100);
        resultsTable.getColumnModel().getColumn(1).setPreferredWidth(130);
        resultsTable.getColumnModel().getColumn(2).setPreferredWidth(160);
        resultsTable.getColumnModel().getColumn(3).setPreferredWidth(130);
        resultsTable.getColumnModel().getColumn(4).setPreferredWidth(160);
        resultsTable.getColumnModel().getColumn(5).setPreferredWidth(130);
        resultsTable.getColumnModel().getColumn(6).setPreferredWidth(160);
        resultsTable.getColumnModel().getColumn(7).setPreferredWidth(150);
        resultsTable.getColumnModel().getColumn(8).setPreferredWidth(150);
        resultsTable.getColumnModel().getColumn(9).setPreferredWidth(150);
        resultsTable.getColumnModel().getColumn(10).setPreferredWidth(100);

        JScrollPane scrollPane = new JScrollPane(resultsTable);

        add(scrollPane, BorderLayout.CENTER);
    }

    /**
     * Actualiza la tabla con los resultados recibidos.
     *
     * @param results resultados de la ultima corrida.
     */
    public void updateResults(List<BenchmarkResult> results) {
        tableModel.setRowCount(0);

        for (BenchmarkResult result : results) {
            String deletionTime = result.isDeletionAvailable()
                    ? String.valueOf(result.getDeletionTime())
                    : "N/A";

            String deletionComparisons = result.isDeletionAvailable()
                    ? String.valueOf(result.getDeletionComparisons())
                    : "N/A";

            Object[] row = {
                    result.getStructureName(),
                    result.getInsertionTime(),
                    result.getInsertionComparisons(),
                    result.getSearchTime(),
                    result.getSearchComparisons(),
                    deletionTime,
                    deletionComparisons,
                    result.getInsertionComplexity(),
                    result.getSearchComplexity(),
                    result.getDeletionComplexity(),
                    result.getHeightOrSize()
            };

            tableModel.addRow(row);
        }
    }

    /**
     * Retorna el modelo de la tabla.
     *
     * Esto puede servir luego si se desea exportar exactamente lo visible.
     *
     * @return modelo de la tabla.
     */
    public DefaultTableModel getTableModel() {
        return tableModel;
    }
}