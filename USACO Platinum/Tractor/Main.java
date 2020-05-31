import java.util.*;
import java.io.*;

class Main {
  public static final String TASKNAME = "tractor";

  public static void main(String[] args) throws IOException {
    InputStream is = new FileInputStream(TASKNAME+".in");
    PrintWriter pw = new PrintWriter(new BufferedWriter(new FileWriter(TASKNAME+".out")));
    FastScanner sc = new FastScanner(is);

    int N = sc.ni();
    int[][] grid = new int[N][N];
    for (int i = 0; i < N; i++) {
      for (int j = 0; j < N; j++) {
        grid[i][j] = sc.ni();
      }
    }

    int[][] edges = new int[4*N*N][3]; //int node1, int node2, int weight
    for (int i = 0; i < 4*N*N; i++)
      edges[i][2] = 1000000007;
    int e = 0;
    int[][] dirs = {{-1,0},{1,0},{0,1},{0,-1}};
    for (int i = 0; i < N; i++) {
      for (int j = 0; j < N; j++) {
        for (int[] dir: dirs) {
          int newI = i+dir[0];
          int newJ = j+dir[1];
          if (newI>=0&&newI<N&&newJ>=0&&newJ<N) {
            edges[e][0] = i*N+j;
            edges[e][1] = newI*N+newJ;
            edges[e][2] = Math.abs(grid[i][j]-grid[newI][newJ]);
            e++;
          }
        }
      }
    }
    int half = (N*N+1)/2;
    Arrays.sort(edges, new Comparator<int[]>() {
      @Override
      public int compare(int[] arr1, int[] arr2) {
        return arr1[2]-arr2[2];
        //Ascending order.
      }
    });
    DisjointSetUnion dsu = new DisjointSetUnion(N*N);
    int ans = Integer.MAX_VALUE;
    for (int i = 0; i < e; i++) {
      dsu.connect(edges[i][0],edges[i][1]);
      int size = dsu.weight[dsu.root(edges[i][0])];
      if (size >= half) {
        ans = edges[i][2];
        break;
      }
    }

    pw.println(ans);
    pw.close();
  }

  static class DisjointSetUnion {
    public int[] parent;
    public int[] weight;
    public int count;

    public DisjointSetUnion(int nodes) {
      count = nodes;
      parent = new int[nodes];
      weight = new int[nodes];
      for (int i = 0; i < nodes; i++) {
        parent[i] = i;
        weight[i] = 1;
      }
    }
    //"find"
    public int root(int p) {
      while (p != parent[p]) {
        p = parent[p];
      }
      return p;
    }

    //"union"
    public void connect(int p, int q) {
      int rootP = root(p);
      int rootQ = root(q);
      if (rootP == rootQ) return;
      if (weight[rootP] < weight[rootQ]) {
        parent[rootP] = rootQ;
        weight[rootQ] += weight[rootP];
      } else {
        parent[rootQ] = rootP;
        weight[rootP] += weight[rootQ];
      }
      count--;
    }

    public boolean connected(int p, int q) {
      return root(p) == root(q);
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