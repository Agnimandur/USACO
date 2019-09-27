import java.util.*;
import java.math.*;
import java.io.*;

class Main {
  public static void main(String[] args) throws IOException {
    InputStream is = new FileInputStream("reststops.in");
    PrintWriter pw = new PrintWriter(new BufferedWriter(new FileWriter("reststops.out")));
    FastScanner sc = new FastScanner(is);

    int L = sc.nextInt();
    int N = sc.nextInt();
    long R = sc.nextLong() - sc.nextLong();
    int[][] stops = new int[N+1][2]; // {xi, ci}
    for (int i = 1; i <= N; i++) {
      stops[i][0] = sc.nextInt();
      stops[i][1] = sc.nextInt();
    }
    boolean[] visit = new boolean[N+1];
    int max = 0;
    for (int i = N; i >= 1; i--) {
      if (stops[i][1] > max) {
        visit[i] = true;
        max = stops[i][1];
      }
    }
    long time = 0;
    long ans = 0;
    for (int i = 1; i <= N; i++) {
      time += R*(stops[i][0]-stops[i-1][0]);
      if (visit[i]) {
        ans += time*stops[i][1];
        time = 0;
      }
    }

    //System.out.println(ans);
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