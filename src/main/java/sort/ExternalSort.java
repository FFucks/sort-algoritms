package sort;

import java.io.*;
import java.nio.file.*;
import java.util.*;

public class ExternalSort {

    /** Tamanho máximo do buffer em número de linhas (aprox memória). */
    private static final int MAX_LINES_IN_MEMORY = 100_000;

    public static void sort(File input, File output, File tmpDir) throws IOException {
        // 1. Geração de runs
        List<File> runs = createRuns(input, tmpDir);

        // 2. Fusão k-way
        mergeRuns(runs, output);

        // Limpa arquivos temporários
        for (File f : runs) f.delete();
    }

    private static List<File> createRuns(File input, File tmpDir) throws IOException {
        List<File> runs = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(input))) {
            List<String> buffer = new ArrayList<>(MAX_LINES_IN_MEMORY);
            String line;
            while ((line = reader.readLine()) != null) {
                buffer.add(line);
                if (buffer.size() >= MAX_LINES_IN_MEMORY) {
                    runs.add(writeRun(buffer, tmpDir));
                    buffer.clear();
                }
            }
            if (!buffer.isEmpty()) {
                runs.add(writeRun(buffer, tmpDir));
                buffer.clear();
            }
        }
        return runs;
    }

    private static File writeRun(List<String> buffer, File tmpDir) throws IOException {
        buffer.sort(Comparator.naturalOrder());
        File runFile = Files.createTempFile(tmpDir.toPath(), "run", ".txt").toFile();
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(runFile))) {
            for (String s : buffer) writer.write(s + "\n");
        }
        return runFile;
    }

    private static void mergeRuns(List<File> runs, File output) throws IOException {
        // Inicia um leitor para cada run
        List<BufferedReader> readers = new ArrayList<>();
        for (File run : runs) readers.add(new BufferedReader(new FileReader(run)));

        // Min-heap que armazena (valor, índice do reader)
        PriorityQueue<Pair> heap = new PriorityQueue<>();

        // Preeenche o heap com o primeiro elemento de cada run
        for (int i = 0; i < readers.size(); i++) {
            String line = readers.get(i).readLine();
            if (line != null) heap.offer(new Pair(line, i));
        }

        // Abre o writer de saída
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(output))) {
            while (!heap.isEmpty()) {
                Pair p = heap.poll();
                writer.write(p.value);
                writer.newLine();

                // Lê próximo da mesma run e reinsera no heap
                String next = readers.get(p.readerIndex).readLine();
                if (next != null) heap.offer(new Pair(next, p.readerIndex));
            }
        }

        // Fecha leitores
        for (BufferedReader r : readers) r.close();
    }

    /** Classe auxiliar para o heap. */
    private static class Pair implements Comparable<Pair> {
        String value;
        int readerIndex;
        Pair(String value, int readerIndex) {
            this.value = value;
            this.readerIndex = readerIndex;
        }
        @Override
        public int compareTo(Pair o) {
            return this.value.compareTo(o.value);
        }
    }

    // Exemplo de uso:
    public static void main(String[] args) throws IOException {
        File input  = new File("grande_input.txt");
        File output = new File("ordenado.txt");
        File tmpDir = new File("tmpRuns");
        tmpDir.mkdirs();

        ExternalSort.sort(input, output, tmpDir);
    }
}