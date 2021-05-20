import java.util.*;
import java.io.*;

class Main {
  public static void main(String[] args) throws IOException {
    InputStream is = new FileInputStream("distant.in");
    PrintWriter pw = new PrintWriter(new BufferedWriter(new FileWriter("distant.out")));
    FastScanner sc = new FastScanner(is);

    int N = sc.nextInt();
    int A = sc.nextInt();
    int B = sc.nextInt();
    boolean[][] grid = new boolean[N][N];
    for (int i = 0; i < N; i++) {
      String s = sc.next();
      for (int j = 0; j < N; j++) {
        if (s.charAt(j)=='(') {
          grid[i][j] = true;
        } else {
          grid[i][j] = false;
        }
      }
    }

    ArrayList<Edge>[] graph = new ArrayList[N*N];
    for (int i = 0; i < N*N; i++) {
      graph[i] = new ArrayList<Edge>();
    }
    for (int row = 0; row < N; row++) {
      for (int col = 0; col < N; col++) {
        int index = row*N+col;
        if (col < N-1) {
          if (grid[row][col]==grid[row][col+1]) {
            graph[index].add(new Edge(index+1,A));
          } else {
            graph[index].add(new Edge(index+1,B));
          }
        }
        if (col > 0) {
          if (grid[row][col]==grid[row][col-1]) {
            graph[index].add(new Edge(index-1,A));
          } else {
            graph[index].add(new Edge(index-1,B));
          }
        }
        if (row < N-1) {
          if (grid[row][col]==grid[row+1][col]) {
            graph[index].add(new Edge(index+N,A));
          } else {
            graph[index].add(new Edge(index+N,B));
          }
        }
        if (row > 0) {
          if (grid[row][col]==grid[row-1][col]) {
            graph[index].add(new Edge(index-N,A));
          } else {
            graph[index].add(new Edge(index-N,B));
          }
        }
      }
    }
    int ans = 0;
    for (int i = 0; i < N*N; i++) {
      //Run Dijkstra's Algorithm starting from node i.
      PriorityQueue<int[]> pq = new PriorityQueue<int[]>(new Comparator<int[]>(){
        public int compare(int[] a, int[] b) {
          return a[1] - b[1];
        }
      });
      int[] distances = new int[N*N];
      Arrays.fill(distances,Integer.MAX_VALUE);
      distances[i] = 0;
      int[] temp = {i,0};
      pq.add(temp);
      while (! pq.isEmpty()) {
        temp = pq.poll();
        int parent = temp[0];
        for (Edge e: graph[parent]) {
          if (distances[e.to] > distances[parent] + e.w) {
            distances[e.to] = distances[parent] + e.w;
            int[] temp2 = {e.to,distances[e.to]};
            pq.add(temp2);
          }
        }
      }

      int maxDist = 0;
      for (int j = 0; j < N*N; j++)
        maxDist = Math.max(maxDist,distances[j]);
      ans = Math.max(ans,maxDist);
    }

    pw.println(ans);
    pw.close();
  }

  static class Edge {
    public int to;
    public int w;
    public Edge(int to, int w) {
      this.to = to;
      this.w = w;
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