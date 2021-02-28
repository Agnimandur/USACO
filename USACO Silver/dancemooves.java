import java.io.*;
import java.util.*;
 
import java.math.*;
import java.awt.Point;
 
public class dancemooves {
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
        int K = sc.ni();
        int[] nums = new int[N];
        for (int i = 0; i < N; i++) nums[i] = i;
        HashSet<Integer>[] hit = new HashSet[N];
        for (int i = 0; i < N; i++) {
            hit[i] = new HashSet<Integer>();
            hit[i].add(i);
        }
        for (int i = 0; i < K; i++) {
            int a = sc.ni()-1;
            int b = sc.ni()-1;
            int temp = nums[a];
            nums[a] = nums[b];
            nums[b] = temp;
            hit[nums[a]].add(a);
            hit[nums[b]].add(b);
        }
        int[] next = new int[N];
        for (int i = 0; i < N; i++)
            next[nums[i]] = i;
        int[] ans = new int[N];
        for (int i = 0; i < N; i++) {
            if (ans[i] > 0) continue;
            HashSet<Integer> all = new HashSet<Integer>();
            ArrayList<Integer> cyc = new ArrayList<Integer>();
            cyc.add(i);
            all.addAll(hit[i]);
            int j = next[i];
            while (j != i) {
                cyc.add(j);
                all.addAll(hit[j]);
                j = next[j];
            }
            int sz = all.size();
            for (int c: cyc)
                ans[c] = sz;
        }
        for (int a: ans)
            pw.println(a);
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