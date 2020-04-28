import java.util.*;
import java.io.*;

class Main {
  public static final String TASKNAME = "gpsduel";
  public static long answer;
  public static void main(String[] args) throws IOException {
    InputStream is = new FileInputStream(TASKNAME + ".in");
    PrintWriter pw = new PrintWriter(new BufferedWriter(new FileWriter(TASKNAME + ".out")));
    FastScanner sc = new FastScanner(is);

    int N = sc.ni();
    int M = sc.ni();
    ArrayList<Edge>[] transpose1 = new ArrayList[N];
    for (int i = 0; i < N; i++)
      transpose1[i] = new ArrayList<Edge>();
    
    ArrayList<Edge>[] transpose2 = new ArrayList[N];
    for (int i = 0; i < N; i++)
      transpose2[i] = new ArrayList<Edge>();
    Edge[] edges1 = new Edge[M];
    Edge[] edges2 = new Edge[M];

    for (int i = 0; i < M; i++) {
      int from = sc.ni()-1;
      int to = sc.ni()-1;
      long w1 = sc.nl();
      long w2 = sc.nl();
      edges1[i] = new Edge(from,to,w1);
      edges2[i] = new Edge(from,to,w2);
      transpose1[to].add(edges1[i]);
      transpose2[to].add(edges2[i]);
    }

    dijkstra(transpose1,edges1);
    dijkstra(transpose2,edges2);
    /*for (int i = 0; i < M; i++)
      System.out.println(edges1[i]);
    System.out.println("----------------");
    for (int i = 0; i < M; i++)
      System.out.println(edges2[i]);*/

    Edge[] edges3 = new Edge[M];
    ArrayList<Edge>[] transpose3 = new ArrayList[N];
    for (int i = 0; i < N; i++)
      transpose3[i] = new ArrayList<Edge>();
    for (int i = 0; i < M; i++) {
      int from = edges1[i].from;
      int to = edges1[i].to;
      edges3[i] = new Edge(from,to,edges1[i].preferred+edges2[i].preferred);
      transpose3[to].add(edges3[i]);
    }
    answer = 0;
    dijkstra(transpose3,edges3);

    pw.println(answer);
    pw.close();
  }

  public static void dijkstra(ArrayList<Edge>[] transpose, Edge[] edges) {
    int N = transpose.length;
    int M = edges.length;
    PriorityQueue<Pair> pq = new PriorityQueue<Pair>();
    long[] distances = new long[N];
    int[] previousNode = new int[N];
    Arrays.fill(previousNode,-1);
    Arrays.fill(distances,Long.MAX_VALUE);
    distances[N-1] = 0L;

    //Dijkstra's Algorithm
    pq.add(new Pair(N-1,0L));
    while (! pq.isEmpty()) {
      Pair p = pq.poll();
      int parent = p.node;
      for (Edge e: transpose[parent]) {
        if (distances[e.from] > distances[parent] + e.weight) {
          previousNode[e.from] = parent;
          distances[e.from] = distances[parent] + e.weight;
          pq.add(new Pair(e.from,distances[e.from]));
        }
      }
    }

    for (Edge e: edges) {
      //this is a preferred edge
      if (previousNode[e.from] == e.to) {
        e.preferred = 0L;
      }
    }
    answer = distances[0];
  }

  static class Pair implements Comparable<Pair> {
    public int node;
    public long weight;
    public Pair(int n, long w) {
      node = n;
      weight = w;
    }

    public int compareTo(Pair p) {
      if (this.weight < p.weight)
        return -1;
      else if (this.weight > p.weight)
        return 1;
      else
        return 0;
    }
  }

  static class Edge {
    public int from;
    public int to;
    public long weight;
    public long preferred;
    public Edge (int f, int t, long w) {
      from = f;
      to = t;
      weight = w;
      preferred = 1L;
    }

    public String toString() {
      return "(" + from + "," + to + "," + weight + "): " + preferred;
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