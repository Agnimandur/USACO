import java.util.*;
import java.io.*;

class Main {
  public static final String TASKNAME = "fairphoto";

  public static void main(String[] args) throws IOException {
    InputStream is = new FileInputStream(TASKNAME+".in");
    PrintWriter pw = new PrintWriter(new BufferedWriter(new FileWriter(TASKNAME+".out")));
    FastScanner sc = new FastScanner(is);

    int N = sc.ni();
    int[][] nums = new int[N][3]; //pos,type,prefix
    for (int i = 0; i < N; i++) {
      nums[i][0] = sc.ni();
      char c = sc.next().charAt(0);
      if (c=='W')
        nums[i][1] = 1;
      else
        nums[i][1] = -1;
    }
    Arrays.sort(nums, new Comparator<int[]>() {
      @Override
      public int compare(int[] arr1, int[] arr2) {
        return arr1[0]-arr2[0];
        //Ascending order.
      }
    });
    int prefix = 0;
    for (int i = 0; i < N; i++) {
      prefix += nums[i][1];
      nums[i][2] = prefix;
    }
    Arrays.sort(nums, new Comparator<int[]>() {
      @Override
      public int compare(int[] arr1, int[] arr2) {
        if (arr1[2] != arr2[2])
          return arr1[2]-arr2[2];
        else
          return arr1[0]-arr2[0];
      }
    });
    HashMap<Integer,Integer> indexMap = new HashMap<Integer,Integer>();
    int F = Integer.MAX_VALUE;
    int L = Integer.MIN_VALUE;
    for (int i = 0; i < N; i++) {
      if (!indexMap.containsKey(nums[i][2])) {
        indexMap.put(nums[i][2],i);
        F = Math.min(F,nums[i][2]);
        L = Math.max(L,nums[i][2]);
      }
    }
    System.out.println(indexMap);

    SegmentTree evens = new SegmentTree(N);
    SegmentTree odds = new SegmentTree(N);
    for (int i = 0; i < N; i++) {
      if (nums[i][2] % 2 == 0) {
        evens.update(0,0,N-1,i,nums[i][0]);
      } else {
        odds.update(0,0,N-1,i,nums[i][0]);
      }
    }
    int ans = 0;
    for (int i = 0; i < N; i++) {
      int prev = nums[i][2]-nums[i][1];
      if (prev > L) continue;
      int index = 0;
      if (prev >= F) index = indexMap.get(prev);
      int max = 0;
      if (prev % 2 == 0) {
        max = evens.query(0,0,N-1,index,N-1);
      } else {
        max = odds.query(0,0,N-1,index,N-1);
      }
      ans = Math.max(ans,max-nums[i][0]);
    }

    pw.println(ans);
    pw.close();
  }

  //No lazy propagation. 0 indexed. Very fast.
  static class SegmentTree {
    public int[] tree;
    public int N;

    //Zero initialization
    public SegmentTree(int n) {
      N = n;
      tree = new int[4*N+1];
    }

    public int query(int treeIndex, int lo, int hi, int i, int j) {
      // query for arr[i..j]
      if (lo > j || hi < i)
        return 0;
      if (i <= lo && j >= hi)
        return tree[treeIndex];
      int mid = lo + (hi - lo) / 2;

      if (i > mid)
        return query(2 * treeIndex + 2, mid + 1, hi, i, j);
      else if (j <= mid)
        return query(2 * treeIndex + 1, lo, mid, i, j);
      
      int leftQuery = query(2 * treeIndex + 1, lo, mid, i, mid);
      int rightQuery = query(2 * treeIndex + 2, mid + 1, hi, mid + 1, j);

      // merge query results
      return merge(leftQuery, rightQuery);
    }

    public void update(int treeIndex, int lo, int hi, int arrIndex, int val) {
      if (lo == hi) {
        tree[treeIndex] = val;
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

    public int merge(int a, int b) {
      return Math.max(a,b);
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