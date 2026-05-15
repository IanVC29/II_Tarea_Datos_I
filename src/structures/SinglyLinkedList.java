package structures;

/**
 * Lista enlazada simple usada para el benchmark.
 *
 * Esta estructura usa nodos conectados con una referencia next.
 */
public class SinglyLinkedList implements BenchmarkStructure {

    /**
     * Nodo interno de la lista.
     */
    private static class Node {

        // Valor guardado en el nodo.
        int value;

        // Referencia al siguiente nodo.
        Node next;

        /**
         * Constructor del nodo.
         *
         * @param value valor que se guardará.
         */
        Node(int value) {
            this.value = value;
            this.next = null;
        }
    }

    // Primer nodo de la lista.
    private Node head;

    // Último nodo de la lista.
    private Node tail;

    // Cantidad de nodos.
    private int size;

    // Contador de comparaciones.
    private long comparisons;

    /**
     * Constructor de la lista.
     *
     * Inicializa la lista vacía.
     */
    public SinglyLinkedList() {
        this.head = null;
        this.tail = null;
        this.size = 0;
        this.comparisons = 0;
    }

    /**
     * Inserta un valor al final de la lista.
     *
     * @param value valor que se desea insertar.
     */
    @Override
    public void insert(int value) {

        // Se crea el nuevo nodo.
        Node newNode = new Node(value);

        // Si la lista está vacía, head y tail apuntan al nuevo nodo.
        if (head == null) {
            head = newNode;
            tail = newNode;
        }

        // Si ya hay datos, se enlaza al final.
        else {
            tail.next = newNode;
            tail = newNode;
        }

        // Se aumenta el tamaño.
        size++;
    }

    /**
     * Busca un valor recorriendo la lista.
     *
     * @param value valor que se desea buscar.
     * @return true si existe, false si no existe.
     */
    @Override
    public boolean search(int value) {

        // Se empieza desde el primer nodo.
        Node current = head;

        // Se recorre mientras haya nodos.
        while (current != null) {

            // Cada comparación de clave se cuenta.
            comparisons++;

            // Si se encuentra el valor, se retorna true.
            if (current.value == value) {
                return true;
            }

            // Se avanza al siguiente nodo.
            current = current.next;
        }

        // Si se llega al final, el valor no existe.
        return false;
    }

    /**
     * Elimina un valor de la lista.
     *
     * @param value valor que se desea eliminar.
     */
    @Override
    public void delete(int value) {

        // Si la lista está vacía, no se hace nada.
        if (head == null) {
            return;
        }

        // Se compara contra el primer nodo.
        comparisons++;

        // Caso especial: el valor está en la cabeza.
        if (head.value == value) {
            head = head.next;
            size--;

            // Si la lista quedó vacía, también se limpia tail.
            if (head == null) {
                tail = null;
            }

            return;
        }

        // Se recorre desde la cabeza.
        Node current = head;

        // Se busca el nodo anterior al que se desea eliminar.
        while (current.next != null) {

            // Se compara contra el siguiente nodo.
            comparisons++;

            // Si el siguiente nodo tiene el valor, se elimina.
            if (current.next.value == value) {

                // Si se elimina el último nodo, se actualiza tail.
                if (current.next == tail) {
                    tail = current;
                }

                // Se salta el nodo eliminado.
                current.next = current.next.next;
                size--;
                return;
            }

            // Se avanza al siguiente nodo.
            current = current.next;
        }
    }

    /**
     * Limpia completamente la lista.
     */
    @Override
    public void clear() {
        head = null;
        tail = null;
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
     * @return cantidad de nodos.
     */
    @Override
    public int getHeightOrSize() {
        return size;
    }

    /**
     * Devuelve el nombre de la estructura.
     *
     * @return nombre de la lista.
     */
    @Override
    public String getName() {
        return "Lista enlazada simple";
    }
}