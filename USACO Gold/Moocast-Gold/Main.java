import java.util.*;
import java.math.*;
import java.io.*;

class Main {
  static int N;
  static int[][] cows;
  static int[][] edges;
  public static void main(String[] args) throws IOException {
    InputStream is = new FileInputStream("moocast.in");
    PrintWriter pw = new PrintWriter(new BufferedWriter(new FileWriter("moocast.out")));
    FastScanner sc = new FastScanner(is);

    N = sc.nextInt();
    cows = new int[N][2];
    for (int i = 0; i < N; i++)
      cows[i] = new int[]{sc.nextInt(),sc.nextInt()};
    
    edges = new int[N*(N-1)/2][3]; //{node1,node2,weight}
    int index = 0;
    for (int i = 0; i < N-1; i++) {
      for (int j = i+1; j < N; j++) {
        edges[index][0] = i;
        edges[index][1] = j;
        edges[index][2] = dist(i,j);
        index++;
      }
    }
    Arrays.sort(edges, new Comparator<int[]>() {
      @Override
      public int compare(int[] arr1, int[] arr2) {
        return arr1[2]-arr2[2];
        //Ascending order.
      }
    });

    DisjointSetUnion dsu = new DisjointSetUnion(N);
    index = 0;
    while (dsu.count > 1) {
      int p = edges[index][0];
      int q = edges[index][1];
      if (! dsu.isConnected(p,q)) {
        dsu.connect(p,q);
      }
      index++;
    }

    pw.println(edges[index-1][2]);
    pw.close();
  }
  public static int dist(int i, int j) {
    return (cows[i][0]-cows[j][0])*(cows[i][0]-cows[j][0])+(cows[i][1]-cows[j][1])*(cows[i][1]-cows[j][1]);
  }

  static class DisjointSetUnion {
    public int N;
    public int[] parent;
    public int[] rank;
    public int count;

    public DisjointSetUnion(int numNodes) {
      N = numNodes;
      parent = new int[N];
      rank = new int[N];
      for (int i = 0; i < N; i++) {
        parent[i] = i;
        rank[i] = 1;
      }
      count = numNodes;
    }

    public boolean isConnected(int p, int q) {
      return root(p) == root(q);
    }

    public int root(int p) {
      while (p != parent[p])
        p = parent[p];
      return p;
    }

    //only connect p and q if they are disjointed.
    public void connect(int p, int q) {
      int rootP = root(p);
      int rootQ = root(q);
      if (rank[rootP] >= rank[rootQ]) {
        parent[rootQ] = rootP;
        rank[rootP] += rank[rootQ];
      } else if (rank[rootQ] > rank[rootP]) {
        parent[rootP] = rootQ;
        rank[rootQ] += rank[rootP];
      }
      count--;
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