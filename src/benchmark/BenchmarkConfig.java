package benchmark;

import java.util.ArrayList;
import java.util.List;

/**
 * Clase que almacena la configuración necesaria para ejecutar un benchmark.
 *
 * Esta clase funciona como un "paquete" de datos:
 * la interfaz gráfica llena estos valores y luego BenchmarkRunner los usa
 * para ejecutar el experimento.
 */
public class BenchmarkConfig {

    /**
     * Cantidad de claves que se van a insertar.
     */
    private int n;

    /**
     * Semilla usada para generar datos aleatorios reproducibles.
     */
    private long seed;

    /**
     * Cantidad de corridas de calentamiento.
     * Estas corridas se ejecutan, pero no se toman en cuenta en el promedio.
     */
    private int warmupRuns;

    /**
     * Cantidad de corridas medidas.
     * Estas corridas sí se promedian para la tabla final.
     */
    private int measuredRuns;

    /**
     * Indica si las búsquedas serán automáticas.
     * Si es false, se usarán las consultas manuales.
     */
    private boolean automaticSearchMode;

    /**
     * Cantidad de consultas generadas automáticamente.
     */
    private int queryCount;

    /**
     * Lista de consultas manuales ingresadas por el usuario.
     */
    private List<Integer> manualQueries;

    /**
     * Lista con los nombres de las estructuras activas.
     * Ejemplos: "Array", "List", "BST", "AVL", "Splay", "Red-Black".
     */
    private List<String> enabledStructures;

    /**
     * Constructor con valores por defecto razonables.
     * Esto sirve para el botón "Experimento de ejemplo".
     */
    public BenchmarkConfig() {
        this.n = 100;
        this.seed = 12345L;
        this.warmupRuns = 1;
        this.measuredRuns = 3;
        this.automaticSearchMode = true;
        this.queryCount = 50;
        this.manualQueries = new ArrayList<>();
        this.enabledStructures = new ArrayList<>();

        enabledStructures.add("Array");
        enabledStructures.add("List");
        enabledStructures.add("BST");
        enabledStructures.add("AVL");
        enabledStructures.add("Splay");
        enabledStructures.add("Red-Black");
    }

    public int getN() {
        return n;
    }

    public void setN(int n) {
        this.n = n;
    }

    public long getSeed() {
        return seed;
    }

    public void setSeed(long seed) {
        this.seed = seed;
    }

    public int getWarmupRuns() {
        return warmupRuns;
    }

    public void setWarmupRuns(int warmupRuns) {
        this.warmupRuns = warmupRuns;
    }

    public int getMeasuredRuns() {
        return measuredRuns;
    }

    public void setMeasuredRuns(int measuredRuns) {
        this.measuredRuns = measuredRuns;
    }

    public boolean isAutomaticSearchMode() {
        return automaticSearchMode;
    }

    public void setAutomaticSearchMode(boolean automaticSearchMode) {
        this.automaticSearchMode = automaticSearchMode;
    }

    public int getQueryCount() {
        return queryCount;
    }

    public void setQueryCount(int queryCount) {
        this.queryCount = queryCount;
    }

    public List<Integer> getManualQueries() {
        return manualQueries;
    }

    public void setManualQueries(List<Integer> manualQueries) {
        this.manualQueries = manualQueries;
    }

    public List<String> getEnabledStructures() {
        return enabledStructures;
    }

    public void setEnabledStructures(List<String> enabledStructures) {
        this.enabledStructures = enabledStructures;
    }
}