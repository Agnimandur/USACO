import java.util.*;
import java.io.*;

class Main {
  public static void main(String[] args) throws IOException {
    InputStream is = new FileInputStream("cowmbat.in");
    PrintWriter pw = new PrintWriter(new BufferedWriter(new FileWriter("cowmbat.out")));
    FastScanner sc = new FastScanner(is);

    int N = sc.nextInt();
    int M = sc.nextInt();
    int K = sc.nextInt();
    String s = sc.next();
    int[] nums = new int[N+1];
    for (int i = 1; i <= N; i++) {
      nums[i] = s.charAt(i-1)-'a';
    }
    int[][] matrix = new int[M][M];
    for (int i = 0; i < M; i++) {
      for (int j = 0; j < M; j++) {
        matrix[i][j] = sc.nextInt();
      }
    }

    //Floyd Warshall to find the real shortest distance between two letters
    for (int k = 0; k < M; k++) {
      for (int i = 0; i < M; i++) {
        for (int j = 0; j < M; j++) {
          if (matrix[i][j] > matrix[i][k]+matrix[k][j]) {
            matrix[i][j] = matrix[i][k]+matrix[k][j];
          }
        }
      }
    }

    //each row is each combo move
    //each column is a combo ending with the respective letter
    //the (i,j) dp state is based on either converting the current number into the ongoing combo (i-1,j)+COST or create a new combo of len K based on MIN(i-K,all possible js)+COST(last K numbers into j)
    int[][] dp = new int[N+1][M];
    int INF = 1000000007;
    for (int i = 1; i <= N; i++) {
      for (int j = 0; j < M; j++) {
        dp[i][j] = INF;
      }
    }

    int[] rowMins = new int[N+1];
    Arrays.fill(rowMins,INF);
    rowMins[0] = 0;

    //Used in finding the cost of converting a whole string of characters into one char.
    int[][] pref = new int[N+1][M];
    for (int i = 1; i <= N; i++) {
      for (int j = 0; j < M; j++) {
        pref[i][j] = pref[i-1][j]+matrix[nums[i]][j];
      }
    }

    for (int i = K; i <= N; i++) {
      for (int j = 0; j < M; j++) {
        int cost1 = dp[i-1][j]+matrix[nums[i]][j];
        //code
        int cost2 = rowMins[i-K]+(pref[i][j]-pref[i-K][j]);

        dp[i][j] = Math.min(dp[i][j],Math.min(cost1,cost2));
        rowMins[i] = Math.min(rowMins[i],dp[i][j]);
      }
    }

    int ans = INF;
    for (int j = 0; j < M; j++) {
      ans = Math.min(ans,dp[N][j]);
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