import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;
import java.util.Random;

public class DataGenerator {

    public static void generate() {
        int[] SIZES = {512, 8192, 65536};
        
        for (int size : SIZES) {
            int[] randomizedData = new int[size];
            Random rand = new Random(21);

            for (int i = 0; i < size; i++) {
                randomizedData[i] = rand.nextInt(size) + 1;
            }
            save(randomizedData, "random", size);

            int[] sortedData = Arrays.copyOf(randomizedData, size);
            Arrays.sort(sortedData);
            save(sortedData, "sorted", size);

            int[] reversedData = new int[size];
            for (int i = 0; i < size; i++) {
                reversedData[i] = sortedData[size-i-1];
            }
            save(reversedData, "reversed", size);
        }
    }

    public static void save(int[] array, String status, int size) {
        String filename = String.format("data/%s_%d", status, size);
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter(filename));

            for (int i = 0; i < array.length; i++) {
                writer.write(Integer.toString(array[i]));
                writer.newLine();
            }

            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
