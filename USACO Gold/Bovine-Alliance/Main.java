import java.util.*;
import java.io.*;

class Main {
  public static void main(String[] args) throws IOException {
    InputStream is = new FileInputStream("alliance.in");
    PrintWriter pw = new PrintWriter(new BufferedWriter(new FileWriter("alliance.out")));
    FastScanner sc = new FastScanner(is);

    int N = sc.ni();
    int M = sc.ni();
    int[][] edges = new int[M][2];
    DisjointSetUnion dsu = new DisjointSetUnion(N);
    for (int i = 0; i < M; i++) {
      int n1 = sc.ni()-1;
      int n2 = sc.ni()-1;
      edges[i][0] = n1;
      edges[i][1] = n2;
      dsu.connect(n1,n2);
    }
    int[] cnt = new int[N];
    for (int i = 0; i < M; i++) {
      cnt[dsu.root(edges[i][0])] += 1;
    }

    long MOD = 1000000007L;
    long ans = 1L;
    for (int i = 0; i < N; i++) {
      if (cnt[i] > 0) {
        //Component
        int n = dsu.weight[i];
        int e = cnt[i];
        if (e > n) {
          ans = 0;
          break;
        } else if (e == n) {
          ans *= 2;
          ans %= MOD;
        } else {
          ans *= n;
          ans %= MOD;
        }
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