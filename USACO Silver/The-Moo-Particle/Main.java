import java.util.*;
import java.io.*;

class Main {
  public static void main(String[] args) throws IOException {
    InputStream is = new FileInputStream("moop.in");
    PrintWriter pw = new PrintWriter(new BufferedWriter(new FileWriter("moop.out")));
    FastScanner sc = new FastScanner(is);

    int N = sc.ni();
    int[][] points = new int[N][2];
    int[] Ys = new int[N];
    for (int i = 0; i < N; i++) {
      points[i][0] = sc.ni();
      points[i][1] = sc.ni();
      Ys[i] = points[i][1];
    }

    Arrays.sort(points, new Comparator<int[]>() {
      @Override
      public int compare(int[] arr1, int[] arr2) {
        if (arr1[0] != arr2[0]) {
          return (arr1[0]-arr2[0]);
        } else {
          return (arr1[1]-arr2[1]);
        }
      }
    });
    Arrays.sort(Ys);

    HashMap<Integer,Integer> Ymap = new HashMap<Integer,Integer>();
    int compress = 0;
    for (int i = 0; i < N; i++) {
      if (i==0||Ys[i]>Ys[i-1]) {
        Ymap.put(Ys[i],compress);
        compress++;
      }
    }

    int M = Ymap.size();
    int K = 0;
    boolean[] isolated = new boolean[N]; //isolated points
    SegmentTree st = new SegmentTree(M);
    for (int i = 0; i < N; i++) {
      int index = Ymap.get(points[i][1]);
      long interact = st.query(0,0,M-1,0,index);
      if (interact == 0L) {
        isolated[i] = true;
        K++;
      }
      st.update(0,0,M-1,index,1L);
    }

    int index = 0;
    int[][] type3 = new int[K][3];
    for (int i = 0; i < N; i++) {
      if (isolated[i]) {
        type3[index] = new int[]{points[i][0],points[i][1],i};
        index++;
      }
    }

    Arrays.sort(type3, new Comparator<int[]>() {
      @Override
      public int compare(int[] arr1, int[] arr2) {
        if (arr1[0] != arr2[0]) {
          return (arr1[0]-arr2[0]);
        } else {
          return (arr1[1]-arr2[1]);
        }
      }
    });

    int[] maxY = new int[N+1];
    maxY[N] = Integer.MIN_VALUE;
    for (int i = N-1; i >= 0; i--) {
      maxY[i] = Math.max(points[i][1],maxY[i+1]);
    }

    int ans = 1;
    for (int i = 1; i < K; i++) {
      if (maxY[type3[i][2]] < type3[i-1][1]) {
        ans++;
      }
    }

    pw.println(ans);
    pw.close();
  }

  static class SegmentTree {
    public long[] arr;
    public long[] tree;
    public int N;

    //Zero initialization
    public SegmentTree(int n) {
      N = n;
      arr = new long[N];
      tree = new long[4*N+1];
    }

    public long query(int treeIndex, int lo, int hi, int i, int j) {
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
      
      long leftQuery = query(2 * treeIndex + 1, lo, mid, i, mid);
      long rightQuery = query(2 * treeIndex + 2, mid + 1, hi, mid + 1, j);

      // merge query results
      return merge(leftQuery, rightQuery);
    }

    public void update(int treeIndex, int lo, int hi, int arrIndex, long val) {
      if (lo == hi) {
        tree[treeIndex] = val;
        arr[arrIndex] = val;
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

    public long merge(long a, long b) {
      return (a+b);
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