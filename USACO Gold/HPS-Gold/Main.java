import java.util.*;
import java.math.*;
import java.io.*;

class Main {
  public static void main(String[] args) throws IOException {
    FastScanner sc = new FastScanner("hps.in");
    PrintWriter pw = new PrintWriter(new BufferedWriter(new FileWriter("hps.out")));
    int N = sc.nextInt();
    int K = sc.nextInt();
    int[] hps = new int[N];
    String s = "HPS";
    for (int i = 0; i < N; i++) {
      hps[i] = s.indexOf(sc.next());
    }
    int[][][] dp = new int[N+1][K+1][3];
    for (int i = 1; i <= N; i++) {
      for (int k = 0; k < 3; k++) {
        dp[i][0][k] = dp[i-1][0][k];
      }
      dp[i][0][hps[i-1]] += 1;
    }
    for (int i = 1; i <= N; i++) {
      for (int j = 1; j <= K; j++) {
        for (int k = 0; k < 3; k++) {
          dp[i][j][k] = Math.max(dp[i-1][j-1][(k+1)%3],dp[i-1][j-1][(k+2)%3]);
          dp[i][j][k] = Math.max(dp[i][j][k],dp[i-1][j][k]);
        }
        dp[i][j][hps[i-1]] += 1;
      }
    }
    /*for (int i = 0; i <= N; i++) {
      for (int j = 0; j <= K; j++) {
        System.out.print("[" + dp[i][j][0] + "," + dp[i][j][1] + "," + dp[i][j][2] + "] ");
      }
      System.out.println();
    }*/
    //System.out.println(dp[N][K]);
    pw.println(Math.max(dp[N][K][0],Math.max(dp[N][K][1],dp[N][K][2])));
    pw.close();
  }

  static class FastScanner { 
    BufferedReader br; 
    StringTokenizer st; 
  
    public FastScanner(String filename) throws IOException { 
      br = new BufferedReader(new FileReader(filename));
    }
  
    String next() { 
      while (st == null || !st.hasMoreElements()) { 
        try { 
          st = new StringTokenizer(br.readLine()); 
        } 
        catch (IOException  e) { 
          e.printStackTrace(); 
        } 
      } 
      return st.nextToken(); 
    } 
  
    int nextInt() { 
      return Integer.parseInt(next()); 
    } 
  
    long nextLong() { 
      return Long.parseLong(next()); 
    } 
  
    double nextDouble() { 
      return Double.parseDouble(next()); 
    } 
  
    String nextLine() { 
      String str = ""; 
      try { 
        str = br.readLine(); 
      } catch (IOException e) { 
        e.printStackTrace(); 
      } 
      return str; 
    }
  }
}