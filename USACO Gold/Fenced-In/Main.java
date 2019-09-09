import java.util.*;
import java.io.*;

class Main {
  static int N; //num of nodes
  static int A; //dimensions of the field
  static int B;
  static int n; //num of vertical fences
  static int m; //num of horizontal fences
  static ArrayList<Edge> edges;

  public static void main(String[] args) throws IOException {
    BufferedReader br = new BufferedReader(new FileReader("fencedin.in"));
    StringTokenizer st = new StringTokenizer(br.readLine());
    A = Integer.parseInt(st.nextToken());
    B = Integer.parseInt(st.nextToken());
    n = Integer.parseInt(st.nextToken());
    m = Integer.parseInt(st.nextToken());
    N = (n+1) * (m+1);
    edges = new ArrayList<Edge>();

    int[] v_fences = new int[n+2];
    v_fences[0] = 0;
    for (int i = 1; i <= n; i++) {
      int x = Integer.parseInt(br.readLine());
      v_fences[i] = x;
    }
    v_fences[n+1] = A;
    Arrays.sort(v_fences);

    int[] h_fences = new int[m+2];
    h_fences[0] = 0;
    for (int i = 1; i <= m; i++) {
      int x = Integer.parseInt(br.readLine());
      h_fences[i] = x;
    }
    h_fences[m+1] = B;
    Arrays.sort(h_fences);

    for (int row = 0; row < m; row++) {
      for (int section = 0; section <= n; section++) {
        int nodeAbove = (n+1)*row + section;
        int edgeLength = v_fences[section+1] - v_fences[section];
        Edge e = new Edge(nodeAbove,nodeAbove+(n+1),edgeLength);
        edges.add(e);
      }
    }
    for (int col = 0; col < n; col++) {
      for (int section = 0; section <= m; section++) {
        int nodeLeft = (n+1)*section + col;
        int edgeLength = h_fences[section+1] - h_fences[section];
        Edge e = new Edge(nodeLeft,nodeLeft + 1,edgeLength);
        edges.add(e);
      }
    }
    Collections.sort(edges);

    //Kruskal's Algorithm
    long fenceLength = kruskal();

    //fencedin.out
    PrintWriter pw = new PrintWriter(new BufferedWriter(new FileWriter("fencedin.out")));
    pw.println(fenceLength + "");
    pw.close();
  }

  public static long kruskal() {
    int index = 0;
    DisjointSets DS = new DisjointSets(N);
    long fenceLength = 0;
    
    for (Edge e: edges) {
      int p = e.node1;
      int q = e.node2;
      if (! DS.isConnected(p,q)) {
        DS.connect(p,q);
        fenceLength += e.weight;
      }
      if (DS.count == 1) {break;}
    }
    return fenceLength;
  }
}

class DisjointSets {
  public int N;
  public int[] parent;
  public int[] rank;
  public int count;

  public DisjointSets(int numNodes) {
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

class Edge implements Comparable<Edge> {
  public int node1;
  public int node2;
  public int weight;

  public Edge(int p, int q, int w) {
    node1 = p;
    node2 = q;
    weight = w;
  }

  public int compareTo (Edge e) {
    return weight - e.weight;
  }

  public String toString() {
    return "[" + node1 + " " + node2 + " " + weight + "]";
  }
}