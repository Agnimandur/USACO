import java.util.*;
import java.math.*;
import java.io.*;

class Main {
  public static void main(String[] args) throws IOException {
    InputStream is = new FileInputStream("marathon.in");
    PrintWriter pw = new PrintWriter(new BufferedWriter(new FileWriter("marathon.out")));
    FastScanner sc = new FastScanner(is);
    int N = sc.nextInt();
    int[][] points = new int[N][2];
    int dist = 0;
    for (int i = 0; i < N; i++) {
      points[i][0] = sc.nextInt();
      points[i][1] = sc.nextInt();
      if (i > 0) {
        dist += Math.abs(points[i][0]-points[i-1][0]);
        dist += Math.abs(points[i][1]-points[i-1][1]);
      }
    }
    int best = dist;
    for (int i = 1; i < N-1; i++) {
        int save = 0;
        save += Math.abs(points[i-1][0]-points[i][0]);
        save += Math.abs(points[i-1][1]-points[i][1]);
        save += Math.abs(points[i][0]-points[i+1][0]);
        save += Math.abs(points[i][1]-points[i+1][1]);
        save -= Math.abs(points[i-1][0]-points[i+1][0]);
        save -= Math.abs(points[i-1][1]-points[i+1][1]);
        best = Math.min(best,dist-save);
    }
    pw.println(best);
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