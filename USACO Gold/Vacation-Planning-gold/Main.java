import java.util.*;
import java.io.*;

class Main {
  public static void main(String[] args) throws IOException {
    InputStream is = new FileInputStream("vacationgold.in");
    PrintWriter pw = new PrintWriter(new BufferedWriter(new FileWriter("vacationgold.out")));
    FastScanner sc = new FastScanner(is);

    int N = sc.ni();
    int M = sc.ni();
    int K = sc.ni();
    int Q = sc.ni();
    ArrayList<Edge>[] graph = new ArrayList[N];
    ArrayList<Edge>[] reverse = new ArrayList[N];
    for (int i = 0; i < N; i++) {
      graph[i] = new ArrayList<Edge>();
      reverse[i] = new ArrayList<Edge>();
    }
    for (int i = 0; i < M; i++) {
      int s = sc.ni()-1;
      int e = sc.ni()-1;
      int w = sc.ni();
      graph[s].add(new Edge(e,w));
      reverse[e].add(new Edge(s,w));
    }
    int[] hubs = new int[K];
    for (int i = 0; i < K; i++) {
      hubs[i] = sc.ni()-1;
    }
    Arrays.sort(hubs);

    int[][] hubToNode = new int[K][N]; //use the regular graph and run Dijkstra's K times
    for (int i = 0; i < K; i++) {
      int[] distances = dijkstra(hubs[i],graph);
      for (int j = 0; j < N; j++) {
        hubToNode[i][j] = distances[j];
      }
    }

    int[][] nodeToHub = new int[N][K]; //use the reversed graph and run Dijkstra's K times
    for (int j = 0; j < K; j++) {
      int[] distances = dijkstra(hubs[j],reverse);
      for (int i = 0; i < N; i++) {
        nodeToHub[i][j] = distances[i];
      }
    }

    int routes = 0;
    long length = 0;

    for (int q = 0; q < Q; q++) {
      int s = sc.ni()-1;
      int e = sc.ni()-1;

      //Go from s to hubs[h], and then from hubs[h] to e (h is index in hubs (0 to K-1)).
      int minDist = Integer.MAX_VALUE/2;
      for (int h = 0; h < K; h++) {
        int dist = nodeToHub[s][h]+hubToNode[h][e];
        minDist = Math.min(minDist,dist);
      }
      if (minDist < Integer.MAX_VALUE/2) {
        routes++;
        length += minDist;
      }
    }

    pw.println(routes);
    pw.println(length);
    pw.close();
  }

  static int[] dijkstra(int root, ArrayList<Edge>[] g) {
    PriorityQueue<int[]> pq = new PriorityQueue<int[]>(new Comparator<int[]>(){
      public int compare(int[] a, int[] b) {
        return a[1] - b[1];
      }
    });
    int[] distances = new int[g.length];
    Arrays.fill(distances,Integer.MAX_VALUE/2);
    distances[root] = 0;

    //Dijkstra's Algorithm
    int[] temp = {root,0};
    pq.add(temp);
    while (! pq.isEmpty()) {
      temp = pq.poll();
      int parent = temp[0];
      for (Edge e: g[parent]) {
        int child = e.to;
        if (distances[child] > distances[parent] + e.weight) {
          distances[child] = distances[parent] + e.weight;
          int[] temp2 = {child,distances[child]};
          pq.add(temp2);
        }
      }
    }
    return distances;
  }

  static class Edge {
    public int to;
    public int weight;
    public Edge(int t, int w) {
      to = t;
      weight = w;
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