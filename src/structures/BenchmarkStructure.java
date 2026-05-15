package structures;

/**
 * Interfaz común para las estructuras que se van a usar en el benchmark.
 *
 * La idea es que el benchmark pueda trabajar con BST, AVL, Splay,
 * Red-Black, arreglo y lista usando los mismos métodos principales.
 */
public interface BenchmarkStructure {

    /**
     * Inserta una clave en la estructura.
     *
     * @param key clave que se desea insertar.
     */
    void insert(int key);

    /**
     * Busca una clave dentro de la estructura.
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
     * Limpia la estructura y la deja vacía.
     *
     * Esto sirve para reiniciar la estructura entre corridas del benchmark.
     */
    void clear();

    /**
     * Devuelve la cantidad de comparaciones de clave realizadas.
     *
     * @return cantidad total de comparaciones.
     */
    long getComparisons();

    /**
     * Reinicia el contador de comparaciones.
     *
     * Esto permite medir por separado inserción, búsqueda y borrado.
     */
    void resetComparisons();

    /**
     * Devuelve la altura si la estructura es un árbol.
     * En estructuras lineales puede usarse para devolver el tamaño.
     *
     * @return altura o tamaño de la estructura.
     */
    int getHeightOrSize();

    /**
     * Devuelve el nombre de la estructura.
     *
     * @return nombre de la estructura.
     */
    String getName();
}