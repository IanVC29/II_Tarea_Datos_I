package structures;

/**
 * Arreglo propio usado para el benchmark.
 *
 * Esta estructura usa almacenamiento contiguo y operaciones lineales
 * para búsqueda y borrado.
 */
public class ArrayStructure implements BenchmarkStructure {

    // Arreglo donde se guardan los datos.
    private int[] data;

    // Cantidad actual de elementos guardados.
    private int size;

    // Contador de comparaciones de clave.
    private long comparisons;

    /**
     * Constructor del arreglo.
     *
     * Crea un arreglo con capacidad inicial de 10.
     */
    public ArrayStructure() {
        this.data = new int[10];
        this.size = 0;
        this.comparisons = 0;
    }

    /**
     * Inserta un valor al final del arreglo.
     *
     * @param value valor que se desea insertar.
     */
    @Override
    public void insert(int value) {

        // Si el arreglo está lleno, se aumenta su capacidad.
        if (size == data.length) {
            resize();
        }

        // Se agrega el nuevo valor al final.
        data[size] = value;
        size++;
    }

    /**
     * Busca un valor recorriendo el arreglo linealmente.
     *
     * @param value valor que se desea buscar.
     * @return true si existe, false si no existe.
     */
    @Override
    public boolean search(int value) {

        // Se recorre el arreglo desde el inicio hasta size.
        for (int i = 0; i < size; i++) {

            // Cada comparación de clave se cuenta.
            comparisons++;

            // Si se encuentra el valor, se retorna true.
            if (data[i] == value) {
                return true;
            }
        }

        // Si termina el ciclo, el valor no existe.
        return false;
    }

    /**
     * Elimina un valor del arreglo.
     *
     * Si encuentra el valor, mueve los elementos posteriores una posición
     * hacia la izquierda.
     *
     * @param value valor que se desea eliminar.
     */
    @Override
    public void delete(int value) {

        // Se busca el valor linealmente.
        for (int i = 0; i < size; i++) {

            // Cada comparación de clave se cuenta.
            comparisons++;

            // Si se encuentra el valor, se elimina.
            if (data[i] == value) {

                // Se desplazan los elementos hacia la izquierda.
                for (int j = i; j < size - 1; j++) {
                    data[j] = data[j + 1];
                }

                // Se reduce el tamaño.
                size--;

                // Se termina el método porque ya se eliminó.
                return;
            }
        }
    }

    /**
     * Duplica la capacidad del arreglo.
     */
    private void resize() {

        // Se crea un nuevo arreglo con el doble de capacidad.
        int[] newData = new int[data.length * 2];

        // Se copian los datos actuales.
        for (int i = 0; i < data.length; i++) {
            newData[i] = data[i];
        }

        // Se reemplaza el arreglo anterior.
        data = newData;
    }

    /**
     * Limpia el arreglo.
     */
    @Override
    public void clear() {
        data = new int[10];
        size = 0;
        comparisons = 0;
    }

    /**
     * Devuelve las comparaciones acumuladas.
     *
     * @return comparaciones realizadas.
     */
    @Override
    public long getComparisons() {
        return comparisons;
    }

    /**
     * Reinicia el contador de comparaciones.
     */
    @Override
    public void resetComparisons() {
        comparisons = 0;
    }

    /**
     * En estructuras lineales, este método devuelve el tamaño.
     *
     * @return cantidad de elementos guardados.
     */
    @Override
    public int getHeightOrSize() {
        return size;
    }

    /**
     * Devuelve el nombre de la estructura.
     *
     * @return nombre del arreglo.
     */
    @Override
    public String getName() {
        return "Arreglo";
    }
}