import java.util.*;
import java.io.*;

class Main {
  public static void main(String[] args) throws IOException {
    InputStream is = new FileInputStream("timeline.in");
    PrintWriter pw = new PrintWriter(new BufferedWriter(new FileWriter("timeline.out")));
    FastScanner sc = new FastScanner(is);

    int N = sc.ni();
    int M = sc.ni();
    int C = sc.ni();
    int[] days = new int[N];
    for (int i = 0; i < N; i++)
      days[i] = sc.ni();
    
    HashSet<Integer>[] parents = new HashSet[N];
    ArrayList<Edge>[] graph = new ArrayList[N];
    for (int i = 0; i < N; i++) {
      graph[i] = new ArrayList<Edge>();
      parents[i] = new HashSet<Integer>();
    }
    for (int i = 0; i < C; i++) {
      int a = sc.ni()-1;
      int b = sc.ni()-1;
      int x = sc.ni();
      graph[a].add(new Edge(b,x));
      parents[b].add(a);
    }

    ArrayDeque<Integer> bfs = new ArrayDeque<Integer>();
    //Initialize the multi-source bfs
    for (int i = 0; i < N; i++) {
      if (parents[i].isEmpty())
        bfs.add(i);
    }

    while (!bfs.isEmpty()) {
      int node = bfs.poll();
      for (Edge e: graph[node]) {
        int next = e.to;
        int w = e.weight;
        days[next] = Math.max(days[next],days[node]+w);
        parents[next].remove(node);
        if (parents[next].isEmpty())
          bfs.add(next);
      }
    }

    for (int d: days)
      pw.println(d);
    pw.close();
  }

  static class Edge {
    public int to;
    public int weight;
    
    public Edge(int t, int w) {
      to = t;
      weight = w;
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