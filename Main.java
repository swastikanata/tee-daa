import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;

public class Main {    
    public static void main(String[] args) {
        // only when generating data
        // DataGenerator.generate();

        int[] SIZE = {512, 8192, 65536};
        String[] STATUS = {"random", "sorted", "reversed"};
        Runtime runtime = Runtime.getRuntime();
        System.gc();
        double startTime, endTime, beforeUsedMemory, afterUsedMemory, memoryUsed, timeTaken;

        for (int size : SIZE) {
            for (String status : STATUS) {
                int[] array = read(size, status);

                // Merge Sort
                int[] copyArray = Arrays.copyOf(array, array.length);
                startTime = System.currentTimeMillis();
                beforeUsedMemory = runtime.totalMemory() - runtime.freeMemory();
                MergeSort.sort(copyArray, 0, copyArray.length - 1);
                afterUsedMemory = runtime.totalMemory() - runtime.freeMemory();
                endTime = System.currentTimeMillis();

                memoryUsed = (double) (afterUsedMemory - beforeUsedMemory) / 1024.0;
                timeTaken = (double) (endTime - startTime);
                System.out.println("Mergesort for " + status + " " + size + " numbers, " + timeTaken + " ms, " + memoryUsed + " kB");
            
                // Two-pivot block quicksort
                copyArray = Arrays.copyOf(array, array.length);
                startTime = System.currentTimeMillis();
                beforeUsedMemory = runtime.totalMemory() - runtime.freeMemory();
                TwoPivotBlockQuicksort.sort(copyArray);
                afterUsedMemory = runtime.totalMemory() - runtime.freeMemory();
                endTime = System.currentTimeMillis();
                
                memoryUsed = (double) (afterUsedMemory - beforeUsedMemory) / 1024.0;
                timeTaken = (double) (endTime - startTime);
                System.out.println("Two-pivot block quicksort for " + status + " " + size + " numbers, " + timeTaken + " ms, " + memoryUsed + " kB");
            
                System.out.println();
            }
        }
    }

    public static int[] read(int size, String status) {
        String filename = String.format("data/%s_%d", status, size);
        
        int[] intArray = new int[size];
        int currentIndex = 0;

        try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
            String line;
            while ((line = reader.readLine()) != null && currentIndex < size) {
                intArray[currentIndex] = Integer.parseInt(line);
                currentIndex++;
            }
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }

        return intArray;
    }
}