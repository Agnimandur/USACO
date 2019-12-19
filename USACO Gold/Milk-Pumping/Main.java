import java.util.*;
import java.io.*;

class Main {
  public static void main(String[] args) throws IOException {
    InputStream is = new FileInputStream("pump.in");
    PrintWriter pw = new PrintWriter(new BufferedWriter(new FileWriter("pump.out")));
    FastScanner sc = new FastScanner(is);

    int N = sc.nextInt();
    int M = sc.nextInt();
    ArrayList<Edge>[] graph = new ArrayList[N];
    for (int i = 0; i < N; i++) {
      graph[i] = new ArrayList<Edge>();
    }
    for (int i = 0; i < M; i++) {
      int n1 = sc.nextInt()-1;
      int n2 = sc.nextInt()-1;
      int c = sc.nextInt();
      int f = sc.nextInt();
      graph[n1].add(new Edge(n2,c,f));
      graph[n2].add(new Edge(n1,c,f));
    }

    double ans = 0.0;

    for (int minF = 1; minF <= 1000; minF++) {
      //Run dijkstra's
      PriorityQueue<int[]> pq = new PriorityQueue<int[]>(new Comparator<int[]>(){
        public int compare(int[] a, int[] b) {
          return a[1] - b[1];
        }
      });
      int[] distances = new int[N];
      Arrays.fill(distances,Integer.MAX_VALUE/2);
      distances[0] = 0;
      int[] temp = {0,0};
      pq.add(temp);
      while (! pq.isEmpty()) {
        temp = pq.poll();
        int parent = temp[0];
        for (Edge e: graph[parent]) {
          if (e.flow>=minF && distances[e.to] > distances[parent] + e.cost) {
            distances[e.to] = distances[parent] + e.cost;
            int[] temp2 = {e.to,distances[e.to]};
            pq.add(temp2);
          }
        }
      }

      if (distances[N-1] < Integer.MAX_VALUE) {
        ans = Math.max(ans,minF/((double)distances[N-1]));
      }
    }

    pw.println((long)(1000000*ans));
    pw.close();
  }

  static class Edge {
    public int to;
    public int cost;
    public int flow;

    public Edge(int t, int c , int f) {
      to = t;
      cost = c;
      flow = f;
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