import java.util.*;
import java.io.*;

class Main {
  public static void main(String[] args) throws IOException {
    InputStream is = new FileInputStream("mootube.in");
    PrintWriter pw = new PrintWriter(new BufferedWriter(new FileWriter("mootube.out")));
    FastScanner sc = new FastScanner(is);
    int N = sc.nextInt();
    int Q = sc.nextInt();
    int[][] tree = new int[N][3];
    for (int i = 0; i < N-1; i++) {
      tree[i][0] = sc.nextInt()-1; //video1
      tree[i][1] = sc.nextInt()-1; //video2
      tree[i][2] = sc.nextInt(); //relevance
    }
    tree[N-1] = new int[]{-1,-1,0};
    int[][] queries = new int[Q][3];
    for (int i = 0; i < Q; i++) {
      queries[i][0] = sc.nextInt(); //rel. threshold
      queries[i][1] = sc.nextInt()-1; //video
      queries[i][2] = i; //index (offline queries)
    }
    //Highest relevance connections in the tree come first
    Arrays.sort(tree, new Comparator<int[]>() {
      @Override
      public int compare(int[] arr1, int[] arr2) {
        return arr2[2]-arr1[2];
      }
    });

    //strictest relevance thresholds come first.
    Arrays.sort(queries, new Comparator<int[]>() {
      @Override
      public int compare(int[] arr1, int[] arr2) {
        return arr2[0]-arr1[0];
      }
    });

    DisjointSetUnion dsu = new DisjointSetUnion(N);
    int tIndex = 0;
    int[] queryAns = new int[Q];
    for (int q = 0; q < Q; q++) {
      int rel = queries[q][0];
      int vid = queries[q][1];
      while (tree[tIndex][2] >= rel) {
        if (! dsu.connected(tree[tIndex][0],tree[tIndex][1])) {
          dsu.connect(tree[tIndex][0],tree[tIndex][1]);
        }
        tIndex++;
      }

      //Answer to the query is how many videos are in the videos "disjoint set".
      int ans = dsu.weight[dsu.root(vid)]-1;
      queryAns[queries[q][2]] = ans;
    }

    for (int i = 0; i < Q; i++)
      pw.println(queryAns[i]);
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