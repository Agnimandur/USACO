public class DisjointSetUnion {
  public int[] parent;
  public int[] weight;
  public int count;

  public DisjointSetUnion(int nodes) {
    count = nodes;
    parent = new int[nodes];
    weight = new int[nodes];
    for (int i = 0; i < nodes; i++) {
      parent[i] = i;
      weight[i] = 1;
    }
  }

  //"find"
  public int root(int p) {
    while (p != parent[p]) {
      p = parent[p];
    }
    return p;
  }

  //"union"
  public void connect(int p, int q) {
    int rootP = root(p);
    int rootQ = root(q);
    if (rootP == rootQ) return;
    if (weight[rootP] < weight[rootQ]) {
      parent[rootP] = rootQ;
      weight[rootQ] += weight[rootP];
    } else {
      parent[rootQ] = rootP;
      weight[rootP] += weight[rootQ];
    }
    count--;
  }

  public boolean connected(int p, int q) {
    return root(p) == root(q);
  }
}