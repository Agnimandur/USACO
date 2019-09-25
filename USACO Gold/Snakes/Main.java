import java.util.*;
import java.math.*;
import java.io.*;

class Main {
  public static int[] prefix;
	public static int[][] maxInRange;
  public static void main(String[] args) throws IOException {
    InputStream is = new FileInputStream("snakes.in");
    PrintWriter pw = new PrintWriter(new BufferedWriter(new FileWriter("snakes.out")));
    FastScanner sc = new FastScanner(is);
    int N = sc.nextInt();
    int K = sc.nextInt();
    int[] nums = new int[N];
    prefix = new int[N+1];
    for (int i = 1; i <= N; i++) {
      nums[i-1] = sc.nextInt();
      prefix[i] = prefix[i-1] + nums[i-1];
    }
    maxInRange = new int[N][N];
    for (int i = 0; i < N; i++) {
      int val = nums[i];
      for (int j = i; j < N; j++) {
        val = Math.max(val,nums[j]);
        maxInRange[i][j] = val;
      }
    }
    
    int[][] dp = new int[N+1][K+1];
    for (int i = 1; i <= N; i++) {
      dp[i][0] = wasted(0,i-1);
    }
    for (int j = 1; j <= K; j++) {
      for (int i = 1; i <= N; i++) {
        int val = dp[i][j-1];
        for (int k = 1; k < i; k++) {
          val = Math.min(val,dp[i-k][j-1]+wasted(i-k,i-1));
        }
        dp[i][j] = val;
      }
    }
    //System.out.println(dp[N][K]);
    pw.println(dp[N][K]);
    pw.close();
  }
  public static int wasted(int s, int e) {
    return (e-s+1)*maxInRange[s][e] - (prefix[e+1]-prefix[s]);
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