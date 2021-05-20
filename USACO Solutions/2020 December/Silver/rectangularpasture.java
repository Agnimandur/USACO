import java.io.*;
import java.util.*;
 
import java.math.*;
import java.awt.Point;
 
public class rectangularpasture {
    static final long MOD = 1000000007L;
    //static final long MOD2 = 1000000009L;
    //static final long MOD = 998244353L;
    //static final long INF = 500000000000L;
    static final int INF = 1100000000;
    static final int NINF = -100000;
    static FastScanner sc;
    static PrintWriter pw;
    static final int[][] dirs = {{-1,0},{1,0},{0,-1},{0,1}};

    public static void main(String[] args) {
        sc = new FastScanner();
        pw = new PrintWriter(System.out);

        int N = sc.ni();
        int[][] nums = new int[N][2];
        TreeMap<Integer,Integer> X = new TreeMap<>();
        for (int i = 0; i < N; i++) {
            nums[i] = new int[]{sc.ni(),sc.ni()};
            X.put(nums[i][0],0);
        }
        int index = 0;
        for (int x: X.keySet()) {
            X.put(x,index);
            index++;
        }
        for (int i = 0; i < N; i++) {
            nums[i][0] = X.get(nums[i][0]);
        }
        sort(nums);

        long ans = 0;
        for (int i = 0; i < N-1; i++) {
            BinaryIndexedTree bit = new BinaryIndexedTree(N);
            for (int j = i+1; j < N; j++) {
                int minX = Math.min(nums[i][0],nums[j][0]);
                int maxX = Math.max(nums[i][0],nums[j][0]);
                ans += (1+bit.sum(0, minX))*(1+bit.sum(maxX,N-1));
                bit.add(1, nums[j][0]);
            }
        }

        //empty set and sets of 1 cow
        pw.println(ans+N+1);
        pw.close();
    }

    static class BinaryIndexedTree {
        public int[] arr;
      
        public BinaryIndexedTree (int N) {
          arr = new int[N+1];
        }
      
        //add k to the i-th element.
        public void add(int k, int i) {
          int node = i+1;
          while (node < arr.length) {
            arr[node] += k;
            node += node & (-node);
          }
        }
      
        //sum up the elements from input[s_i] to input[e_i], from [s_i,e_i].
        public int sum(int s_i, int e_i) {
          return sum(e_i+1) - sum(s_i);
        }
      
        private int sum(int i) {
          int total = 0;
          int node = i;
          while (node > 0) {
            total += arr[node];
            node -= node & (-node);
          }
          return total;
        }
    }

    public static void sort(int[][] arr) {
        //Sort an array (immune to quicksort TLE)
            Random rgen = new Random();
            for (int i = 0; i < arr.length; i++) {
          int randomPosition = rgen.nextInt(arr.length);
          int[] temp = arr[i];
          arr[i] = arr[randomPosition];
          arr[randomPosition] = temp;
            }
        Arrays.sort(arr, new Comparator<int[]>() {
          @Override
          public int compare(int[] a, int[] b) {
            return a[1]-b[1];
          }
        });
    }
 
    static class FastScanner {
        BufferedReader br;
        StringTokenizer st;

        public FastScanner() {
            br = new BufferedReader(new InputStreamReader(System.in), 32768);
            st = null;
        }
 
        String next() {
            while (st == null || !st.hasMoreElements()) {
                try {
                    st = new StringTokenizer(br.readLine());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            return st.nextToken();
        }
 
        int ni() {
            return Integer.parseInt(next());
        }
 
        int[] intArray(int N, int mod) {
            int[] ret = new int[N];
            for (int i = 0; i < N; i++)
                ret[i] = ni()+mod;
            return ret;
        }
 
        long nl() {
            return Long.parseLong(next());
        }
 
        long[] longArray(int N, long mod) {
            long[] ret = new long[N];
            for (int i = 0; i < N; i++)
                ret[i] = nl()+mod;
            return ret;
        }
 
        double nd() {
            return Double.parseDouble(next());
        }
 
        String nextLine() {
            String str = "";
            try {
                str = br.readLine();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return str;
        }
    }
}