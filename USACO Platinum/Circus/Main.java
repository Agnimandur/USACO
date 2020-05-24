import java.util.*;
import java.io.*;

class Main {
  public static final String TASKNAME = "circus";
  public static final long MOD = 1000000007L;
  public static void main(String[] args) throws IOException {
    InputStream is = new FileInputStream(TASKNAME+".in");
    PrintWriter pw = new PrintWriter(new BufferedWriter(new FileWriter(TASKNAME+".out")));
    FastScanner sc = new FastScanner(is);

    int N = sc.ni();
    ArrayList<Integer>[] tree = new ArrayList[N];
    for (int i = 0; i < N; i++)
      tree[i] = new ArrayList<Integer>();
    for (int i = 0; i < N-1; i++) {
      int n1 = sc.ni()-1;
      int n2 = sc.ni()-1;
      tree[n1].add(n2);
      tree[n2].add(n1);
    }
    boolean[] centers = new boolean[N];
    for (int i = 0; i < N; i++) {
      if (tree[i].size() >= 3)
        centers[i] = true;
    }

    //Math
    long[] fac = new long[100001];
    fac[0] = 1L;
    for (int i = 1; i <= 100000; i++)
      fac[i] = (i*fac[i-1])%MOD;
    long[] invFac = new long[100001];
    invFac[100000] = power(fac[100000],MOD-2,MOD);
    for (int i = 99999; i >= 0; i--) {
      invFac[i] = ((i+1)*invFac[i+1])%MOD;
    }


    long[] ans = new long[N+1];
    ans[N] = fac[N];
    ans[N-1] = fac[N-1];

    ArrayDeque<Vertex> active = new ArrayDeque<Vertex>();
    ArrayDeque<Pair> join = new ArrayDeque<Pair>();
    int[] cnt = new int[N];
    ArrayList<Integer> aliveCenters = new ArrayList<Integer>();
    for (int i = 0; i < N; i++) {
      if (centers[i]) {
        active.add(new Vertex(i,i,-1,1));
        cnt[i] = 1;
        aliveCenters.add(i);
      }
    }
    DisjointSetUnion dsu = new DisjointSetUnion(N);
    for (int k = N-2; k >= 1; k--) {
      int j = N-k;
      while (!join.isEmpty()) {
        Pair p = join.pollFirst();
        int firstRoot = dsu.root(p.first);
        int secondRoot = dsu.root(p.second);
        int F = cnt[firstRoot];
        int S = cnt[secondRoot];
        cnt[firstRoot] = 0;
        cnt[secondRoot] = 0;
        dsu.connect(firstRoot,secondRoot);
        int root = dsu.root(firstRoot);
        cnt[root] = F+S-(j-1);
      }
      ArrayList<Integer> stillAlive = new ArrayList<Integer>();
      for (int c: aliveCenters) {
        if (cnt[c] > 0)
          stillAlive.add(c);
      }
      aliveCenters = stillAlive;

      int curSize = active.size();
      for (int i = 0; i < curSize; i++) {
        Vertex v = active.pollFirst();
        for (int neighbor: tree[v.vertex]) {
          if (neighbor != v.parent) {
            cnt[dsu.root(v.center)] += 1;
            if (centers[neighbor]) {
              //a join between two centers will occur soon (also don't join a with b and b with a)
              if (v.center < neighbor)
                join.addLast(new Pair(v.center,neighbor));
            } else {
              active.addLast(new Vertex(v.center,neighbor,v.vertex,v.level+1));
            }
          }
        }
      }

      long val = fac[N-j];
      for (int c: aliveCenters)
        val = (val*invFac[Math.max(cnt[c]-j,0)])%MOD;
      ans[k] = val;
    }

    for (int i = 1; i <= N; i++)
      pw.println(ans[i]);
    pw.close();
  }

  //Fast exponentiation (x^y mod m)
  public static long power(long x, long y, long m) { 
    long ans = 1;
    x %= m;
    while (y > 0) { 
      if(y % 2 == 1) 
        ans = (ans * x) % m; 
      y /= 2;  
      x = (x * x) % m;
    } 
    return ans; 
  }

  static class Pair {
    public int first;
    public int second;
    public Pair (int f, int s) {
      first = f;
      second = s;
    }
  }

  static class Vertex {
    public int center;
    public int vertex;
    public int parent;
    public int level;

    public Vertex(int c, int v, int p, int l) {
      center = c;
      vertex = v;
      parent = p;
      level = l;
    }
  }

  static class DisjointSetUnion {
    public int[] parent;
    public int[] weight;
    public int count;

    public DisjointSetUnion(int nodes) {
      count = nodes;
      parent = new int[nodes];
      weight = new int[nodes];
      for (int i = 0; i < nodes; i++) {
        parent[i] = i;
        weight[i] = 1;
      }
    }
    //"find"
    public int root(int p) {
      while (p != parent[p]) {
        p = parent[p];
      }
      return p;
    }

    //"union"
    public void connect(int p, int q) {
      int rootP = root(p);
      int rootQ = root(q);
      if (rootP == rootQ) return;
      if (weight[rootP] < weight[rootQ]) {
        parent[rootP] = rootQ;
        weight[rootQ] += weight[rootP];
      } else {
        parent[rootQ] = rootP;
        weight[rootP] += weight[rootQ];
      }
      count--;
    }

    public boolean connected(int p, int q) {
      return root(p) == root(q);
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