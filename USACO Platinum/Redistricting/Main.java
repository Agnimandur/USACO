import java.util.*;
import java.io.*;

class Main {
  public static final String TASKNAME = "redistricting";
  public static int INF = 1000000;

  public static void main(String[] args) throws IOException {
    InputStream is = new FileInputStream(TASKNAME+".in");
    PrintWriter pw = new PrintWriter(new BufferedWriter(new FileWriter(TASKNAME+".out")));
    FastScanner sc = new FastScanner(is);

    int N = sc.ni();
    int K = sc.ni();
    String s = sc.next();
    int[] val = new int[N+1]; //pref of cow advantage
    for (int i = 1; i <= N; i++) {
      if (s.charAt(i-1)=='G')
        val[i] = val[i-1]+1;
      else
        val[i] = val[i-1]-1;
    }

    int[] dp = new int[N+1];
    Arrays.fill(dp,INF);
    dp[0] = 0;
    SegmentTree dpST = new SegmentTree(N+1);

    int M = 2*N+1;
    MonoQueue[] mins = new MonoQueue[M]; //a min-queue for each value of val.
    SegmentTree st = new SegmentTree(M); //st for minimum DP value with a given val.
    for (int i = 0; i < M; i++)
      st.update(i,INF);

    //base case
    mins[N] = new MonoQueue();
    mins[N].add(0);
    st.update(N,0);

    for (int i = 1; i <= N; i++) {
      //case 1: create a new guernsey controlled or tied district (min(dp[i-K:i-1]+1))
      int case1 = dpST.query(Math.max(0,i-K),i-1)+1;

      //case 2: create a new holstein controlled district (using the val helper array to determine the valid districts)
      int v = N+val[i];
      int case2 = st.query(v+1,M-1);

      int ans = Math.min(case1,case2);
      dp[i] = ans;
      dpST.update(i,dp[i]);

      //update the data structures
      if (mins[v]==null) mins[v] = new MonoQueue();
      mins[v].add(ans);
      st.update(v,mins[v].query());
      if (i >= K) {
        int p = val[i-K]+N;
        mins[p].remove();
        st.update(p,mins[p].query());
      }
    }

    pw.println(dp[N]);
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
      NONE = INF;
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

  //As a queue, but it can query the minimum/maximum value in the queue in O(1) time. 
  //full name of the data structure is "monotonic queue"
  static class MonoQueue {
    private ArrayDeque<Integer> s1Num;
    private ArrayDeque<Integer> s1M;
    private ArrayDeque<Integer> s2Num;
    private ArrayDeque<Integer> s2M;
    private int size;
    public int NONE;
    
    public MonoQueue() {
      s1Num = new ArrayDeque<Integer>();
      s1M = new ArrayDeque<Integer>();
      s2Num = new ArrayDeque<Integer>();
      s2M = new ArrayDeque<Integer>();
      size = 0;
      NONE = INF; //set this value based on the problem
    }
    
    //Get the min or the max in the monoqueue
    public int query() {
      if (size==0)
        return NONE;
      int m;
      if (s1Num.isEmpty()) {
        m = s2M.peek();
      } else if (s2Num.isEmpty()) {
        m = s1M.peek();
      } else {
        m = merge(s1M.peek(),s2M.peek()); 
      }
      return m;
    }
    
    //Add a number to the back of the queue
    public void add(int n) {
      int m = s1Num.isEmpty() ? n : merge(n,s1M.peek());
      s1Num.push(n);
      s1M.push(m);
      size++;
    }
    
    //Remove the number at the front of the queue
    public void remove() {
      if (s2Num.isEmpty()) {
        while (!s1Num.isEmpty()) {
          int n = s1Num.pop();
          s1M.pop();
          int m = s2Num.isEmpty() ? n : merge(n, s2M.peek());
          s2Num.push(n);
          s2M.push(m);
        }
      }
      s2Num.pop();
      s2M.pop();
      size--;
    }

    public int size() {
      return size;
    }

    //set this to be either min or max if you want a MinQueue or a MaxQueue
    private int merge(int a, int b) {
      return Math.min(a,b);
    }
  }

  static class FastScanner {
    public BufferedReader br; 
    public StringTokenizer st; 
  
    public FastScanner(InputStream is) throws IOException { 
      br = new BufferedReader(new InputStreamReader(is),32768);
      st = null;
    }
  
    public String next() { 
      while (st == null || !st.hasMoreTokens()) { 
        try { 
          st = new StringTokenizer(br.readLine()); 
        } 
        catch (IOException  e) { 
          throw new RuntimeException(e);
        }
      } 
      return st.nextToken(); 
    } 
  
    public int ni() { 
      return Integer.parseInt(next()); 
    } 
  
    public long nl() { 
      return Long.parseLong(next()); 
    } 
  
    public double nd() { 
      return Double.parseDouble(next()); 
    } 
  
    public String nextLine() { 
      String str = ""; 
      try { 
        str = br.readLine(); 
      } catch (IOException e) { 
        throw new RuntimeException(e);
      } 
      return str; 
    }
  }
}