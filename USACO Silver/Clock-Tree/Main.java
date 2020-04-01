import java.util.*;
import java.io.*;

class Main {
  public static void main(String[] args) throws IOException {
    InputStream is = new FileInputStream("clocktree.in");
    PrintWriter pw = new PrintWriter(new BufferedWriter(new FileWriter("clocktree.out")));
    FastScanner sc = new FastScanner(is);

    int N = sc.ni();
    int[] clock = new int[N];
    for (int i = 0; i < N; i++)
      clock[i] = sc.ni()-1;
    
    ArrayList<Integer>[] tree = new ArrayList[N];
    for (int i = 0; i < N; i++)
      tree[i] = new ArrayList<Integer>();
    for (int i = 0; i < N-1; i++) {
      int n1 = sc.ni()-1;
      int n2 = sc.ni()-1;
      tree[n1].add(n2);
      tree[n2].add(n1);
    }

    int ans = 0;
    for (int root = 0; root < N; root++) {
      int[] copy = Arrays.copyOf(clock,N);
      int[] parent = new int[N];
      Arrays.fill(parent,Integer.MIN_VALUE);
      parent[root] = -1;
      ArrayDeque<Integer> bfs = new ArrayDeque<Integer>();
      bfs.add(root);
      ArrayList<Integer> leaves = new ArrayList<Integer>();
      int[] children = new int[N];
      while (! bfs.isEmpty()) {
        int node = bfs.poll();
        int numChildren = 0;
        for (int next: tree[node]) {
          if (parent[next] == Integer.MIN_VALUE) {
            parent[next] = node;
            numChildren++;
            bfs.add(next);
          }
        }
        children[node] = numChildren;
        if (numChildren == 0)
          leaves.add(node);
      }

      //Now trace back up the tree, updating the clock as you go.
      for (int leaf: leaves)
        bfs.add(leaf);
      
      while (! bfs.isEmpty()) {
        int node = bfs.poll();
        if (node == root) break;
        copy[parent[node]] = (copy[parent[node]]+11-copy[node])%12;
        copy[node] = 11;
        children[parent[node]] -= 1;
        if (children[parent[node]] == 0) {
          //this was the last child. now move up.
          bfs.add(parent[node]);
        }
      }

      if (copy[root] == 0 || copy[root] == 11) {
        ans++;
      }
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