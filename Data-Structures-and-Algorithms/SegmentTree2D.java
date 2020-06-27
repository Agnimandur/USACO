//2D segment tree class. 0 indexed, query endpoints are inclusive.
public class SegmentTree2D {
  public long[][] st;
  public long[][] matrix;
  public int N;
  public int M;
  public int NULL;
  
  public SegmentTree2D(long[][] mat) {
    NULL = -100000;
    N = mat.length;
    M = mat[0].length;
    matrix = mat;
    st = new long[4*N+1][4*M+1];
    build2D(1,0,N-1);
  }
  
  private void build2D(int index, int L, int R) {
    if (L==R)
      build(index,matrix[L],1,0,M-1);
    else {
      int mid = (L+R)/2;
      build2D(2*index, L, mid);
      build2D(2*index+1, mid+1, R);
      for (int i = 0; i <= 4*M; i++) {
        st[index][i] = merge(st[2*index][i],st[2*index+1][i]);
      }
    }
  }
  
  private void build(int ind, long[] nums, int index, int L, int R) {
    if (L==R) {
      st[ind][index] = nums[L];
    } else {
      int mid = (L + R) / 2;
      build(ind,nums, 2*index, L, mid);
      build(ind,nums, 2*index+1, mid+1,R);
      st[ind][index] = merge(st[ind][2*index], st[ind][2*index + 1]);
    }
  }
  
  private long query(int ind, int index, int L, int R, int y1, int y2) {
    if (y1 > R || y2 < L)
      return NULL;
    if (L>=y1 && R<=y2) {
      return st[ind][index];
    }
    int mid = (L + R) / 2;
    long left = query(ind, 2*index, L, mid, y1, y2);
    long right = query(ind, 2*index + 1, mid + 1, R, y1, y2);
    if(left == NULL)
      return right;
    if(right == NULL)
      return left;
    return merge(left,right);
  }
  
  private long query2D(int index, int L, int R, int x1, int y1, int x2, int y2){
    if(L > x2 || R < x1)
      return NULL;

    if(L >= x1 && R <= x2)
      return query(index, 1, 0, M - 1, y1, y2);

    int mid = (L + R) / 2;

    long left = query2D(2*index, L, mid, x1, y1, x2, y2);
    long right = query2D(2*index + 1, mid + 1, R, x1, y1, x2, y2);

    if(left == NULL)
      return right;
    if(right == NULL)
      return left;
    
    return merge(left,right);
  }
  
  public long query(int x1, int y1, int x2, int y2){
    return query2D(1, 0, N - 1, x1, y1, x2, y2);
  }
  
  public long merge(long a, long b) {
    return Math.min(a,b);
  }
}