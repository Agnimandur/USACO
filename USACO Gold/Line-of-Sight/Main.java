import java.util.*;
import java.io.*;

class Main {
  public static void main(String[] args) throws IOException {
    InputStream is = new FileInputStream("sight.in");
    PrintWriter pw = new PrintWriter(new BufferedWriter(new FileWriter("sight.out")));
    FastScanner sc = new FastScanner(is);

    int N = sc.ni();
    double R = sc.nd();
    Point[] points = new Point[N];
    for (int i = 0; i < N; i++) {
      double x = sc.nd();
      double y = sc.nd();
      double len = Math.sqrt(x*x+y*y);
      double a1 = Math.acos(R/len); //angle formed between the upper line of the silo view, and the line connecting the center to the point.
      double a2 = Math.atan2(y,x); //angle of the point;

      double exit = a2+a1;
      double enter = a2-a1;
      if (enter < 0) {
        enter += (2*Math.PI);
        exit += (2*Math.PI);
      }
      points[i] = new Point(enter,exit);
    }

    Arrays.sort(points);

    PriorityQueue<Double> pq = new PriorityQueue<Double>();
    for(int i = 0; i < N; i++) {
      while (!pq.isEmpty() && pq.peek() < points[i].enter) {
        //this point is no longer in the "angle sweep"
        pq.poll();
      }

      //add this exit point to the angle sweep
      pq.add(points[i].exit);
      points[i].enter += 2*Math.PI;
      points[i].exit += 2*Math.PI;
    }


    //second sweep of the points
    long ans = 0;

    for(int i = 0; i < N; i++) {
      while (!pq.isEmpty() && pq.peek() < points[i].enter) {
        //this point is no longer in the "angle sweep"
        pq.poll();
      }

      ans += pq.size();

      //add this exit point to the angle sweep
      pq.add(points[i].exit);
      points[i].enter += 2*Math.PI;
      points[i].exit += 2*Math.PI;
    }

    pw.println(ans);
    pw.close();
  }

  static class Point implements Comparable<Point> {
    double enter;
    double exit;
    public Point(double enterAngle, double exitAngle) {
      //the angle covered by this point in the sweep
      enter = enterAngle;
      exit = exitAngle;
    }

    public int compareTo(Point p) {
      if (enter < p.enter)
        return -1;
      else
        return 1;
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