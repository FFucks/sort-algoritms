import java.util.Arrays;
import java.util.Collections;
import java.util.Random;
import java.util.stream.IntStream;

public class Sort {

    private static final int INIT_VALUE = 0;
    private static final int MAX_VALUE = 5;

    public static void main(String[] args) {

        int[] randomCase = new Random().ints(INIT_VALUE, MAX_VALUE)
                .distinct()
                .limit(MAX_VALUE)
                .boxed()
                .mapToInt(Integer::intValue).toArray();

        int[] worstCase = IntStream.rangeClosed(INIT_VALUE, MAX_VALUE)
                .boxed()
                .sorted(Collections.reverseOrder())
                .mapToInt(Integer::intValue)
                .toArray();

        int [] bestCase = IntStream.rangeClosed(INIT_VALUE, MAX_VALUE)
                .boxed()
                .sorted()
                .mapToInt(Integer::intValue)
                .toArray();

        //System.out.println(Arrays.toString(randomCase));

        //bubbleSort(randomCase);
        insertionSort(randomCase);
        selectionSort(randomCase);
        mergeSort(randomCase, randomCase.length);
        heapSort(randomCase);

    }

    /*
        Complexidade Pior caso:             O(n^{2})
        Complexidade Caso medio:            O(n^{2})
        Complexidade Melhor caso:           O(n)
        Complexidade de espaços pior caso:  O(1) auxiliar
     */
    public static void bubbleSort(int[] numberVector) {
        boolean swap;
        int aux;
        int numberOfInteractions = 0;

        System.out.println(Arrays.toString(numberVector));

        do {
            swap = false;
            for (int i = 0; i < numberVector.length - 1; i++) {
                if (numberVector[i] > numberVector[i +1]) {
                    aux = numberVector[i];
                    numberVector[i] = numberVector[i +1];
                    numberVector[i +1] = aux;
                    swap = true;
                    numberOfInteractions ++;

                    System.out.println(Arrays.toString(numberVector));
                }
            }
        } while (swap);

        System.out.println("Number of interactions: " + numberOfInteractions);
    }

    /*
        Complexidade Pior caso:             O(n^{2})
        Complexidade Caso medio:            O(n^{2})
        Complexidade Melhor caso:           O(n)
        Complexidade de espaços pior caso:  O(n) total, O(1) auxiliar
     */
    public static void insertionSort(int[] numberVector) {
        int numberOfInteractions = 0;

        for (int i = 1; i < numberVector.length; i++){

            int aux = numberVector[i];
            int j = i;

            while ((j > 0) && (numberVector[j - 1] > aux)){
                numberVector[j] = numberVector[j - 1];
                j -= 1;

                numberOfInteractions ++;
                //System.out.println(Arrays.toString(numberVector));
            }
            numberVector[j] = aux;
            System.out.println(Arrays.toString(numberVector));
        }
        System.out.println("Number of interactions: " + numberOfInteractions);
    }

    static void selectionSort(int[] numberVector){
        int n = numberVector.length;
        for (int i = 0; i < n - 1; i++) {

            // Assume the current position holds
            // the minimum element
            int min_idx = i;

            // Iterate through the unsorted portion
            // to find the actual minimum
            for (int j = i + 1; j < n; j++) {
                if (numberVector[j] < numberVector[min_idx]) {

                    // Update min_idx if a smaller element
                    // is found
                    min_idx = j;
                }
            }

            // Move minimum element to its
            // correct position
            int temp = numberVector[i];
            numberVector[i] = numberVector[min_idx];
            numberVector[min_idx] = temp;
        }
    }

    public static void mergeSort(int[] numberVector, int n) {
        if (n < 2) {
            return;
        }
        int mid = n / 2;
        int[] l = new int[mid];
        int[] r = new int[n - mid];

        for (int i = 0; i < mid; i++) {
            l[i] = numberVector[i];
        }
        for (int i = mid; i < n; i++) {
            r[i - mid] = numberVector[i];
        }
        mergeSort(l, mid);
        mergeSort(r, n - mid);

        merge(numberVector, l, r, mid, n - mid);
    }

    public static void merge(
            int[] a, int[] l, int[] r, int left, int right) {

        int i = 0, j = 0, k = 0;
        while (i < left && j < right) {
            if (l[i] <= r[j]) {
                a[k++] = l[i++];
            }
            else {
                a[k++] = r[j++];
            }
        }
        while (i < left) {
            a[k++] = l[i++];
        }
        while (j < right) {
            a[k++] = r[j++];
        }
    }

    public static void heapSort(int arr[]) {
        int n = arr.length;

        // Build max heap
        for (int i = n / 2 - 1; i >= 0; i--) {
            heapify(arr, n, i);
        }

        // Heap sort
        for (int i = n - 1; i >= 0; i--) {
            int temp = arr[0];
            arr[0] = arr[i];
            arr[i] = temp;

            // Heapify root element
            heapify(arr, i, 0);
        }
    }

    public static void heapify(int arr[], int n, int i) {
        // Find largest among root, left child and right child
        int largest = i;
        int l = 2 * i + 1;
        int r = 2 * i + 2;

        if (l < n && arr[l] > arr[largest])
            largest = l;

        if (r < n && arr[r] > arr[largest])
            largest = r;

        // Swap and continue heapifying if root is not largest
        if (largest != i) {
            int swap = arr[i];
            arr[i] = arr[largest];
            arr[largest] = swap;

            heapify(arr, n, largest);
        }
    }

}
