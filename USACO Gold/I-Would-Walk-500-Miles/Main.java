import java.util.*;
import java.io.*;

class Main {
  public static void main(String[] args) throws IOException {
    InputStream is = new FileInputStream("walk.in");
    PrintWriter pw = new PrintWriter(new BufferedWriter(new FileWriter("walk.out")));
    FastScanner sc = new FastScanner(is);

    int N = sc.nextInt();
    int K = sc.nextInt();

    //Prim's algorithm
    boolean[] visited = new boolean[N];
    int[] distances = new int[N];
    Arrays.fill(distances,2019201997);
    distances[0] = 0;
    PriorityQueue<int[]> pq = new PriorityQueue<int[]>(new Comparator<int[]>(){
      public int compare(int[] a, int[] b) {
        return a[1] - b[1];
      }
    });

    int[] temp = {0,0}; //{node,distance}
    pq.add(temp);
    while (! pq.isEmpty()) {
      temp = pq.poll();
      int p = temp[0];
      visited[p] = true;
      for (int c = 0; c < N; c++) {
        int pcDistance = dist(p,c);
        if (!visited[c] && distances[c] > pcDistance) {
          distances[c] = pcDistance;
          int[] temp2 = {c,pcDistance};
          pq.add(temp2);
        }
      }
    }
    Arrays.sort(distances);
    pw.println(distances[N+1-K]);
    pw.close();
  }

  public static int dist(int p, int c) {
    long d = (2019201913L*(Math.min(p,c)+1))+(2019201949L*(Math.max(p,c)+1));
    return (int)(d%2019201997);
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