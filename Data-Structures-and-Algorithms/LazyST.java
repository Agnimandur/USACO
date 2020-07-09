//Data structure that supports range updates and range min, max, and sum queries. 0-indexed!
//NOTE: still potentially buggy!
//MANUALLY SET: none,merge,operation

public class LazyST {
  public long[] tree;
  public long[] lazy;
  public int N;
  public long NONE;

  public LazyST(int n) {
    N = n;
    tree = new long[4*N+1];
    lazy = new long[4*N+1];
    NONE = 0; //manually set NONE based on the problem.
  }

  //range update nums[updateL..updateR] += val;
  public void add(int updateL, int updateR, long val) {
    add(0,0,N-1,updateL,updateR,val);
  }

  private void add(int n, int L, int R, int updateL, int updateR, long val) {
    push(n,L,R);
    if (updateL <= L && R <= updateR) {
      //fully contained
      lazy[n] += val;
      push(n,L,R);
      return;
    } else if (L>updateR || R<updateL || L==R) {
      //not contained at all or leaf
      return;
    }
    add(2*n+1,L,(L+R)/2,updateL,updateR,val); 
    add(2*n+2,(L+R)/2+1,R,updateL,updateR,val);
    tree[n] = merge(tree[2*n+1],tree[2*n+2]);
  }

  public long query(int Lq, int Rq) {
    return query(0,0,N-1,Lq,Rq);
  }

  private long query(int n, int L, int R, int Lq, int Rq) {
    push(n,L,R);
    if (Lq <= L && R <= Rq) {
      return tree[n];
    } else if (R < Lq || Rq < L) {
      return NONE;
    } else {
      return merge(query(2*n+1,L,(L+R)/2,Lq,Rq),query(2*n+2,(L+R)/2+1,R,Lq,Rq));
    }
  }

  private void push(int n, int L, int R) {
    tree[n] += operation(lazy[n],L,R);
    
    if (L < R) {
      lazy[2*n+1] += lazy[n];
      lazy[2*n+2] += lazy[n];
    }
    lazy[n] = 0L;
  }

  public long merge(long a, long b) {
    //return Math.min(a,b); //min,NONE=INF
    //return Math.max(a,b); //max,NONE=NEG INF
    return (a+b); //sum,NONE=0
  }

  //used to process lazy updates
  public long operation(long a, long L, long R) {
    //return a; //min or max
    return (R-L+1)*a; //sum
  }
}