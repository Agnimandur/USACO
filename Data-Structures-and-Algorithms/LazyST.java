//Data structure that supports range updates and range min, max, and sum queries
public class LazyST {
  public long[][] ranges;

  public SegmentTree(long[] array) {
    ranges = new long[4*array.length+1][4];
    for (int i = 0; i < array.length; i++) {
      add(1,1,array.length,i+1,i+1,array[i]);
    }
  }

  //range update nums[updateL..updateR] += val;
  public void add(int n, int L, int R, int updateL, int updateR, long val) {
    push(n,L,R);
    if (updateL <= L && R <= updateR) {
      //fully contained
      ranges[n][3] += val;
      push(n,L,R);
      return;
    } else if (L>updateR || R<updateL || L==R) {
      //not contained at all or leaf
      return;
    }
    add(2*n,L,(L+R)/2,updateL,updateR,val); 
    add(2*n+1,(L+R)/2+1,R,updateL,updateR,val);
    ranges[n][1] = Math.min(ranges[2*n][1],ranges[2*n+1][1]);
    ranges[n][2] = Math.max(ranges[2*n][2],ranges[2*n+1][2]);
    ranges[n][0] = ranges[2*n][0] + ranges[2*n+1][0];
  }

  public long minQuery(int n, int L, int R, int Lq, int Rq) {
    push(n,L,R);
    if (Lq <= L && R <= Rq) {
      return ranges[n][1];
    } else if (R < Lq || Rq < L || L==R) {
      return 1000000000000007L;
    } else {
      return Math.min(minQuery(2*n,L,(L+R)/2,Lq,Rq),minQuery(2*n+1,(L+R)/2+1,R,Lq,Rq));
    }
  }

  public long maxQuery(int n, int L, int R, int Lq, int Rq) {
    push(n,L,R);
    if (Lq <= L && R <= Rq) {
      return ranges[n][2];
    } else if (R < Lq || Rq < L || L==R) {
      return -1L;
    } else {
      return Math.max(maxQuery(2*n,L,(L+R)/2,Lq,Rq),maxQuery(2*n+1,(L+R)/2+1,R,Lq,Rq));
    }
  }

  public long sumQuery(int n, int L, int R, int Lq, int Rq) {
    push(n,L,R);
    if (Lq <= L && R <= Rq) {
      return ranges[n][0];
    } else if (R < Lq || Rq < L || L==R) {
      return 0L;
    } else {
      return (sumQuery(2*n,L,(L+R)/2,Lq,Rq) + sumQuery(2*n+1,(L+R)/2+1,R,Lq,Rq));
    }
  }

  public void push(int n, int L, int R) {
    ranges[n][1] += ranges[n][3];
    ranges[n][2] += ranges[n][3];
    ranges[n][0] += (R-L+1)*ranges[n][3];
    
    if (L < R) {
      ranges[2*n][3] += ranges[n][3];
      ranges[2*n+1][3] += ranges[n][3];
    }

    ranges[n][3] = 0;
  }
}