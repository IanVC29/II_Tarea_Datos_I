package structures;

// Interfaz común para todas las estructuras que se van a medir.
public interface BenchmarkStructure {

    // Inserta un valor en la estructura.
    void insert(int value);

    // Busca un valor en la estructura.
    boolean search(int value);

    // Elimina un valor de la estructura.
    boolean delete(int value);

    // Retorna la cantidad de elementos guardados.
    int size();

    // Retorna las comparaciones acumuladas.
    long getComparisons();

    // Reinicia el contador de comparaciones.
    void resetComparisons();

    // Retorna el nombre de la estructura.
    String getName();
}