import java.util.*;
import java.math.*;
import java.io.*;

class Main {
  static ArrayList<Integer>[] backtrack;
  static int[] cows;
  static boolean[] visited;
  public static void main(String[] args) throws IOException {
    InputStream is = new FileInputStream("shortcut.in");
    PrintWriter pw = new PrintWriter(new BufferedWriter(new FileWriter("shortcut.out")));
    FastScanner sc = new FastScanner(is);

    int N = sc.nextInt();
    int M = sc.nextInt();
    int T = sc.nextInt();
    cows = new int[N];
    Node[] graph = new Node[N];
    for (int i = 0; i < N; i++)
      graph[i] = new Node(i);
    for (int i = 0; i < N; i++)
      cows[i] = sc.nextInt();
    for (int i = 0; i < M; i++) {
      int a = sc.nextInt()-1;
      int b = sc.nextInt()-1;
      int w = sc.nextInt();
      graph[a].addChild(graph[b],w);
      graph[b].addChild(graph[a],w);
    }
    PriorityQueue<int[]> pq = new PriorityQueue<int[]>(new Comparator<int[]>(){
      public int compare(int[] a, int[] b) {
        return a[1] - b[1];
      }
    });
    int[] distances = new int[N];
    int[] previousNode = new int[N];
    Arrays.fill(previousNode,-1);
    Arrays.fill(distances,Integer.MAX_VALUE);
    distances[0] = 0;
    int[] temp = new int[]{0,0};
    pq.add(temp);
    while (! pq.isEmpty()) {
      int[] parent = pq.poll();
      for (Edge e: graph[parent[0]].neighbors) {
        int child = e.destination.n;
        if (distances[child] > distances[parent[0]]+e.weight) {
          previousNode[child] = parent[0];
          distances[child] = distances[parent[0]]+e.weight;
          int[] temp2 = {child,distances[child]};
          pq.add(temp2);
        } else if (distances[child] == distances[parent[0]] + e.weight) {
          previousNode[child] = Math.min(previousNode[child],parent[0]);
        }
      }
    }

    //Find the number of cows whose path takes them through field i.
    backtrack = new ArrayList[N];
    visited = new boolean[N];
    visited[0] = true;
    for (int i = 0; i < N; i++)
      backtrack[i] = new ArrayList<Integer>();
    for (int i = 1; i < N; i++)
      backtrack[previousNode[i]].add(i);
    rec(0);
    //System.out.println(Arrays.toString(distances));
    //System.out.println(Arrays.toString(cows));
    long ans = 0;
    for (int i = 1; i < N; i++) {
      if (distances[i] > T) {
        ans = Math.max(ans,(distances[i]-T)*((long)cows[i]));
      }
    }
    //System.out.println(ans);
    pw.println(ans);
    pw.close();
  }

  public static int rec(int field) {
    int cowsThroughField = cows[field];
    for (int back: backtrack[field]) {
      if (visited[back]) {
        cowsThroughField += cows[back];
      } else {
        visited[back] = true;
        cowsThroughField += rec(back);
      }
    }
    cows[field] = cowsThroughField;
    return cows[field];
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

class Node {
  public ArrayList<Edge> neighbors;
  public int n;

  public Node(int n) {
    this.n = n;
    neighbors = new ArrayList<Edge>();
  }
  public void addChild(Node b, int w) {
    Edge e = new Edge(b,w);
    neighbors.add(e);
  }
}

class Edge {
  public Node destination;
  public int weight;

  public Edge(Node b, int w) {
    destination = b;
    weight = w;
  }
}