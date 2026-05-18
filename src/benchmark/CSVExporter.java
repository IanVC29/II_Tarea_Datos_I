package benchmark;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

/**
 * Clase encargada de exportar los resultados del benchmark a un archivo CSV.
 *
 * El CSV debe coincidir con la ultima corrida mostrada en la tabla.
 */
public class CSVExporter {

    /**
     * Exporta una lista de resultados a un archivo CSV.
     *
     * Se usa punto y coma como separador porque Excel en configuraciones
     * regionales en espanol suele abrir mejor los CSV con este formato.
     *
     * @param results lista de resultados de la ultima corrida.
     * @param filePath ruta donde se guardara el archivo CSV.
     * @throws IOException si ocurre un error al escribir el archivo.
     */
    public void export(List<BenchmarkResult> results, String filePath) throws IOException {

        try (PrintWriter writer = new PrintWriter(new FileWriter(filePath))) {

            writer.println(
                    "Estructura;" +
                    "Tiempo insercion ns;" +
                    "Comparaciones insercion;" +
                    "Tiempo busqueda ns;" +
                    "Comparaciones busqueda;" +
                    "Tiempo borrado ns;" +
                    "Comparaciones borrado;" +
                    "Complejidad insercion;" +
                    "Complejidad busqueda;" +
                    "Complejidad borrado;" +
                    "Altura/Tamano"
            );

            for (BenchmarkResult result : results) {

                String deletionTime = result.isDeletionAvailable()
                        ? String.valueOf(result.getDeletionTime())
                        : "N/A";

                String deletionComparisons = result.isDeletionAvailable()
                        ? String.valueOf(result.getDeletionComparisons())
                        : "N/A";

                writer.println(
                        result.getStructureName() + ";" +
                        result.getInsertionTime() + ";" +
                        result.getInsertionComparisons() + ";" +
                        result.getSearchTime() + ";" +
                        result.getSearchComparisons() + ";" +
                        deletionTime + ";" +
                        deletionComparisons + ";" +
                        result.getInsertionComplexity() + ";" +
                        result.getSearchComplexity() + ";" +
                        result.getDeletionComplexity() + ";" +
                        result.getHeightOrSize()
                );
            }
        }
    }
}