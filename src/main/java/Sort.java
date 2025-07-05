import java.util.Arrays;
import java.util.Collections;
import java.util.Random;
import java.util.stream.IntStream;

public class Sort {

    private static final int INIT_VALUE = 0;
    private static final int MAX_VALUE = 10;

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

        //int [] defaultValue = {4, 3, 6, 8, 7, 1, 0, 5, 2, 9};
        int [] defaultValue = {0, 3, 1, 4, 2, 5, 7, 6};

        //System.out.println(Arrays.toString(randomCase));

        long startTime = System.currentTimeMillis();

        //bubbleSort(worstCase);
        //insertionSort(randomCase);
        //selectionSort(randomCase);
        //mergeSort(randomCase, randomCase.length);
        //heapSort(defaultValue);
        //quickSort(defaultValue);
        //blockSort(defaultValue);

        long endTime = System.currentTimeMillis();
        long duration = endTime - startTime;
        System.out.println("Tempo de execução bubble sort: " + duration + " milisegundos");

        startTime = System.currentTimeMillis();

        heapSort(worstCase);

        endTime = System.currentTimeMillis();
        duration = endTime - startTime;
        System.out.println("Tempo de execução heapsort: " + duration + " milisegundos");

    }


    /*
        -------------------------------- Big O Notation ------------------------------
        O(1) (Constante): O tempo de execução é constante, não importa o tamanho da entrada.
        O(log n) (Logarítmico): O tempo de execução cresce muito lentamente.
        O(n) (Linear): O tempo de execução cresce diretamente com o tamanho da entrada.
        O(n log n): (Linear logaritmico) O tempo de execução cresce com o tamanho da entrada multiplicado pela entrada lentamente.
        O(n²) (Quadrático): O tempo de execução cresce exponencialmente com o quadrado do tamanho da entrada (geralmente menos eficiente para grandes entradas).
        O(2ⁿ) (Exponencial): O tempo de execução cresce muito rapidamente, tornando-se impraticável para entradas moderadamente grandes.
        --------------------------------------------------------------------------------
    */

    /* BlockSort
        Complexidade Pior caso:             O(n log n)
        Complexidade Caso medio:            O(n log n)
        Complexidade Melhor caso:           O(n)
        Complexidade de espaços pior caso:  Worst-case space complexity	O(1)
        Sorting In Place: Yes (Dont need extra list)
        Stable: Yes (value on correct index change his initial position)
    */



    /* QuickSort
        Complexidade Pior caso:             O(n^{2})
        Complexidade Caso medio:            O(n log n)
        Complexidade Melhor caso:           O(n log n)
        Complexidade de espaços pior caso:  O(n)
        Sorting In Place: Yes (Dont need extra list)
        Stable: No (value on correct index change his initial position)
    */
    private static void quickSort(int[] array) {
        quickSort(array, 0, array.length - 1);
    }

    public static void quickSort(int[] array, int lowIndex, int highIndex) {
        if (lowIndex >= highIndex) {
            return;
        }
        int pivotIndex = new Random().nextInt(highIndex - lowIndex) + lowIndex;
        /*int pivot = array[highIndex];*/
        int pivot = array[pivotIndex];
        swap(array, pivotIndex, highIndex);

        int leftPointer = partitioning(array, lowIndex, highIndex, pivot);

        swap(array, leftPointer, highIndex);
        quickSort(array, lowIndex, leftPointer - 1);
        quickSort(array, leftPointer + 1, highIndex);
    }

    private static int partitioning(int[] array, int lowIndex, int highIndex, int pivot) {
        int leftPointer = lowIndex;
        int rightPointer = highIndex;

        while(leftPointer < rightPointer) {
            while (array[leftPointer] <= pivot && leftPointer < rightPointer) {
                leftPointer++;
            }
            while(array[rightPointer] >= pivot && leftPointer < rightPointer) {
                rightPointer--;
            }
            swap(array, leftPointer, rightPointer);
        }
        return leftPointer;
    }


    /*
        Complexidade Pior caso:             O(n^{2})
        Complexidade Caso medio:            O(n^{2})
        Complexidade Melhor caso:           O(n)
        Complexidade de espaços pior caso:  O(1) auxiliar
        Sorting in place: Yes (Dont need extra list)
        Stable: Yes (value on correct index dont change his initial position)
     */
    public static void bubbleSort(int[] array) {
        /*System.out.println(Arrays.toString(array));
        int numberOfComparisons = 0;
        int numberOfSwaps = 0;
*/
        int index = 0;
        int length = array.length;
        boolean swapNeeded = true;

        while (index < length - 1 && swapNeeded) {
            swapNeeded = false;
            //numberOfComparisons++;
            for (int compareIndex = 1; compareIndex < length - index; compareIndex++) {
                //numberOfComparisons++;
                if (array[compareIndex - 1] > array[compareIndex]) {
                    //numberOfSwaps++;
                    int aux = array[compareIndex - 1];
                    array[compareIndex - 1] = array[compareIndex];
                    array[compareIndex] = aux;
                    swapNeeded = true;
                }
            }
            if(!swapNeeded) {
                break;
            }
            index++;
        }
        //System.out.println(Arrays.toString(array));
        //System.out.println("Number of comparisons: " + numberOfComparisons);
        //System.out.println("Number of swaps: " + numberOfSwaps);
    }

    /*
        Complexidade Pior caso:             O(n^{2})
        Complexidade Caso medio:            O(n^{2})
        Complexidade Melhor caso:           O(n)
        Complexidade de espaços pior caso:  O(n) total, O(1) auxiliar
        Sorting In Place: Yes (Dont need extra list)
        Stable: Yes (value on correct index dont change his initial position)
     */
    public static void insertionSort(int[] numberVector) {
        int numberOfInteractions = 0;

        for (int index = 1; index < numberVector.length; index++){
            numberOfInteractions ++;
            System.out.println(Arrays.toString(numberVector));
            int key = numberVector[index];
            int compareIndex = index;

            while ((compareIndex > 0) && (numberVector[compareIndex - 1] > key)){
                numberOfInteractions ++;
                numberVector[compareIndex] = numberVector[compareIndex - 1];
                compareIndex -= 1;

                System.out.println(Arrays.toString(numberVector));
            }
            numberVector[compareIndex] = key;
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

    /*
        HeapSort utiliza arvore binaria, funciona em duas etapas, primeiro ordena os nodos com valores maiores que os leafs,
        depois faz o sort pegando o topo da piramide com o ultimo leaf (ponta do array), desconsidera o valor adicionado nas seguintes trocas e faz o processo novamente.

        Complexidade Pior caso:             O(n log n)
        Complexidade Caso medio:            O(n log n)
        Complexidade Melhor caso:           O(n log n)
        Complexidade de espaços pior caso:  O(n) total, O(1) auxiliar
        Sorting In Place: Yes (Dont need extra list)
        Stable: No (value on correct index change his initial position)
     */
    public static void heapSort(int[] array) {
        int length = array.length;

        //Build the max heap
        for (int index = length / 2 - 1; index >= 0; index--) {
            heapify(array, length, index);
        }

        //sort the heap
        for (int index = length - 1; index > 0; index --) {
            swap(array, 0, index);
            heapify(array, index, 0);
        }

        //System.out.println(Arrays.toString(array));
    }

    public static void heapify(int[] array, int length, int index) {
        int max = index;
        int left = 2 * index + 1;
        int right = 2 * index + 2;

        //Compare left and right child nodes to
        //find the index of the max code
        if (left < length && array[left] > array[max]) {
            max = left;
        }
        if (right < length && array[right] > array[max]) {
            max = right;
        }

        if (max != index) { //If max is child node
            swap(array, index, max);
            //Recursively heapify the sub-tree
            heapify(array, length, max);
        }
    }

    static void swap(int[] array, int index, int minMax) {
        int aux = array[index];
        array[index] = array[minMax];
        array[minMax] = aux;
    }

}
