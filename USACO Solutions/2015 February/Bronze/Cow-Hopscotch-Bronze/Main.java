import java.util.*;
import java.math.*;
import java.io.*;

class Main {
  public static void main(String[] args) throws IOException {
    InputStream is = new FileInputStream("hopscotch.in");
    PrintWriter pw = new PrintWriter(new BufferedWriter(new FileWriter("hopscotch.out")));
    FastScanner sc = new FastScanner(is);

    int N = sc.nextInt();
    int M = sc.nextInt();
    char[][] grid = new char[N][M];
    for (int i = 0; i < N; i++) {
      String row = sc.next();
      for (int j = 0; j < M; j++) {
        grid[i][j] = row.charAt(j);
      }
    }
    long[][] paths = new long[N][M];
    paths[N-1][M-1] = 1; //at the destination
    for (int i = N-2; i >= 0; i--) {
      for (int j = M-2; j >= 0; j--) {
        char c = grid[i][j];
        long val = 0;
        for (int a = i+1; a < N; a++) {
          for (int b = j+1; b < M; b++) {
            if (grid[a][b] != c) {
              val += paths[a][b];
            }
          }
        }
        paths[i][j] = val;
      }
    }
    //System.out.println(paths[0][0]);
    pw.println(paths[0][0]);
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