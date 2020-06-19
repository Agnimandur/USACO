//Dijkstra's Shortest Path Algorithm
public class Dijkstra {
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
    graph[n1].add(new Edge(n1,n2,w));
    //graph[n2].add(new Edge(n2,n1,w));
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