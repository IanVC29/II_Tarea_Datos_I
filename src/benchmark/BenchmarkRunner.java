package benchmark;

import structures.ArrayStructure;
import structures.AVLTree;
import structures.BSTree;
import structures.BenchmarkStructure;
import structures.RedBlackTree;
import structures.SinglyLinkedList;
import structures.SplayTree;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

/**
 * Clase encargada de ejecutar el benchmark.
 *
 * Recibe una configuración, genera los datos de prueba y mide:
 * - Tiempo de inserción.
 * - Comparaciones de inserción.
 * - Tiempo de búsqueda.
 * - Comparaciones de búsqueda.
 * - Tiempo de borrado.
 * - Comparaciones de borrado.
 */
public class BenchmarkRunner {

    /**
     * Ejecuta el benchmark completo usando la configuración recibida.
     *
     * @param config configuración del experimento.
     * @return lista de resultados, uno por cada estructura activa.
     */
    public List<BenchmarkResult> run(BenchmarkConfig config) {

        validateConfig(config);

        List<BenchmarkResult> finalResults = new ArrayList<>();

        for (String structureName : config.getEnabledStructures()) {
            BenchmarkResult accumulatedResult = new BenchmarkResult(structureName);

            int totalRuns = config.getWarmupRuns() + config.getMeasuredRuns();

            for (int runIndex = 0; runIndex < totalRuns; runIndex++) {
                BenchmarkStructure structure = createStructure(structureName);

                List<Integer> insertionData = generateInsertionData(config.getN(), config.getSeed() + runIndex);
                List<Integer> searchData = generateSearchData(config, insertionData);
                List<Integer> deletionData = new ArrayList<>(insertionData);

                BenchmarkResult currentResult = runSingleStructure(structure, insertionData, searchData, deletionData);

                boolean isMeasuredRun = runIndex >= config.getWarmupRuns();

                if (isMeasuredRun) {
                    addResult(accumulatedResult, currentResult);
                }
            }

            divideResultByMeasuredRuns(accumulatedResult, config.getMeasuredRuns());
            applyComplexities(accumulatedResult);
            finalResults.add(accumulatedResult);
        }

        return finalResults;
    }

    /**
     * Ejecuta una estructura una sola vez.
     */
    private BenchmarkResult runSingleStructure(
            BenchmarkStructure structure,
            List<Integer> insertionData,
            List<Integer> searchData,
            List<Integer> deletionData
    ) {
        BenchmarkResult result = new BenchmarkResult(structure.getName());

        structure.resetComparisons();

        long startInsertion = System.nanoTime();

        for (int value : insertionData) {
            structure.insert(value);
        }

        long endInsertion = System.nanoTime();

        result.setInsertionTime(endInsertion - startInsertion);
        result.setInsertionComparisons(structure.getComparisons());

        structure.resetComparisons();

        long startSearch = System.nanoTime();

        for (int value : searchData) {
            structure.search(value);
        }

        long endSearch = System.nanoTime();

        result.setSearchTime(endSearch - startSearch);
        result.setSearchComparisons(structure.getComparisons());

        result.setHeightOrSize(structure.getHeightOrSize());

        if (isRedBlackStructure(structure)) {
            result.setDeletionAvailable(false);
            result.setDeletionTime(0);
            result.setDeletionComparisons(0);
        } else {
            structure.resetComparisons();

            long startDeletion = System.nanoTime();

            for (int value : deletionData) {
                structure.delete(value);
            }

            long endDeletion = System.nanoTime();

            result.setDeletionTime(endDeletion - startDeletion);
            result.setDeletionComparisons(structure.getComparisons());
            result.setDeletionAvailable(true);
        }

        return result;
    }

    /**
     * Genera una secuencia aleatoria reproducible de N claves.
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
     * Genera o toma las consultas según el modo elegido.
     */
    private List<Integer> generateSearchData(BenchmarkConfig config, List<Integer> insertionData) {
        if (!config.isAutomaticSearchMode()) {
            return config.getManualQueries();
        }

        List<Integer> queries = new ArrayList<>();
        Random random = new Random(config.getSeed() + 999);

        for (int i = 0; i < config.getQueryCount(); i++) {
            int randomIndex = random.nextInt(insertionData.size());
            queries.add(insertionData.get(randomIndex));
        }

        return queries;
    }

    /**
     * Crea una estructura a partir de su nombre.
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
     * Suma los resultados de una corrida medida al acumulado.
     */
    private void addResult(BenchmarkResult accumulated, BenchmarkResult current) {
        accumulated.setInsertionTime(accumulated.getInsertionTime() + current.getInsertionTime());
        accumulated.setSearchTime(accumulated.getSearchTime() + current.getSearchTime());
        accumulated.setDeletionTime(accumulated.getDeletionTime() + current.getDeletionTime());

        accumulated.setInsertionComparisons(accumulated.getInsertionComparisons() + current.getInsertionComparisons());
        accumulated.setSearchComparisons(accumulated.getSearchComparisons() + current.getSearchComparisons());
        accumulated.setDeletionComparisons(accumulated.getDeletionComparisons() + current.getDeletionComparisons());

        accumulated.setHeightOrSize(current.getHeightOrSize());
        accumulated.setDeletionAvailable(current.isDeletionAvailable());
    }

    /**
     * Promedia los resultados acumulados entre la cantidad de corridas medidas.
     */
    private void divideResultByMeasuredRuns(BenchmarkResult result, int measuredRuns) {
        result.setInsertionTime(result.getInsertionTime() / measuredRuns);
        result.setSearchTime(result.getSearchTime() / measuredRuns);
        result.setDeletionTime(result.getDeletionTime() / measuredRuns);

        result.setInsertionComparisons(result.getInsertionComparisons() / measuredRuns);
        result.setSearchComparisons(result.getSearchComparisons() / measuredRuns);
        result.setDeletionComparisons(result.getDeletionComparisons() / measuredRuns);
    }

    /**
     * Asigna la complejidad teórica según la estructura.
     */
    private void applyComplexities(BenchmarkResult result) {
        String name = result.getStructureName();

        switch (name) {
            case "Array":
            case "List":
                result.setInsertionComplexity("O(1)");
                result.setSearchComplexity("O(n)");
                result.setDeletionComplexity("O(n)");
                break;

            case "BST":
                result.setInsertionComplexity("O(h)");
                result.setSearchComplexity("O(h)");
                result.setDeletionComplexity("O(h)");
                break;

            case "AVL":
            case "Red-Black":
                result.setInsertionComplexity("O(log n)");
                result.setSearchComplexity("O(log n)");
                result.setDeletionComplexity(name.equals("Red-Black") ? "N/A" : "O(log n)");
                break;

            case "Splay":
                result.setInsertionComplexity("O(log n) amortizado");
                result.setSearchComplexity("O(log n) amortizado");
                result.setDeletionComplexity("O(log n) amortizado");
                break;

            default:
                result.setInsertionComplexity("N/A");
                result.setSearchComplexity("N/A");
                result.setDeletionComplexity("N/A");
                break;
        }
    }

    /**
     * Valida datos básicos para evitar corridas inválidas.
     */
    private void validateConfig(BenchmarkConfig config) {
        if (config.getN() <= 0) {
            throw new IllegalArgumentException("N debe ser mayor que 0.");
        }

        if (config.getWarmupRuns() < 0) {
            throw new IllegalArgumentException("W debe ser mayor o igual que 0.");
        }

        if (config.getMeasuredRuns() <= 0) {
            throw new IllegalArgumentException("R debe ser mayor o igual que 1.");
        }

        if (config.getEnabledStructures().isEmpty()) {
            throw new IllegalArgumentException("Debe seleccionar al menos una estructura.");
        }

        if (config.isAutomaticSearchMode() && config.getQueryCount() <= 0) {
            throw new IllegalArgumentException("La cantidad de consultas debe ser mayor que 0.");
        }

        if (!config.isAutomaticSearchMode() && config.getManualQueries().isEmpty()) {
            throw new IllegalArgumentException("En modo manual debe ingresar al menos una consulta.");
        }
    }

    /**
     * Verifica si la estructura recibida corresponde a Red-Black.
     *
     * Se usa este método porque el nombre puede variar entre:
     * "Red-Black" y "Red-Black Tree".
     */
    private boolean isRedBlackStructure(BenchmarkStructure structure) {
        return structure.getName().equals("Red-Black")
                || structure.getName().equals("Red-Black Tree");
    }
}