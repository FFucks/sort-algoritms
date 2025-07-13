package benchmark;

import sort.Sort;

import java.util.*;
import java.util.function.Consumer;
import java.util.stream.IntStream;

public class SortingBenchmark {

    private static final int INIT_VALUE = 0;
    private static final int MAX_VALUE = 100000;

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

        benchmarkAndPrint("BEST CASE  (array já ordenado)", bestCase);
        benchmarkAndPrint("WORST CASE (array inverso)",      worstCase);
        benchmarkAndPrint("RANDOM CASE (valores aleatórios)", randomCase);
    }

    /**
     * Roda todos os algoritmos sobre um mesmo array-base
     * e retorna map (nome→duração em nanossegundos).
     */
    public static Map<String, Long> benchmarkAll(int[] base) {
        Map<String, Long> timings = new LinkedHashMap<>();
        Sort sort = new Sort();

        measure("bubbleSort",    base, sort::bubbleSort,    timings);
        measure("insertionSort", base, sort::insertionSort, timings);
        measure("selectionSort", base, sort::selectionSort, timings);
        measure("mergeSort",     base, sort::mergeSort,     timings);
        measure("heapSort",      base, sort::heapSort,      timings);
        measure("quickSort",     base, sort::quickSort,     timings);
        measure("countingSort",  base, sort::countingSort,  timings);
        measure("blockSort",     base, sort::blockSort,     timings);
        measure("shellSort",     base, sort::shellSort,     timings);
        measure("radixSort",     base, sort::radixSort,     timings);

        return timings;
    }

    private static void benchmarkAndPrint(String label, int[] array) {
        System.out.println("\n=== " + label + " ===");
        Map<String, Long> result = benchmarkAll(array);

        // Ordena pelo valor (tempo) ascendente
        result.entrySet().stream()
                .sorted(Map.Entry.comparingByValue())
                .forEach(e -> {
                    double ms = e.getValue() / 1_000_000.0;
                    System.out.printf("%-15s : %8.3f ms%n", e.getKey(), ms);
                });
    }

    private static void measure(String name, int[] base, Consumer<int[]> sortMethod, Map<String, Long> timings) {
        int[] arr = base.clone();
        long start = System.nanoTime();
        sortMethod.accept(arr);
        long duration = System.nanoTime() - start;
        timings.put(name, duration);
    }

}
