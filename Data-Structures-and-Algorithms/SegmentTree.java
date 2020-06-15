//No lazy propagation. 0 indexed. Very fast.
public class SegmentTree {
  public long[] arr;
  public long[] tree;
  public int N;

  //Zero initialization
  public SegmentTree(int n) {
    N = n;
    tree = new long[4*N+1];
  }

  public long query(int i, int j) {
    return query(0,0,N-1,i,j);
  }

  public void update(int arrIndex, long val) {
    update(0,0,N-1,arrIndex,val);
  }

  private long query(int treeIndex, int lo, int hi, int i, int j) {
    // query for arr[i..j]
    if (lo > j || hi < i)
      return 0;
    if (i <= lo && j >= hi)
      return tree[treeIndex];
    int mid = lo + (hi - lo) / 2;

    if (i > mid)
      return query(2 * treeIndex + 2, mid + 1, hi, i, j);
    else if (j <= mid)
      return query(2 * treeIndex + 1, lo, mid, i, j);
    
    long leftQuery = query(2 * treeIndex + 1, lo, mid, i, mid);
    long rightQuery = query(2 * treeIndex + 2, mid + 1, hi, mid + 1, j);

    // merge query results
    return merge(leftQuery, rightQuery);
  }

  private void update(int treeIndex, int lo, int hi, int arrIndex, long val) {
    if (lo == hi) {
      tree[treeIndex] = val;
      return;
    }

    int mid = lo + (hi - lo) / 2;

    if (arrIndex > mid)
      update(2 * treeIndex + 2, mid + 1, hi, arrIndex, val);
    else if (arrIndex <= mid)
      update(2 * treeIndex + 1, lo, mid, arrIndex, val);

    // merge updates
    tree[treeIndex] = merge(tree[2 * treeIndex + 1], tree[2 * treeIndex + 2]);
  }

  private long merge(long a, long b) {
    return (a+b);
  }
}