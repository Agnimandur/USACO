import java.util.*;
import java.io.*;

class Main {
  public static void main(String[] args) throws IOException {
    InputStream is = new FileInputStream("piggyback.in");
    PrintWriter pw = new PrintWriter(new BufferedWriter(new FileWriter("piggyback.out")));
    FastScanner sc = new FastScanner(is);
    int B = sc.ni();
    int E = sc.ni();
    int P = sc.ni();
    int N = sc.ni();
    int M = sc.ni();
    ArrayList<Integer>[] graph = new ArrayList[N];
    for (int i = 0 ; i < N; i++) {
      graph[i] = new ArrayList<Integer>();
    }
    for (int i = 0; i < M; i++) {
      int n1 = sc.ni()-1;
      int n2 = sc.ni()-1;
      graph[n1].add(n2);
      graph[n2].add(n1);
    }

    //In the following three BFSes, we calculate the distance of bessie, elsie, and the farm to all other cells. This will allow us to quickly find the cost of a rondezvous.

    int[] bessieDist = new int[N];
    Arrays.fill(bessieDist,Integer.MAX_VALUE);
    bessieDist[0] = 0;
    ArrayDeque<Integer> bfs = new ArrayDeque<Integer>();
    bfs.add(0);
    while (! bfs.isEmpty()) {
      int node = bfs.pollFirst();
      for (int neighbor: graph[node]) {
        if (bessieDist[neighbor] == Integer.MAX_VALUE) {
          bessieDist[neighbor] = bessieDist[node]+1;
          bfs.add(neighbor);
        }
      }
    }

    int[] elsieDist = new int[N];
    Arrays.fill(elsieDist,Integer.MAX_VALUE);
    elsieDist[1] = 0;
    //bfs is empty
    bfs.add(1);
    while (! bfs.isEmpty()) {
      int node = bfs.pollFirst();
      for (int neighbor: graph[node]) {
        if (elsieDist[neighbor] == Integer.MAX_VALUE) {
          elsieDist[neighbor] = elsieDist[node]+1;
          bfs.add(neighbor);
        }
      }
    }

    int[] endDist = new int[N];
    Arrays.fill(endDist,Integer.MAX_VALUE);
    endDist[N-1] = 0;
    //bfs is empty
    bfs.add(N-1);
    while (! bfs.isEmpty()) {
      int node = bfs.pollFirst();
      for (int neighbor: graph[node]) {
        if (endDist[neighbor] == Integer.MAX_VALUE) {
          endDist[neighbor] = endDist[node]+1;
          bfs.add(neighbor);
        }
      }
    }

    int ans = Integer.MAX_VALUE;
    for (int i = 0; i < N; i++) {
      //Let bessie and elsie rondezvous at node i, and then continue together to node n-1.
      int cost = B*bessieDist[i]+E*elsieDist[i]+P*endDist[i];
      ans = Math.min(ans,cost);
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