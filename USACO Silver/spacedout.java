import java.io.*;
import java.util.*;
 
import java.math.*;
import java.awt.Point;
 
public class spacedout {
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
        int[][] nums = new int[N][N];
        for (int i = 0; i < N; i++)
            nums[i] = sc.intArray(N, 0);
        
        int case1 = 0;
        for (int i = 0; i < N; i++) {
            int[] sum = new int[2];
            for (int j = 0; j < N; j++) {
                sum[j%2] += nums[i][j];
            }
            case1 += Math.max(sum[0],sum[1]);
        }

        int case2 = 0;
        for (int j = 0; j < N; j++) {
            int[] sum = new int[2];
            for (int i = 0; i < N; i++) {
                sum[i%2] += nums[i][j];
            }
            case2 += Math.max(sum[0],sum[1]);
        }

        pw.println(Math.max(case1,case2));
        pw.close();
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