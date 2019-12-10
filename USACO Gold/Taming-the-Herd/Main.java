import java.util.*;
import java.io.*;

class Main {
  public static void main(String[] args) throws IOException {
    InputStream is = new FileInputStream("taming.in");
    PrintWriter pw = new PrintWriter(new BufferedWriter(new FileWriter("taming.out")));
    FastScanner sc = new FastScanner(is);

    int N = sc.nextInt();
    int[] input = new int[N+1];
    for (int i = 1; i <= N; i++)
      input[i] = sc.nextInt();

    int[][][] dp = new int[N+1][N+1][N+1]; //first i days, j breakouts, k days since last breakout
    for (int i = 0; i <= N; i++) {
      for (int j = 0; j <= N; j++) {
        for (int k = 0; k <= N; k++) {
          dp[i][j][k] = Integer.MAX_VALUE/2;
        }
      }
    }
    dp[0][0][0] = 0;

    for (int i = 1; i <= N; i++) {
      for (int j = 1; j <= i; j++) {
        //Breakout today!
        int minEdits = Integer.MAX_VALUE;
        for (int k = 0; k < i; k++) {
          minEdits = Math.min(minEdits,dp[i-1][j-1][k]);
        }
        dp[i][j][0] = minEdits;
        if (input[i] != 0)
          dp[i][j][0] += 1;
        //Last breakout was k>0 days back
        for (int k = 1; k <= i; k++) {
          int v = 0;
          if (input[i]!=k)
            v = 1;
          dp[i][j][k] = dp[i-1][j][k-1]+v;
        }
      }
    }

    for (int j = 1; j <= N; j++) {
      int minEdits = Integer.MAX_VALUE;
      for (int k = 0; k <= N; k++) {
        minEdits = Math.min(minEdits,dp[N][j][k]);
      }
      pw.println(minEdits);
    }
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