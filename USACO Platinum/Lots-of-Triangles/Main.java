
import java.util.*;
import java.io.*;

class Main {
  public static void main(String[] args) throws IOException {
    InputStream is = new FileInputStream("triangles.in");
    PrintWriter pw = new PrintWriter(new BufferedWriter(new FileWriter("triangles.out")));
    FastScanner sc = new FastScanner(is);

    int N = sc.ni();
    long[][] points = new long[N][2];
    for (int i = 0; i < N; i++) {
      points[i][0] = sc.nl();
      points[i][1] = sc.nl();
    }
    Arrays.sort(points, new Comparator<long[]>() {
      @Override
      public int compare(long[] arr1, long[] arr2) {
        if (arr1[0] != arr2[0]) {
          if (arr1[0] < arr2[0])
            return -1;
          else
            return 1;
        } else {
          if (arr1[1] < arr2[1])
            return -1;
          else
            return 1;
        }
      }
    });

    int[][] below = new int[N][N]; // the number of points below the line from p_i to p_j.
    int[][] above = new int[N][N]; // the number of points above the line from p_i to p_j.
    long[][][] lines = new long[N][N][3];
    for (int i = 0; i < N-1; i++) {
      for (int j = i+1; j < N; j++) {
        if (points[i][0] < points[j][0]) {
          //non-vertical line
          long A = points[j][0]-points[i][0];
          long B = points[j][1]-points[i][1];
          long C = points[j][0]*points[i][1]-points[i][0]*points[j][1];
          lines[i][j] = new long[]{A,B,C};
          for (int k = 0; k < N; k++) {
            if (k==i||k==j) continue;
            if (points[i][0] < points[k][0] && points[k][0] < points[j][0]) {
              if (A*points[k][1]-B*points[k][0] < C) {
                below[i][j] += 1;
              } else {
                above[i][j] += 1;
              }
            }
          }
        }
      }
    }

    int[] ans = new int[N-2];
    for (int i = 0; i < N-2; i++) {
      for (int j = i+1; j < N-1; j++) {
        for (int k = j+1; k < N; k++) {
          int c = 0;
          if (below[i][k] > below[i][j]+below[j][k]) {
            //case 1
            c = below[i][k]-below[i][j]-below[j][k]-1;
            if (points[j][0]==points[j+1][0]&&lines[i][k][0]*points[j+1][1]-lines[i][k][1]*points[j+1][0] < lines[i][k][2]) {
              c++;
            }
          } else {
            //case 2
            c = above[i][k]-above[i][j]-above[j][k]-1;
            if (points[j][0]==points[j-1][0]&&lines[i][k][0]*points[j-1][1]-lines[i][k][1]*points[j-1][0] > lines[i][k][2]) {
              c++;
            }
          }
		  
		  if (c >= 0)
			ans[c] += 1;
        }
      }
    }

    for (int a: ans)
      pw.println(a);
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