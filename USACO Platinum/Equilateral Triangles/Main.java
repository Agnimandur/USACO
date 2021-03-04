import java.util.*;
import java.io.*;

class Main {
  public static void main(String[] args) throws IOException {
    InputStream is = new FileInputStream("triangles.in");
    PrintWriter pw = new PrintWriter(new BufferedWriter(new FileWriter("triangles.out")));
    FastScanner sc = new FastScanner(is);

    int N = sc.ni();
    int[][] grid = new int[3*N][3*N];
    for (int i = N; i < 2*N; i++) {
      String row = sc.next();
      for (int j = N; j < 2*N; j++) {
        if (row.charAt(j-N) == '*') {
          grid[i][j] = 1;
        }
      }
    }

    long[][] pref1 = new long[3*N][3*N]; //the prefix sum of the diagonal beginning in the lower-left, and ending in the upper-right.
    
    for (int col = 1; col <= 3*N-1; col++) {
      for (int row = 3*N-2; row >= 0; row--) {
        pref1[row][col] = pref1[row+1][col-1]+grid[row][col];
      }
    }

    long[][] pref2 = new long[3*N][3*N]; //the prefix sum of the diagonal beginning in the upper-left, and ending in the lower-right.
    
    for (int col = 1; col <= 3*N-1; col++) {
      for (int row = 1; row <= 3*N-1; row++) {
        pref2[row][col] = pref2[row-1][col-1]+grid[row][col];
      }
    }

    long ans = 0;
    long overcount = 0;
    for (int i = N; i < 2*N; i++) {
      for (int j = N; j < 2*N; j++) {
        //[i,j] is the position of a fixed vertex of the equilateral triangle
        if (grid[i][j] == 0) continue;

        //case 1: shift the second vertex up and to the right
        int row = i-1;
        int col = j+1;
        int d = 1;
        while (row >= N && col < 2*N) {
          //[row,col] is the position of the second vertex of the triangle.
          if (grid[row][col] == 1) {
            ans += (pref1[i-2*d][j]-pref1[row+1][col-2*d-1]);
            ans += (pref1[i][j+2*d]-pref1[row+2*d+1][col-1]);
            overcount += grid[i-2*d][j];
            overcount += grid[row][col-2*d];
            overcount += grid[i][j+2*d];
            overcount += grid[row+2*d][col];
          }
          row--;
          col++;
          d++;
        }

        //case2: shift the second vertex down and to the right
        row = i+1;
        col = j+1;
        d = 1;
        while (row < 2*N && col < 2*N) {
          //[row,col] is the position of the second vertex of the triangle.
          if (grid[row][col] == 1) {
            ans += (pref2[i][j+2*d]-pref2[row-2*d-1][col-1]);
            ans += (pref2[i+2*d][j]-pref2[row-1][col-2*d-1]);
            overcount += grid[i][j+2*d];
            overcount += grid[row-2*d][col];
            overcount += grid[i+2*d][j];
            overcount += grid[row][col-2*d];
          }
          row++;
          col++;
          d++;
        }
      }
    }

    //final answer is ans-(overcount/2)
    pw.println(ans-overcount/2);
    //System.out.println(checker(grid));
    pw.close();
  }

  public static int checker(int[][] grid) {
    int N = grid.length/7;
    int ans = 0;
    for (int r1 = 0; r1 < N; r1++) {
      for (int c1 = 0; c1 < N; c1++) {
        for (int r2 = 0; r2 < N; r2++) {
          for (int c2 = 0; c2 < N; c2++) {
            for (int r3 = 0; r3 < N; r3++) {
              for (int c3 = 0; c3 < N; c3++) {
                if (grid[r1+3*N][c1+3*N]==0||grid[r2+3*N][c2+3*N]==0||grid[r3+3*N][c3+3*N]==0)
                  continue;
                if ((r1!=r2||c1!=c2)&&(r1!=r3||c1!=c3)&&(r2!=r3||c2!=c3)) {
                  int d12 = Math.abs(r1-r2)+Math.abs(c1-c2);
                  int d13 = Math.abs(r1-r3)+Math.abs(c1-c3);
                  int d23 = Math.abs(r2-r3)+Math.abs(c2-c3);
                  if (d12 == d13 && d13 == d23) {
                    ans++;
                  }
                }
              }
            }
          }
        }
      }
    }

    return (ans/6);
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