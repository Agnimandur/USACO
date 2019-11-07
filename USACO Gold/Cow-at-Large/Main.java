import java.util.*;
import java.io.*;

class Main {
  public static void main(String[] args) throws IOException {
    InputStream is = new FileInputStream("atlarge.in");
    PrintWriter pw = new PrintWriter(new BufferedWriter(new FileWriter("atlarge.out")));
    FastScanner sc = new FastScanner(is);
    int N = sc.nextInt();
    int K = sc.nextInt()-1;
    ArrayList<Integer>[] tree = new ArrayList[N];
    for (int i = 0; i < N; i++)
      tree[i] = new ArrayList<Integer>();
    for (int i = 0; i < N-1; i++) {
      int x = sc.nextInt()-1;
      int y = sc.nextInt()-1;
      tree[x].add(y);
      tree[y].add(x);
    }

    int[] distToRoot = new int[N];
    Arrays.fill(distToRoot,Integer.MAX_VALUE);
    distToRoot[K] = 0;
    Stack<Integer> dfs = new Stack<Integer>();
    dfs.push(K);
    while (! dfs.isEmpty()) {
      int node = dfs.pop();
      for (int neighbor: tree[node]) {
        if (distToRoot[neighbor] == Integer.MAX_VALUE) {
          distToRoot[neighbor] = distToRoot[node]+1;
          dfs.push(neighbor);
        }
      }
    }
    //System.out.println(Arrays.toString(distToRoot));
    int[] distToLeaf = new int[N];
    Arrays.fill(distToLeaf,Integer.MAX_VALUE);
    Queue<Integer> bfs = new LinkedList<Integer>();
    for (int i = 0; i < N; i++) {
      if (tree[i].size() == 1) {
        distToLeaf[i] = 0;
        bfs.add(i);
      }
    }

    while (! bfs.isEmpty()) {
      int node = bfs.remove();
      for (int neighbor: tree[node]) {
        if (distToLeaf[neighbor] == Integer.MAX_VALUE) {
          distToLeaf[neighbor] = distToLeaf[node]+1;
          bfs.add(neighbor);
        }
      }
    }
    //System.out.println(Arrays.toString(distToLeaf));
    boolean[] unsafe = new boolean[N];
    //if the distance Bessie needs to reach node i is greater than or equal to the distance that a farmer needs to reach that same node, then node i is "unsafe".
    for (int i = 0; i < N; i++) {
      if (distToRoot[i] >= distToLeaf[i])
        unsafe[i] = true;
    }

    //Run a BFS from the root. Whenever you hit an unsafe spot, you know you need one farmer.
    int ans = 0;
    if (! unsafe[K]) {
      boolean[] visited = new boolean[N];
      visited[K] = true;
      bfs.add(K);
      //Queue<Integer> bfs = new LinkedList<Integer>();
      while (! bfs.isEmpty()) {
        int node = bfs.remove();
        for (int neighbor: tree[node]) {
          if (!visited[neighbor]) {
            visited[neighbor] = true;
            if (unsafe[neighbor])
              ans++;
            else
              bfs.add(neighbor);
          }
        }
      }
    } else {
      ans = 1;
    }

    pw.println(ans);
    pw.close();
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