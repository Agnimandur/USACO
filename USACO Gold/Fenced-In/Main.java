import java.util.*;
import java.io.*;

class Main {
  public static void main(String[] args) throws IOException {
    InputStream is = new FileInputStream("fencedin.in");
    PrintWriter pw = new PrintWriter(new BufferedWriter(new FileWriter("fencedin.out")));
    FastScanner sc = new FastScanner(is);
    int A = sc.nextInt();
    int B = sc.nextInt();
    int n = sc.nextInt();
    int m = sc.nextInt();
    int[] vertical = new int[n+2];
    for (int i = 1; i <= n; i++)
      vertical[i] = sc.nextInt();
    vertical[n+1] = A;
    int[] horizontal = new int[m+2];
    for (int i = 1; i <= m; i++)
      horizontal[i] = sc.nextInt();
    horizontal[m+1] = B;
    Arrays.sort(vertical);
    Arrays.sort(horizontal);

    ArrayList<Edge> fences = new ArrayList<Edge>((2*m*n)+(m+n));

    //Get all the fences
    for (int i = 0; i <= n; i++) {
      for (int j = 0; j < m; j++) {
        fences.add(new Edge(i, j, i, j+1, vertical[i+1]-vertical[i]));
      }
    }

    for (int j = 0; j <= m; j++) {
      for (int i = 0; i < n; i++) {
        fences.add(new Edge(i, j, i+1, j, horizontal[j+1]-horizontal[j]));
      }
    }
    Collections.sort(fences);

    //System.out.println(Arrays.deepToString(fences));
    DisjointSetUnion dsu = new DisjointSetUnion((n+1)*(m+1));
    long ans = 0;
    for (Edge e: fences) {
      int p = e.r1 + e.c1 * (n+1);
      int q = e.r2 + e.c2 * (n+1);
      if (! dsu.connected(p,q)) {
        ans += e.weight;
        dsu.connect(p,q);
        if (dsu.count == 1)
          break;
      }
    }

    pw.println(ans);
    pw.close();
  }

  static class Edge implements Comparable<Edge> {
    public int r1, c1, r2, c2;
    public int weight;
    public Edge(int r1, int c1, int r2, int c2, int w) {
      this.r1 = r1;
      this.c1 = c1;
      this.r2 = r2;
      this.c2 = c2;
      this.weight = w;
    }
    public int compareTo(Edge other) {
      return weight - other.weight;
    }
  }


  static class DisjointSetUnion {
    private int[] parent;
    private int[] weight;
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
    private int root(int p) {
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
  
    public int nextInt() { 
      return Integer.parseInt(next()); 
    } 
  
    public long nextLong() { 
      return Long.parseLong(next()); 
    } 
  
    public double nextDouble() { 
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