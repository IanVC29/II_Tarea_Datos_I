package structures;

// Arreglo propio usado para el benchmark.
public class ArrayStructure implements BenchmarkStructure {

    private int[] data;
    private int size;
    private long comparisons;

    // Crea un arreglo con capacidad inicial de 10.
    public ArrayStructure() {
        this.data = new int[10];
        this.size = 0;
        this.comparisons = 0;
    }

    // Inserta el valor al final del arreglo.
    @Override
    public void insert(int value) {
        if (size == data.length) {
            resize();
        }

        data[size] = value;
        size++;
    }

    // Busca el valor recorriendo el arreglo de inicio a fin.
    @Override
    public boolean search(int value) {
        for (int i = 0; i < size; i++) {
            comparisons++;

            if (data[i] == value) {
                return true;
            }
        }

        return false;
    }

    // Elimina el valor y desplaza los elementos restantes.
    @Override
    public boolean delete(int value) {
        for (int i = 0; i < size; i++) {
            comparisons++;

            if (data[i] == value) {
                for (int j = i; j < size - 1; j++) {
                    data[j] = data[j + 1];
                }

                size--;
                return true;
            }
        }

        return false;
    }

    // Duplica la capacidad del arreglo.
    private void resize() {
        int[] newData = new int[data.length * 2];

        for (int i = 0; i < data.length; i++) {
            newData[i] = data[i];
        }

        data = newData;
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
        return "Arreglo";
    }
}