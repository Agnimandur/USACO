import java.util.*;
import java.io.*;

class Main {
  static ArrayDeque<long[]> s;
  static long[] p0;

  public static void main(String[] args) throws IOException {
    InputStream is = new FileInputStream("balance.in");
    PrintWriter pw = new PrintWriter(new BufferedWriter(new FileWriter("balance.out")));
    FastScanner sc = new FastScanner(is);

    int N = sc.ni();
    long[][] points = new long[N+2][2];
    points[0] = new long[]{0,0};
    for (int i = 1; i <= N; i++) {
      points[i][0] = i;
      points[i][1] = 100000*sc.nl();
    }
    points[N+1] = new long[]{N+1,0};
    double[] atan = new double[N+2];
    for (int i = 1; i < atan.length; i++) {
      atan[i] = points[i][1]/(i+0.0);
    }

    Random rgen = new Random();
		for (int i = 1; i < points.length; i++) {
      int randomPosition = rgen.nextInt(points.length-1)+1;
      long[] temp = points[i];
      points[i] = points[randomPosition];
      points[randomPosition] = temp;
		}

    Arrays.sort(points, 1, N+2, new Comparator<long[]>() {
      @Override
      public int compare(long[] arr1, long[] arr2) {
        double a1 = atan[(int)arr1[0]];
        double a2 = atan[(int)arr2[0]];
        if (Math.abs(a1-a2) < 1e-7) {
          if (arr1[0] < arr2[0])
            return -1;
          else
            return 1;
        } else {
          if (a1 < a2)
            return -1;
          else
            return 1;
        }
      }
    });

    //Graham Scan
    p0 = points[0];

    int m = 1;
    for (int i=1; i < points.length; i++) { 
      while (i < points.length-1 && orientation(p0, points[i],points[i+1]) == 0) i++; 

      points[m] = points[i]; 
      m++;
    }

    s = new ArrayDeque<long[]>();
    s.push(points[0]);
    s.push(points[1]);
    s.push(points[2]);

    for (int i = 3; i < m; i++) {
      // Keep removing top while the angle formed by 
      // points next-to-top, top, and points[i] makes 
      // a non-left turn 
      while (badTurn(points[i])) {
        s.pop();
      }
      s.push(points[i]);
    }

    long[] hull = new long[N+2];
    Arrays.fill(hull,-1);
    while (! s.isEmpty()) {
      long[] p = s.poll();
      hull[(int)p[0]] = p[1];
    }
    int L = 0;
    int R = 0;
    for (int x = 1; x <= N; x++) {
      if (hull[x] >= 0) {
        L = x;
        R = x;
        pw.println(hull[x]);
      } else {
        for (int j = x+1; j <= N+1; j++) {
          if (hull[j] >= 0) {
            R = j;
            break;
          }
        }

        long FL = hull[L];
        long FR = hull[R];

        long num = ((x-L)*(FR-FL)+(R-L)*FL);
        long den = R-L;
        pw.println(num/den);
      }
    }
    pw.close();
  }

  public static boolean badTurn(long[] p) {
    long[] top = s.poll();
    long[] nextOnTop = s.peek();
    s.push(top);

    if (orientation(nextOnTop,top,p) != 2)
      return true;
    else
      return false;
  }

  public static int orientation(long[] p, long[] q, long[] r) {
    long val = (q[1] - p[1]) * (r[0] - q[0]) - (q[0] - p[0]) * (r[1] - q[1]);
    if (val > 0) {
      return 1; //clockwise
    } else if (val < 0){
      return 2; //counter-clockwise
    } else {
      return 0; //collinear
    }
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