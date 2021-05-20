import java.util.*;
import java.math.*;
import java.io.*;

class Main {
  public static void main(String[] args) throws IOException {
    InputStream is = new FileInputStream("crosswords.in");
    PrintWriter pw = new PrintWriter(new BufferedWriter(new FileWriter("crosswords.out")));
    FastScanner sc = new FastScanner(is);
    int N = sc.nextInt();
    int M = sc.nextInt();
    boolean[][] blocked = new boolean[N][M];
    for (int i = 0; i < N; i++) {
      String s = sc.next();
      for (int j = 0; j < M; j++) {
        if (s.charAt(j)=='#') {
          blocked[i][j] = true;
        }
      }
    }
    int good = 0;
    int[][] begs = new int[2500][2];
    for (int i = 0; i < N; i++) {
      for (int j = 0; j < M; j++) {
        if (blocked[i][j])
          continue;
        //horizontal
        if ((j == 0 || blocked[i][j-1]) && j < M-2 && !blocked[i][j+1] && !blocked[i][j+2]) {
          begs[good][0] = i+1;
          begs[good][1] = j+1;
          good++;
        } else if ((i == 0 || blocked[i-1][j]) && i < N-2 && !blocked[i+1][j] && !blocked[i+2][j]) {
          begs[good][0] = i+1;
          begs[good][1] = j+1;
          good++;
        }
      }
    }
    pw.println(good);
    for (int i = 0; i < good; i++) {
      pw.println(begs[i][0] + " " + begs[i][1]);
    }
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