import java.util.*;
import java.math.*;
import java.io.*;

class Main {
  public static void main(String[] args) throws IOException {
    InputStream is = new FileInputStream("radio.in");
    PrintWriter pw = new PrintWriter(new BufferedWriter(new FileWriter("radio.out")));
    FastScanner sc = new FastScanner(is);
    int N = sc.nextInt();
    int M = sc.nextInt();
    int[][] fPos= new int[N+1][2];
    fPos[0] = new int[]{sc.nextInt(),sc.nextInt()};
    int[][] bPos = new int[M+1][2];
    bPos[0] = new int[]{sc.nextInt(),sc.nextInt()};
    String fjMove = sc.next();
    String besMove = sc.next();
    for (int i = 1; i <= N; i++) {
      if (fjMove.charAt(i-1)=='N')
        fPos[i] = new int[]{fPos[i-1][0],fPos[i-1][1]+1};
      else if (fjMove.charAt(i-1)=='S')
        fPos[i] = new int[]{fPos[i-1][0],fPos[i-1][1]-1};
      else if (fjMove.charAt(i-1)=='E')
        fPos[i] = new int[]{fPos[i-1][0]+1,fPos[i-1][1]};
      else
        fPos[i] = new int[]{fPos[i-1][0]-1,fPos[i-1][1]};
    }
    for (int i = 1; i <= M; i++) {
      if (besMove.charAt(i-1)=='N')
        bPos[i] = new int[]{bPos[i-1][0],bPos[i-1][1]+1};
      else if (besMove.charAt(i-1)=='S')
        bPos[i] = new int[]{bPos[i-1][0],bPos[i-1][1]-1};
      else if (besMove.charAt(i-1)=='E')
        bPos[i] = new int[]{bPos[i-1][0]+1,bPos[i-1][1]};
      else
        bPos[i] = new int[]{bPos[i-1][0]-1,bPos[i-1][1]};
    }
    
    long[][] dp = new long[N+1][M+1];
    for (int i = 1; i <= N; i++)
      dp[i][0] = dp[i-1][0] + dist(fPos[i],bPos[0]);
    for (int j = 1; j <= M; j++)
      dp[0][j] = dp[0][j-1] + dist(fPos[0],bPos[j]);
    for (int i = 1; i <= N; i++) {
      for (int j = 1; j <= M; j++) {
        long num = Math.min(dp[i-1][j],dp[i][j-1]);
        num = Math.min(num,dp[i-1][j-1]);
        dp[i][j] = num+dist(fPos[i],bPos[j]);
      }
    }
    //System.out.println(dp[N][M]);

    pw.println(dp[N][M]);
    pw.close();
  }
      
  public static long dist(int[] point, int[] point2) {
    return (long)(Math.pow((point2[1]-point[1]),2)+Math.pow((point2[0]-point[0]),2));
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