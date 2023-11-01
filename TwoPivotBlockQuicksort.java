import java.util.Stack;

public class TwoPivotBlockQuicksort {
    
    public static void sort(int[] A) {
        Stack<Integer[]> recursionStack = new Stack<>();
        recursionStack.push(new Integer[]{0, A.length-1, 0});

        while(!recursionStack.isEmpty()) {
            Integer[] cur = recursionStack.pop();
            int l = cur[0];
            int r = cur[1];
            int depth = cur[2];

            int[] result = blockLomuto2(A, l, r);
            int i = result[0];
            int j = result[1];
            
            if (i - 1 - l + 1 > 1) {
                recursionStack.push(new Integer[]{l, i-1, depth+1});
            }
            if (j - 1 - i - 1 + 1 > 1) {
                recursionStack.push(new Integer[]{i + 1, j - 1, depth+1});
            }
            if (r - j - 1 + 1 > 1) {
                recursionStack.push(new Integer[]{j + 1, r, depth+1});
            }
        }
    }

    public static int[] blockLomuto2(int[] A, int l, int r) {
        if (A[l] > A[r]) {
            int temp = A[l];
            A[l] = A[r];
            A[r] = temp;
        }
        
        int p = A[l];
        int q = A[r];
        
        int B = 1024;
        int[] block = new int[B];
        
        int i = l + 1;
        int j = i;
        int k = i;
        int num_p = 0;
        int num_q = 0;

        while (k < r) {
            int t = Math.min(B, r - k);
            for (int c = 0; c < t; c++) {
                block[num_q] = c;
                num_q += (q > A[k + c]) ? 1 : 0;
            }

            for (int c = 0; c < num_q; c++) {
                int temp = A[j + c];
                A[j + c] = A[k + block[c]];
                A[k + block[c]] = temp;
            }

            k += t;
            
            for (int c = 0; c < num_q; c++) {
                block[num_p] = c;
                num_p += (p > A[j + c]) ? 1 : 0;
            }
            
            for (int c = 0; c < num_p; c++) {
                int temp = A[i];
                A[i] = A[j + block[c]];
                A[j + block[c]] = temp;
                i++;
            }
            
            j += num_q;
            num_p = 0;
            num_q = 0;
        }
        
        int temp = A[i - 1];
        A[i - 1] = A[l];
        A[l] = temp;
        temp = A[j];
        A[j] = A[r];
        A[r] = temp;
        
        return new int[]{i - 1, j};
    }
}