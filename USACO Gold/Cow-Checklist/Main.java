import java.util.*;
import java.io.*;

class Main {
  public static void main(String[] args) throws IOException {
    InputStream is = new FileInputStream("checklist.in");
    PrintWriter pw = new PrintWriter(new BufferedWriter(new FileWriter("checklist.out")));
    FastScanner sc = new FastScanner(is);

    int H = sc.nextInt();
    int G = sc.nextInt();
    int[][] holsteins = new int[H+1][2];
    for (int i = 1; i <= H; i++) {
      holsteins[i] = new int[]{sc.nextInt(),sc.nextInt()};
    }
    int[][] guernseys = new int[G+1][2];
    for (int i = 1; i <= G; i++) {
      guernseys[i] = new int[]{sc.nextInt(),sc.nextInt()};
    }
    long[][][] dp = new long[H+1][G+1][2];
    for (int i = 0; i <= H; i++) {
      for (int j = 0; j <= G; j++) {
        for (int k = 0; k < 2; k++) {
          dp[i][j][k] = Long.MAX_VALUE/2;
        }
      }
    }
    dp[1][0][0] = 0;
    for (int h = 2; h <= H; h++) {
      dp[h][0][0] = dp[h-1][0][0]+dist(holsteins[h-1],holsteins[h]);
    }
    for (int h = 1; h <= H; h++) {
      for (int g = 1; g <= G; g++) {
        long distHH = dp[h-1][g][0]+dist(holsteins[h-1],holsteins[h]);
        long distGH = dp[h-1][g][1]+dist(guernseys[g],holsteins[h]);
        long distHG = dp[h][g-1][0]+dist(holsteins[h],guernseys[g]);
        long distGG = dp[h][g-1][1]+dist(guernseys[g-1],guernseys[g]);
        dp[h][g][0]=Math.min(distHH,distGH);
        dp[h][g][1]=Math.min(distHG,distGG);
      }
    }
    pw.println(dp[H][G][0]);
    pw.close();
  }

  public static int dist(int[] c1, int[] c2) {
    return ((c2[1]-c1[1])*(c2[1]-c1[1])+(c2[0]-c1[0])*(c2[0]-c1[0]));
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