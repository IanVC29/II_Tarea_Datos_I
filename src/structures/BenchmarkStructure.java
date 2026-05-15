package structures;

/**
 * Interfaz común para todas las estructuras que se van a medir en el benchmark.
 *
 * Esta interfaz permite usar arreglo, lista, BST, AVL, Splay y Red-Black
 * con los mismos métodos principales.
 */
public interface BenchmarkStructure {

    /**
     * Inserta una clave en la estructura.
     *
     * @param key clave que se desea insertar.
     */
    void insert(int key);

    /**
     * Busca una clave en la estructura.
     *
     * @param key clave que se desea buscar.
     * @return true si la clave existe, false si no existe.
     */
    boolean search(int key);

    /**
     * Elimina una clave de la estructura.
     *
     * @param key clave que se desea eliminar.
     */
    void delete(int key);

    /**
     * Limpia completamente la estructura.
     */
    void clear();

    /**
     * Devuelve las comparaciones acumuladas.
     *
     * @return cantidad de comparaciones.
     */
    long getComparisons();

    /**
     * Reinicia el contador de comparaciones.
     */
    void resetComparisons();

    /**
     * Devuelve la altura si es árbol o el tamaño si es estructura lineal.
     *
     * @return altura o tamaño.
     */
    int getHeightOrSize();

    /**
     * Devuelve el nombre de la estructura.
     *
     * @return nombre de la estructura.
     */
    String getName();
}