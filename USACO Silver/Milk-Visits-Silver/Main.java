import java.util.*;
import java.io.*;

class Main {
  public static void main(String[] args) throws IOException {
    InputStream is = new FileInputStream("milkvisits.in");
    PrintWriter pw = new PrintWriter(new BufferedWriter(new FileWriter("milkvisits.out")));
    FastScanner sc = new FastScanner(is);

    int N = sc.ni();
    int Q = sc.ni();
    String s = sc.next();
    DisjointSetUnion holsteins = new DisjointSetUnion(N);
    DisjointSetUnion guernseys = new DisjointSetUnion(N);
    //Save in the DSU all connected groups of holsteins and guernseys, that don't include any cow of the other type within the "group".
    for (int i = 0; i < N-1; i++) {
      int n1 = sc.ni()-1;
      int n2 = sc.ni()-1;
      if (s.charAt(n1)==s.charAt(n2)) {
        if (s.charAt(n1)=='H') {
          holsteins.connect(n1,n2);
        } else {
          guernseys.connect(n1,n2);
        }
      }
    }
    StringBuilder ans = new StringBuilder();
    for (int q = 0; q < Q; q++) {
      int n1 = sc.ni()-1;
      int n2 = sc.ni()-1;
      char type = sc.next().charAt(0);
      if (n1==n2) {
        //Edge case. No travel occurs.
        if (type == s.charAt(n1)) {
          ans.append(1);
        } else {
          ans.append(0);
        }
      } else if (type == 'H') {
        if (guernseys.connected(n1,n2)) {
          //Only guernsey cows on route
          ans.append(0);
        } else {
          ans.append(1);
        }
      } else {
        if (holsteins.connected(n1,n2)) {
          //Only holstein cows on route
          ans.append(0);
        } else {
          ans.append(1);
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