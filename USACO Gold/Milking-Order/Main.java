import java.util.*;
import java.io.*;

class Main {
  public static void main(String[] args) throws IOException {
    InputStream is = new FileInputStream("milkorder.in");
    PrintWriter pw = new PrintWriter(new BufferedWriter(new FileWriter("milkorder.out")));
    FastScanner sc = new FastScanner(is);

    int N = sc.ni();
    int M = sc.ni();
    ArrayList<Integer>[] orders = new ArrayList[M];
    for (int i = 0; i < M; i++) {
      orders[i] = new ArrayList<Integer>();
    }
    for (int i = 0; i < M; i++) {
      int size = sc.ni();
      orders[i].add(size);
      for (int j = 0; j < size; j++)
        orders[i].add(sc.ni()-1);
    }

    int low = 0;
    int high = M;
    ArrayList<Integer>[] graph = new ArrayList[N];
    for (int i = 0; i < N; i++)
      graph[i] = new ArrayList<Integer>();
    while (low < high) {
      int med = (low+high+1)/2;
      for (int i = 0; i < N; i++)
        graph[i].clear();
      for (int i = 0; i < med; i++) {
        int size = orders[i].get(0);
        for (int j = 2; j <= size; j++) {
          graph[orders[i].get(j-1)].add(orders[i].get(j));
        }
      }

      if (isCycle(graph)) {
        high = med-1;
      } else {
        low = med;
      }
    }

    for (int i = 0; i < N; i++)
      graph[i].clear();
    for (int i = 0; i < low; i++) {
      int size = orders[i].get(0);
      for (int j = 2; j <= size; j++) {
        graph[orders[i].get(j-1)].add(orders[i].get(j));
      }
    }

    HashSet<Integer>[] parents = new HashSet[N];
    for (int i = 0; i < N; i++)
      parents[i] = new HashSet<Integer>();
    for (int p = 0; p < N; p++) {
      for (int c: graph[p]) {
        parents[c].add(p);
      }
    }

    int[] ans = new int[N];
    int index = 0;
    PriorityQueue<Integer> next = new PriorityQueue<Integer>();
    for (int i = 0; i < N; i++) {
      if (parents[i].isEmpty()) {
        next.add(i);
      }
    }

    while (!next.isEmpty()) {
      int node = next.poll();
      ans[index] = node+1;
      index++;
      for (int c: graph[node]) {
        parents[c].remove(node);
        if (parents[c].isEmpty())
          next.add(c);
      }
    }

    for (int i = 0; i < N-1; i++)
      pw.print(ans[i] + " ");
    pw.println(ans[N-1]);
    pw.close();
  }

  public static boolean isCycle(ArrayList<Integer>[] graph) {
    int N = graph.length;
    boolean[] visited = new boolean[N];
    boolean[] recursiveArr = new boolean[N];

    //do DFS from each node
    for (int i = 0; i < N; i++) {
      if (isCycleUtil(graph,i, visited, recursiveArr))
        return true;
    }
    return false;
  }

  public static boolean isCycleUtil(ArrayList<Integer>[] graph, int vertex, boolean[] visited, boolean[] recursiveArr) {
    visited[vertex] = true;
    recursiveArr[vertex] = true;

    //recursive call to all the adjacent vertices
    for (int adj: graph[vertex]) {
      //if not already visited
      if (!visited[adj] && isCycleUtil(graph, adj, visited, recursiveArr))
        return true;
      else if (recursiveArr[adj])
        return true;
    }
    //if reached here means cycle has not found in DFS from this vertex
    //reset
    recursiveArr[vertex] = false;
    return false;
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