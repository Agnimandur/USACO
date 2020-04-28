import java.util.*;
import java.io.*;

class Main {
  public static final String TASKNAME = "squares";

  public static void main(String[] args) throws IOException {
    InputStream is = new FileInputStream(TASKNAME+".in");
    PrintWriter pw = new PrintWriter(new BufferedWriter(new FileWriter(TASKNAME+".out")));
    FastScanner sc = new FastScanner(is);

    int N = sc.ni();
    int K = sc.ni();

    Center[] squares = new Center[N];
    int[][] Ymap = new int[N][2];
    for (int i = 0; i < N; i++) {
      int x = sc.ni();
      int y = sc.ni();
      squares[i] = new Center(x,y);
      Ymap[i][0] = y;
      Ymap[i][1] = i;
    }
    Arrays.sort(Ymap, new Comparator<int[]>() {
      @Override
      public int compare(int[] arr1, int[] arr2) {
        return arr1[0]-arr2[0];
        //Ascending order.
      }
    });
    for (int i = 0; i < N; i++) {
      squares[Ymap[i][1]].index = i;
    }
    int[][] ranges = new int[N][2];
    int L = 0;
    for (int i = 0; i < N; i++) {
      while (Ymap[L][0]+K<=Ymap[i][0])
        L++;
      ranges[i][0] = L;
    }
    int R = N-1;
    for (int i = N-1; i >= 0; i--) {
      while (Ymap[R][0]-K>=Ymap[i][0])
        R--;
      ranges[i][1] = R;
    }
    Arrays.sort(squares); //sort centers by x-coordinate, and then by y-coordinate

    SegmentTree st = new SegmentTree(N); //a segment tree to process the centers
    int[] arr = new int[N]; //value for each Y-coordinate.
    int intersections = 0;
    Center meet = new Center(0,0); //temp

    ArrayDeque<Center> alive = new ArrayDeque<Center>();
    for (int i = 0; i < N; i++) {
      Center c = squares[i];
      
      //remove all centers greater than or equal to K to the left of c.
      while (!alive.isEmpty() && alive.peekFirst().x+K <= c.x) {
        Center remove = alive.pollFirst();
        arr[remove.index] -= 1;
        st.update(0,0,N-1,remove.index,arr[remove.index]);
      }

      //query for any intersection with any square to the bottom left of the current square.
      L = ranges[c.index][0];
      R = ranges[c.index][1];
      int cnt = st.query(0,0,N-1,L,R);
      intersections += cnt;
      if (cnt > 0) {
        if (intersections > 1) {
          //more than 1 intersection
          pw.println(-1);
          pw.close();
          return;
        } else {
          meet = c;
        }
      }

      //add the current square
      alive.addLast(c);
      arr[c.index] += 1;
      st.update(0,0,N-1,c.index,arr[c.index]);
    }

    if (intersections == 0) {
      pw.println(0);
    } else {
      //there is exactly one intersection, and it is with the square meet.
      long ans = 0;
      for (int i = 0; i < N; i++) {
        Center c = squares[i];
        if (Math.abs(c.x-meet.x) < K && Math.abs(c.y-meet.y) < K) {
          //this is the intersection!!
          int xInt = K-Math.abs(c.x-meet.x);
          int yInt = K-Math.abs(c.y-meet.y);
          ans = (xInt+0L)*(yInt+0L);
          break;
        }
      }

      pw.println(ans);
    }
    pw.close();
  }

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
      return (a+b);
    }
  }

  static class Center implements Comparable<Center> {
    public int x;
    public int y;
    public int index;

    public Center(int xC, int yC) {
      x = xC;
      y = yC;
      index = -1;
    }

    public int compareTo(Center c) {
      if (this.x != c.x)
        return (this.x-c.x);
      else
        return (this.y-c.y);
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