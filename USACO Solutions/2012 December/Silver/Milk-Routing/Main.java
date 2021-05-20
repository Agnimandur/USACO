import java.util.*;
import java.io.*;

class Main {
  public static void main(String[] args) throws IOException {
    InputStream is = new FileInputStream("mroute.in");
    PrintWriter pw = new PrintWriter(new BufferedWriter(new FileWriter("mroute.out")));
    FastScanner sc = new FastScanner(is);

    int N = sc.nextInt();
    int M = sc.nextInt();
    double X = sc.nextDouble();
    int[][][] adj = new int[N][N][2];
    TreeSet<Integer> capacities = new TreeSet<Integer>();
    for (int i = 0; i < M; i++) {
      int n1 = sc.nextInt()-1;
      int n2 = sc.nextInt()-1;
      int L = sc.nextInt();
      int C = sc.nextInt();
      adj[n1][n2] = new int[]{L,C};
      adj[n2][n1] = new int[]{L,C};
      capacities.add(C);
    }
    int U = capacities.size();
    int[] caps = new int[U];
    int index = 0;
    HashMap<Integer,Integer> capacityToIndex = new HashMap<Integer,Integer>();
    for (int cap: capacities) {
      caps[index] = cap;
      capacityToIndex.put(cap,index);
      index++;
    }
    ArrayList<Edge>[] graph = new ArrayList[N*U];
    for (int i = 0; i < N*U; i++)
      graph[i]  = new ArrayList<Edge>();
    
    for (int i = 0; i < N; i++) {
      for (int j = 0; j < N; j++) {
        if (adj[i][j][0] > 0) {
          int L = adj[i][j][0];
          int C = adj[i][j][1];
          int I = capacityToIndex.get(C);
          for (int n1 = U*i; n1 < U*i+U; n1++) {
            int ind = Math.min(n1-U*i,I);
            graph[n1].add(new Edge(j*U+ind,L));
          }
        }
      }
    }
    for (int i = 0; i < N*U; i++) {
      if (i % U != 0) {
        graph[i].add(new Edge(i-1,0));
      }
    }

    //Begin at node U-1.
    PriorityQueue<int[]> pq = new PriorityQueue<int[]>(new Comparator<int[]>(){
      public int compare(int[] a, int[] b) {
        return a[1] - b[1];
      }
    });
    int[] distances = new int[N*U];
    Arrays.fill(distances,Integer.MAX_VALUE);
    distances[U-1] = 0;
    //Dijkstra's Algorithm
    int[] temp = {U-1,0};
    pq.add(temp);
    while (! pq.isEmpty()) {
      temp = pq.poll();
      int parent = temp[0];
      for (Edge e: graph[parent]) {
        int child = e.to;
        if (distances[child] > distances[parent] + e.latency) {
          distances[child] = distances[parent] + e.latency;
          int[] temp2 = {child,distances[child]};
          pq.add(temp2);
        }
      }
    }
    double ans = Double.MAX_VALUE;
    for (int i = 0; i < U; i++) {
      int L = distances[(N-1)*U+i];
      int C = caps[i];
      if (L < Integer.MAX_VALUE) {
        ans = Math.min(ans,X/C + L);
      }
    }

    pw.println((int)ans);
    pw.close();
  }
  static class Edge {
    int to;
    int latency;
    public Edge(int n, int l) {
      to = n;
      latency = l;
    }

    public String toString() {
      return ("["+to+","+latency+"]");
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