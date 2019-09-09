import java.util.*;
import java.io.*;

class Main {
  static final long MOD = 1000000007L;
  public static void main(String[] args) throws IOException {
    PrintWriter pw = new PrintWriter(new BufferedWriter(new FileWriter("poetry.out")));
    FastScanner sc = new FastScanner();
    int N = sc.nextInt();
    int M = sc.nextInt();
    int K = sc.nextInt();
    int[][] words = new int[N][2];

    //Save the input in two lists
    for (int i = 0; i < N; i++) {
      words[i][0] = sc.nextInt();
      words[i][1] = sc.nextInt();
    }

    //The number of occurences of each letter in the rhyme scheme.
    int[] rhymeScheme = new int[26];
    for (int i = 0; i < M; i++) {
      int c = (int)sc.next().charAt(0) - (int)'A';
      rhymeScheme[c] += 1;
    }
    
    long[] rhymeClasses = new long[N+1];
    long[] dp = new long[K+1];
    dp[0] = 1;
    for (int i = 0; i <= K; i++) {
      for (int j = 0; j < N; j++) {
        if (i+words[j][0] <= K) {
          if (i+words[j][0] == K) {
            rhymeClasses[words[j][1]] += dp[i];
            rhymeClasses[words[j][1]] %= MOD;
          }
          dp[i+words[j][0]] += dp[i];
          dp[i+words[j][0]] %= MOD;
        }
      }
    }

    long ans = 1;
    for (int i = 0; i < 26; i++) {
      if (rhymeScheme[i] == 0) continue;
      long val = 0;
      for (int j = 1; j <= N; j++) {
        //The number of ways to assign rhyme class j to letter i = Math.pow(rhymeClasses[j],rhymeScheme[i]).
        if (rhymeClasses[j] == 0) continue;
        val += fastExp(rhymeClasses[j],rhymeScheme[i]);
        val %= MOD;
      }
      ans = (ans * val) % MOD;
    }

    //poetry.out
    pw.println(ans);
    pw.close();
  }

  public static long exp(long base, int power) {
    long ans = 1;
    for (int i = 0; i < power; i++) {
      ans *= base;
      ans %= MOD;
    }
    return ans;
  }

  public static long fastExp(long base, long power) {
    if (power == 0) {
      return 1;
    } else if (power == 1) {
      return base;
    } else if (power % 2 == 0) {
      return fastExp((base*base % MOD),power/2);
    } else {
      return (base*fastExp((base*base % MOD),power/2))%MOD;
    }
  }

  static class FastScanner { 
    BufferedReader br; 
    StringTokenizer st; 
  
    public FastScanner() throws IOException { 
      br = new BufferedReader(new FileReader("poetry.in"));
    }
  
    String next() { 
      while (st == null || !st.hasMoreElements()) { 
        try
        { 
          st = new StringTokenizer(br.readLine()); 
        } 
        catch (IOException  e) 
        { 
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
      try
      { 
        str = br.readLine(); 
      } 
      catch (IOException e) 
      { 
        e.printStackTrace(); 
      } 
      return str; 
    }
  }
}