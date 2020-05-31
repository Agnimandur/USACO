//Dijkstra's Shortest Path Algorithm
public class Dijkstra {
  public long[] distances;
  public int[] previousNode;
  public int N;
  public ArrayList<Edge>[] graph;

  public Dijkstra(int numNodes, long[][] edges) {
    N = numNodes;
    graph = new ArrayList[N];
    for (int i = 0; i < N; i++) {
      graph[i] = new ArrayList<Edge>();
    }
    for (long[] edge: edges) {
      int n1 = (int)edge[0];
      int n2 = (int)edge[1];
      long weight = edge[2];
      graph[n1].add(new Edge(n1,n2,weight));
      graph[n2].add(new Edge(n2,n1,weight));
    }

    distances = new long[N];
    previousNode = new int[N];
  }

  public static void dijkstra(ArrayList<Edge>[] graph) {
    Arrays.fill(distances,Long.MAX_VALUE);
    distances[0] = 0L;
    PriorityQueue<Input> pq = new PriorityQueue<Input>();

    Input temp = new Input(0,0L);
    pq.add(temp);
    while (! pq.isEmpty()) {
      temp = pq.poll();
      for (int child: graph[temp.node]) {
        if (distances[child] > distances[temp.node] + temp.weight) {
          previousNode[child] = temp.node;
          distances[child] = distances[temp.node] + temp.weight;
          pq.add(new Input(child,distances[child]));
        }
      }
    }
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