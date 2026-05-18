package structures;

import java.util.List;
import java.util.ArrayList;

/**
 * Implementación de un Árbol Splay.
 */
public class SplayTree implements BenchmarkStructure {

    /**
     * Nodo interno del árbol Splay.
     */
    private static class Node {
        int key;
        Node left, right, parent;

        Node(int key) {
            this.key = key;
        }
    }

    private Node root;
    private int size;
    private long comparisons;

    /**
     * Constructor del árbol Splay vacío.
     */
    public SplayTree() {
        this.root = null;
        this.size = 0;
        this.comparisons = 0;
    }

    @Override
    public void insert(int key) {
        if (root == null) {
            root = new Node(key);
            size++;
            return;
        }

        Node current = root;
        Node parent = null;

        while (current != null) {
            parent = current;
            comparisons++;
            if (key < current.key) {
                current = current.left;
            } else if (key > current.key) {
                current = current.right;
            } else {
                splay(current); // Si ya existe, se le hace Splay
                return;
            }
        }

        Node newNode = new Node(key);
        newNode.parent = parent;
        if (key < parent.key) {
            parent.left = newNode;
        } else {
            parent.right = newNode;
        }

        size++;
        splay(newNode); // Splay del nuevo nodo insertado hacia la raíz
    }

    @Override
    public boolean search(int key) {
        Node node = findNode(key);
        if (node != null) {
            splay(node); // Splay del nodo encontrado hacia la raíz
            return true;
        }
        return false;
    }

    @Override
    public void delete(int key) {
        Node node = findNode(key);
        if (node == null) return;

        splay(node); // Lleva el nodo a eliminar a la raíz

        if (node.left == null) {
            replace(node, node.right);
        } else if (node.right == null) {
            replace(node, node.left);
        } else {
            Node successor = minimum(node.right);
            if (successor.parent != node) {
                replace(successor, successor.right);
                successor.right = node.right;
                successor.right.parent = successor;
            }
            replace(node, successor);
            successor.left = node.left;
            if (successor.left != null) successor.left.parent = successor;
        }
        size--;
    }

    @Override
    public int getHeightOrSize() {
        return getHeight(root);
    }

    /**
     * Calcula la altura del árbol Splay.
     *
     * La altura se calcula contando nodos:
     * - Árbol vacío: 0
     * - Árbol con solo raíz: 1
     *
     * @param node nodo actual.
     * @return altura desde ese nodo.
     */
    private int getHeight(Node node) {
        if (node == null) {
            return 0;
        }

        int leftHeight = getHeight(node.left);
        int rightHeight = getHeight(node.right);

        return 1 + Math.max(leftHeight, rightHeight);
    }

    @Override
    public long getComparisons() {
        return comparisons;
    }

    @Override
    public void resetComparisons() {
        this.comparisons = 0;
    }

    @Override
    public String getName() {
        return "Splay Tree";
    }

    // ==========================================
    //          LÓGICA INTERNA DE SPLAY
    // ==========================================

    private void splay(Node x) {
        while (x.parent != null) {
            if (x.parent.parent == null) {
                // Caso Zig o Zag (Rotación simple)
                if (x == x.parent.left) rotateRight(x.parent);
                else rotateLeft(x.parent);
            } else if (x == x.parent.left && x.parent == x.parent.parent.left) {
                // Caso Zig-Zig (Mismo sentido por la izquierda)
                rotateRight(x.parent.parent);
                rotateRight(x.parent);
            } else if (x == x.parent.right && x.parent == x.parent.parent.right) {
                // Caso Zag-Zag (Mismo sentido por la derecha)
                rotateLeft(x.parent.parent);
                rotateLeft(x.parent);
            } else if (x == x.parent.right && x.parent == x.parent.parent.left) {
                // Caso Zig-Zag (Diferente sentido)
                rotateLeft(x.parent);
                rotateRight(x.parent);
            } else {
                // Caso Zag-Zig (Diferente sentido)
                rotateRight(x.parent);
                rotateLeft(x.parent);
            }
        }
    }

    private void rotateLeft(Node x) {
        Node y = x.right;
        if (y == null) return;
        x.right = y.left;
        if (y.left != null) y.left.parent = x;
        y.parent = x.parent;
        if (x.parent == null) root = y;
        else if (x == x.parent.left) x.parent.left = y;
        else x.parent.right = y;
        y.left = x;
        x.parent = y;
    }

    private void rotateRight(Node x) {
        Node y = x.left;
        if (y == null) return;
        x.left = y.right;
        if (y.right != null) y.right.parent = x;
        y.parent = x.parent;
        if (x.parent == null) root = y;
        else if (x == x.parent.right) x.parent.right = y;
        else x.parent.left = y;
        y.right = x;
        x.parent = y;
    }

    private Node findNode(int key) {
        Node current = root;
        while (current != null) {
            comparisons++;
            if (key == current.key) return current;
            current = (key < current.key) ? current.left : current.right;
        }
        return null;
    }

    private void replace(Node u, Node v) {
        if (u.parent == null) root = v;
        else if (u == u.parent.left) u.parent.left = v;
        else u.parent.right = v;
        if (v != null) v.parent = u.parent;
    }

    private Node minimum(Node x) {
        while (x.left != null) x = x.left;
        return x;
    }

    @Override
    public void clear() {
        this.root = null;
        this.size = 0;
        this.comparisons = 0; // O la variable que uses para contar comparaciones
    }

    public Node getRoot() {
        return this.root;
    }

    /**
     * Devuelve una representacion textual del arbol Splay.
     *
     * @return arbol en formato texto.
     */
    public String toStructuredString() {
        StringBuilder builder = new StringBuilder();

        if (root == null) {
            return "(arbol vacio)";
        }

        builder.append("ROOT: ")
                .append(root.key)
                .append("\n");

        buildString(root.left, builder, "", false, "L");
        buildString(root.right, builder, "", true, "R");

        return builder.toString();
    }

    /**
     * Construye una representacion visual del arbol Splay.
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