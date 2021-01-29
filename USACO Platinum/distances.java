import java.io.*;
import java.util.*;

public class distances {
    static final long MOD = 1000000007L;
    static final int INF = 10000000;
    static FastScanner sc;
    static PrintWriter pw;

    static int K;
    static int[][] nums;

    public static void main(String[] args) {
        sc = new FastScanner();
        pw = new PrintWriter(System.out);

        K = sc.ni();
        nums = new int[K][];
        for (int i = 0; i < K; i++) {
            int N = sc.ni();
            int M = sc.ni();
            //for each node in each separate graph, find the shortest path of even and odd length.
            nums[i] = calc(N,M);
        }
        //strategic counting
        long ans = (solve(0)+solve(1))%MOD;
        pw.println(ans);
        pw.close();
    }

    public static long solve(int parity) {
        //range product queries of main and overcount.
        SegmentTree prod = new SegmentTree(K);
        SegmentTree over = new SegmentTree(K);
        //process each node in sorted order by distance.
        PriorityQueue<int[]> pq = new PriorityQueue<int[]>(new Comparator<int[]>() {
            @Override
            public int compare(int[] a, int[] b) {
                return a[0]-b[0];
            }
        });
        for (int i = 0; i < K; i++) {
            int N = nums[i].length/2;
            for (int j = 0; j < N; j++) {
                if (parity==0)
                    pq.add(new int[]{nums[i][j],nums[i][j+N],i});
                else
                    pq.add(new int[]{nums[i][j+N],nums[i][j],i});
            }
        }

        long ans = 0L;
        while (!pq.isEmpty()) {
            int[] v = pq.poll(); //assume this is the "restricting" node.
            if (v[0]==INF) break;

            if (v.length == 3) {
                long sum;
                if (v[1] < v[0])
                    sum = (prod.query(v[2])-over.query(v[2])+MOD)%MOD;
                else
                    sum = prod.query(v[2]);
                ans = (ans+sum*v[0])%MOD;
                prod.add(v[2],1L);
                pq.add(new int[]{v[1],v[2]});
            } else {
                over.add(v[1],1L);
            }
        }
        return ans;
    }

    static class SegmentTree {
        public long[] tree;
        public long[] arr;
        public long NONE;
        public int N;
      
        //Zero initialization
        public SegmentTree(int n) {
            N = n;
            tree = new long[2*N+1];
            arr = new long[N];
            NONE = 1L; //multiplicative identity
        }
      
        public long merge(long a, long b) {
            return (a*b)%MOD;
        }
      
        public long query(int exclude) {
            return (query(0,0,N-1,0,exclude-1)*query(0,0,N-1,exclude+1,N-1))%MOD;
        }
      
        public void add(int i, long val) {
            arr[i] += val;
            update(0,0,N-1,i,arr[i]);
        }
      
        private long query(int t, int lo, int hi, int i, int j) {
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
      
        private void update(int t, int lo, int hi, int i, long val) {
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

        public String toString() {
            return Arrays.toString(arr);
        }
    }

    public static int[] calc(int N, int M) {
        //state BFS (0..N-1 is even state, N,.2N-1 is odd state)
        ArrayList<Integer>[] graph = new ArrayList[N];
        for (int i = 0; i < N; i++) graph[i] = new ArrayList<Integer>();
        for (int i = 0; i < M; i++) {
            int u = sc.ni()-1;
            int v = sc.ni()-1;
            graph[u].add(v);
            graph[v].add(u);
        }

        int[] ans = new int[2*N];
        Arrays.fill(ans,INF);
        ans[0] = 0;
        ArrayDeque<Integer> bfs = new ArrayDeque<Integer>();
        bfs.add(0);
        while (!bfs.isEmpty()) {
            int u = bfs.pollFirst();
            for (int v: graph[u%N]) {
                int target = (u < N ? v+N : v);
                if (ans[target]==INF) {
                    ans[target] = ans[u]+1;
                    bfs.add(target);
                }
            }
        }
        return ans;
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