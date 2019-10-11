import java.util.*;
import java.math.*;
import java.io.*;
import java.awt.Point;

class Main {
  static int N;
  static int C;
  static int[][] fields;

  public static void main(String[] args) throws IOException {
    InputStream is = new FileInputStream("irrigation.in");
    PrintWriter pw = new PrintWriter(new BufferedWriter(new FileWriter("irrigation.out")));
    FastScanner sc = new FastScanner(is);

    N = sc.nextInt();
    C = sc.nextInt();
    fields = new int[N][2];
    for (int i = 0; i < N; i++)
      fields[i] = new int[]{sc.nextInt(),sc.nextInt()};

    //Prim's algorithm
    boolean[] visited = new boolean[N];
    int[] distances = new int[N];
    Arrays.fill(distances,Integer.MAX_VALUE);
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
        if (p==c)
          continue;
        int pcDistance = dist(p,c);
        if (! visited[c] && distances[c] > pcDistance && pcDistance >= C) {
          distances[c] = pcDistance;
          int[] temp2 = {c,pcDistance};
          pq.add(temp2);
        }
      }
    }

    long spanDistance = 0;
    for (int i = 1; i < N; i++) {
      if (distances[i] == Integer.MAX_VALUE) {
        spanDistance = -1;
        break;
      } else {
        spanDistance += distances[i];
      }
    }
    //System.out.println(Arrays.toString(distances));
    pw.println(spanDistance);
    pw.close();
  }

  public static int dist(int p, int c) {
    return (fields[p][0]-fields[c][0])*(fields[p][0]-fields[c][0])+(fields[p][1]-fields[c][1])*(fields[p][1]-fields[c][1]);
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