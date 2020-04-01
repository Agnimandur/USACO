import java.util.*;
import java.io.*;

class Main {
  public static void main(String[] args) throws IOException {
    InputStream is = new FileInputStream("cowland.in");
    PrintWriter pw = new PrintWriter(new BufferedWriter(new FileWriter("cowland.out")));
    FastScanner sc = new FastScanner(is);

    int N = sc.ni();
    int Q = sc.ni();
    int[] input = new int[N];
    for (int i = 0; i < N; i++)
      input[i] = sc.ni();
    int[][] edges = new int[N-1][2];
    for (int i = 0; i < N-1; i++)
      edges[i] = new int[]{sc.ni()-1,sc.ni()-1};
    
    //Heavy Light Decomposition to solve the problem
    HeavyLightDecomposition HLD = new HeavyLightDecomposition(edges,input);
    for (int q = 0; q < Q; q++) {
      int type = sc.ni();
      if (type == 1) {
        //update
        int index = sc.ni()-1;
        int newVal = sc.ni();
        HLD.hldUpdate(index,newVal);
      } else {
        //query
        int a = sc.ni()-1;
        int b = sc.ni()-1;
        int ans = HLD.hldQuery(a,b);
        pw.println(ans);
      }
    }
    pw.close();
  }

  static class HeavyLightDecomposition {
    //Remember to set the merge operator manually. Also, if necessary, change everything to longs.

    //Original Graph
    public ArrayList<Integer>[] original;
    public int[] subSize;
    public int[] originalP;
    public int N;

    //HLD graph
    public int[] hldMap; //MAP for queries and updates
    public int[][] table; //lca TABLE
    public int[] depth;
    public ArrayList<Integer>[] graph;
    public int[] root;

    public SegmentTree st; //assume 0-indexed
    public int[] vals;

    public HeavyLightDecomposition(int[][] edges, int[] input) {
      N = edges.length+1;
      original = new ArrayList[N];
      for (int i = 0; i < N; i++)
        original[i] = new ArrayList<Integer>();
      for (int[] edge: edges) {
        original[edge[0]].add(edge[1]);
        original[edge[1]].add(edge[0]);
      }
      //Calculate the size of the subgraph for each node (key in determining HLD chains)
      subSize = new int[N];
      originalP = new int[N];
      originalP[0] = -1;
      subSize[0] = dfsSubTree(0);

      //Create a new graph that is friendly to HLD (i.e. every HLD chain consists of consecutive nodes)
      hldMap = new int[N];

      //the root of each HLD chain
      root = new int[N];
      Arrays.fill(hldMap,-1);
      int newVal = 0;
      ArrayDeque<Integer> bfs = new ArrayDeque<Integer>();
      bfs.add(0);
      while (! bfs.isEmpty()) {
        int n = bfs.poll();
        for (int neighbor: original[n]) {
          if (neighbor != originalP[n])
            bfs.add(neighbor);
        }
        if (hldMap[n] >= 0) continue;
        int r = newVal;
        while (n >= 0) {
          root[newVal] = r;
          hldMap[n] = newVal;
          newVal++;
          int next = -1;
          int size = 0;
          for (int neighbor: original[n]) {
            if (neighbor != originalP[n] && subSize[neighbor] > size) {
              size = subSize[neighbor];
              next = neighbor;
            }
          }
          n = next;
        }
      }

      //Create the new HLD graph
      graph = new ArrayList[N];
      for (int i = 0; i < N; i++)
        graph[i] = new ArrayList<Integer>();
      for (int[] edge: edges) {
        graph[hldMap[edge[0]]].add(hldMap[edge[1]]);
        graph[hldMap[edge[1]]].add(hldMap[edge[0]]);
      }

      //Create depth and table (used for LCA)
      table = new int[N][18];
      for (int i = 0; i < N; i++)
        Arrays.fill(table[i],-1);
      depth = new int[N];
      Arrays.fill(depth,-1);
      depth[0] = 0;
      dfsDepth(0);
      for (int i = 0; i < N; i++) {
        for (int j = 1; j < 18; j++) {
          if (table[i][j-1]==-1)
            table[i][j] = -1;
          else
            table[i][j] = table[table[i][j-1]][j-1];
        }
      }

      //Initialize vals
      vals = new int[N];
      for (int i = 0; i < N; i++)
        vals[hldMap[i]] = input[i];

      //Initialize SegmentTree
      st = new SegmentTree(vals);
    }

    private void dfsDepth(int n) {
      for (int child: graph[n]) {
        if (depth[child]==-1) {
          depth[child] = depth[n]+1;
          table[child][0] = n;
          dfsDepth(child);
        }
      }
    }

    private int dfsSubTree(int n) {
      if (subSize[n] > 0) return subSize[n];
      int size = 1;
      for (int neighbor: original[n]) {
        if (neighbor != originalP[n]) {
          originalP[neighbor] = n;
          size += dfsSubTree(neighbor);
        }
      }
      subSize[n] = size;
      return size;
    }

    private int lca(int p, int q) {
      if (depth[p] < depth[q]) {
        return lca(q,p);
      }
      int diff = depth[p]-depth[q];
      for (int i = 0; i < 18; i++) {
        if (diff == 0) break;
        if ((diff&(1<<i)) > 0) {
          p = table[p][i];
        }
      }

      for (int i = 17; i >= 0; i--) {
        if (table[p][i] != -1 && table[q][i] != -1 && table[p][i] != table[q][i]) {
          p = table[p][i];
          q = table[q][i];
        } 
      }

      if (p == q)
        return p;
      else
        return table[p][0];
    }

    private int hld(int p, int q) {
      int lca = lca(p,q);
      int ans = 0;
      
      while (p > lca) {
        if (root[p] <= lca) {
          //final query
          ans = merge(ans,st.query(0,0,N-1,lca+1,p));
          break;
        } else {
          ans = merge(ans,st.query(0,0,N-1,root[p],p));
          p = table[root[p]][0];
        }
      }

      while (q > lca) {
        if (root[q] <= lca) {
          //final query
          ans = merge(ans,st.query(0,0,N-1,lca+1,q));
          break;
        } else {
          ans = merge(ans,st.query(0,0,N-1,root[q],q));
          q = table[root[q]][0];
        }
      }
      ans = merge(ans,vals[lca]);
      return ans;
    }

    public int hldQuery(int a, int b) {
      return hld(hldMap[a],hldMap[b]);
    }

    public void hldUpdate(int node, int newVal) {
      st.update(0,0,N-1,hldMap[node],newVal);
      vals[hldMap[node]] = newVal;
    }

    //set the merge operator manually!!!!
    public static int merge(int a, int b) {
      return (a^b);
    }

    //No lazy propagation. 0 indexed. Very fast.
    static class SegmentTree {
      public int[] arr;
      public int[] tree;
      public int N;

      public SegmentTree(int[] a) {
        //0 indexed seg tree
        N = a.length;
        arr = a;
        tree = new int[4*N+1];
        buildSegTree(0,0,N-1);
      }

      public void buildSegTree(int treeIndex, int lo, int hi) {
        if (lo == hi) {
          tree[treeIndex] = arr[lo];
          return;
        }

        int mid = lo + (hi - lo) / 2;
        buildSegTree(2 * treeIndex + 1, lo, mid);
        buildSegTree(2 * treeIndex + 2, mid + 1, hi);
        tree[treeIndex] = merge(tree[2 * treeIndex + 1], tree[2 * treeIndex + 2]);
      }

      public int query(int treeIndex, int lo, int hi, int i, int j) {
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
        
        int leftQuery = query(2 * treeIndex + 1, lo, mid, i, mid);
        int rightQuery = query(2 * treeIndex + 2, mid + 1, hi, mid + 1, j);

        // merge query results
        return merge(leftQuery, rightQuery);
      }

      public void update(int treeIndex, int lo, int hi, int arrIndex, int val) {
        if (lo == hi) {
          tree[treeIndex] = val;
          arr[arrIndex] = val;
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
    }
  }

  static class FastScanner { 
    public BufferedReader br; 
    public StringTokenizer st; 
  
    public FastScanner(InputStream is) throws IOException { 
      br = new BufferedReader(new InputStreamReader(is),32768);
      st = null;
    }
  
    public String next() { 
      while (st == null || !st.hasMoreTokens()) { 
        try { 
          st = new StringTokenizer(br.readLine()); 
        } 
        catch (IOException  e) { 
          throw new RuntimeException(e);
        }
      } 
      return st.nextToken(); 
    } 
  
    public int ni() { 
      return Integer.parseInt(next()); 
    } 
  
    public long nl() { 
      return Long.parseLong(next()); 
    } 
  
    public double nd() { 
      return Double.parseDouble(next()); 
    } 
  
    public String nextLine() { 
      String str = ""; 
      try { 
        str = br.readLine(); 
      } catch (IOException e) { 
        throw new RuntimeException(e);
      } 
      return str; 
    }
  }
}