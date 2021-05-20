//Calculates the topological sorting of a DAG.
//int[] sorting = new TopologicalSort(graph).run();
public class TopologicalSort {
    ArrayList<Integer>[] graph;
    int N;
    static boolean[] vis;
    static int[] ans;
    static int index;
    public TopologicalSort(ArrayList<Integer>[] g) {
        graph = g;
        N = g.length;
        vis = new boolean[N];
        ans = new int[N];
        index = N-1;
    }

    public int[] run() {
        for (int i = 0; i < N; i++) {
            if (!vis[i]) dfs(i);
        }
        return ans;
    }

    private void dfs(int n) {
        vis[n] = true;
        for (int next: graph[n]) {
            if (!vis[next])
                dfs(next);
        }
        ans[index] = n;
        index--;
    }
}