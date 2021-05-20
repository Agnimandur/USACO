import java.util.*;
import java.io.*;

class Main {
  public static final String TASKNAME = "pails";

  public static void main(String[] args) throws IOException {
    InputStream is = new FileInputStream(TASKNAME+".in");
    PrintWriter pw = new PrintWriter(new BufferedWriter(new FileWriter(TASKNAME+".out")));
    FastScanner sc = new FastScanner(is);

    int X = sc.ni();
    int Y = sc.ni();
    int K = sc.ni();
    int M = sc.ni();

    boolean[][][] dp = new boolean[K+1][X+1][Y+1];
    dp[0][0][0] = true;
    for (int op = 0; op < K; op++) {
      for (int x = 0; x <= X; x++) {
        for (int y = 0; y <= Y; y++) {
          if (!dp[op][x][y]) continue;

          //fill either pail to the top
          dp[op+1][X][y] = true;
          dp[op+1][x][Y] = true;

          //empty either pail
          dp[op+1][0][y] = true;
          dp[op+1][x][0] = true;

          //pour x into y
          int xy = Math.min(x,Y-y);
          dp[op+1][x-xy][y+xy] = true;

          //pour y into x
          int yx = Math.min(y,X-x);
          dp[op+1][x+yx][y-yx] = true;
        }
      }
    }

    int ans = M;
    for (int op = 1; op <= K; op++) {
      for (int x = 0; x <= X; x++) {
        for (int y = 0; y <= Y; y++) {
          if (dp[op][x][y]) {
            ans = Math.min(ans,Math.abs(M-x-y));
          }
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