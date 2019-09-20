import java.util.*;
import java.math.*;
import java.io.*;

class Main {
  public static void main(String[] args) throws IOException {
    InputStream is = new FileInputStream("nocross.in");
    PrintWriter pw = new PrintWriter(new BufferedWriter(new FileWriter("nocross.out")));
    FastScanner sc = new FastScanner(is);

    int N = sc.nextInt();
    int[] top = new int[N+1];
    int[] bottom = new int[N+1];
    for (int i = 1; i <= N; i++) {
      top[i] = sc.nextInt();
    }
    for (int i = 1; i <= N; i++) {
      bottom[i] = sc.nextInt();
    }
    int[][] dp = new int[N+1][N+1];
    for (int i = 1; i <= N; i++) {
      for (int j =  1; j <= N; j++) {
        int val = Math.max(dp[i][j-1],dp[i-1][j]);
        if (Math.abs(top[i]-bottom[j]) <= 4) {
          val = Math.max(val,dp[i-1][j-1]+1);
        } else {
          val = Math.max(val,dp[i-1][j-1]);
        }
        dp[i][j] = val;
      }
    }

    pw.println(dp[N][N]);
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