package structures;

// Lista enlazada simple usada para el benchmark.
public class SinglyLinkedList implements BenchmarkStructure {

    // Nodo interno de la lista.
    private static class Node {
        int value;
        Node next;

        // Crea un nodo con un valor.
        Node(int value) {
            this.value = value;
            this.next = null;
        }
    }

    private Node head;
    private Node tail;
    private int size;
    private long comparisons;

    // Crea una lista vacía.
    public SinglyLinkedList() {
        this.head = null;
        this.tail = null;
        this.size = 0;
        this.comparisons = 0;
    }

    // Inserta el valor al final de la lista.
    @Override
    public void insert(int value) {
        Node newNode = new Node(value);

        if (head == null) {
            head = newNode;
            tail = newNode;
        } else {
            tail.next = newNode;
            tail = newNode;
        }

        size++;
    }

    // Busca el valor recorriendo nodo por nodo.
    @Override
    public boolean search(int value) {
        Node current = head;

        while (current != null) {
            comparisons++;

            if (current.value == value) {
                return true;
            }

            current = current.next;
        }

        return false;
    }

    // Elimina el valor ajustando los enlaces de los nodos.
    @Override
    public boolean delete(int value) {
        if (head == null) {
            return false;
        }

        comparisons++;

        if (head.value == value) {
            head = head.next;
            size--;

            if (head == null) {
                tail = null;
            }

            return true;
        }

        Node current = head;

        while (current.next != null) {
            comparisons++;

            if (current.next.value == value) {
                if (current.next == tail) {
                    tail = current;
                }

                current.next = current.next.next;
                size--;
                return true;
            }

            current = current.next;
        }

        return false;
    }

    // Retorna la cantidad de elementos guardados.
    @Override
    public int size() {
        return size;
    }

    // Retorna las comparaciones acumuladas.
    @Override
    public long getComparisons() {
        return comparisons;
    }

    // Reinicia el contador de comparaciones.
    @Override
    public void resetComparisons() {
        comparisons = 0;
    }

    // Retorna el nombre de la estructura.
    @Override
    public String getName() {
        return "Lista enlazada simple";
    }
}