import java.util.*;
import java.io.*;

class Main {
  public static int index;
  public static int time;
  public static int[] parent;
  public static int[] low;
  public static int[] disc;
  public static ArrayDeque<Edge> stack;

  public static int[][] blocked;
  public static int[][] graph;
  public static int[][] components;
  public static int size;
  public static void main(String[] args) throws IOException {
    InputStream is = new FileInputStream("pushabox.in");
    PrintWriter pw = new PrintWriter(new BufferedWriter(new FileWriter("pushabox.out")));
    FastScanner sc = new FastScanner(is);
    long t1 = System.currentTimeMillis();
    int N = sc.ni();
    int M = sc.ni();
    size = N*M;
    int Q = sc.ni();
    blocked = new int[N][M];
    int A = -1;
    int B = -1;

    for (int i = 0; i < N; i++) {
      String row = sc.next();
      for (int j = 0; j < M; j++) {
        char c = row.charAt(j);
        if (c == '#') {
          blocked[i][j] = 3;
        } else if (c == 'B') {
          B = M*i+j;
          blocked[i][j] = 2;
        } else if (c == 'A') {
          A = M*i+j;
          blocked[i][j] = 1;
        }
      }
    }

    int[][] dirs = {{0,-1},{0,1},{-1,0},{1,0}};

    //Build the graph from the input
    graph = new int[size][5];
    for (int i = 0; i < N; i++) {
      for (int j = 0; j < M; j++) {
        if (blocked[i][j] == 3) continue;
        for (int[] dir: dirs) {
          int newR = i+dir[0];
          int newC = j+dir[1];
          if (newR >= 0 && newC >= 0 && newR < N && newC < M && blocked[newR][newC] <= 2) {
            graph[M*i+j][graph[M*i+j][4]] = (M*newR+newC);
            graph[M*i+j][4] += 1;
          }
        }
      }
    }

    long t2 = System.currentTimeMillis();
    //Run Tarjan's algorithm
    tarjan();
    long t3 = System.currentTimeMillis();

    //check what positions A can begin in (next to B);
    boolean[] reachable = new boolean[size];
    reachable[A] = true;
    
    ArrayDeque<Integer> b = new ArrayDeque<Integer>();
    b.add(A);
    while (! b.isEmpty()) {
      int node = b.poll(); //M*row+col;
      int row = node/M;
      int col = node%M;
      for (int[] dir: dirs) {
        int newR = row+dir[0];
        int newC = col+dir[1];
        int newNode = M*newR+newC;
        if (newR >= 0 && newR < N && newC > 0 && newC < M && blocked[newR][newC] == 0 && !reachable[newNode]) {
          reachable[newNode] = true;
          b.add(newNode);
        }
      }
    }

    //Run final bfs
    boolean[] push = new boolean[4*size];
    int Brow = B/M;
    int Bcol = B%M;
    //Find what positions the box can be pushed to with a quick multisource bfs.
    ArrayDeque<Integer> bfs = new ArrayDeque<Integer>();
    for (int j = 0; j < 4; j++) {
      int Arow = Brow+dirs[j][0];
      int Acol = Bcol+dirs[j][1];
      if (Arow >= 0 && Arow < N && Acol >= 0 && Acol < M && reachable[Arow*M+Acol]) {
        push[j*size+B] = true;
        bfs.add(j*size+B);
      }
    }

    while (!bfs.isEmpty()) {
      int node = bfs.poll();
      int i = node%size;
      int j = node/size;

      int oldRowB = i/M;
      int oldColB = i%M;
      int oldRowA = oldRowB+dirs[j][0];
      int oldColA = oldColB+dirs[j][1];

      for (int k = 0; k < 4; k++) {
        if (j==k) {
          //just push
          int newRowB = oldRowB - dirs[j][0];
          int newColB = oldColB - dirs[j][1];
          int newN = newRowB*M+newColB;
          if (newRowB >= 0 && newRowB < N && newColB >= 0 && newColB < M && blocked[newRowB][newColB] <= 2 && !push[k*size+newN]) {
            push[k*size+newN] = true;
            bfs.add(k*size+newN);
          }
        } else {
          //check components to see if you can maneuver to the correct position
          int newRowA = oldRowB + dirs[k][0];
          int newColA = oldColB + dirs[k][1];
          if (newRowA >= 0 && newRowA < N && newColA >= 0 && newColA < M && blocked[newRowA][newColA] <= 2 && biconnected(components[oldRowA*M+oldColA],components[newRowA*M+newColA]) && !push[k*size+i]) {
            push[k*size+i] = true;
            bfs.add(k*size+i);
          }
        }
      }
    }

    //process queries
    for (int q = 0; q < Q; q++) {
      int row = sc.ni()-1;
      int col = sc.ni()-1;
      int node = M*row+col;
      boolean good = false;
      if (node==B)
        good = true;
      for (int j = 0; j < 4; j++) {
        if (push[size*j+node]) {
          good = true;
          break;
        }
      }
      if (good)
        pw.println("YES");
      else
        pw.println("NO");
    }

    long t4 = System.currentTimeMillis();

    System.out.println(t2-t1); //read input and initialize the graph
    System.out.println(t3-t2); //run Tarjan's Algorithm
    System.out.println(t4-t3); //run the bfs of all the states *MAIN POINT OF TLE!!!*
    pw.close();
  }

  public static boolean biconnected(int[] a, int[] b) {
    for (int i = 0; i < a[4]; i++) {
      for (int j = 0; j < b[4]; j++) {
        if (a[i]==b[j])
          return true;
      }
    }
    return false;
  }

  public static void BCCUtil(int u) {
    // Initialize discovery time and low value 
    disc[u] = low[u] = ++time; 
    int children = 0; 
    for (int i = 0; i < graph[u][4]; i++) {
      int v = graph[u][i];
      if (disc[v] == -1) {
        children++;
        parent[v] = u;

        stack.add(new Edge(u, v));
        BCCUtil(v);
        low[u] = Math.min(low[u],low[v]);

        // pop all edges from stack till u -- v 
        if ((disc[u] == 1 && children > 1) || (disc[u] > 1 && low[v] >= disc[u])) {
          while (stack.getLast().u != u || stack.getLast().v != v) {
            int n1 = stack.getLast().u;
            int n2 = stack.getLast().v;
            components[n1][components[n1][4]] = index;
            components[n1][4] += 1;
            components[n2][components[n2][4]] = index;
            components[n2][4] += 1;
            stack.removeLast(); 
          }
          int n1 = stack.getLast().u;
          int n2 = stack.getLast().v;
          components[n1][components[n1][4]] = index;
          components[n1][4] += 1;
          components[n2][components[n2][4]] = index;
          components[n2][4] += 1;
          index++;
          stack.removeLast();
        }
      } else if (v != parent[u] && disc[v] < disc[u] ) {
        low[u] = Math.min(low[u],disc[v]);
        stack.add(new Edge(u, v)); 
      }
    }
  }
  
  public static void tarjan() {
    disc = new int[size]; 
    low = new int[size]; 
    parent = new int[size];
    Arrays.fill(disc,-1);
    Arrays.fill(low,-1);
    Arrays.fill(parent,-1);
    stack = new ArrayDeque<Edge>();
    components = new int[size][5];
    time = 0;
    index = 1;

    for (int i = 0; i < size; i++) {
      if (graph[i][4] == 0) continue;
      if (disc[i] == -1) 
        BCCUtil(i);
      if (stack.isEmpty()) continue;

      while (!stack.isEmpty()) {
        int n1 = stack.getLast().u;
        int n2 = stack.getLast().v;
        components[n1][components[n1][4]] = index;
        components[n1][4] += 1;
        components[n2][components[n2][4]] = index;
        components[n2][4] += 1;
        stack.removeLast(); 
      }
      index++;
    }
  }

  static class Edge {
    public int u;
    public int v;
    public Edge (int n1, int n2) {
      u = n1;
      v = n2;
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