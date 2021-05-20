import java.io.*;
import java.util.*;

public class uddered {
    static final long MOD = 1000000007L;
    static final int INF = 10000000;
    static FastScanner sc;
    static PrintWriter pw;

    public static void main(String[] args) {
        sc = new FastScanner();
        pw = new PrintWriter(System.out);

        String s = sc.next();
        int[] map = new int[26];
        for (int i = 0; i < 26; i++)
            map[s.charAt(i)-'a'] = i;
        String t = sc.next();
        int ans = 1;
        for (int i = 1; i < t.length(); i++) {
            if (map[t.charAt(i)-'a'] <= map[t.charAt(i-1)-'a'])
                ans++;
        }
        pw.println(ans);
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

        int[] intArray(int N) {
            int[] ret = new int[N];
            for (int i = 0; i < N; i++)
                ret[i] = ni();
            return ret;
        }
 
        long nl() {
            return Long.parseLong(next());
        }

        long[] longArray(int N) {
            long[] ret = new long[N];
            for (int i = 0; i < N; i++)
                ret[i] = nl();
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