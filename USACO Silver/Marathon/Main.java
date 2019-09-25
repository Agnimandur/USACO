import java.util.*;
import java.math.*;
import java.io.*;

class Main {
  public static int[][] points;
  public static void main(String[] args) throws IOException {
    InputStream is = new FileInputStream("marathon.in");
    PrintWriter pw = new PrintWriter(new BufferedWriter(new FileWriter("marathon.out")));
    FastScanner sc = new FastScanner(is);
    int N = sc.nextInt();
    int K = sc.nextInt();
    points = new int[N+1][2];
    for (int i = 1; i <= N; i++) {
      points[i] = new int[]{sc.nextInt(),sc.nextInt()};
    }
    int[][] dp = new int[N+1][K+1];
    for (int j = 0; j <= K; j++) {
      dp[1][j] = 0;
      dp[2][j] = man(1,2);
    }
    for (int i = 3; i <= N; i++) {
      dp[i][0] = dp[i-1][0] + man(i-1,i);
    }
    
    for (int j = 1; j <= K; j++) {
      for (int i = 3; i <= N; i++) {
        //Visit points[i-1]
        int visitDist = dp[i-1][j]+man(i-1,i);
        
        //skip points[i-1]
        int skipDist = Integer.MAX_VALUE;
        for (int k = 1; k <= Math.min(j,i-1); k++)
          skipDist = Math.min(skipDist,dp[i-1-k][j-k]+man(i-1-k,i));
        dp[i][j] = Math.min(visitDist,skipDist);
      }
    }
    //System.out.println(dp[N][K]);
    pw.println(dp[N][K]);
    pw.close();
  }
  
  public static int man(int p1, int p2) {
    return Math.abs(points[p1][0]-points[p2][0])+Math.abs(points[p1][1]-points[p2][1]);
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