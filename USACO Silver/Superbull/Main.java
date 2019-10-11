import java.util.*;
import java.math.*;
import java.io.*;

class Main {
  public static void main(String[] args) throws IOException {
    InputStream is = new FileInputStream("superbull.in");
    PrintWriter pw = new PrintWriter(new BufferedWriter(new FileWriter("superbull.out")));
    FastScanner sc = new FastScanner(is);
    int N = sc.nextInt();
    int[] nums = new int[N];
    for (int i = 0; i < N; i++)
      nums[i] = sc.nextInt();
    int[][] adj = new int[N][N];
    for (int i = 0; i < N; i++) {
      for (int j = 0; j < N; j++) {
        //Convert the problem into a MST problem
        adj[i][j] = -1 * (nums[i] ^ nums[j]);
      }
    }

    int[] distances = new int[N];
    boolean[] visited = new boolean[N];
    Arrays.fill(distances,Integer.MAX_VALUE);
    distances[0]  = 0;
    //Prim's Algorithm
    PriorityQueue<int[]> pq = new PriorityQueue<int[]>(new Comparator<int[]>(){
      public int compare(int[] a, int[] b) {
        return a[1] - b[1];
      }
    });
    int[] temp = {0,0};
    pq.add(temp);
    while (! pq.isEmpty()) {
      temp = pq.poll();
      int parent = temp[0];
      visited[parent] = true;
      for (int child = 0; child < N; child++) {
        if (! visited[child] && distances[child] > adj[parent][child]) {
          distances[child] = adj[parent][child];
          int[] temp2 = {child,distances[child]};
          pq.add(temp2);
        }
      }
    }
    long totalMST = 0;
    for (int i = 1; i < N; i++) {
      totalMST -= distances[i];
    }
    //System.out.println(totalMST);
    pw.println(totalMST);
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