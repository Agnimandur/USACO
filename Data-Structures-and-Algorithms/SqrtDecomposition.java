//Sqrt decomposition code (range updates and queries), can easily be generalized to sum,min,max,etc.
//Initialization: O(N)
//Update: O(sqrtN)
//Query: O(sqrtN)
//Equiv check: O(N)
public class SqrtDecomposition {
	int N;
	int SZ;
	ArrayList<Bucket> decomp;

	public SqrtDecomposition(int[] init) {
		N = init.length;
		SZ = (int)Math.sqrt(N);
		decomp = new ArrayList<Bucket>(SZ);
		for (int i = 0; i < N; i += SZ) {
			decomp.add(new Bucket(i, Math.min(N-1,i+SZ-1), init));
		}
	}

	public void update(int L, int R, int x) {
		for (Bucket b: decomp) {
			b.update(L, R, x);
		}
	}

	public int query(int L, int R) {
		int ans = 0;
		for (Bucket b: decomp) {
			ans += b.query(L, R);
		}
		return ans;
	}

	public boolean equiv(int[] with) {
		ArrayList<Integer> arr = new ArrayList<Integer>();
		for (Bucket b: decomp) {
			for (int z: b.arr) {
				if (b.all >= 0)
					arr.add(b.all);
				else
					arr.add(z);
			}
		}
		if (with.length != arr.size())
			return false;
		for (int i = 0; i < with.length; i++) {
			if (with[i] != arr.get(i))
				return false;
		}
		return true;
	}

	static class Bucket {
		int L;
		int R;
		int[] arr;
		int val;
		int all;

		public Bucket(int L, int R, int[] init) {
			arr = new int[R-L+1];
			this.L=L;
			this.R=R;
			val = 0;
			all = -1;
			for (int i = L; i <= R; i++) {
				arr[i-L] = init[i];
				val += init[i];
			}
		}

		public void update(int from, int to, int x) {
			if (to < L || from > R)
				return;
			if (from <= L && R <= to) {
				all = x;
				val = x*(R-L+1);
			} else {
				if (all >= 0) {
					Arrays.fill(arr,all);
					all = -1;
				}
				for (int i = Math.max(from,L); i <= Math.min(to,R); i++) {
					val += (x-arr[i-L]);
					arr[i-L] = x;
				}
			}
		}

		public int query(int from, int to) {
			if (to < L || from > R)
				return 0;
			if (from <= L && R <= to) {
				return val;
			} else {
				int beg = Math.max(from,L);
				int end = Math.min(to,R);
				if (all >= 0) {
					return (end-beg+1)*all;
				} else {
					int ans = 0;
					for (int i = beg; i <= end; i++)
						ans += arr[i-L];
					return ans;
				}
			}
		}
	}
}