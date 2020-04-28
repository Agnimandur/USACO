import java.util.*;
import java.io.*;

class Main {
  static int[] nums;
  static int N;
  static int[][][][] dp;
  public static void main(String[] args) throws IOException {
    InputStream is = new FileInputStream("subrev.in");
    PrintWriter pw = new PrintWriter(new BufferedWriter(new FileWriter("subrev.out")));
    FastScanner sc = new FastScanner(is);

    N = sc.ni();
    nums = new int[N];
    for (int i = 0; i < N; i++)
      nums[i] = sc.ni()-1;
    dp = new int[N][N][50][50]; //the longest increasing subsequence from nums[i]..nums[j] all of whose numbers are between a..b
    //parameters: i,j,a,b
    for (int i = 0; i < N; i++) {
      for (int j = 0; j < N; j++) {
        for (int a = 0; a < 50; a++) {
          for (int b = 0; b < 50; b++) {
            if (i > j || a > b)
              dp[i][j][a][b] = 0;
            else if (i==j && a <= nums[i] && nums[i] <= b)
              dp[i][j][a][b] = 1;
            else
              dp[i][j][a][b] = -1;
          }
        }
      }
    }
    pw.println(recursive(0,N-1,0,49));
    pw.close();
  }

  public static int recursive(int i, int j, int a, int b) {
    if (i>=N||i<0||j>=N||j<0||a>=50||a<0||b>=50||b<0)
      return 0;
    if (dp[i][j][a][b] >= 0) return dp[i][j][a][b];
    int ans = 0;
    //Use both i & j
    if (nums[i] <= nums[j]) {
      if (a <= nums[i] && nums[j] <= b) {
        ans = Math.max(ans,2+recursive(i+1,j-1,nums[i],nums[j]));
      }
    } else {
      //reverse nums[i] and nums[j]
      if (a <= nums[j] && nums[i] <= b) {
        ans = Math.max(ans,2+recursive(i+1,j-1,nums[j],nums[i]));
      }
    }

    //Use only i.
    if (a <= nums[i] && nums[i] <= b) {
      ans = Math.max(ans,1+Math.max(recursive(i+1,j,nums[i],b),recursive(i+1,j-1,a,nums[i])));
    }

    //Use only j.
    if (a <= nums[j] && nums[j] <= b) {
      ans = Math.max(ans,1+recursive(i,j-1,a,nums[j]));

      ans = Math.max(ans,1+Math.max(recursive(i,j-1,a,nums[j]),recursive(i+1,j-1,nums[j],b)));
    }

    //Use neither i nor j (right now)
    ans = Math.max(ans,Math.max(recursive(i,j-1,a,b),recursive(i+1,j,a,b)));

    dp[i][j][a][b] = ans;
    return ans;
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