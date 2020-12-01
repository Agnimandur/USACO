//implementation of union find data structure
public class DisjointSetUnion {
  public int[] parent;
  public int[] weight;
  public int count;

  public DisjointSetUnion(int N) {
    count = N;
    parent = new int[N];
    for (int i = 0; i < N; i++)
      parent[i] = i;
    weight = new int[N];
    Arrays.fill(weight,1);
  }

  //"find"
  public int root(int p) {
    if (p == parent[p])
      return p;
    return parent[p] = root(parent[p]);
  }

  //"union"
  public void connect(int p, int q) {
    p = root(p);
    q = root(q);
    if (p==q)
      return;
    if (weight[p] < weight[q]) {
      parent[p] = q;
      weight[q] += weight[p];
    } else {
      parent[q] = p;
      weight[p] += weight[q];
    }
    count--;
  }

  public boolean connected(int p, int q) {
    return root(p) == root(q);
  }
}