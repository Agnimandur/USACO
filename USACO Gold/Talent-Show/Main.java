import java.util.*;
import java.math.*;
import java.io.*;

class Main {
  public static void main(String[] args) throws IOException {
    FastScanner sc = new FastScanner("talent.in");
    PrintWriter pw = new PrintWriter(new BufferedWriter(new FileWriter("talent.out")));
    int N = sc.nextInt();
    int W = sc.nextInt();
    int[][] cows = new int[N+1][2]; //{weight,talent}
    for (int i = 1; i <= N; i++) {
      cows[i][0] = sc.nextInt();
      cows[i][1] = sc.nextInt();
    }

    int[][] knapsack = new int[N+1][250001];
    for (int[] row : knapsack) 
      Arrays.fill(row, Integer.MAX_VALUE);
    for (int cow = 1; cow <= N; cow++) {
      for (int talent = 0; talent <= 250000; talent++) {
        knapsack[cow][talent] = knapsack[cow-1][talent];
      }
      knapsack[cow][cows[cow][1]] = Math.min(knapsack[cow][cows[cow][1]],cows[cow][0]);
      for (int talent = cows[cow][1]+1; talent <= 250000; talent++) {
        if (knapsack[cow-1][talent-cows[cow][1]] < Integer.MAX_VALUE) {
          knapsack[cow][talent] = Math.min(knapsack[cow][talent],knapsack[cow-1][talent-cows[cow][1]]+cows[cow][0]);
        }
      }
    }

    double best = 0.0;
    for (int talent = 0; talent <= 250000; talent++) {
      if (knapsack[N][talent] >= W) {
        best = Math.max(best,(talent+0.0)/knapsack[N][talent]);
      }
    }
    int ans = (int)Math.floor(1000*best);
    //System.out.println(ans);
    pw.println(ans);
    pw.close();
  }

  static class FastScanner {
    BufferedReader br; 
    StringTokenizer st; 
  
    public FastScanner(String filename) throws IOException { 
      br = new BufferedReader(new FileReader(filename));
    }
  
    String next() { 
      while (st == null || !st.hasMoreElements()) { 
        try { 
          st = new StringTokenizer(br.readLine()); 
        } 
        catch (IOException  e) { 
          e.printStackTrace(); 
        } 
      } 
      return st.nextToken(); 
    } 
  
    int nextInt() { 
      return Integer.parseInt(next()); 
    } 
  
    long nextLong() { 
      return Long.parseLong(next()); 
    } 
  
    double nextDouble() { 
      return Double.parseDouble(next()); 
    } 
  
    String nextLine() { 
      String str = ""; 
      try { 
        str = br.readLine(); 
      } catch (IOException e) { 
        e.printStackTrace(); 
      } 
      return str; 
    }
  }
}