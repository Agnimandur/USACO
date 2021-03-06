import java.util.*;
import java.math.*;
import java.io.*;

class Main {
  public static int[][] dp;
  public static void main(String[] args) throws IOException {
    InputStream is = new FileInputStream("248.in");
    PrintWriter pw = new PrintWriter(new BufferedWriter(new FileWriter("248.out")));
    FastScanner sc = new FastScanner(is);
    int N = sc.nextInt();
    int[] nums = new int[N];
    for (int i = 0; i < N; i++) {
      nums[i] = sc.nextInt();
    }
    dp = new int[N][N];
    for (int d = 0; d < N; d++) {
      dp[d][d] = nums[d];
    }
    int ans = solve(0,N-1);
    for (int i = 0; i < N; i++) {
      for (int j = i; j < N; j++) {
        ans = Math.max(ans,dp[i][j]);
      }
    }
    //System.out.println(ans);
    pw.println(ans);
    pw.close();
  }

  public static int solve(int s, int e) {
    if (dp[s][e] != 0)
      return dp[s][e];
    int collapseInto = -1;
    for (int i = s; i < e; i++) {
      int collapse1 = solve(s,i);
      int collapse2 = solve(i+1,e);
      if (collapse1 > 0 && collapse2 > 0 && collapse1 == collapse2) {
        collapseInto = collapse1+1;
        break;
      }
    }
    dp[s][e] = collapseInto;
    //If collapseInto == -1, then it doesn't collapse into any one value.
    return collapseInto;
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