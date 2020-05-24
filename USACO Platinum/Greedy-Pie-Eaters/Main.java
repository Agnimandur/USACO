import java.util.*;
import java.io.*;

class Main {
  public static final String TASKNAME = "pieaters";

  public static void main(String[] args) throws IOException {
    InputStream is = new FileInputStream(TASKNAME+".in");
    PrintWriter pw = new PrintWriter(new BufferedWriter(new FileWriter(TASKNAME+".out")));
    FastScanner sc = new FastScanner(is);

    int N = sc.ni();
    int M = sc.ni();
    int[][] cows = new int[M][3];
    for (int i = 0; i < M; i++) {
      cows[i][0] = sc.ni(); //weight
      cows[i][1] = sc.ni()-1; //L
      cows[i][2] = sc.ni()-1; //R
    }
    Arrays.sort(cows, (a, b) -> Integer.compare(b[0],a[0]));

    if (N==1) {
      pw.println(cows[0][0]);
      pw.close();
      return;
    }

    int[][][] maxWeight = new int[N][N][N]; //the maximum weight of a cow contained entirely within the range [i,j] that also includes pie k.
    for (int c = 0; c < M; c++) {
      for (int k = cows[c][1]; k <= cows[c][2]; k++) {
        for (int i = cows[c][1]; i >= 0; i--) {
          if (maxWeight[i][cows[c][2]][k] > 0)
            break;
          for (int j = cows[c][2]; j < N; j++) {
            if (maxWeight[i][j][k] > 0)
              break;
            else
              maxWeight[i][j][k] = cows[c][0];
          }
        }
      }
    }

    long[][] dp = new long[N][N]; //find the maximum weight of all the cows that can be included in the range [i,j].
    for (int s = 0; s < N; s++) {
      for (int i = 0; i < N-s; i++) {
        int j = i+s;

        //case 1: combine two smaller ranges into [i,j]
        for (int k = i; k < j; k++) {
          dp[i][j] = Math.max(dp[i][j],dp[i][k]+dp[k+1][j]);
        }

        //case 2: combine two smaller ranges into [i,j] but also include a cow (give that cow the k-th pie)
        for (int k = i; k <= j; k++) {
          if (k==0) {
            dp[i][j] = Math.max(dp[i][j],maxWeight[i][j][k]+dp[k+1][j]);
          } else if (k == N-1) {
            dp[i][j] = Math.max(dp[i][j],dp[i][k-1]+maxWeight[i][j][k]);
          } else {
            dp[i][j] = Math.max(dp[i][j],dp[i][k-1]+maxWeight[i][j][k]+dp[k+1][j]);
          }
        }
      }
    }

    long ans = dp[0][N-1];
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