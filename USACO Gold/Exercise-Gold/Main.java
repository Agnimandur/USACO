import java.util.*;
import java.io.*;

class Main {
  public static void main(String[] args) throws IOException {
    InputStream is = new FileInputStream("exercise.in");
    PrintWriter pw = new PrintWriter(new BufferedWriter(new FileWriter("exercise.out")));
    FastScanner sc = new FastScanner(is);

    int N = sc.ni();
    long MOD = sc.nl();

    ArrayList<Integer> primes = new ArrayList<Integer>();
    primes.add(1);
    for (int i = 2; i <= N; i++) {
      boolean prime = true;
      for (int j = 2; j*j <= i; j++) {
        if (i%j==0) {
          prime = false;
          break;
        }
      }
      if (prime)
        primes.add(i);
    }
    int P = primes.size();

    long[][] dp = new long[N+1][P]; //the sum of the total reachable step lengths if the number of cows is i and you only use cycles that divide primes up to primes.get(j).
    for (int j = 0; j < P; j++)
      dp[0][j] = 1L;
    for (int i = 1; i <= N; i++)
      dp[i][0] = 1L;
    
    for (int i = 1; i <= N; i++) {
      for (int j = 1; j < P; j++) {
        //don't use this prime
        dp[i][j] = dp[i][j-1];
        int p = primes.get(j);
        int pow = p;
        while (pow <= i) {
          dp[i][j] += (pow*dp[i-pow][j-1]);
          dp[i][j] %= MOD;
          pow *= p;
        }
      }
    }

    pw.println(dp[N][P-1]);
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