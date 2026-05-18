package benchmark;

/**
 * Representa los resultados obtenidos por una estructura
 * después de ejecutar el benchmark.
 *
 * Cada objeto de esta clase equivale a una fila en la tabla comparativa.
 */
public class BenchmarkResult {

    private String structureName;

    private long insertionTime;
    private long searchTime;
    private long deletionTime;

    private long insertionComparisons;
    private long searchComparisons;
    private long deletionComparisons;

    private String insertionComplexity;
    private String searchComplexity;
    private String deletionComplexity;

    private int heightOrSize;

    private boolean deletionAvailable;

    public BenchmarkResult(String structureName) {
        this.structureName = structureName;
        this.deletionAvailable = true;
    }

    public String getStructureName() {
        return structureName;
    }

    public long getInsertionTime() {
        return insertionTime;
    }

    public void setInsertionTime(long insertionTime) {
        this.insertionTime = insertionTime;
    }

    public long getSearchTime() {
        return searchTime;
    }

    public void setSearchTime(long searchTime) {
        this.searchTime = searchTime;
    }

    public long getDeletionTime() {
        return deletionTime;
    }

    public void setDeletionTime(long deletionTime) {
        this.deletionTime = deletionTime;
    }

    public long getInsertionComparisons() {
        return insertionComparisons;
    }

    public void setInsertionComparisons(long insertionComparisons) {
        this.insertionComparisons = insertionComparisons;
    }

    public long getSearchComparisons() {
        return searchComparisons;
    }

    public void setSearchComparisons(long searchComparisons) {
        this.searchComparisons = searchComparisons;
    }

    public long getDeletionComparisons() {
        return deletionComparisons;
    }

    public void setDeletionComparisons(long deletionComparisons) {
        this.deletionComparisons = deletionComparisons;
    }

    public String getInsertionComplexity() {
        return insertionComplexity;
    }

    public void setInsertionComplexity(String insertionComplexity) {
        this.insertionComplexity = insertionComplexity;
    }

    public String getSearchComplexity() {
        return searchComplexity;
    }

    public void setSearchComplexity(String searchComplexity) {
        this.searchComplexity = searchComplexity;
    }

    public String getDeletionComplexity() {
        return deletionComplexity;
    }

    public void setDeletionComplexity(String deletionComplexity) {
        this.deletionComplexity = deletionComplexity;
    }

    public int getHeightOrSize() {
        return heightOrSize;
    }

    public void setHeightOrSize(int heightOrSize) {
        this.heightOrSize = heightOrSize;
    }

    public boolean isDeletionAvailable() {
        return deletionAvailable;
    }

    public void setDeletionAvailable(boolean deletionAvailable) {
        this.deletionAvailable = deletionAvailable;
    }
}