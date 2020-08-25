//Dijkstra's Shortest Path Algorithm
public class Dijkstra {
  public int N;
  public ArrayList<long[]>[] graph;

  public Dijkstra(int numNodes) {
    N = numNodes;
    graph = new ArrayList[N];
    for (int i = 0; i < N; i++) {
      graph[i] = new ArrayList<long[]>();
    }
  }

  //uncomment the last line to make every edge two-way
  public void addEdge(long[] edge) {
    graph[n1].add(new long[]{edge[1],edge[2]});
    //graph[n2].add(new long[]{edge[0],edge[2]});
  }

  //uncomment the lines with "previousNode" if you'd like to calculate that
  public long[] dijkstra(int root) {
    //long[] previousNode = new long[N];
    //Arrays.fill(previousNode,-1);
    
    long[] distances = new long[N];
    Arrays.fill(distances,Long.MAX_VALUE);
    distances[root] = 0L;

    PriorityQueue<long[]> pq = new PriorityQueue<long[]>(new Comparator<long[]>() {
      @Override
      public int compare(long[] arr1, long[] arr2) {
        if (arr1[1] < arr2[1])
          return -1;
        else if (arr1[1] > arr2[1])
          return 1;
        else
          return 0;
      }
    });
    long[] temp = new long[]{root,0};
    pq.add(temp);
    while (! pq.isEmpty()) {
      temp = pq.poll();
      for (long[] e: graph[temp.node]) {
        int child = (int)e[0];
        long w = e[1];
        int node = (int)temp[0];
        if (distances[child] > distances[node] + w) {
          //previousNode[child] = temp.node;
          distances[child] = distances[node] + w;
          pq.add(new long[]{child,distances[child]});
        }
      }
    }

    return distances;
  }
}