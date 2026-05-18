import gui.MainWindow;

import javax.swing.SwingUtilities;

/**
 * Punto de entrada final del proyecto.
 *
 * Este Main abre la interfaz grafica principal.
 */
public class Main {

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            MainWindow window = new MainWindow();
            window.setVisible(true);
        });
    }
}