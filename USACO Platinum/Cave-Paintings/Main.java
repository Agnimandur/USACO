import java.util.*;
import java.io.*;

class Main {
  public static final String TASKNAME = "cave";
  public static final long MOD = 1000000007L;

  public static void main(String[] args) throws IOException {
    InputStream is = new FileInputStream(TASKNAME+".in");
    PrintWriter pw = new PrintWriter(new BufferedWriter(new FileWriter(TASKNAME+".out")));
    FastScanner sc = new FastScanner(is);

    int N = sc.ni();
    int M = sc.ni();
    char[][] grid = new char[N][M];
    for (int i = 0; i < N; i++) {
      String s = sc.next();
      for (int j = 0; j < M; j++) {
        grid[i][j] = s.charAt(j);
      }
    }
    int K = 0; //number of components
    int[][] ranges = new int[N][2];
    int[][] nodes = new int[N][M];
    for (int i = 0; i < N; i++) {
      int F = -1;
      int L = -1;
      for (int j = 0; j < M; j++) {
        if (grid[i][j] == '#') {
          nodes[i][j] = -1;
        } else if (grid[i][j-1] == '#') {
          //new node
          if (F==-1) F = K;
          L = K;
          nodes[i][j] = K;
          K++;
        } else {
          nodes[i][j] = nodes[i][j-1];
        }
      }
      ranges[i] = new int[]{F,L};
    }

    DisjointSetUnion dsu = new DisjointSetUnion(K);
    long[] dp = new long[K];
    Arrays.fill(dp,1L);

    for (int i = N-2; i >= 1; i--) {
      if (ranges[i][0] == -1) continue;
      //join all the components of the current layer and the previous layer
      for (int j = 1; j < M-1; j++) {
        if (grid[i][j]=='.'&&grid[i+1][j]=='.'&&!(grid[i][j-1]=='.'&&grid[i+1][j-1]=='.')) {
          int r1 = dsu.root(nodes[i][j]);
          int r2 = dsu.root(nodes[i+1][j]);
          if (r1 != r2) {
            long v1 = dp[r1];
            long v2 = dp[r2];
            dsu.connect(r1,r2);
            dp[dsu.root(r1)] = (v1*v2)%MOD;
          }
        }
      }
      
      //add one to all the active sets (to represent making everything empty)
      HashSet<Integer> roots = new HashSet<Integer>();
      for (int j = ranges[i][0]; j <= ranges[i][1]; j++) {
        int r = dsu.root(j);
        if (!roots.contains(r)) {
          roots.add(r);
          dp[r] = (dp[r]+1)%MOD;
        }
      }
    }

    long ans = 1L;
    for (int i = 0; i < K; i++) {
      if (i==dsu.root(i))
        ans = (ans*dp[i])%MOD;
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
      int root = p;
      while (root != parent[root]) {
        root = parent[root];
      }
      while (root != parent[p]) {
        int temp = parent[p];
        parent[p] = root;
        p = temp;
      }
      return root;
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