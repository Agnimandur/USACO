import java.util.*;
import java.io.*;

class Main {
  public static void main(String[] args) throws IOException {
    InputStream is = new FileInputStream("closing.in");
    PrintWriter pw = new PrintWriter(new BufferedWriter(new FileWriter("closing.out")));
    FastScanner sc = new FastScanner(is);
    int N = sc.nextInt();
    int M = sc.nextInt();
    ArrayList<Integer>[] graph = new ArrayList[N];
    for (int i = 0; i < N; i++)
      graph[i] = new ArrayList<Integer>();
    for (int i = 0; i < M; i++) {
      int n1 = sc.nextInt()-1;
      int n2 = sc.nextInt()-1;
      graph[n1].add(n2);
      graph[n2].add(n1);
    }

    int[] closing = new int[N];
    for (int i = 0; i < N; i++)
      closing[i] = sc.nextInt()-1;

    //Simulate the process of closing the farm in reverse order.
    DisjointSetUnion dsu = new DisjointSetUnion(N);
    HashSet<Integer> open = new HashSet<Integer>();
    String[] ans = new String[N];
    for (int i = N-1; i >= 0; i--) {
      open.add(closing[i]);
      for (int child: graph[closing[i]]) {
        if (open.contains(child) && !dsu.connected(child,closing[i])) {
          dsu.connect(child,closing[i]);
        }
      }

      if (dsu.count == i+1) {
        ans[i] = "YES";
      } else {
        ans[i] = "NO";
      }
    }
    for (int i = 0; i < N; i++)
      pw.println(ans[i]);
    pw.close();
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