/*
ID: shivara2
LANG: JAVA
TASK: moocast
*/

import java.util.*;
import java.io.*;
import java.awt.Point;

class Main {
  static int N;
  static Point[] cows;
  static Edge[] edges;

  public static void main(String[] args) throws IOException {
    //moocast.in
    BufferedReader br = new BufferedReader(new FileReader("moocast.in"));
    N = Integer.parseInt(br.readLine());
    int E = N * (N-1) / 2;
    edges = new Edge[E];
    cows = new Point[N];
    for (int i = 0; i < N; i++) {
      StringTokenizer st = new StringTokenizer(br.readLine());
      int x = Integer.parseInt(st.nextToken());
      int y = Integer.parseInt(st.nextToken());
      cows[i] = new Point(x,y);
    }
    br.close();

    int ind = 0;
    for (int i = 0; i < N-1; i++) {
      for (int j = i+1; j < N; j++) {
        Edge e = new Edge(i,j,(int)(Math.pow((cows[j].y-cows[i].y),2) + Math.pow((cows[j].x-cows[i].x),2)));
        edges[ind] = e;
        ind++;
      }
    }

    Arrays.sort(edges);
    //Kruskal's Algorithm
    int longestEdge = kruskal();

    //moocast.out
    PrintWriter pw = new PrintWriter(new BufferedWriter(new FileWriter("moocast.out")));
    pw.println(longestEdge + "");
    pw.close();
  }

  public static int kruskal() {
    int index = 0;
    int longestEdge = 0;
    DisjointSets DS = new DisjointSets(N);
    while (DS.count > 1) {
      int p = edges[index].node1;
      int q = edges[index].node2;
      if (! DS.isConnected(p,q)) {
        DS.connect(p,q);
        if (edges[index].weight > longestEdge)
          longestEdge = edges[index].weight;
      }
      index++;
    }
    return longestEdge;
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
  public int weight; // squared distance between two points

  public Edge(int p, int q, int w) {
    node1 = p;
    node2 = q;
    weight = w;
  }

  public int compareTo (Edge e) {
    return weight - e.weight;
  }

  public String toString() {
    return "[" + node1 + "," + node2 + ",weight:" + weight + "]";
  }
}