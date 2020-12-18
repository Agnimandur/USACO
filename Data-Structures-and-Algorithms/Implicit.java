//Implicit Segment Tree Node (initially all values are 0). Don't forget to set NONE and the merge methods!
public class Implicit {
	long L;
	long R;
	long val;
	Implicit left;
	Implicit right;
	final long NONE = 0L; //set this manually!!!

	public Implicit(long L, long R) {
		this.L=L;
		this.R=R;
		val = 0;
		left = null;
		right = null;
	}

	public long merge(long a, long b) {
		return (a+b);
		//return Math.min(a,b);
		//return Math.max(a,b);
	}

	//add child nodes if necessary
	public void build() {
		if (left==null && L < R) {
			long m = (L+R)/2;
			left = new Implicit(L,m);
			right = new Implicit(m+1,R);
		}
	}

	//set index i to x.
	public void update(long i, long x) {
		if (L==R) {
			val = x;
			return;
		}
		build();
		//propagate
		if (i <= left.R)
			left.update(i,x);
		else
			right.update(i,x);
		val = merge(left.val,right.val);
	}

	public long query(long lq, long rq) {
		if (lq <= L && R <= rq)
			return val;
		else if (Math.max(lq,L) > Math.min(rq,R))
			return NONE;
		else {
			build();
			return merge(left.query(lq,rq),right.query(lq,rq));
		}
	}
}