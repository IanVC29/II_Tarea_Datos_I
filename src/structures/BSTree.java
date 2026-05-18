package structures;

import java.util.ArrayList;
import java.util.List;

/**
 * Implementación propia de un árbol binario de búsqueda.
 *
 * Esta clase permite:
 * - Insertar claves.
 * - Buscar claves.
 * - Borrar claves.
 * - Contar comparaciones.
 * - Calcular altura.
 * - Guardar estados para una secuencia paso a paso.
 */
public class BSTree implements BenchmarkStructure {

    /**
     * Nodo interno del árbol BST.
     *
     * Cada nodo guarda una clave y dos referencias:
     * una al hijo izquierdo y otra al hijo derecho.
     */
    private static class Node {

        // Clave almacenada en el nodo.
        int key;

        // Referencia al hijo izquierdo.
        Node left;

        // Referencia al hijo derecho.
        Node right;

        /**
         * Constructor del nodo.
         *
         * @param key clave que se guardará en el nodo.
         */
        Node(int key) {
            this.key = key;
        }
    }

    // Raíz del árbol.
    private Node root;

    // Cantidad de nodos dentro del árbol.
    private int size;

    // Contador de comparaciones de clave.
    private long comparisons;

    // Lista de estados para el modo paso a paso.
    private final List<String> sequenceStates;

    /**
     * Constructor del árbol BST.
     *
     * Inicializa el árbol vacío.
     */
    public BSTree() {
        this.root = null;
        this.size = 0;
        this.comparisons = 0;
        this.sequenceStates = new ArrayList<>();
    }

    /**
     * Inserta una clave en el árbol.
     *
     * @param key clave que se desea insertar.
     */
    @Override
    public void insert(int key) {
        root = insertRecursive(root, key);
        saveState("Insertado: " + key);
    }

    /**
     * Método recursivo para insertar una clave.
     *
     * @param current nodo actual.
     * @param key clave que se desea insertar.
     * @return nodo actualizado.
     */
    private Node insertRecursive(Node current, int key) {

        // Si llegamos a una posición vacía, se crea el nuevo nodo.
        if (current == null) {
            size++;
            return new Node(key);
        }

        // Se compara la clave nueva con la clave del nodo actual.
        comparisons++;

        // Si la clave nueva es menor, se inserta en el lado izquierdo.
        if (key < current.key) {
            current.left = insertRecursive(current.left, key);
        }

        // Si la clave nueva es mayor, se inserta en el lado derecho.
        else if (key > current.key) {
            current.right = insertRecursive(current.right, key);
        }

        // Si la clave ya existe, no se inserta de nuevo.
        return current;
    }

    /**
     * Busca una clave dentro del árbol.
     *
     * @param key clave que se desea buscar.
     * @return true si la clave existe, false si no existe.
     */
    @Override
    public boolean search(int key) {
        return searchRecursive(root, key);
    }

    /**
     * Método recursivo para buscar una clave.
     *
     * @param current nodo actual.
     * @param key clave buscada.
     * @return true si se encuentra, false si no.
     */
    private boolean searchRecursive(Node current, int key) {

        // Si llegamos a null, la clave no está en el árbol.
        if (current == null) {
            return false;
        }

        // Se compara la clave buscada con la clave del nodo actual.
        comparisons++;

        // Si son iguales, encontramos la clave.
        if (key == current.key) {
            return true;
        }

        // Si la clave buscada es menor, se busca a la izquierda.
        if (key < current.key) {
            return searchRecursive(current.left, key);
        }

        // Si la clave buscada es mayor, se busca a la derecha.
        return searchRecursive(current.right, key);
    }

    /**
     * Elimina una clave del árbol.
     *
     * @param key clave que se desea eliminar.
     */
    @Override
    public void delete(int key) {
        root = deleteRecursive(root, key);
        saveState("Eliminado: " + key);
    }

    /**
     * Método recursivo para eliminar una clave.
     *
     * @param current nodo actual.
     * @param key clave que se desea eliminar.
     * @return nodo actualizado.
     */
    private Node deleteRecursive(Node current, int key) {

        // Si llegamos a null, la clave no existe.
        if (current == null) {
            return null;
        }

        // Se compara la clave a eliminar con la clave del nodo actual.
        comparisons++;

        // Si la clave es menor, se busca en el subárbol izquierdo.
        if (key < current.key) {
            current.left = deleteRecursive(current.left, key);
        }

        // Si la clave es mayor, se busca en el subárbol derecho.
        else if (key > current.key) {
            current.right = deleteRecursive(current.right, key);
        }

        // Si encontramos la clave, se elimina este nodo.
        else {

            // Caso 1: el nodo no tiene hijos.
            if (current.left == null && current.right == null) {
                size--;
                return null;
            }

            // Caso 2: el nodo solo tiene hijo derecho.
            if (current.left == null) {
                size--;
                return current.right;
            }

            // Caso 2: el nodo solo tiene hijo izquierdo.
            if (current.right == null) {
                size--;
                return current.left;
            }

            // Caso 3: el nodo tiene dos hijos.
            // Se busca el menor valor del subárbol derecho.
            int successorValue = findSmallestValue(current.right);

            // Se reemplaza la clave actual por la clave sucesora.
            current.key = successorValue;

            // Se elimina el nodo sucesor del subárbol derecho.
            current.right = deleteRecursive(current.right, successorValue);
        }

        return current;
    }

    /**
     * Busca el menor valor dentro de un subárbol.
     *
     * @param node raíz del subárbol.
     * @return menor clave encontrada.
     */
    private int findSmallestValue(Node node) {

        // Se avanza hacia la izquierda hasta llegar al menor nodo.
        while (node.left != null) {
            comparisons++;
            node = node.left;
        }

        return node.key;
    }

    /**
     * Vacía completamente el árbol.
     */
    @Override
    public void clear() {
        root = null;
        size = 0;
        comparisons = 0;
        sequenceStates.clear();
    }

    /**
     * Devuelve la cantidad de comparaciones acumuladas.
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
     * Devuelve la altura del árbol.
     *
     * @return altura del BST.
     */
    @Override
    public int getHeightOrSize() {
        return height(root);
    }

    /**
     * Calcula la altura de un nodo.
     *
     * @param node nodo actual.
     * @return altura del subárbol.
     */
    private int height(Node node) {

        // Un nodo vacío tiene altura 0.
        if (node == null) {
            return 0;
        }

        // La altura es 1 más la altura mayor entre izquierda y derecha.
        return 1 + Math.max(height(node.left), height(node.right));
    }

    /**
     * Devuelve el nombre de la estructura.
     *
     * @return nombre BST.
     */
    @Override
    public String getName() {
        return "BST";
    }

    /**
     * Guarda un estado textual del árbol.
     *
     * Esto será útil para que la interfaz pueda avanzar y retroceder
     * entre pasos de construcción.
     *
     * @param action descripción de la acción realizada.
     */
    private void saveState(String action) {
        sequenceStates.add(action + "\n" + toStructuredString());
    }

    /**
     * Devuelve los estados guardados del árbol.
     *
     * @return lista de estados textuales.
     */
    public List<String> getSequenceStates() {
        return sequenceStates;
    }

    /**
     * Devuelve una representación textual del árbol.
     *
     * La salida indica si cada nodo es:
     * - ROOT: raíz del árbol.
     * - L: hijo izquierdo.
     * - R: hijo derecho.
     *
     * @return árbol en formato texto.
     */
    public String toStructuredString() {
        StringBuilder builder = new StringBuilder();

        if (root == null) {
            return "(árbol vacío)";
        }

        builder.append("ROOT: ")
                .append(root.key)
                .append("\n");

        buildString(root.left, builder, "", false, "L");
        buildString(root.right, builder, "", true, "R");

        return builder.toString();
    }

    /**
     * Construye una representación visual del árbol usando texto.
     *
     * @param node nodo actual.
     * @param builder texto acumulado.
     * @param prefix espacios para ordenar el dibujo.
     * @param isTail indica si es el último nodo del nivel actual.
     * @param side indica si el nodo es hijo izquierdo o derecho.
     */
    private void buildString(Node node, StringBuilder builder, String prefix, boolean isTail, String side) {

        if (node == null) {
            return;
        }

        builder.append(prefix)
                .append(isTail ? "└── " : "├── ")
                .append(side)
                .append(": ")
                .append(node.key)
                .append("\n");

        boolean hasLeft = node.left != null;
        boolean hasRight = node.right != null;

        if (hasLeft && hasRight) {
            buildString(node.left, builder, prefix + (isTail ? "    " : "│   "), false, "L");
            buildString(node.right, builder, prefix + (isTail ? "    " : "│   "), true, "R");
        } else if (hasLeft) {
            buildString(node.left, builder, prefix + (isTail ? "    " : "│   "), true, "L");
        } else if (hasRight) {
            buildString(node.right, builder, prefix + (isTail ? "    " : "│   "), true, "R");
        }
    }
}