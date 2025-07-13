package sort;

import java.util.Arrays;

public class ParallelQuickSort {

    // Exemplo de uso e medição de desempenho
    public static void main(String[] args) throws InterruptedException {
        int n = 1000000;
        int[] original = new int[n];
        for (int i = 0; i < n; i++) {
            original[i] = (int)(Math.random() * n);
        }

        int[] a1 = Arrays.copyOf(original, n);
        int[] a2 = Arrays.copyOf(original, n);

        // QuickSort sequencial
        long t0 = System.nanoTime();
        quickSort(a1, 0, a1.length - 1);
        long t1 = System.nanoTime();

        // QuickSort paralelo
        long t2 = System.nanoTime();
        parallelQuickSort(a2);
        long t3 = System.nanoTime();

        System.out.println("Quicksort Time with 1.000.000 random values:");
        System.out.printf("Sequential: %,.3f ms%n", (t1 - t0) / 1e6);
        System.out.printf("Paralell:   %,.3f ms%n", (t3 - t2) / 1e6);
    }

    // Tamanho mínimo para paralelizar; abaixo disso, ordena-se sequencialmente
    private static final int THRESHOLD = 16_000;

    public static void parallelQuickSort(int[] arr) throws InterruptedException {
        parallelQuickSort(arr, 0, arr.length - 1);
    }

    private static void parallelQuickSort(int[] arr, int low, int high) throws InterruptedException {
        if (low < high) {
            int pivotIndex = partition(arr, low, high);

            // Se o segmento for grande, paraleliza; senão, faz sequencial
            if (high - low > THRESHOLD) {
                Thread leftSorter = new Thread(() -> {
                    try {
                        parallelQuickSort(arr, low, pivotIndex - 1);
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                    }
                });
                Thread rightSorter = new Thread(() -> {
                    try {
                        parallelQuickSort(arr, pivotIndex + 1, high);
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                    }
                });

                leftSorter.start();
                rightSorter.start();
                leftSorter.join();
                rightSorter.join();
            } else {
                // Se pequeno, cai para QuickSort normal
                quickSort(arr, low, pivotIndex - 1);
                quickSort(arr, pivotIndex + 1, high);
            }
        }
    }

    // QuickSort clássico sem threads
    private static void quickSort(int[] arr, int low, int high) {
        if (low < high) {
            int p = partition(arr, low, high);
            quickSort(arr, low, p - 1);
            quickSort(arr, p + 1, high);
        }
    }

    // Particiona em torno do pivô (último elemento)
    private static int partition(int[] arr, int low, int high) {
        int pivot = arr[high];
        int i = low - 1;
        for (int j = low; j < high; j++) {
            if (arr[j] <= pivot) {
                i++;
                swap(arr, i, j);
            }
        }
        swap(arr, i + 1, high);
        return i + 1;
    }

    private static void swap(int[] arr, int i, int j) {
        int tmp = arr[i];
        arr[i] = arr[j];
        arr[j] = tmp;
    }


}
