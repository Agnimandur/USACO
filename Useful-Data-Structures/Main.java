class Main {
  public static void main(String[] args) {
    System.out.println("Hello world!");
  }


  //As a queue, but it can query the minimum/maximum value in the queue in O(1) time. 
  static class MinQueue {
		private ArrayDeque<Integer> s1Num;
		private ArrayDeque<Integer> s1Min;
		private ArrayDeque<Integer> s2Num;
		private ArrayDeque<Integer> s2Min;
		
		public MinQueue() {
			s1Num = new ArrayDeque<Integer>();
			s1Min = new ArrayDeque<Integer>();
			s2Num = new ArrayDeque<Integer>();
			s2Min = new ArrayDeque<Integer>();
		}
		
		public int queryMin() {
			int min;
			if (s1Num.isEmpty()) {
			    min = s2Min.peek();
			} else if (s2Num.isEmpty()) {
			    min = s1Min.peek();
			} else {
			    min = Math.min(s1Min.peek(),s2Min.peek()); 
			}
			return min;
		}
		
		public void add(int n) {
			int min = s1Num.isEmpty()? n : Math.min(n,s1Min.peek());
			s1Num.push(n);
			s1Min.push(min);
		}
		
		public void remove() {
			if (s2Num.isEmpty()) {
				while (!s1Num.isEmpty()) {
					int n = s1Num.pop();
					s1Min.pop();
					int min = s2Num.isEmpty() ? n : Math.min(n, s2Min.peek());
					s2Num.push(n);
					s2Min.push(min);
				}
			}
			s2Num.pop();
			s2Min.pop();
		}
	}

  //Fast exponentiation (x^y mod p)
  public static long power(long x, long y, long p) { 
    // Initialize result
    long res = 1;
      
    // Update x if it is more   
    // than or equal to p 
    x = x % p;  
  
    while (y > 0) { 
      // If y is odd, multiply x 
      // with result 
      if((y & 1)==1) 
          res = (res * x) % p; 

      // y must be even now 
      // y = y / 2 
      y = y >> 1;  
      x = (x * x) % p;  
    } 
    return res; 
  }

  //Find the distance between two points
  public static long dist(int[] point, int[] point2) {
    return (long)(Math.pow((point2[1]-point[1]),2)+Math.pow((point2[0]-point[0]),2));
  }
  
  //Find the GCD of two numbers
  public static long gcd(long a, long b) {
    if (b == 0)
      return a;
    else
      return gcd(b,a%b);
  }

  //Data structure that supports range updates and range min, max, and sum queries
  static class SegmentTree {

    public Node[] ranges;

    public SegmentTree(long[] array) {
      ranges = new Node[4*array.length+1];
      for (int i = 0; i < ranges.length; i++) {
        ranges[i] = new Node();
      }
      for (int i = 0; i < array.length; i++) {
        add(1,i+1,i+1,1,array.length,array[i]);
      }
    }

    //range update nums[updateL..updateR] += val;
    public void add(int n, int updateL, int updateR, int L, int R, long val) {
      push(n,L,R);
      if (updateL <= L && R <= updateR) {
        //fully contained
        ranges[n].lazy += val;
        push(n,L,R);
        return;
      } else if (L>updateR || R<updateL || L==R) {
        //not contained at all or leaf
        return;
      }
      add(2*n,updateL,updateR,L,(L+R)/2,val); 
      add(2*n+1,updateL,updateR,(L+R)/2+1,R,val);
      ranges[n].min = Math.min(ranges[2*n].min,ranges[2*n+1].min);
      ranges[n].max = Math.max(ranges[2*n].max,ranges[2*n+1].max);
      ranges[n].sum = ranges[2*n].min + ranges[2*n+1].sum;
    }

    public long minQuery(int n, int L, int R, int Lq, int Rq) {
      push(n,L,R);
      if (Lq <= L && R <= Rq) {
        return ranges[n].min;
      } else if (R < Lq || Rq < L || L==R) {
        return 1000000000000007L;
      } else {
        return Math.min(minQuery(2*n,L,(L+R)/2,Lq,Rq),minQuery(2*n+1,(L+R)/2+1,R,Lq,Rq));
      }
    }

    public long maxQuery(int n, int L, int R, int Lq, int Rq) {
      push(n,L,R);
      if (Lq <= L && R <= Rq) {
        return ranges[n].max;
      } else if (R < Lq || Rq < L || L==R) {
        return -1L;
      } else {
        return Math.max(maxQuery(2*n,L,(L+R)/2,Lq,Rq),maxQuery(2*n+1,(L+R)/2+1,R,Lq,Rq));
      }
    }

    public long sumQuery(int n, int L, int R, int Lq, int Rq) {
      push(n,L,R);
      if (Lq <= L && R <= Rq) {
        return ranges[n].sum;
      } else if (R < Lq || Rq < L || L==R) {
        return 0L;
      } else {
        return (sumQuery(2*n,L,(L+R)/2,Lq,Rq) + sumQuery(2*n+1,(L+R)/2+1,R,Lq,Rq));
      }
    }

    public void push(int n, int L, int R) {
      ranges[n].min += ranges[n].lazy;
      ranges[n].max += ranges[n].lazy;
      ranges[n].sum += (R-L+1)*ranges[n].lazy;
      
      if (L < R) {
        ranges[2*n].lazy += ranges[n].lazy;
        ranges[2*n+1].lazy += ranges[n].lazy;
      }

      ranges[n].lazy = 0;
    }

    static class Node {
      public long min;
      public long max;
      public long sum;
      public long lazy;

      public Node() {
      }
    }
  }
}