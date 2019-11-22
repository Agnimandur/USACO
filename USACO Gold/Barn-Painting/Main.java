import java.util.*;
import java.io.*;

class Main {
  static final long MOD = 1000000007;
  static ArrayList<Integer>[] tree;
  static int[] preColored;
  static boolean[] visited;
  static long[][] memoized;

  public static void main(String[] args) throws IOException {
    InputStream is = new FileInputStream("barnpainting.in");
    PrintWriter pw = new PrintWriter(new BufferedWriter(new FileWriter("barnpainting.out")));
    FastScanner sc = new FastScanner(is);
    int N = sc.nextInt();
    int K = sc.nextInt();
    tree = new ArrayList[N];
    for (int i = 0; i < N; i++)
      tree[i] = new ArrayList<Integer>();
    for (int i = 0; i < N-1; i++) {
      int n1 = sc.nextInt()-1;
      int n2 = sc.nextInt()-1;
      tree[n1].add(n2);
      tree[n2].add(n1);
    }
    preColored = new int[N];
    Arrays.fill(preColored,-1);
    for (int i = 0; i < K; i++) {
      int n = sc.nextInt()-1;
      int color = sc.nextInt()-1;
      preColored[n] = color;
    }
    visited = new boolean[N];
    memoized = new long[N][3];
    recursion(0);
    long ans = (memoized[0][0] + memoized[0][1] + memoized[0][2])%MOD;
    pw.println(ans);
    pw.close();
  }

  public static void recursion(int node) {
    visited[node] = true;
    long[] nums = {1L,1L,1L};
    for (int child: tree[node]) {
      if (! visited[child]) {
        recursion(child);
        for (int i = 0; i < 3; i++) {
          nums[i] *= (memoized[child][(i+1)%3]+memoized[child][(i+2)%3]);
          nums[i] %= MOD;
        }
      }
    }
    if (preColored[node] >= 0) {
      nums[(preColored[node]+1)%3] = 0L;
      nums[(preColored[node]+2)%3] = 0L;
    }
    for (int i = 0; i < 3; i++) {
      memoized[node][i] = nums[i];
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
  
    public int nextInt() { 
      return Integer.parseInt(next()); 
    } 
  
    public long nextLong() { 
      return Long.parseLong(next()); 
    } 
  
    public double nextDouble() { 
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