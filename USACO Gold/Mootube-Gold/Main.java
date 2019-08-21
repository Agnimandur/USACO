/*
ID: shivara2
LANG: JAVA
TASK: mootube
*/

import java.util.*;
import java.io.*;

class Main {
  static int N; //num of nodes
  static int Q; //num of questions
  static Edge[] edges;
  static Query[] queries;

  public static void main(String[] args) throws IOException {
    //mootube.in
    BufferedReader br = new BufferedReader(new FileReader("mootube.in"));
    StringTokenizer st = new StringTokenizer(br.readLine());
    N = Integer.parseInt(st.nextToken());
    Q = Integer.parseInt(st.nextToken());
     
    edges = new Edge[N-1];
    for (int i = 0; i < N-1; i++) {
      st = new StringTokenizer(br.readLine());
      int p = Integer.parseInt(st.nextToken())-1;
      int q = Integer.parseInt(st.nextToken())-1;
      int r = Integer.parseInt(st.nextToken());
      edges[i] = new Edge(p,q,r);
    }
    Arrays.sort(edges);

    queries = new Query[Q];
    for (int i = 0; i < Q; i++) {
      st = new StringTokenizer(br.readLine());
      int k = Integer.parseInt(st.nextToken());
      int v = Integer.parseInt(st.nextToken())-1;
      queries[i] = new Query(k,v,i);
    }
    Arrays.sort(queries);

    //Disjoint Set Data Structure
    DisjointSets ds = new DisjointSets(N);
    //The answer to each question
    int[] answers = new int[Q];

		int idx = 0;
		for (Query query: queries) {
			while (idx < edges.length && edges[idx].relevance >= query.relThreshold) {
        Edge e = edges[idx];
				ds.connect(e.node1,e.node2);
				idx++;
      }
			answers[query.number] = ds.branchSize(query.video)-1;
		}

    //mootube.out
    PrintWriter pw = new PrintWriter(new BufferedWriter(new FileWriter("mootube.out")));
		for (int i = 0; i < Q; i++)
			pw.println(answers[i]);
		pw.close();
  }
}

class DisjointSets {
  public int N;
  public int[] parent;
  public int[] rank;

  public DisjointSets(int numNodes) {
    N = numNodes;
    parent = new int[N];
    rank = new int[N];
    for (int i = 0; i < N; i++) {
      parent[i] = i;
      rank[i] = 1;
    }
  }

  public int branchSize(int p) {
    return rank[root(p)];
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
  }
}

class Edge implements Comparable<Edge> {
  public int node1,node2,relevance;

  public Edge(int p, int q, int r) {
    node1 = p;
    node2 = q;
    relevance = r;
  }

  public int compareTo (Edge e) {
    return e.relevance - relevance;
  }

  public String toString() {
    return "[" + node1 + " " + node2 + " " + relevance + "]";
  }
}

class Query implements Comparable<Query> {
  public int relThreshold, video, number;

  public Query(int k, int v, int i) {
    relThreshold = k;
    video = v;
    number = i;
  }
  public int compareTo(Query q) {
    return q.relThreshold - relThreshold;
  }
}