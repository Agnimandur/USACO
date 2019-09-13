import java.util.*;
import java.math.*;
import java.io.*;

class Main {
  public static void main(String[] args) throws IOException {
    FastScanner sc = new FastScanner("dining.in");
    PrintWriter pw = new PrintWriter(new BufferedWriter(new FileWriter("dining.out")));
    int N = sc.nextInt();
    int M = sc.nextInt();
    int K = sc.nextInt();
    ArrayList<Edge>[] graph = new ArrayList[N];
    for (int i = 0; i < N; i++) {
      graph[i] = new ArrayList<Edge>();
    }
    for (int i = 0; i < M; i++) {
      int p = sc.nextInt()-1;
      int q = sc.nextInt()-1;
      int w = sc.nextInt();
      Edge e1 = new Edge(p,q,w);
      Edge e2 = new Edge(q,p,w);
      graph[p].add(e1);
      graph[q].add(e2);
    }
    int[][] haybales = new int[K][2];
    for (int i = 0; i < K; i++) {
      haybales[i][0] = sc.nextInt()-1;
      haybales[i][1] = sc.nextInt();
    }
    int[] optimal = dijkstra(graph);
    //Case 1: If the yummyness of any haybale is >= twice the distance needed to reach the haybale, the optimal strategy for every cow is to go to the barn, then go to the haybale, then go back to the barn.
    for (int i = 0; i < K; i++) {
      if (haybales[i][1] >= 2*optimal[haybales[i][0]]) {
        for (int j = 0; j < N-1; j++) {
          pw.println(1);
        }
        pw.close();
        return;
      }
    }

    //Case 2: It is never optimal for any cow to ever visit the barn on its way to a haybale.
    //Run Dijkstra again on a modified graph
    //Remove all the edges from the barn to its adjacent vertexes. Add a directed edge from the barn to each haybale of weight (optimal[haybale]-yumminess).
    //This forces the path from the barn to each cow to include a haybale.
    ArrayList<Edge>[] modifiedGraph = new ArrayList[N];
    for (int i = 0; i < N; i++)
      modifiedGraph[i] = new ArrayList<Edge>();
    for (int i = 0; i < N-1; i++) {
      for (Edge e: graph[i]) {
        if (e.n2 != N-1) {
          modifiedGraph[i].add(e);
        }
      }
    }
    for (int i = 0; i < K; i++) {
      Edge e = new Edge(N-1,haybales[i][0],optimal[haybales[i][0]]-haybales[i][1]);
      modifiedGraph[N-1].add(e);
    }
    int[] haybaleVisit = dijkstra(modifiedGraph);
    for (int i = 0; i < N-1; i++) {
      if (optimal[i] < haybaleVisit[i]) {
        pw.println(0);
      } else {
        pw.println(1);
      }
    }
    pw.close();
  }

  public static int[] dijkstra(ArrayList<Edge>[] graph) {
    int N = graph.length;
    PriorityQueue<int[]> pq = new PriorityQueue<int[]>(new Comparator<int[]>(){
      public int compare(int[] a, int[] b) {
        return a[1] - b[1];
      }
    });
    int[] distances = new int[N];
    for (int i = 0; i < N-1; i++) {
      distances[i] = Integer.MAX_VALUE;
    }
    int[] temp = {N-1,0};
    pq.add(temp);
    while (! pq.isEmpty()) {
      temp = pq.poll();
      int parent = temp[0];
      for (Edge e: graph[parent]) {
        int child = e.n2;
        if (distances[child] == Integer.MAX_VALUE || distances[child] > distances[parent] + e.weight) {
          distances[child] = distances[parent] + e.weight;
          int[] temp2 = {child,distances[child]};
          pq.add(temp2);
        }
      }
    }
    return distances;
  }

  static class FastScanner { 
    BufferedReader br; 
    StringTokenizer st; 
  
    public FastScanner(String filename) throws IOException { 
      br = new BufferedReader(new FileReader(filename));
    }
  
    String next() { 
      while (st == null || !st.hasMoreElements()) { 
        try { 
          st = new StringTokenizer(br.readLine()); 
        } 
        catch (IOException  e) { 
          e.printStackTrace(); 
        } 
      } 
      return st.nextToken(); 
    } 
  
    int nextInt() { 
      return Integer.parseInt(next()); 
    } 
  
    long nextLong() { 
      return Long.parseLong(next()); 
    } 
  
    double nextDouble() { 
      return Double.parseDouble(next()); 
    } 
  
    String nextLine() { 
      String str = ""; 
      try { 
        str = br.readLine(); 
      } catch (IOException e) { 
        e.printStackTrace(); 
      } 
      return str; 
    }
  }
}

class Edge {
  public int n1;
  public int n2;
  public int weight;
  public Edge (int p, int q, int w) {
    n1 = p;
    n2 = q;
    weight = w;
  }
}