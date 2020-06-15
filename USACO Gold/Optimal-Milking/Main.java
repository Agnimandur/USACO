import java.util.*;
import java.io.*;

class Main {
  public static final String TASKNAME = "optmilk";
  public static final long NINF = -10000000000007L;
  public static void main(String[] args) throws IOException {
    InputStream is = new FileInputStream(TASKNAME+".in");
    PrintWriter pw = new PrintWriter(new BufferedWriter(new FileWriter(TASKNAME+".out")));
    FastScanner sc = new FastScanner(is);

    int N = sc.ni();
    int D = sc.ni();

    long[] nums = new long[N];
    SegmentTree st = new SegmentTree(N);
    for (int i = 0; i < N; i++) {
      nums[i] = sc.nl();
      st.update(i,nums[i]);
    }

    long ans = 0;
    for (int i = 0; i < D; i++) {
      int ind = sc.ni()-1;
      long val = sc.nl();
      nums[ind] = val;
      st.update(ind,nums[ind]);
      long[] q = st.query(0,N-1);

      ans += max(q);
    }
    pw.println(ans);
    pw.close();
  }

  public static long max(long[] query) {
    long n = 0;
    for (long q: query)
      n = Math.max(n,q);
    return n;
  }

  //segment tree specialized for this divide and conquer DP.
  static class SegmentTree {
    public long[][] tree;
    public int N;

    //Zero initialization
    public SegmentTree(int n) {
      N = n;
      tree = new long[4*N+1][4]; //0=include neither endpoint, 1=include right endpoint, 2 = include left endpoint, 3 = include both endpoints.
    }

    public long[] query(int i, int j) {
      return query(0,0,N-1,i,j);
    }

    public void update(int arrIndex, long val) {
      update(0,0,N-1,arrIndex,val);
    }

    private long[] query(int treeIndex, int lo, int hi, int i, int j) {
      // query for arr[i..j]
      if (lo > j || hi < i)
        return new long[4];
      if (i <= lo && j >= hi)
        return tree[treeIndex];
      int mid = lo + (hi - lo) / 2;

      if (i > mid)
        return query(2 * treeIndex + 2, mid + 1, hi, i, j);
      else if (j <= mid)
        return query(2 * treeIndex + 1, lo, mid, i, j);
      
      long[] leftQuery = query(2 * treeIndex + 1, lo, mid, i, mid);
      long[] rightQuery = query(2 * treeIndex + 2, mid + 1, hi, mid + 1, j);

      // merge query results
      return merge(leftQuery, rightQuery);
    }

    private void update(int treeIndex, int lo, int hi, int arrIndex, long val) {
      if (lo == hi) {
        tree[treeIndex][0] = 0;
        tree[treeIndex][1] = 0;
        tree[treeIndex][2] = 0;
        tree[treeIndex][3] = val;
        return;
      }

      int mid = lo + (hi - lo) / 2;

      if (arrIndex > mid)
        update(2 * treeIndex + 2, mid + 1, hi, arrIndex, val);
      else if (arrIndex <= mid)
        update(2 * treeIndex + 1, lo, mid, arrIndex, val);

      // merge updates
      tree[treeIndex] = merge(tree[2 * treeIndex + 1], tree[2 * treeIndex + 2]);
    }

    //merge two subintervals together
    private long[] merge(long[] a, long[] b) {
      long[] ret = new long[4];

      ret[0] = Math.max(a[0]+Math.max(b[2],b[0]),Math.max(a[1],a[0])+b[0]);
      ret[1] = Math.max(b[1]+Math.max(a[0],a[1]),Math.max(b[3],b[1])+a[0]);
      ret[2] = Math.max(a[2]+Math.max(b[0],b[2]),Math.max(a[2],a[3])+b[0]);
      ret[3] = Math.max(a[2]+Math.max(b[1],b[3]),Math.max(a[2],a[3])+b[1]);
      return ret;
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