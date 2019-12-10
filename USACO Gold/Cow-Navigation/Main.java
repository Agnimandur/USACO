import java.util.*;
import java.io.*;

class Main {
  public static void main(String[] args) throws IOException {
    InputStream is = new FileInputStream("cownav.in");
    PrintWriter pw = new PrintWriter(new BufferedWriter(new FileWriter("cownav.out")));
    FastScanner sc = new FastScanner(is);
    int[][] dirs = {{-1,0},{0,1},{1,0},{0,-1}}; //NESW
    int N = sc.nextInt();
    boolean[][] grid = new boolean[N][N];
    for (int i = 0; i < N; i++) {
      String row = sc.next();
      for (int j = 0; j < N; j++) {
        if (row.charAt(j)=='H') {
          grid[i][j] = true;
        }
      }
    }

    int[][][][][][] dist = new int[N][N][4][N][N][4]; //(a,b) is first possible point, c is direction of first point, (d,e) is second possible point, f is direction of second point
    for (int a = 0; a < N; a++) {
      for (int b = 0; b < N; b++) {
        for (int c = 0; c < 4; c++) {
          for (int d = 0; d < N; d++) {
            for (int e = 0; e < N; e++) {
              for (int f = 0; f < 4; f++) {
                dist[a][b][c][d][e][f] = Integer.MAX_VALUE/2;
              }
            }
          }
        }
      }
    }
    //initial two points
    dist[N-1][0][0][N-1][0][1] = 0;
    ArrayDeque<int[]> bfs = new ArrayDeque<int[]>();
    int[] root = {N-1,0,0,N-1,0,1};
    bfs.add(root);
    int ans = Integer.MAX_VALUE;
    while (! bfs.isEmpty()) {
      int[] state = bfs.pollFirst();
      int D = dist[state[0]][state[1]][state[2]][state[3]][state[4]][state[5]];
      if (state[0]==0&&state[1]==N-1&&state[3]==0&&state[4]==N-1) {
        ans = D;
        break;
      }

      int[] turnLeft = new int[]{state[0],state[1],(state[2]+3)%4,state[3],state[4],(state[5]+3)%4};
      if (dist[turnLeft[0]][turnLeft[1]][turnLeft[2]][turnLeft[3]][turnLeft[4]][turnLeft[5]]==Integer.MAX_VALUE/2) {
        dist[turnLeft[0]][turnLeft[1]][turnLeft[2]][turnLeft[3]][turnLeft[4]][turnLeft[5]]=D+1;
        bfs.add(turnLeft);
      }

      int[] turnRight = new int[]{state[0],state[1],(state[2]+1)%4,state[3],state[4],(state[5]+1)%4};
      if (dist[turnRight[0]][turnRight[1]][turnRight[2]][turnRight[3]][turnRight[4]][turnRight[5]]==Integer.MAX_VALUE/2) {
        dist[turnRight[0]][turnRight[1]][turnRight[2]][turnRight[3]][turnRight[4]][turnRight[5]]=D+1;
        bfs.add(turnRight);
      }

      int[] forward = new int[]{state[0]+dirs[state[2]][0],state[1]+dirs[state[2]][1],state[2],state[3]+dirs[state[5]][0],state[4]+dirs[state[5]][1],state[5]};
      //No movement conditions (already at end, or off map, or into haybales)
      if ((state[0]==0&&state[1]==N-1)||forward[0]<0||forward[0]>=N||forward[1]<0||forward[1]>=N||grid[forward[0]][forward[1]]) {
        forward[0] = state[0];
        forward[1] = state[1];
        forward[2] = state[2];
      }
      if ((state[3]==0&&state[4]==N-1)||forward[3]<0||forward[3]>=N||forward[4]<0||forward[4]>=N||grid[forward[3]][forward[4]]) {
        forward[3] = state[3];
        forward[4] = state[4];
        forward[5] = state[5];
      }

      if (dist[forward[0]][forward[1]][forward[2]][forward[3]][forward[4]][forward[5]]==Integer.MAX_VALUE/2) {
        dist[forward[0]][forward[1]][forward[2]][forward[3]][forward[4]][forward[5]]=D+1;
        bfs.add(forward);
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