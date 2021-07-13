public class HopcroftKarp {
  int[] match;
  int[] dist;
  ArrayList<Integer>[] graph;
  int N;
  int M;
  int T;
  int INF = 1000000000;

  HopcroftKarp(int N, int M) {
    this.N=N;
    this.M=M;
    T = N+M+1;
    match = new int[T];
    dist = new int[T];
    graph = new ArrayList[T];
    for (int i = 0; i < T; i++)
      graph[i] = new ArrayList<Integer>();
  }

  //1 indexed
  void addEdge(int u, int v) {
    graph[u].add(v);
    graph[v].add(u);
  }

  boolean BFS() {
    ArrayDeque<Integer> q = new ArrayDeque<Integer>();
    for (int u = 1; u <= N; u++) {
        if (match[u]==0) {
          dist[u] = 0;
          q.add(u);
        } else {
          dist[u] = INF;
        }
    }
    dist[0] = INF;
    while (!q.isEmpty()) {
      int u = q.pollFirst();
      if (dist[u]<dist[0]) {
        for (int v: graph[u]) {
          if (dist[match[v]] == INF) {
            dist[match[v]] = dist[u]+1;
            q.add(match[v]);
          }
        }
      }
    }
    return dist[0]<INF;
  }

  boolean DFS(int u) {
    if (u > 0) {
      for (int v: graph[u]) {
        if (dist[match[v]]==dist[u]+1 && DFS(match[v])) {
          match[v] = u;
          match[u] = v;
          return true;
        }
      }
      dist[u] = INF;
      return false;
    }
    return true;
  }

  ArrayList<int[]> run() {
    int m = 0;
    while (BFS()) {
      for (int u = 1; u <= N; u++) {
        if (match[u]==0 && DFS(u)) {
          m++;
        }
      }
    }
    ArrayList<int[]> ans = new ArrayList<>();
    for (int v = N+1; v <= N+M; v++) {
      if (match[v]>0) {
        ans.add(new int[]{match[v],v});
      }
    }
    return ans;
  }
}