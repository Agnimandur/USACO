import java.util.*;
import java.io.*;

class Main {
  public static final String TASKNAME = "team";

  public static void main(String[] args) throws IOException {
    InputStream is = new FileInputStream(TASKNAME+".in");
    PrintWriter pw = new PrintWriter(new BufferedWriter(new FileWriter(TASKNAME+".out")));
    FastScanner sc = new FastScanner(is);
    long MOD = 1000000009L;
    int N = sc.ni();
    int M = sc.ni();
    int K = sc.ni();
    int[] A = new int[N];
    for (int i = 0; i < N; i++)
      A[i] = sc.ni();
    Arrays.sort(A);
    int[] B = new int[M];
    for (int i = 0; i < M; i++)
      B[i] = sc.ni();
    Arrays.sort(B);

    long[][][] dp = new long[N+1][M+1][K+1];
    for (int i = 0; i <= N; i++) {
      for (int j = 0; j <= M; j++) {
        dp[i][j][0] = 1L;
      }
    }
    
    for (int k = 1; k <= K; k++) {
      for (int i = 1; i <= N; i++) {
        for (int j = 1; j <= M; j++) {
          if (A[i-1] > B[j-1]) {
            //FJ has a winning pair
            dp[i][j][k] = dp[i-1][j-1][k-1];
          }
          //don't use this pair together
          dp[i][j][k] += (dp[i][j-1][k]+dp[i-1][j][k]-dp[i-1][j-1][k]+MOD);
          dp[i][j][k] %= MOD;
        }
      }
    }

    pw.println(dp[N][M][K]);
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