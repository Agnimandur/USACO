import java.io.*;
import java.util.*;
 
import java.math.*;
import java.awt.Point;
 
public class notimetopaint {
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
        int Q = sc.ni();
        String s = sc.next();
        SegmentTree st = new SegmentTree(N+2);
        for (int i = 1; i <= N; i++) {
            st.update(i,s.charAt(i-1)-'A'+1);
        }
        int[] next = new int[27];
        Arrays.fill(next,N+1);
        int[] rl = new int[N+2];
        for (int i = N; i >= 1; i--) {
            int c = s.charAt(i-1)-'A'+1;
            if (st.query(i, next[c]) < c) {
                rl[i] = rl[i+1]+1;
            } else {
                rl[i] = rl[i+1];
            }
            next[c] = i;
        }

        int[] prev = new int[27];
        Arrays.fill(prev,0);
        int[] lr = new int[N+2];
        for (int i = 1; i <= N; i++) {
            int c = s.charAt(i-1)-'A'+1;
            if (st.query(prev[c],i) < c) {
                lr[i] = lr[i-1]+1;
            } else {
                lr[i] = lr[i-1];
            }
            prev[c] = i;
        }
        
        for (int q = 0; q < Q; q++) {
            int L = sc.ni();
            int R = sc.ni();
            int ans = lr[L-1]+rl[R+1];
            pw.println(ans);
        }
        pw.close();
    }

    static class SegmentTree {
        public int[] tree;
        public int NONE;
        public int N;
      
        //Zero initialization
        public SegmentTree(int n) {
          N = n;
          tree = new int[2*N+1];
          NONE = Integer.MAX_VALUE;
        }
      
        public int merge(int a, int b) {
          return Math.min(a,b);
        }
      
        public int query(int i, int j) {
          return query(0,0,N-1,i,j);
        }
      
        public void update(int i, int val) {
          update(0,0,N-1,i,val);
        }
      
        private int query(int t, int lo, int hi, int i, int j) {
          // query for arr[i..j]
          if (lo > j || hi < i)
            return NONE;
          if (i <= lo && j >= hi)
            return tree[t];
          
          int mid = (lo+hi)/2;
          if (i > mid)
            return query(t+2*(mid-lo+1),mid+1,hi,i,j);
          else if (j <= mid)
            return query(t+1,lo,mid,i,j);
      
          // merge query results
          return merge(query(t+1, lo, mid, i, mid), query(t+2*(mid-lo+1),mid+1,hi,mid+1,j));
        }
      
        private void update(int t, int lo, int hi, int i, int val) {
          if (lo == hi) {
            tree[t] = val;
            return;
          }
      
          int mid = (lo+hi)/2;
          if (i > mid)
            update(t+2*(mid-lo+1),mid+1,hi,i,val);
          else if (i <= mid)
            update(t+1,lo,mid,i,val);
      
          // merge updates
          tree[t] = merge(tree[t+1],tree[t+2*(mid-lo+1)]);
        }
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