package sort;

import java.util.Arrays;

public class MergeQuickSort {

    // Metodo público que dispara o Hybrid Sort
    public static void hybridSort(int[] arr) {
        if (arr == null || arr.length < 2) return;
        hybridSort(arr, 0, arr.length - 1);
    }

    // Versão recursiva que divide em duas metades, aplica QuickSort e depois faz merge
    private static void hybridSort(int[] arr, int left, int right) {
        if (left >= right) return;

        int mid = left + (right - left) / 2;
        // Ordena a primeira metade com QuickSort
        quickSort(arr, left, mid);
        // Ordena a segunda metade com QuickSort
        quickSort(arr, mid + 1, right);
        // Une (merge) as duas metades já ordenadas
        merge(arr, left, mid, right);
    }

    // Implementação padrão de QuickSort
    private static void quickSort(int[] arr, int low, int high) {
        if (low < high) {
            int p = partition(arr, low, high);
            quickSort(arr, low, p - 1);
            quickSort(arr, p + 1, high);
        }
    }

    // Particiona o array em torno do pivô (último elemento)
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

    // Une dois sub‐arrays ordenados: [left..mid] e [mid+1..right]
    private static void merge(int[] arr, int left, int mid, int right) {
        int n1 = mid - left + 1;
        int n2 = right - mid;

        int[] L = new int[n1];
        int[] R = new int[n2];

        System.arraycopy(arr, left, L, 0, n1);
        System.arraycopy(arr, mid + 1, R, 0, n2);

        int i = 0, j = 0, k = left;
        while (i < n1 && j < n2) {
            if (L[i] <= R[j]) {
                arr[k++] = L[i++];
            } else {
                arr[k++] = R[j++];
            }
        }
        while (i < n1) {
            arr[k++] = L[i++];
        }
        while (j < n2) {
            arr[k++] = R[j++];
        }
    }

    private static void swap(int[] arr, int i, int j) {
        int tmp = arr[i];
        arr[i] = arr[j];
        arr[j] = tmp;
    }

    // Exemplo de uso e comparação simples
    public static void main(String[] args) {
        int[] original = { 5, 2, 9, 1, 5, 6, 3, 7, 4, 8 };
        int[] a1 = Arrays.copyOf(original, original.length);
        int[] a2 = Arrays.copyOf(original, original.length);

        // Medindo QuickSort puro
        long t0 = System.nanoTime();
        quickSort(a1, 0, a1.length - 1);
        long t1 = System.nanoTime();

        // Medindo Merge+QuickSort híbrido
        long t2 = System.nanoTime();
        hybridSort(a2);
        long t3 = System.nanoTime();

        System.out.println("QuickSort puro:           " + Arrays.toString(a1)
                + " (tempo = " + (t1 - t0) + " ns)");
        System.out.println("Merge+QuickSort híbrido:  " + Arrays.toString(a2)
                + " (tempo = " + (t3 - t2) + " ns)");
    }
}
