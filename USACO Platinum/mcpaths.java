import java.io.*;
import java.util.*;

public class mcpaths {
    static FastScanner sc;
    static PrintWriter pw;

    static ArrayList<Quadratic> quads;
    public static void main(String[] args) {
        sc = new FastScanner();
        pw = new PrintWriter(System.out);

        int N = sc.ni();
        int M = sc.ni();
        long[] C = sc.longArray(M);

        int Q = sc.ni();
        //process the queries by column
        int[][] queries = new int[Q][3];
        for (int q = 0; q < Q; q++) {
            queries[q] = new int[]{sc.ni(),sc.ni(),q};
        }
        Arrays.sort(queries, new Comparator<int[]>() {
            @Override
            public int compare(int[] a, int[] b) {
              return a[1]-b[1];
            }
        });
        long[] ans = new long[Q];
        int ind = 0;

        //store a stack of the quadratic functions.
        quads = new ArrayList<Quadratic>();
        //base column of the dp
        quads.add(new Quadratic(1, C[0],-C[0]));
        while (ind < Q && queries[ind][1]==1) {
            ans[queries[ind][2]] = quads.get(0).eval(1,queries[ind][0]);
            ind++;
        }

        for (int col = 2; col <= M; col++) {
            long slope = C[col-1];
            //CHT style trick
            while (quads.size() > 1) {
                Quadratic q1 = quads.get(quads.size()-2);
                Quadratic q2 = quads.get(quads.size()-1);
                //q2.better(q1) is the column where q2 first becomes cheaper than q1.
                //q2.first(col,slope) is the column where the potential line becomes cheaper than q2.
                if (q2.better(q1) > q2.firstC(col, slope)) {
                    quads.remove(quads.size()-1);
                } else {
                    break;
                }
            }
            Quadratic top = quads.get(quads.size()-1);
            long fc = top.firstC(col, slope);
            long val = top.eval(col,fc);
            //we have to manually calculate the quadratics y-intercept.
            quads.add(new Quadratic(col,slope,val-slope*fc));

            while (ind < Q && queries[ind][1]==col) {
                ans[queries[ind][2]] = solve(col,queries[ind][0]);
                ind++;
            }
        }

        for (int q = 0; q < Q; q++)
            pw.println(ans[q]);
        pw.close();
    }

    public static long solve(long curC, long x) {
        //binary search to find which quadratic covers "x".
        int lo = 0;
        int hi = quads.size()-1;
        while (lo < hi) {
            int m = (lo+hi)/2;
            long better = quads.get(m+1).better(quads.get(m));
            if (x >= better)
                lo = m+1;
            else
                hi = m;
        }
        Quadratic quad = quads.get(lo);
        return quad.eval(curC, x);
    }

    static class Quadratic {
        long a;
        long b;
        long c;

        public Quadratic(long a, long b, long c) {
            this.a=a;
            this.b=b;
            this.c=c;
        }

        public long eval(long curC, long x) {
            return (curC-a)*x*x+b*x+c;
        }

        public long firstC(long curC, long slope) {
            //first c when the difference between quad(c) and quad(c+1) is >= slope.
            long num = slope-b-(curC-a);
            long den = 2*(curC-a);
            return Math.max((num+den-1)/den,1L);
        }

        //find at which column does the current quadratic become "better" than q.
        //this.a > q.a
        public long better(Quadratic q) {
            long A = a-q.a;
            long B = q.b-b;
            long C = q.c-c;
            //Find rightmost X such that AX^2+BX+C >= 0.
            long X = (long)Math.ceil((Math.sqrt(B*B-4*A*C)-B)/(2*A));
            return X;
        }

        public String toString() {
            return (a + " " + b + " " + c);
        }
    }

    //before the contest I memorized the constructor, next(), and nextLine()!
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