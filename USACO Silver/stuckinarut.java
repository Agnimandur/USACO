import java.io.*;
import java.util.*;
 
import java.math.*;
import java.awt.Point;
 
public class stuckinarut {
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
        ArrayList<Cow> east = new ArrayList<Cow>();
        ArrayList<Cow> north = new ArrayList<Cow>();
        for (int i = 0; i < N; i++) {
            String s = sc.next();
            int x = sc.ni();
            int y = sc.ni();
            if (s.equals("E")) {
                east.add(new Cow(x,y,1,i));
            } else {
                north.add(new Cow(x,y,0,i));
            }
        }
        Collections.sort(east);
        Collections.sort(north);
        //pw.println(east);
        //pw.println(north);

        boolean[] stopped = new boolean[N];
        int[] ans = new int[N];

        for (Cow a: east) {
            for (Cow b: north) {
                if (stopped[a.i]||stopped[b.i] || a.coord[0]>b.coord[0] || a.coord[1]<b.coord[1]) continue;
                int ta = b.coord[0]-a.coord[0];
                int tb = a.coord[1]-b.coord[1];
                if (ta > tb) {
                    //cow a is stopped by cow b
                    stopped[a.i] = true;
                    ans[b.i] += (1+ans[a.i]);
                } else if (tb > ta) {
                    //cow b is stopped by cow a
                    stopped[b.i] = true;
                    ans[a.i] += (1+ans[b.i]);
                }
            }
        }

        for (int a: ans)
            pw.println(a);
        pw.close();
    }

    static class Cow implements Comparable<Cow> {
        int type; //0 is east, 1 is north
        int[] coord;
        int i;
        public Cow(int x, int y, int type, int i) {
            this.coord = new int[]{x,y};
            this.type=type;
            this.i=i;
        }

        public int compareTo(Cow other) {
            return coord[type]-other.coord[type];
        }

        public String toString() {
            return coord[0] + " " + coord[1];
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