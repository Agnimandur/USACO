import java.util.*;
import java.io.*;

class Main {
  public static void main(String[] args) throws IOException {
    InputStream is = new FileInputStream("262144.in");
    PrintWriter pw = new PrintWriter(new BufferedWriter(new FileWriter("262144.out")));
    FastScanner sc = new FastScanner(is);

    int N = sc.ni();
    int[] nums = new int[N];
    for (int i = 0; i < N; i++)
      nums[i] = sc.ni()-1;

    //row is the starting index, column is the number, value is the unique ending index (if any) of a range that will collapse down into column.
    int[][] dp = new int[N][58];
    for (int i = 0; i < N; i++) {
      Arrays.fill(dp[i],-1);
      dp[i][nums[i]] = i;
    }

    for (int j = 1; j < 58; j++) {
      for (int i = 0; i < N; i++) {
        if (dp[i][j] == -1 && dp[i][j-1] >= 0 && dp[i][j-1] < N-1) {
          dp[i][j] = dp[dp[i][j-1]+1][j-1];
        }
      }
    }
    
    int ans = 0;
    findAns:
    for (int j = 57; j >= 0; j--) {
      for (int i = 0; i < N; i++) {
        if (dp[i][j] >= 0) {
          ans = j+1;
          break findAns;
        }
      }
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