import java.util.*;
import java.io.*;

class Main {
  static long MOD = 1000000007L;
  static long DIV2 = 500000004L;
  public static void main(String[] args) throws IOException {
    InputStream is = new FileInputStream("sprinklers2.in");
    PrintWriter pw = new PrintWriter(new BufferedWriter(new FileWriter("sprinklers2.out")));
    FastScanner sc = new FastScanner(is);
    int N = sc.ni();
    int W = 0; //number of wooly cows
    boolean[][] blocked = new boolean[N][N];
    for (int i = 0; i < N; i++) {
      String row = sc.next();
      for (int j = 0; j < N; j++) {
        if (row.charAt(j)=='W') {
          blocked[i][j] = true;
          W++;
        }
      }
    }

    long[] powers = new long[N*N+1];
    powers[0] = 1L;
    for (int i = 1; i <= N*N; i++) {
      powers[i] = (2*powers[i-1]);
      if (powers[i] > MOD)
        powers[i] -= MOD;
    }

    long[][][] dp = new long[N+1][N+1][2]; //0 is heading down, 1 is heading to the right. (begin in the top left corner and draw a jagged line separating alfalfa from corn).
    for (int i = 0; i <= N; i++) {
      dp[i][0][0] = powers[N*N-W];
    }
    for (int j = 0; j <= N; j++) {
      dp[0][j][1] = powers[N*N-W];
    }
    for (int i = 1; i <= N; i++) {
      for (int j = 1; j <= N; j++) {
        //continue heading straight down
        dp[i][j][0] = dp[i-1][j][0];
        if (!blocked[i-1][j-1]) {
          //make a left turn
          dp[i][j][0] += (DIV2*dp[i-1][j][1]);
          dp[i][j][0] %= MOD;
        }

        //continue heading straight to the right.
        dp[i][j][1] = dp[i][j-1][1];
        if (!blocked[i-1][j-1]) {
          //make a right turn
          dp[i][j][1] += (DIV2*dp[i][j-1][0]);
          dp[i][j][1] %= MOD;
        }
      }
    }

    long ans = (dp[N][N][0]+dp[N][N][1])%MOD;
    pw.println(ans);
    pw.close();

    long t4 = System.currentTimeMillis();
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