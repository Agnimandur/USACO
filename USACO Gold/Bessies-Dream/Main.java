import java.util.*;
import java.io.*;

class Main {
  static int N;
  static int M;
  static int[][] maze;
  static int[][] dirs;
  static int[][] slideTo;

  public static void main(String[] args) throws IOException {
    InputStream is = new FileInputStream("dream.in");
    PrintWriter pw = new PrintWriter(new BufferedWriter(new FileWriter("dream.out")));
    FastScanner sc = new FastScanner(is);
    N = sc.nextInt();
    M = sc.nextInt();
    maze = new int[N][M];
    for (int i = 0; i < N; i++) {
      for (int j = 0; j < M; j++) {
        maze[i][j] = sc.nextInt();
      }
    }

    //up,right,down,left
    dirs = new int[][]{{-1,0},{0,1},{1,0},{0,-1}};
    slideTo = new int[2*N*M][4]; //store the distance that you slide in each of the four directions
    for (int i = 0; i < 2*N*M; i++)
      Arrays.fill(slideTo[i],-1);

    for (int s = 0; s < 2; s++) {
      for (int i = 0; i < N; i++) {
        for (int j = 0; j < M; j++) {
          for (int d = 0; d < 4; d++) {
            int node = N*M*s+M*i+j;
            if (slideTo[node][d]==-1)
              slideTo[node][d] = getSlideTo(s,i,j,d);
          }
        }
      }
    }
    //System.out.println(Arrays.deepToString(slideTo));
    
    //Let node (M*i+j) correspond to cell (i,j) with no smell, and let node (N*M+M*i+j) correspond to cell (i,j) with a smell
    ArrayList<Edge>[] graph = new ArrayList[2*N*M];
    for (int i = 0; i < 2*N*M; i++) {
      graph[i] = new ArrayList<Edge>();
    }

    for (int s = 0; s < 2; s++) {
      for (int i = 0; i < N; i++) {
        for (int j = 0; j < M; j++) {
          if (maze[i][j]==0 || (maze[i][j]==2&&s==0) || (maze[i][j]==3 && s==0) || (maze[i][j]==4 && s==1)) {
            continue;
          }
          int node = s*N*M+i*M+j;
          //Consider moving up
          if (slideTo[node][0] != node) {
            int dist = ((node%(N*M))-(slideTo[node][0]%(N*M)))/M;
            graph[node].add(new Edge(slideTo[node][0],dist));
          }
          //Consider moving right
          if (slideTo[node][1] != node) {
            int dist = ((slideTo[node][1]%(N*M))-(node%(N*M)));
            graph[node].add(new Edge(slideTo[node][1],dist));
          }
          //Consider moving down
          if (slideTo[node][2] != node) {
            int dist = ((slideTo[node][2]%(N*M))-(node%(N*M)))/M;
            graph[node].add(new Edge(slideTo[node][2],dist));
          }
          //Consider moving left
          if (slideTo[node][3] != node) {
            int dist = ((node%(N*M))-(slideTo[node][3]%(N*M)));
            graph[node].add(new Edge(slideTo[node][3],dist));
          }
        }
      }
    }

    //Dijkstra to find the shortest path to node N*M-1 and node 2*N*M-1.
    PriorityQueue<int[]> pq = new PriorityQueue<int[]>(new Comparator<int[]>(){
      public int compare(int[] a, int[] b) {
        return a[1] - b[1];
      }
    });
    int[] distances = new int[2*N*M];
    Arrays.fill(distances,Integer.MAX_VALUE/2);
    distances[0]=0;
    
    int[] temp = {0,0};
    pq.add(temp);
    while (! pq.isEmpty()) {
      temp = pq.poll();
      int parent = temp[0];
      for (Edge e: graph[parent]) {
        if (distances[e.to] > distances[parent] + e.weight) {
          distances[e.to] = distances[parent] + e.weight;
          int[] temp2 = {e.to,distances[e.to]};
          pq.add(temp2);
        }
      }
    }
    int ans = Math.min(distances[N*M-1],distances[2*N*M-1]);
    if (ans == Integer.MAX_VALUE/2) {
      pw.println(-1);
    } else {
      pw.println(ans);
    }
    pw.close();
  }

  static class Edge {
    public int to;
    public int weight;
    public Edge(int t, int w) {
      to = t;
      weight = w;
    }

    public String toString() {
      return ("("+to+","+weight+")");
    }
  }

  public static int getSlideTo(int s, int i, int j, int d) {
    int node = s*N*M+i*M+j;
    if (slideTo[node][d] >= 0) {
      //Already computed the answer
      return slideTo[node][d];
    }
    int newR = i+dirs[d][0];
    int newC = j+dirs[d][1];
    if (0<=newR && newR<N && 0<=newC && newC<M && maze[newR][newC]!=0 && (s==1 || maze[newR][newC]!=3)) {
      //If the slide destination is within the maze, and your destination isn't a red tile, and either you don't smell or your destination isn't a blue tile, then you can try and slide!
      if (maze[newR][newC]==4) {
        //Keep sliding, and stop smelling
        slideTo[node][d] = getSlideTo(0,newR,newC,d);
      } else if (maze[newR][newC]==2) {
        //Stop sliding, and start smelling
        slideTo[node][d] = N*M+newR*M+newC;
      } else {
        //Stop sliding, and don't change how you smelt
        slideTo[node][d] = s*N*M+newR*M+newC;
      }
    } else {
      //Can't slide
      slideTo[node][d] = s*N*M+i*M+j;
    }
    return slideTo[node][d];
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