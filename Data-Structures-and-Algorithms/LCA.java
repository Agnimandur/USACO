public class LCA {
	//root is at node 0
	public ArrayList<Integer>[] tree;
	public int N;
	public int[][] table;
	public int[] depth;
	public LCA(ArrayList<Integer>[] tree) {
		this.tree = tree;
		N = tree.length;
		table = new int[N][20];
		depth = new int[N];
		depthDFS(0,0,-1);
		buildTable();
	}

	private void buildTable() {
		for (int d = 1; d < 20; d++) {
			for (int i = 0; i < N; i++) {
				int p = table[i][d-1];
				if (p >= 0)
					table[i][d] = table[p][d-1];
				else
					table[i][d] = -1;
			}
		}
	}

	private void depthDFS(int n, int d, int p) {
		depth[n] = d;
		table[n][0] = p; //the parent of n
		for (int next: tree[n]) {
			if (next==p) continue;
			depthDFS(next,d+1,n);
		}
	}

	public int lca(int u, int v) {
		if (depth[u] < depth[v]) return lca(v,u);
		int diff = depth[u]-depth[v];
		for (int i = 0; i < 20; i++) {
			if ((diff&(1<<i)) > 0)
				u = table[u][i];
		}
		for (int i = 19; i >= 0; i--) {
			if (table[u][i] >= 0 && table[v][i] >= 0 && table[u][i]!=table[v][i]) {
				u = table[u][i];
				v = table[v][i];
			}
		}
		if (u==v)
			return u;
		else
			return table[u][0];
	}
}