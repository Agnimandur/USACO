import java.util.*;
import java.io.*;

public class notimetodry {
    static FastScanner sc;
    static PrintWriter pw;

    public static void main(String[] args) {
        sc = new FastScanner();
        pw = new PrintWriter(System.out);

        int N = sc.ni();
        int Q = sc.ni();
        SegmentTree st = new SegmentTree(N);
        int[] nums = new int[N];
        for (int i = 0; i < N; i++) {
            nums[i] = sc.ni()-1;
            st.update(i, nums[i]);
        }

        HashMap<Integer,Integer> last = new HashMap<>();
        PriorityQueue<int[]> pq = new PriorityQueue<int[]>(new Comparator<int[]>() {
            @Override
            public int compare(int[] a, int[] b) {
                if (a[1] != b[1])
                    return a[1]-b[1];
                else
                    return b[0]-a[0]; //process queries first
            }
        });

        BinaryIndexedTree bit = new BinaryIndexedTree(N);
        for (int i = 0; i < N; i++) {
            boolean alwaysHaveToPay = true;
            if (last.containsKey(nums[i])) {
                int p = last.get(nums[i]);
                if (st.query(p, i) == nums[i]) {
                    //the last index such that segment i is "free" (doesn't require its own stroke)
                    pq.add(new int[]{0,p,i}); //update
                    alwaysHaveToPay = false;
                }
            }
            if (alwaysHaveToPay) {
                bit.add(1, i);
            }
            last.put(nums[i],i);
        }

        //sort queries in increasing order of left endpoint
        for (int i = 0; i < Q; i++) {
            pq.add(new int[]{1,sc.ni()-1,sc.ni()-1,i});
        }
        
        int[] ans = new int[Q];
        while (!pq.isEmpty()) {
            int[] event = pq.poll();
            if (event[0]==0) {
                //update
                bit.add(1, event[2]);
            } else {
                ans[event[3]] = bit.sum(event[1], event[2]);
            }
        }

        for (int a: ans)
            pw.println(a);
        pw.close();
    }

    static class BinaryIndexedTree {
        public int[] arr;
      
        public BinaryIndexedTree(int N) {
            arr = new int[N+1];
        }
      
        public void add(int k, int i) {
            int node = i+1;
            while (node < arr.length) {
                arr[node] += k;
                node += node & (-node);
            }
        }

        public int sum(int s_i, int e_i) {
            return sum(e_i+1) - sum(s_i);
        }
      
        private int sum(int i) {
            int ans = 0;
            int node = i;
            while (node > 0) {
                ans += arr[node];
                node -= node & (-node);
            }
            return ans;
        }
    }

    //min seg tree
    static class SegmentTree {
        public int[] tree;
        public int N;
    
        public SegmentTree(int n) {
            N = n;
            tree = new int[2*N+1];
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
                return Integer.MAX_VALUE;
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

	//I memorized this reduced fileIO before the contest
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