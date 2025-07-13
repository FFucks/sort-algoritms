package jmh;

import org.openjdk.jmh.annotations.*;
import org.openjdk.jmh.infra.Blackhole;
import sort.Sort;

import java.util.Collections;
import java.util.Random;
import java.util.concurrent.TimeUnit;
import java.util.stream.IntStream;

@BenchmarkMode(Mode.AverageTime)
@OutputTimeUnit(TimeUnit.MILLISECONDS)
@Warmup(iterations = 3, time = 500, timeUnit = TimeUnit.MILLISECONDS)
@Measurement(iterations = 5, time = 500, timeUnit = TimeUnit.MILLISECONDS)
@Fork(value = 1)
public class SortingBenchmarkJmh {

    private static final int SIZE = 100_000;
    private static final int INIT  = 1;
    private static final int MAX   = SIZE;

    @State(Scope.Thread)
    public static class BenchmarkState {
        @Param({
                "bubble",
                "insertion",
                "selection",
                "merge",
                "heap",
                "quick",
                "counting",
                "block",
                "shell",
                "radix"
        })
        public String algorithm;

        @Param({ "random", "best", "worst" })
        public String caseType;

        int[] base;    // o array “base” gerado uma vez
        int[] working; // o array que cada invocação realmente ordena

        @Setup(Level.Trial)
        public void generateBaseArray() {
            switch (caseType) {
                case "best":
                    // já ordenado ascendente
                    base = IntStream.rangeClosed(INIT, MAX).toArray();
                    break;
                case "worst":
                    // ordenado decrescente
                    base = IntStream.rangeClosed(INIT, MAX)
                            .boxed()
                            .sorted(Collections.reverseOrder())
                            .mapToInt(Integer::intValue)
                            .toArray();
                    break;
                default:
                    // valores aleatórios e distintos
                    base = new Random()
                            .ints(INIT, MAX + 1)
                            .distinct()
                            .limit(SIZE)
                            .toArray();
                    break;
            }
        }

        @Setup(Level.Invocation)
        public void setUpInvocation() {
            // clona antes de cada iteração para não “contaminar” runs
            working = base.clone();
        }
    }

    @Benchmark
    public void sortBenchmark(BenchmarkState s, Blackhole bh) {
        Sort sort = new Sort();

        switch (s.algorithm) {
            case "bubble":    sort.bubbleSort(s.working);    break;
            case "insertion": sort.insertionSort(s.working); break;
            case "selection": sort.selectionSort(s.working); break;
            case "merge":     sort.mergeSort(s.working);     break;
            case "heap":      sort.heapSort(s.working);      break;
            case "quick":     sort.quickSort(s.working);     break;
            case "counting":  sort.countingSort(s.working);  break;
            case "block":     sort.blockSort(s.working);     break;
            case "shell":     sort.shellSort(s.working);     break;
            case "radix":     sort.radixSort(s.working);     break;
            default: throw new IllegalStateException("Algoritmo inválido: " + s.algorithm);
        }
        // evita dead-code elimination
        bh.consume(s.working);
    }
}
