import structures.BSTree;
import structures.AVLTree;

/**
 * Clase de prueba para la parte del Integrante 2.
 *
 * Esta clase prueba únicamente:
 * - BSTree.
 * - AVLTree.
 *
 * No es el Main final del proyecto.
 * Sirve para que otros integrantes puedan verificar que BST y AVL funcionan.
 */
public class MainIntegrante2 {

    /**
     * Método principal de prueba.
     *
     * @param args argumentos de consola.
     */
    public static void main(String[] args) {

        // Se ejecutan las pruebas del árbol BST.
        probarBST();

        // Separador visual para distinguir las pruebas.
        System.out.println("\n========================================\n");

        // Se ejecutan las pruebas del árbol AVL.
        probarAVL();
    }

    /**
     * Prueba las operaciones principales del árbol BST.
     *
     * Operaciones probadas:
     * - Inserción.
     * - Búsqueda.
     * - Borrado.
     * - Altura.
     * - Comparaciones.
     * - Estados paso a paso.
     */
    private static void probarBST() {

        // Se crea un árbol BST vacío.
        BSTree bst = new BSTree();

        // Se insertan claves de prueba.
        bst.insert(50);
        bst.insert(30);
        bst.insert(70);
        bst.insert(20);
        bst.insert(40);
        bst.insert(60);
        bst.insert(80);

        // Se imprime el árbol después de las inserciones.
        System.out.println("=== BST después de insertar ===");
        System.out.println(bst.toStructuredString());

        // Se busca una clave que sí existe.
        System.out.println("Buscar 40 en BST: " + bst.search(40));

        // Se busca una clave que no existe.
        System.out.println("Buscar 100 en BST: " + bst.search(100));

        // Se elimina una clave del árbol.
        bst.delete(30);

        // Se imprime el árbol después del borrado.
        System.out.println("\n=== BST después de borrar 30 ===");
        System.out.println(bst.toStructuredString());

        // Se imprime la altura actual del árbol.
        System.out.println("Altura BST: " + bst.getHeightOrSize());

        // Se imprime la cantidad total de comparaciones realizadas.
        System.out.println("Comparaciones BST: " + bst.getComparisons());

        // Se imprime la cantidad de estados guardados para el modo paso a paso.
        System.out.println("Estados guardados BST: " + bst.getSequenceStates().size());
    }

    /**
     * Prueba las operaciones principales del árbol AVL.
     *
     * Operaciones probadas:
     * - Inserción.
     * - Rotaciones automáticas.
     * - Búsqueda.
     * - Borrado.
     * - Altura.
     * - Comparaciones.
     * - Estados paso a paso.
     */
    private static void probarAVL() {

        // Se crea un árbol AVL vacío.
        AVLTree avl = new AVLTree();

        // Se insertan claves que provocan rotaciones en el AVL.
        avl.insert(10);
        avl.insert(20);
        avl.insert(30);
        avl.insert(40);
        avl.insert(50);
        avl.insert(25);

        // Se imprime el árbol después de las inserciones.
        System.out.println("=== AVL después de insertar ===");
        System.out.println(avl.toStructuredString());

        // Se busca una clave que sí existe.
        System.out.println("Buscar 25 en AVL: " + avl.search(25));

        // Se busca una clave que no existe.
        System.out.println("Buscar 100 en AVL: " + avl.search(100));

        // Se elimina una clave del árbol.
        avl.delete(40);

        // Se imprime el árbol después del borrado.
        System.out.println("\n=== AVL después de borrar 40 ===");
        System.out.println(avl.toStructuredString());

        // Se imprime la altura actual del árbol.
        System.out.println("Altura AVL: " + avl.getHeightOrSize());

        // Se imprime la cantidad total de comparaciones realizadas.
        System.out.println("Comparaciones AVL: " + avl.getComparisons());

        // Se imprime la cantidad de estados guardados para el modo paso a paso.
        System.out.println("Estados guardados AVL: " + avl.getSequenceStates().size());
    }
}