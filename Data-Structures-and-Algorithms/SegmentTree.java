//No lazy propagation. 0 indexed.
public class SegmentTree {
  public long[] tree;
  public long NONE;
  public int N;

  //Zero initialization
  public SegmentTree(int n) {
    N = n;
    tree = new long[2*N+1];
    NONE = 0L; //set this manually (Long.MIN_VALUE for max-st, Long.MAX_VALUE for min-st, 0 for sum,xor-st, etc.)
  }

  public long merge(long a, long b) {
    return (a+b); //set this manually
  }

  public long query(int i, int j) {
    return query(0,0,N-1,i,j);
  }

  public void update(int i, long val) {
    update(0,0,N-1,i,val);
  }

  private long query(int t, int lo, int hi, int i, int j) {
    // query for arr[i..j]
    if (lo > j || hi < i)
      return NONE;
    if (i <= lo && j >= hi)
      return tree[t];
    
    int mid = (lo+hi)/2;
    if (i > mid)
      return query(t+2*(mid-lo+1),mid+1,hi,i,j);
    else if (j <= mid)
      return query(t+1,lo,mid,i,j);

    // merge query results
    return merge(query(t+1, lo, mid, i, mid), query(t+2*(mid-lo+1),mid+1,hi,mid+1,j));
  }

  private void update(int t, int lo, int hi, int i, long val) {
    if (lo == hi) {
      tree[t] = val;
      return;
    }

    int mid = (lo+hi)/2;
    if (i > mid)
      update(t+2*(mid-lo+1),mid+1,hi,arrIndex,val);
    else if (arrIndex <= mid)
      update(t+1,lo,mid,arrIndex,val);

    // merge updates
    merge(tree[t+1],tree[t+2*(mid-lo+1)]);
  }
}