package structures;

import java.util.ArrayList;
import java.util.List;

/**
 * Implementación propia de un árbol AVL.
 *
 * Un árbol AVL es un árbol binario de búsqueda que se rebalancea
 * automáticamente mediante rotaciones.
 *
 * Esta clase permite:
 * - Insertar claves.
 * - Buscar claves.
 * - Borrar claves.
 * - Contar comparaciones de clave.
 * - Calcular altura.
 * - Guardar estados para una secuencia paso a paso.
 */
public class AVLTree implements BenchmarkStructure {

    /**
     * Nodo interno del árbol AVL.
     *
     * Cada nodo guarda:
     * - Una clave.
     * - Su altura.
     * - Una referencia al hijo izquierdo.
     * - Una referencia al hijo derecho.
     */
    private static class Node {

        // Clave almacenada en el nodo.
        int key;

        // Altura del nodo dentro del árbol.
        int height;

        // Hijo izquierdo.
        Node left;

        // Hijo derecho.
        Node right;

        /**
         * Constructor del nodo AVL.
         *
         * @param key clave que se guardará en el nodo.
         */
        Node(int key) {
            this.key = key;
            this.height = 1;
        }
    }

    // Raíz del árbol AVL.
    private Node root;

    // Cantidad de nodos dentro del árbol.
    private int size;

    // Contador de comparaciones de clave.
    private long comparisons;

    // Lista de estados textuales para el modo paso a paso.
    private final List<String> sequenceStates;

    /**
     * Constructor del árbol AVL.
     *
     * Inicializa el árbol vacío.
     */
    public AVLTree() {
        this.root = null;
        this.size = 0;
        this.comparisons = 0;
        this.sequenceStates = new ArrayList<>();
    }

    /**
     * Inserta una clave en el árbol AVL.
     *
     * @param key clave que se desea insertar.
     */
    @Override
    public void insert(int key) {
        root = insertRecursive(root, key);
        saveState("Insertado: " + key);
    }

    /**
     * Inserta una clave de forma recursiva y rebalancea el árbol.
     *
     * @param node nodo actual.
     * @param key clave que se desea insertar.
     * @return nodo actualizado después de insertar y rebalancear.
     */
    private Node insertRecursive(Node node, int key) {

        // Si llegamos a una posición vacía, se crea un nuevo nodo.
        if (node == null) {
            size++;
            return new Node(key);
        }

        // Se compara la clave nueva con la clave del nodo actual.
        comparisons++;

        // Si la clave nueva es menor, se inserta en el subárbol izquierdo.
        if (key < node.key) {
            node.left = insertRecursive(node.left, key);
        }

        // Si la clave nueva es mayor, se inserta en el subárbol derecho.
        else if (key > node.key) {
            node.right = insertRecursive(node.right, key);
        }

        // Si la clave ya existe, no se inserta duplicada.
        else {
            return node;
        }

        // Se actualiza la altura del nodo actual.
        updateHeight(node);

        // Se rebalancea el nodo actual si quedó desbalanceado.
        return rebalance(node);
    }

    /**
     * Busca una clave dentro del árbol AVL.
     *
     * @param key clave que se desea buscar.
     * @return true si la clave existe, false si no existe.
     */
    @Override
    public boolean search(int key) {
        return searchRecursive(root, key);
    }

    /**
     * Busca una clave de forma recursiva.
     *
     * @param node nodo actual.
     * @param key clave buscada.
     * @return true si la clave fue encontrada, false si no fue encontrada.
     */
    private boolean searchRecursive(Node node, int key) {

        // Si llegamos a null, la clave no existe.
        if (node == null) {
            return false;
        }

        // Se compara la clave buscada con la clave del nodo actual.
        comparisons++;

        // Si las claves son iguales, se encontró el valor.
        if (key == node.key) {
            return true;
        }

        // Si la clave buscada es menor, se busca en el subárbol izquierdo.
        if (key < node.key) {
            return searchRecursive(node.left, key);
        }

        // Si la clave buscada es mayor, se busca en el subárbol derecho.
        return searchRecursive(node.right, key);
    }

    /**
     * Elimina una clave del árbol AVL.
     *
     * @param key clave que se desea eliminar.
     */
    @Override
    public void delete(int key) {
        root = deleteRecursive(root, key);
        saveState("Eliminado: " + key);
    }

    /**
     * Elimina una clave de forma recursiva y rebalancea el árbol.
     *
     * @param node nodo actual.
     * @param key clave que se desea eliminar.
     * @return nodo actualizado después del borrado y rebalanceo.
     */
    private Node deleteRecursive(Node node, int key) {

        // Si llegamos a null, la clave no existe.
        if (node == null) {
            return null;
        }

        // Se compara la clave a eliminar con la clave del nodo actual.
        comparisons++;

        // Si la clave es menor, se elimina en el subárbol izquierdo.
        if (key < node.key) {
            node.left = deleteRecursive(node.left, key);
        }

        // Si la clave es mayor, se elimina en el subárbol derecho.
        else if (key > node.key) {
            node.right = deleteRecursive(node.right, key);
        }

        // Si encontramos la clave, se elimina este nodo.
        else {

            // Caso 1 y caso 2: nodo con cero o un hijo.
            if (node.left == null || node.right == null) {

                // Se toma el hijo existente, si existe.
                Node child = (node.left != null) ? node.left : node.right;

                // Si no tiene hijos, se elimina directamente.
                if (child == null) {
                    size--;
                    return null;
                }

                // Si tiene un hijo, ese hijo reemplaza al nodo actual.
                size--;
                return child;
            }

            // Caso 3: nodo con dos hijos.
            // Se busca el sucesor, que es el menor del subárbol derecho.
            Node successor = findMinNode(node.right);

            // Se copia la clave del sucesor en el nodo actual.
            node.key = successor.key;

            // Se elimina el sucesor del subárbol derecho.
            node.right = deleteRecursive(node.right, successor.key);
        }

        // Se actualiza la altura del nodo actual después de borrar.
        updateHeight(node);

        // Se rebalancea el nodo actual si quedó desbalanceado.
        return rebalance(node);
    }

    /**
     * Encuentra el nodo con menor clave dentro de un subárbol.
     *
     * @param node raíz del subárbol.
     * @return nodo con la clave menor.
     */
    private Node findMinNode(Node node) {

        // Se avanza hacia la izquierda hasta llegar al menor nodo.
        while (node.left != null) {
            comparisons++;
            node = node.left;
        }

        return node;
    }

    /**
     * Rebalancea un nodo del AVL.
     *
     * El factor de balance se calcula como:
     * altura izquierda - altura derecha.
     *
     * @param node nodo posiblemente desbalanceado.
     * @return nueva raíz del subárbol.
     */
    private Node rebalance(Node node) {

        // Se calcula el factor de balance del nodo actual.
        int balance = getBalance(node);

        // Caso izquierdo pesado.
        if (balance > 1) {

            // Caso izquierda-derecha.
            // Primero se rota el hijo izquierdo hacia la izquierda.
            if (getBalance(node.left) < 0) {
                node.left = rotateLeft(node.left);
            }

            // Caso izquierda-izquierda.
            // Se rota el nodo actual hacia la derecha.
            return rotateRight(node);
        }

        // Caso derecho pesado.
        if (balance < -1) {

            // Caso derecha-izquierda.
            // Primero se rota el hijo derecho hacia la derecha.
            if (getBalance(node.right) > 0) {
                node.right = rotateRight(node.right);
            }

            // Caso derecha-derecha.
            // Se rota el nodo actual hacia la izquierda.
            return rotateLeft(node);
        }

        // Si el nodo está balanceado, se devuelve igual.
        return node;
    }

    /**
     * Realiza una rotación hacia la derecha.
     *
     * Antes:
     *        y
     *       /
     *      x
     *
     * Después:
     *      x
     *       \
     *        y
     *
     * @param y nodo desbalanceado.
     * @return nueva raíz del subárbol.
     */
    private Node rotateRight(Node y) {

        // x será la nueva raíz del subárbol.
        Node x = y.left;

        // T2 es el subárbol que se mueve.
        Node T2 = x.right;

        // Se hace la rotación.
        x.right = y;
        y.left = T2;

        // Se actualizan las alturas después de rotar.
        updateHeight(y);
        updateHeight(x);

        // Se guarda el estado de la rotación para el modo paso a paso.
        saveState("Rotación derecha en nodo: " + y.key);

        return x;
    }

    /**
     * Realiza una rotación hacia la izquierda.
     *
     * Antes:
     *      x
     *       \
     *        y
     *
     * Después:
     *        y
     *       /
     *      x
     *
     * @param x nodo desbalanceado.
     * @return nueva raíz del subárbol.
     */
    private Node rotateLeft(Node x) {

        // y será la nueva raíz del subárbol.
        Node y = x.right;

        // T2 es el subárbol que se mueve.
        Node T2 = y.left;

        // Se hace la rotación.
        y.left = x;
        x.right = T2;

        // Se actualizan las alturas después de rotar.
        updateHeight(x);
        updateHeight(y);

        // Se guarda el estado de la rotación para el modo paso a paso.
        saveState("Rotación izquierda en nodo: " + x.key);

        return y;
    }

    /**
     * Actualiza la altura de un nodo.
     *
     * @param node nodo al que se le actualizará la altura.
     */
    private void updateHeight(Node node) {

        // La altura es 1 más la mayor altura entre ambos hijos.
        node.height = 1 + Math.max(height(node.left), height(node.right));
    }

    /**
     * Devuelve la altura de un nodo.
     *
     * @param node nodo consultado.
     * @return altura del nodo o 0 si es null.
     */
    private int height(Node node) {

        // Un nodo null tiene altura 0.
        if (node == null) {
            return 0;
        }

        return node.height;
    }

    /**
     * Calcula el factor de balance de un nodo.
     *
     * Fórmula:
     * balance = altura izquierda - altura derecha
     *
     * @param node nodo consultado.
     * @return factor de balance del nodo.
     */
    private int getBalance(Node node) {

        // Un nodo null tiene balance 0.
        if (node == null) {
            return 0;
        }

        return height(node.left) - height(node.right);
    }

    /**
     * Vacía completamente el árbol AVL.
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
     * Devuelve la altura del árbol AVL.
     *
     * @return altura del árbol.
     */
    @Override
    public int getHeightOrSize() {
        return height(root);
    }

    /**
     * Devuelve el nombre de la estructura.
     *
     * @return nombre AVL.
     */
    @Override
    public String getName() {
        return "AVL";
    }

    /**
     * Devuelve la cantidad de nodos del árbol.
     *
     * Este método no está en la interfaz, pero puede ser útil para pruebas.
     *
     * @return cantidad de nodos.
     */
    public int getSize() {
        return size;
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
     * @return árbol en formato texto.
     */
    public String toStructuredString() {
        StringBuilder builder = new StringBuilder();
        buildString(root, builder, "", true);
        return builder.toString();
    }

    /**
     * Construye una representación visual del árbol usando texto.
     *
     * @param node nodo actual.
     * @param builder texto acumulado.
     * @param prefix espacios para ordenar el dibujo.
     * @param isTail indica si es el último nodo del nivel actual.
     */
    private void buildString(Node node, StringBuilder builder, String prefix, boolean isTail) {

        // Si el nodo no existe, no se imprime.
        if (node == null) {
            return;
        }

        // Se agrega el nodo actual al texto.
        builder.append(prefix)
                .append(isTail ? "└── " : "├── ")
                .append(node.key)
                .append(" (h=")
                .append(node.height)
                .append(")")
                .append("\n");

        // Lista auxiliar para guardar los hijos existentes.
        List<Node> children = new ArrayList<>();

        // Se agrega el hijo izquierdo si existe.
        if (node.left != null) {
            children.add(node.left);
        }

        // Se agrega el hijo derecho si existe.
        if (node.right != null) {
            children.add(node.right);
        }

        // Se imprimen los hijos del nodo actual.
        for (int i = 0; i < children.size(); i++) {
            buildString(
                    children.get(i),
                    builder,
                    prefix + (isTail ? "    " : "│   "),
                    i == children.size() - 1
            );
        }
    }
}