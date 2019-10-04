import java.util.*;
import java.math.*;
import java.io.*;

class Main {
  public static void main(String[] args) throws IOException {
    InputStream is = new FileInputStream("visitfj.in");
    PrintWriter pw = new PrintWriter(new BufferedWriter(new FileWriter("visitfj.out")));
    FastScanner sc = new FastScanner(is);
    int N = sc.nextInt();
    int T = sc.nextInt();
    int[][][][] graph = new int[N][N][16][3];
    int[][] edges = {{-3,0},{-2,1},{-1,2},{0,3},{1,2},{2,1},{3,0},{2,-1},{1,-2},{0,-3},{-1,-2},{-2,-1},{-1,0},{0,1},{1,0},{0,-1}};
    int[][] grass = new int[N][N];
    for (int i = 0; i < N; i++) {
      for (int j = 0; j < N; j++) {
        grass[i][j] = sc.nextInt();
      }
    }
    for (int i = 0; i < N; i++) {
      for (int j = 0; j < N; j++) {
        for (int k = 0; k < 16; k++)
          Arrays.fill(graph[i][j][k],-1);
      }
    }

    for (int i = 0; i < N; i++) {
      for (int j = 0; j < N; j++) {
        if (i+j == 2*N-2) {
          continue;
        } else if (i+j == 2*N-3) {
          graph[i][j][0] = new int[]{N-1,N-1,T};
          continue;
        } else if (i+j == 2*N-4) {
          graph[i][j][0] = new int[]{N-1,N-1,2*T};
          continue;
        }
        for (int edge = 0; edge < 16; edge++) {
          int edgeI = i+edges[edge][0];
          int edgeJ = j+edges[edge][1];
          if (edgeI >= 0 && edgeJ >= 0 && edgeI < N && edgeJ < N) {
            graph[i][j][edge] = new int[]{edgeI,edgeJ,grass[edgeI][edgeJ]+3*T};
          }
        }
      }
    }

    //Dijkstra's Shortest Path Algorithm
    PriorityQueue<int[]> pq = new PriorityQueue<int[]>(new Comparator<int[]>(){
      public int compare(int[] a, int[] b) {
        return a[2] - b[2];
      }
    });
    int[][] distances = new int[N][N];
    for (int i = 0; i < N; i++)
      Arrays.fill(distances[i],Integer.MAX_VALUE);
    distances[0][0] = 0;
    int[] temp = {0,0,0};
    pq.add(temp);
    while (! pq.isEmpty()) {
      temp = pq.poll();
      int row = temp[0];
      int col = temp[1]; 
      for (int[] edge: graph[row][col]) {
        if (edge[0] >= 0 && distances[edge[0]][edge[1]] > distances[row][col] + edge[2]) {
          distances[edge[0]][edge[1]] = distances[row][col] + edge[2];
          int[] add = {edge[0],edge[1],distances[edge[0]][edge[1]]};
          pq.add(add);
        }
      }
    }
    //System.out.println(distances[N-1][N-1]);
    pw.println(distances[N-1][N-1]);
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