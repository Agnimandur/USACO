import java.util.*;
import java.math.*;
import java.io.*;

class Main {
  public static void main(String[] args) throws IOException {
    InputStream is = new FileInputStream("teamwork.in");
    PrintWriter pw = new PrintWriter(new BufferedWriter(new FileWriter("teamwork.out")));
    FastScanner sc = new FastScanner(is);
    int N = sc.nextInt();
    int K = sc.nextInt();
    int[] nums = new int[N];
    for (int i = 0; i < N; i++) {
      nums[i] = sc.nextInt();
    }
    int[][] max = new int[N][K]; //max[i][j] stores the maximum of the j elements following i.
    for (int i = 0; i < N; i++) {
      max[i][0] = nums[i];
      for (int j = 1; j < K; j++) {
        if (i + j >= N) {
          break;
        } else {
          max[i][j] = Math.max(max[i][j-1],nums[i+j]);
        }
      }
    }

    int[] dp = new int[N+1];
    for (int i = 1; i <= Math.min(N,K); i++) {
      dp[i] = i*max[0][i-1];
    }
    for (int i = K+1; i <= N; i++) {
      for (int j = 1; j <= K; j++) {
        int score = dp[i-j]+j*max[i-j][j-1];
        //iterate through placing nums[i-1] on a team of size j.
        dp[i] = Math.max(dp[i],score);
      }
    }
    //System.out.println(dp[N]);
    pw.println(dp[N]);
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