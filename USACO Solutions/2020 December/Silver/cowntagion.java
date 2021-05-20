import java.io.*;
import java.util.*;
 
import java.math.*;
import java.awt.Point;
 
public class cowntagion {
    static final long MOD = 1000000007L;
    //static final long MOD2 = 1000000009L;
    //static final long MOD = 998244353L;
    //static final long INF = 500000000000L;
    static final int INF = 1100000000;
    static final int NINF = -100000;
    static FastScanner sc;
    static PrintWriter pw;
    static final int[][] dirs = {{-1,0},{1,0},{0,-1},{0,1}};

    static ArrayList<Integer>[] tree;
    public static void main(String[] args) {
        sc = new FastScanner();
        pw = new PrintWriter(System.out);

        int N = sc.ni();
        tree = new ArrayList[N];
        for (int i = 0; i < N; i++)
            tree[i] = new ArrayList<Integer>();
        for (int i = 0; i < N-1; i++) {
            int u = sc.ni()-1;
            int v = sc.ni()-1;
            tree[u].add(v);
            tree[v].add(u);
        }
        int ans = solve(0,-1)+(N-1);
        pw.println(ans);
        pw.close();
    }

    public static int solve(int u, int p) {
        int ans = 0;
        int children = 0;
        for (int v: tree[u]) {
            if (v==p) continue;
            ans += solve(v,u);
            children++;
        }
        while (children > 0) {
            ans++;
            children /= 2;
        }
        return ans;
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