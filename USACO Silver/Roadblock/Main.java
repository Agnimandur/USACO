import java.util.*;
import java.math.*;
import java.io.*;

class Main {
  static int N;
  static int M;
  static int[][] adj;
  static ArrayList<Integer>[] graph;
  static ArrayList<Integer> PATH;
  public static void main(String[] args) throws IOException {
    InputStream is = new FileInputStream("rblock.in");
    PrintWriter pw = new PrintWriter(new BufferedWriter(new FileWriter("rblock.out")));
    FastScanner sc = new FastScanner(is);
    N = sc.nextInt();
    M = sc.nextInt();
    adj = new int[N][N];
    graph = new ArrayList[N];
    for (int i = 0; i < N; i++)
      graph[i] = new ArrayList<Integer>();
    for (int i = 0; i < M; i++) {
      int a = sc.nextInt()-1;
      int b = sc.nextInt()-1;
      int w = sc.nextInt();
      adj[a][b] = w;
      adj[b][a] = w;
      graph[a].add(b);
      graph[b].add(a);
    }
    PATH = new ArrayList<Integer>();
    int DIST = dijkstra(true);
    int len = PATH.size()-1;
    int increase = 0;
    for (int i = 0; i < len; i++) {
      int a = PATH.get(i);
      int b = PATH.get(i+1);
      adj[a][b] *= 2;
      adj[b][a] *= 2;
      int dist = dijkstra(false);
      increase = Math.max(increase,dist-DIST);
      adj[a][b] /= 2;
      adj[b][a] /= 2;
    }
    System.out.println(increase);
    pw.println(increase);
    pw.close();
  }

  public static int dijkstra(boolean findPath) {
    PriorityQueue<int[]> pq = new PriorityQueue<int[]>(new Comparator<int[]>(){
      public int compare(int[] a, int[] b) {
        return a[1] - b[1];
      }
    });
    int[] distances = new int[N];
    int[] previousNode = new int[N];
    Arrays.fill(distances,Integer.MAX_VALUE);
    distances[0] = 0;
    Arrays.fill(previousNode,-1);

    int[] temp = {0,0};
    pq.add(temp);
    while (! pq.isEmpty()) {
      temp = pq.poll();
      int parent = temp[0];
      for (int child: graph[parent]) {
        if (distances[child] > distances[parent] + adj[parent][child]) {
          previousNode[child] = parent;
          distances[child] = distances[parent] + adj[parent][child];
          int[] temp2 = {child,distances[child]};
          pq.add(temp2);
        }
      }
    }
    if (findPath == true) {
      int field = N-1;
      while (field >= 0) {
        PATH.add(field);
        field = previousNode[field];
      }
      Collections.reverse(PATH);
    }
    return distances[N-1];
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