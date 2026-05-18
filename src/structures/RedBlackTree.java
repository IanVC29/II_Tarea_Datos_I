package structures;

/**
 * Implementación de Árbol Rojo-Negro para el benchmark.
 * Adaptado al 100% con la interfaz modificada de la carpeta grupal.
 */
public class RedBlackTree implements BenchmarkStructure {

    private enum Color { RED, BLACK }

    private static class Node {
        int key;
        Node left, right, parent;
        Color color;

        Node(int key) {
            this.key = key;
            this.color = Color.RED;
        }
    }

    private Node root;
    private int size;
    private long comparisons;
    private final Node NIL;

    public RedBlackTree() {
        NIL = new Node(-1);
        NIL.color = Color.BLACK;
        NIL.left = NIL.right = NIL;
        this.root = NIL;
        this.size = 0;
        this.comparisons = 0;
    }

    @Override
    public void insert(int key) {
        Node newNode = new Node(key);
        newNode.left = newNode.right = NIL;

        Node y = null;
        Node x = root;

        while (x != NIL) {
            y = x;
            comparisons++;
            if (newNode.key < x.key) {
                x = x.left;
            } else if (newNode.key > x.key) {
                x = x.right;
            } else {
                return;
            }
        }

        newNode.parent = y;
        if (y == null) {
            root = newNode;
        } else if (newNode.key < y.key) {
            y.left = newNode;
        } else {
            y.right = newNode;
        }

        size++;
        fixInsert(newNode);
    }

    @Override
    public boolean search(int key) {
        Node current = root;
        while (current != NIL) {
            comparisons++;
            if (key == current.key) {
                return true;
            }
            current = (key < current.key) ? current.left : current.right;
        }
        return false;
    }

    @Override
    public void delete(int key) {
        // Borrado N/A en Red-Black
    }

    @Override
    public int getHeightOrSize() {
        return calculateHeight(root); // Devuelve la altura esperada para los árboles
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
        return "Red-Black Tree";
    }

    private void fixInsert(Node k) {
        Node u;
        while (k.parent != null && k.parent.color == Color.RED) {
            if (k.parent == k.parent.parent.right) {
                u = k.parent.parent.left;
                if (u.color == Color.RED) {
                    u.color = Color.BLACK;
                    k.parent.color = Color.BLACK;
                    k.parent.parent.color = Color.RED;
                    k = k.parent.parent;
                } else {
                    if (k == k.parent.left) {
                        k = k.parent;
                        rotateRight(k);
                    }
                    k.parent.color = Color.BLACK;
                    k.parent.parent.color = Color.RED;
                    rotateLeft(k.parent.parent);
                }
            } else {
                u = k.parent.parent.right;
                if (u.color == Color.RED) {
                    u.color = Color.BLACK;
                    k.parent.color = Color.BLACK;
                    k.parent.parent.color = Color.RED;
                    k = k.parent.parent;
                } else {
                    if (k == k.parent.right) {
                        k = k.parent;
                        rotateLeft(k);
                    }
                    k.parent.color = Color.BLACK;
                    k.parent.parent.color = Color.RED;
                    rotateRight(k.parent.parent);
                }
            }
            if (k == root) break;
        }
        root.color = Color.BLACK;
    }

    private void rotateLeft(Node x) {
        Node y = x.right;
        x.right = y.left;
        if (y.left != NIL) y.left.parent = x;
        y.parent = x.parent;
        if (x.parent == null) root = y;
        else if (x == x.parent.left) x.parent.left = y;
        else x.parent.right = y;
        y.left = x;
        x.parent = y;
    }

    private void rotateRight(Node x) {
        Node y = x.left;
        x.left = y.right;
        if (y.right != NIL) y.right.parent = x;
        y.parent = x.parent;
        if (x.parent == null) root = y;
        else if (x == x.parent.right) x.parent.right = y;
        else x.parent.left = y;
        y.right = x;
        x.parent = y;
    }

    private int calculateHeight(Node node) {
        if (node == NIL) return 0;
        return 1 + Math.max(calculateHeight(node.left), calculateHeight(node.right));
    }

    @Override
    public void clear() {
        this.root = NIL; // O el nodo centinela que use tu estructura
        this.size = 0;
        this.comparisons = 0;
    }

    public Node getRoot() {
        return this.root;
    }

    /**
     * Devuelve una representacion textual del arbol Red-Black.
     *
     * @return arbol en formato texto.
     */
    public String toStructuredString() {
        StringBuilder builder = new StringBuilder();

        if (root == null || root == NIL) {
            return "(arbol vacio)";
        }

        builder.append("ROOT: ")
                .append(root.key)
                .append(root.color == Color.RED ? " (R)" : " (B)")
                .append("\n");

        buildString(root.left, builder, "", false, "L");
        buildString(root.right, builder, "", true, "R");

        return builder.toString();
    }

    /**
     * Construye una representacion visual del arbol Red-Black.
     */
    private void buildString(Node node, StringBuilder builder, String prefix, boolean isTail, String side) {
        if (node == null || node == NIL) {
            return;
        }

        builder.append(prefix)
                .append(isTail ? "└── " : "├── ")
                .append(side)
                .append(": ")
                .append(node.key)
                .append(node.color == Color.RED ? " (R)" : " (B)")
                .append("\n");

        boolean hasLeft = node.left != NIL;
        boolean hasRight = node.right != NIL;

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
