
import java.util.*;
import java.io.*;

class Main {
  public static void main(String[] args) throws IOException {
    InputStream is = new FileInputStream("fortmoo.in");
    PrintWriter pw = new PrintWriter(new BufferedWriter(new FileWriter("fortmoo.out")));
    FastScanner sc = new FastScanner(is);

    int N = sc.ni();
    int M = sc.ni();
    int[][] grid = new int[N][M];
    for (int i = 0; i < N; i++) {
      String row = sc.next();
      for (int j = 0; j < M; j++) {
        if (row.charAt(j)=='X')
          grid[i][j] = 1;
      }
    }

    int[][] maxRow = new int[N][M];
    for (int j = M-1; j >= 0; j--) {
      for (int i = 0; i < N; i++) {
        if (grid[i][j] == 1 || j == M-1 || grid[i][j+1]==1)
          maxRow[i][j] = j;
        else
          maxRow[i][j] = maxRow[i][j+1];
      }
    }
    int[][] maxCol = new int[N][M];
    for (int i = N-1; i >= 0; i--) {
      for (int j = 0; j < M; j++) {
        if (grid[i][j] == 1 || i == N-1 || grid[i+1][j]==1)
          maxCol[i][j] = i;
        else
          maxCol[i][j] = maxCol[i+1][j];
      }
    }

    ArrayList<Integer>[][] emptyRows = new ArrayList[M][M];
    for (int i = 0; i < M; i++) {
      for (int j = i; j < M; j++)
        emptyRows[i][j] = new ArrayList<Integer>();
    }
    for (int row = 0; row < N; row++) {
      for (int col1 = 0; col1 < M; col1++) {
        for (int col2 = col1; col2 <= maxRow[row][col1]; col2++) {
          if (grid[row][col1]==0 && grid[row][col2]==0)
            emptyRows[col1][col2].add(row);
        }
      }
    }
    
    int ans = 0;
    for (int row1 = 0; row1 < N; row1++) {
      for (int col1 = 0; col1 < M; col1++) {
        for (int col2 = col1; col2 <= maxRow[row1][col1]; col2++) {
          if (grid[row1][col1]==1 || grid[row1][col2]==1) continue;
          int c = Math.min(maxCol[row1][col1], maxCol[row1][col2]);
          int low = 0;
          int high = emptyRows[col1][col2].size()-1;
          while (low < high) {
            int med = (low+high+1)/2;
            if (emptyRows[col1][col2].get(med) <= c)
              low = med;
            else
              high = med-1;
          }
          int row2 = emptyRows[col1][col2].get(low);
          ans = Math.max(ans, (row2 - row1 + 1) * (col2 - col1 + 1));
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