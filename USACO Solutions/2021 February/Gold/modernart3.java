import java.util.*;
import java.io.*;

public class modernart3 {
    static FastScanner sc;
    static PrintWriter pw;

    public static void main(String[] args) {
        sc = new FastScanner();
        pw = new PrintWriter(System.out);

        int N = sc.ni();
        int[] nums = new int[N];
        
        for (int i = 0; i < N; i++) {
            nums[i] = sc.ni()-1;
        }

        int[][] dp = new int[N][N];
        for (int i = 0; i < N; i++) {
            dp[i][i] = 0;
        }

        for (int sz = 2; sz <= N; sz++) {
            for (int i = 0; i <= N-sz; i++) {
                int j = i+sz-1;
                if (nums[i]==nums[j]) {
                    dp[i][j] = Math.max(dp[i][j],1+dp[i+1][j-1]);
                }
                for (int k = i+1; k < j; k++) {
                    dp[i][j] = Math.max(dp[i][j],dp[i][k]+dp[k][j]);
                }
            }
        }
        pw.println(N-dp[0][N-1]);
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
 
        long nl() {
            return Long.parseLong(next());
        }
    }
}