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



}
