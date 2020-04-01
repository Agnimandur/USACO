
import java.util.*;
import java.io.*;

class Main {
  public static void main(String[] args) throws IOException {
    InputStream is = new FileInputStream("cbs.in");
    PrintWriter pw = new PrintWriter(new BufferedWriter(new FileWriter("cbs.out")));
    FastScanner sc = new FastScanner(is);

    int K = sc.ni();
    int N = sc.ni();
    int[][] pref = new int[K][N+1];
    for (int i = 0; i < K; i++) {
      String s = sc.next();
      for (int j = 0; j < N; j++) {
        int c = 1;
        if (s.charAt(j)==')')
          c = -1;
        pref[i][j+1] = pref[i][j] + c;
      }
    }
    HashMap<ArrayList<Integer>,ArrayList<Integer>> hm = new HashMap<ArrayList<Integer>,ArrayList<Integer>>();
    for (int j = 0; j <= N; j++) {
      ArrayList<Integer> a = new ArrayList<Integer>();
      for (int i = 0; i < K; i++)
        a.add(pref[i][j]);
      if (hm.containsKey(a)) {
        hm.get(a).add(j);
      } else {
        ArrayList<Integer> n = new ArrayList<Integer>();
        n.add(j);
        hm.put(a,n);
      }
    }
    SegmentTree[] rmqs = new SegmentTree[K];
    for (int i = 0; i < K; i++) {
      rmqs[i] = new SegmentTree(pref[i]);
    }

    long ans = 0;

    for (ArrayList<Integer> key: hm.keySet()) {
      ArrayList<Integer> indices = hm.get(key);
      int i = 0;
      for (int j = 1; j < indices.size(); j++) {
        while (i < j) {
          boolean good = true;
          for (int k = 0; k < K; k++) {
            if (rmqs[k].query(0,0,N,indices.get(i),indices.get(j)) < pref[k][indices.get(j)]) {
              good = false;
              break;
            } 
          }
          if (good)break;
          i++;
        }
        ans += (j-i);
      }
    }

    pw.println(ans);
    pw.close();
  }

  //No lazy propagation. 0 indexed.
  static class SegmentTree {
    public int[] arr;
    public int[] tree;
    public int N;

    public SegmentTree(int[] a) {
      //0 indexed seg tree
      arr = a;
      N = a.length;
      tree = new int[4*N+1];
      buildSegTree(0,0,N-1);
    }

    public void buildSegTree(int treeIndex, int lo, int hi) {
      if (lo == hi) {
        tree[treeIndex] = arr[lo];
        return;
      }

      int mid = lo + (hi - lo) / 2;
      buildSegTree(2 * treeIndex + 1, lo, mid);
      buildSegTree(2 * treeIndex + 2, mid + 1, hi);
      tree[treeIndex] = merge(tree[2 * treeIndex + 1], tree[2 * treeIndex + 2]);
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

    public int merge(int a, int b) {
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