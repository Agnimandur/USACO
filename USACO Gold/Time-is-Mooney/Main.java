import java.util.*;
import java.io.*;

class Main {
  public static void main(String[] args) throws IOException {
    InputStream is = new FileInputStream("time.in");
    PrintWriter pw = new PrintWriter(new BufferedWriter(new FileWriter("time.out")));
    FastScanner sc = new FastScanner(is);

    int N = sc.ni();
    int M = sc.ni();
    int C = sc.ni();
    int[] money = new int[N];
    for (int i = 0; i < N; i++)
      money[i] = sc.ni();
    ArrayList<Integer>[] graph = new ArrayList[N];
    for (int i = 0; i < N; i++)
      graph[i] = new ArrayList<Integer>();
    
    for (int i = 0; i < M; i++) {
      int s = sc.ni()-1;
      int e = sc.ni()-1;
      graph[s].add(e);
    }
    int[][] profit = new int[N][1001]; //visit city i after travelling for j days.
    for (int i = 0; i < N; i++) {
      for (int j = 0; j <= 1000; j++) {
        profit[i][j] = -1;
      }
    }
    profit[0][0] = 0;

    PriorityQueue<int[]> pq = new PriorityQueue<int[]>(new Comparator<int[]>(){
      public int compare(int[] a, int[] b) {
        if (a[1] != b[1]) {
          return a[1]-b[1];
        } else {
          return b[2]-a[2];
        }
      }
    }); //{city,day,money}
    pq.add(new int[]{0,0,0});
    while (!pq.isEmpty()) {
      int[] city = pq.poll();
      int day = city[1];
      if (day==1000) continue;
      for (int neighbor: graph[city[0]]) {
        if (profit[neighbor][day+1] < city[2]+money[neighbor]) {
          profit[neighbor][day+1] = city[2]+money[neighbor];
          pq.add(new int[]{neighbor,day+1,profit[neighbor][day+1]});
        }
      }
    }
    int ans = 0;
    for (int i = 0; i <= 1000; i++) {
      ans = Math.max(ans,profit[0][i]-C*i*i);
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