import java.util.*;
import java.math.*;
import java.io.*;

class Main {
  public static long MOD = 1000000007;
  public static void main(String[] args) throws IOException {
    InputStream is = new FileInputStream("spainting.in");
    PrintWriter pw = new PrintWriter(new BufferedWriter(new FileWriter("spainting.out")));
    FastScanner sc = new FastScanner(is);
    int N = sc.nextInt();
    int M = sc.nextInt();
    int K = sc.nextInt();
    long[] dp = new long[N+K];
    dp[K] = (long)M;
    long sum = 0;
    for (int block = K+1; block < N+K; block++) {
      sum += dp[block-1];
      sum -= dp[block-K];
      sum = (sum+MOD)%MOD;
      dp[block] = ((M-1)*sum)%MOD;
    }

    long ans = 1;
    for (int i = 0; i < N; i++) {
      ans *= M;
      ans %= MOD;
    }
    for (int i = N+K-1; i > N; i--) {
      ans = (ans+MOD-dp[i])%MOD;
    }
    //System.out.println(ans);
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