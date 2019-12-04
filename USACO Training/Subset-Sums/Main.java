/*
ID: shivara2
LANG: JAVA
TASK: subset
*/

import java.util.*;
import java.io.*;

class subset {
  public static void main(String[] args) throws IOException {
    InputStream is = new FileInputStream("subset.in");
    PrintWriter pw = new PrintWriter(new BufferedWriter(new FileWriter("subset.out")));
    FastScanner sc = new FastScanner(is);

    int N = sc.nextInt();
    int target = N*(N+1)/2;
    long ans = 0;
    if (target % 2 == 0) {
      target /= 2;

      //Knapsack DP to find how many ways to find a subset from 1 to N that has a sum of target.
      long[][] dp = new long[N+1][N*(N+1)/2 + 1];
      dp[0][0] = 1L;
      for (int i = 1; i <= N; i++) {
        for (int j = 0; j < dp[0].length; j++) {
          //Don't include the item i.
          dp[i][j] = dp[i-1][j];
        }
        for (int j = 0; j < dp[0].length-i; j++) {
          //include the item i
          dp[i][j+i] += dp[i-1][j];
        }
      }
      ans = dp[N][target]/2;
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