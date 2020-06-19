import java.util.*;
import java.io.*;

class Main {
  public static final String TASKNAME = "pump";

  public static void main(String[] args) throws IOException {
    InputStream is = new FileInputStream(TASKNAME+".in");
    PrintWriter pw = new PrintWriter(new BufferedWriter(new FileWriter(TASKNAME+".out")));
    FastScanner sc = new FastScanner(is);

    int N = sc.ni();
    int M = sc.ni();
    long[][] edges = new long[M][4];
    for (int i = 0; i < M ; i++) {
      edges[i][0] = sc.nl()-1; //node1
      edges[i][1] = sc.nl()-1; //node2
      edges[i][2] = sc.nl(); //cost
      edges[i][3] = sc.nl(); //flow
    }
    //sort in descending order of flow
    edges = sort(edges);
    Dijkstra graph = new Dijkstra(N);
    double ans = 0.0;
    for (long[] edge: edges) {
      graph.addEdge(edge);
      long[] distances = graph.dijkstra(0);
      long cost = distances[N-1];
      if (cost == Long.MAX_VALUE) {
        //unreachable
        continue;
      }
      ans = Math.max(ans,(edge[3]+0.0)/cost);
    }
    pw.println((long)(1000000*ans));
    pw.close();
  }

  static class Dijkstra {
    public int N;
    public ArrayList<Edge>[] graph;

    public Dijkstra(int numNodes) {
      N = numNodes;
      graph = new ArrayList[N];
      for (int i = 0; i < N; i++) {
        graph[i] = new ArrayList<Edge>();
      }
    }

    //uncomment the last line to make every edge two-way
    public void addEdge(long[] edge) {
      int n1 = (int)edge[0];
      int n2 = (int)edge[1];
      long w = edge[2];
      //bidirectional edges
      graph[n1].add(new Edge(n1,n2,w));
      graph[n2].add(new Edge(n2,n1,w));
    }

    //uncomment the lines with "previousNode" if you'd like to calculate that
    public long[] dijkstra(int root) {
      //long[] previousNode = new long[N];
      //Arrays.fill(previousNode,-1);
      
      long[] distances = new long[N];
      Arrays.fill(distances,Long.MAX_VALUE);
      distances[root] = 0L;

      PriorityQueue<Input> pq = new PriorityQueue<Input>();
      Input temp = new Input(root,0L);
      pq.add(temp);
      while (! pq.isEmpty()) {
        temp = pq.poll();
        for (Edge e: graph[temp.node]) {
          int child = e.to;
          long w = e.weight;
          if (distances[child] > distances[temp.node] + w) {
            //previousNode[child] = temp.node;
            distances[child] = distances[temp.node] + w;
            pq.add(new Input(child,distances[child]));
          }
        }
      }

      return distances;
    }

    static class Input implements Comparable<Input> {
      public int node;
      public long weight;

      public Input(int n, long w) {
        node = n;
        weight = w;
      }

      public int compareTo(Input i) {
        return (int)(weight-i.weight);
      }
    }

    static class Edge {
      public int from;
      public int to;
      public long weight;

      public Edge(int f, int t, long w) {
        from = f;
        to = t;
        weight = w;
      }
    }
  }

  public static long[][] sort(long[][] arr) {
    //Sort an array (immune to quicksort TLE)
		Random rgen = new Random();
		for (int i = 0; i < arr.length; i++) {
      int randomPosition = rgen.nextInt(arr.length);
      long[] temp = arr[i];
      arr[i] = arr[randomPosition];
      arr[randomPosition] = temp;
		}
    Arrays.sort(arr, new Comparator<long[]>() {
      @Override
      public int compare(long[] arr1, long[] arr2) {
        if (arr1[3] > arr2[3])
          return -1;
        else
          return 1;
      }
    });
    return arr;
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