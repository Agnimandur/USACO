import java.util.*;
import java.io.*;

class replication {
  static final int INF = 1000000000;
  static final int[][] dirs = {{-1,0},{1,0},{0,1},{0,-1}};
  public static void main(String[] args) throws IOException {
    PrintWriter pw = new PrintWriter(System.out);
    FastScanner sc = new FastScanner(System.in);

    int N = sc.ni();
    long D = sc.nl();
    char[][] grid = new char[N][N];
    int[][] wallDist = new int[N][N];
    int[][] travel = new int[N][N];
    ArrayDeque<int[]> bfs = new ArrayDeque<int[]>();
    for (int i = 0; i < N; i++) {
      String row = sc.next();
      for (int j = 0; j < N; j++) {
        grid[i][j] = row.charAt(j);
        if (grid[i][j]=='#')
          bfs.add(new int[]{i,j});
        else 
          wallDist[i][j] = INF;
        travel[i][j] = INF;
      }
    }

    //find the distance of each cell to the nearest wall
    while (!bfs.isEmpty()) {
      int[] cell = bfs.pollFirst();
      for (int[] dir: dirs) {
        int newR = cell[0]+dir[0];
        int newC = cell[1]+dir[1];
        if (newR >= 0 && newC >= 0 && newR < N && newC < N && wallDist[newR][newC] == INF) {
          wallDist[newR][newC] = wallDist[cell[0]][cell[1]]+1;
          bfs.add(new int[]{newR,newC});
        }
      }
    }


    //simulate the motion
    for (int i = 0; i < N; i++) {
      for (int j = 0; j < N; j++) {
        if (grid[i][j]=='S') {
          bfs.add(new int[]{i,j});
          travel[i][j] = 0;
        }
      }
    }

    while (!bfs.isEmpty()) {
      int[] cell = bfs.pollFirst();
      if (travel[cell[0]][cell[1]]==D*wallDist[cell[0]][cell[1]]) {
        travel[cell[0]][cell[1]] -= 1;
        continue;
      }
      for (int[] dir: dirs) {
        int newR = cell[0]+dir[0];
        int newC = cell[1]+dir[1];
        int newT = travel[cell[0]][cell[1]]+1;
        if (newR >= 0 && newC >= 0 && newR < N && newC < N && travel[newR][newC] == INF && newT <= D*wallDist[newR][newC]) {
          travel[newR][newC] = newT;
          bfs.add(new int[]{newR,newC});
        }
      }
    }

    boolean[][] vis = new boolean[N][N];
    PriorityQueue<int[]> pq = new PriorityQueue<int[]>(new Comparator<int[]>() {
      @Override
      public int compare(int[] a, int[] b) {
        return b[2]-a[2];
      }
    });
    for (int i = 0; i < N; i++) {
      for (int j = 0; j < N; j++) {
        if (travel[i][j] < INF) {
          pq.add(new int[]{i,j,(int)(travel[i][j]/D)});
          vis[i][j] = true;
        }
      }
    }
    while (!pq.isEmpty()) {
      int[] res = pq.poll();
      if (res[2]==0) continue;
      for (int[] dir: dirs) {
        int newR = res[0]+dir[0];
        int newC = res[1]+dir[1];
        if (!vis[newR][newC]) {
          vis[newR][newC] = true;
          pq.add(new int[]{newR,newC,res[2]-1});
        }
      }
    }

    int ans = 0;
    for (int i = 0; i < N; i++) {
      for (int j = 0; j < N; j++) {
        if (vis[i][j]) ans++;
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