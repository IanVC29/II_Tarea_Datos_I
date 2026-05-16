package structures;

import java.util.ArrayList;
import java.util.List;

/**
 * Implementación propia de un Árbol Splay para el benchmark.
 * Adaptado al 100% con la interfaz modificada de la carpeta grupal.
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
    public boolean delete(int key) {
        Node node = findNode(key);
        if (node == null) return false;

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
        return true;
    }

    @Override
    public int getHeightOrSize() {
        return size; // Satisface la interfaz compartida retornando la cantidad de elementos
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
}