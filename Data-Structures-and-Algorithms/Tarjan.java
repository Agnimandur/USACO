//Tarjan's Strongly Connected Components Algorithm
public class Tarjan {
  ArrayList<Integer>[] graph;
  int N;
  ArrayList<ArrayList<Integer>> sccs;
  
  //algorithm variables
  private int index;
  private int[] indices;
  private int[] lowlink;
  private boolean[] onStack;
  private ArrayDeque<Integer> stack;
  
  public Tarjan(int N) {
    graph = new ArrayList[N];
    for (int i = 0; i < N; i++)
      graph[i] = new ArrayList<Integer>();
    this.N = graph.length;
  }
  
  public void addEdge(int u, int v) {
    graph[u].add(v);
  }
  
  public ArrayList<ArrayList<Integer>> findSCCs() {
    index = 0;
    indices = new int[N];
    Arrays.fill(indices,-1);
    lowlink = new int[N];
    Arrays.fill(lowlink,-1);
    onStack = new boolean[N];
    stack = new ArrayDeque<Integer>();
    sccs = new ArrayList<ArrayList<Integer>>();
    
    for (int i = 0; i < N; i++) {
      if (indices[i]==-1)
        connect(i);
    }
    return sccs;
  }
  
  private void connect(int u) {
    indices[u] = index;
    lowlink[u] = index;
    index++;
    onStack[u] = true;
    stack.push(u);
    
    for (int v: graph[u]) {
      if (indices[v]==-1) {
        connect(v);
        lowlink[u] = Math.min(lowlink[u],lowlink[v]);
      } else if (onStack[v]) {
        lowlink[u] = Math.min(lowlink[u], indices[v]);
      }
    }
    
    if (lowlink[u]==indices[u]) {
      ArrayList<Integer> component = new ArrayList<>();
      while (true) {
        int v = stack.pop();
        onStack[v] = false;
        component.add(v);
        if (v==u) break;
      }
      sccs.add(component);
    }
  }
}