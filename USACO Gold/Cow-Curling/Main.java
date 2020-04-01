import java.util.*;
import java.io.*;

class Main {
  static Point p0;
  static ArrayDeque<Point> s;

  public static void main(String[] args) throws IOException {
    InputStream is = new FileInputStream("curling.in");
    PrintWriter pw = new PrintWriter(new BufferedWriter(new FileWriter("curling.out")));
    FastScanner sc = new FastScanner(is);

    int N = sc.ni();
    Point[] reds = new Point[N];
    for (int i = 0; i < N; i++) {
      reds[i] = new Point(sc.nd(),sc.nd());
    }

    Point[] blues = new Point[N];
    for (int i = 0; i < N; i++) {
      blues[i] = new Point(sc.nd(),sc.nd());
    }

    //build the convex hull
    p0 = new Point(0,0);
    s = new ArrayDeque<Point>();
    int redScore = score(reds,blues);
    int blueScore = score(blues,reds);

    pw.println(redScore + " " + blueScore);
    pw.close();
  }

  public static int score(Point[] points, Point[] other) {
    int score = 0;
    ArrayList<Point> hull = grahamScan(points);
    if (hull.size() >= 3) {
      Point c = center(hull);
      for (Point p: hull) {
        double xShift = p.x-c.x;
        double yShift = p.y-c.y;
        double a = Math.PI/2;
        if (xShift != 0) {
          a = Math.atan(yShift/xShift);
        }
        if (xShift < 0 || (equal(xShift,0)&&yShift < 0)) {
          a += Math.PI;
        }
        if (a < 0) {
          a += 2*Math.PI;
        }
        p.angle = a;
      }

      Collections.sort(hull);
      double[] angles = new double[hull.size()];
      for (int i = 0; i < hull.size(); i++) {
        angles[i] = hull.get(i).angle;
      }

      for (Point p: other) {
        double x = p.x-c.x;
        double y = p.y-c.y;
        double a = Math.PI/2;
        if (x != 0) {
          a = Math.atan(y/x);
        }
        if (x < 0 || (equal(x,0) && y < 0)) {
          a += Math.PI;
        }
        if (a < 0) {
          a += 2*Math.PI;
        }

        int onEdge = -1;
        for (int i = 0; i < angles.length; i++) {
          if (equal(a,angles[i])) {
            onEdge = i;
            break;
          }
        }

        if (onEdge >= 0) {
          if (distSq(c,p) < distSq(c,hull.get(onEdge))) {
            score++;
          }
        } else {
          int index = angles.length-1;
          if (a > angles[0]) {
            int low = 0;
            int high = angles.length-1;
            while (low < high) {
              int med = (low+high+1)/2;
              if (angles[med] < a) {
                low = med;
              } else {
                high = med-1;
              }
            }
            index = low;
          }

          int index2 = (index+1)%(angles.length);
          double CROSS = cross(hull.get(index),hull.get(index2),c) * cross(hull.get(index),hull.get(index2),p);
          if (equal(CROSS,0) || CROSS>0) {
            //the other point is inside the hull or on the line.
            score++;
          }
        }
      }
    }

    return score;
  }

  public static boolean equal(double a, double b) {
    return (Math.abs(a-b) < 0.0000001);
  }

  public static double cross(Point p1, Point p2, Point p) {
    //find the cross product of the line going from p1 to p2 with point p
    return ((p.x-p1.x)*(p2.y-p1.y)-(p.y-p1.y)*(p2.x-p1.x));
  }

  public static Point center(ArrayList<Point> hull) {
    double xSum = 0.0;
    double ySum = 0.0;
    for (Point p: hull) {
      xSum += p.x;
      ySum += p.y;
    }
    xSum /= hull.size();
    ySum /= hull.size();
    Point c = new Point(xSum,ySum);
    return c;
  }

  public static ArrayList<Point> grahamScan(Point[] input) {
    int n = input.length;
    Point[] points = new Point[input.length];
    for (int i = 0; i < n; i++)
      points[i] = input[i];

    // Find the bottommost point 
    double ymin = points[0].y;
    int min = 0; 
    for (int i = 1; i < n; i++) { 
      double y = points[i].y; 
      // Pick the bottom-most or chose the left 
      // most point in case of tie 
      if ((y < ymin) || (equal(ymin,y) && points[i].x < points[min].x)) {
        ymin = points[i].y;
        min = i; 
      }
    }

    // Place the bottom-most point at first position
    Point temp = points[0];
    points[0] = points[min];
    points[min] = temp;

    p0 = points[0];

    Arrays.sort(points,1,points.length);


    int m = 1;
    for (int i=1; i<n; i++) { 
      // Keep removing i while angle of i and i+1 is same 
      while (i < n-1 && orientation(p0, points[i],points[i+1]) == 0) i++; 


      points[m] = points[i]; 
      m++;
    }

    if (m < 3) {
      //no convex hull possible
      ArrayList<Point> ans = new ArrayList<Point>();
      return ans;
    }


    s = new ArrayDeque<Point>();
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

    ArrayList<Point> ans = new ArrayList<Point>();
    while (! s.isEmpty()) {
      ans.add(s.poll());
    }

    return ans;
  }

  public static boolean badTurn(Point p) {
    Point top = s.poll();
    Point nextOnTop = s.peek();
    s.push(top);

    if (orientation(nextOnTop,top,p) != 2)
      return true;
    else
      return false;
  }

  public static int orientation(Point p, Point q, Point r) {
    double val = (q.y - p.y) * (r.x - q.x) - (q.x - p.x) * (r.y - q.y);
    if (equal(val,0)) {
      return 0; //collinear
    } else if (val > 0) {
      return 1; //clockwise
    } else if (val < 0){
      return 2; //counter-clockwise
    } else {
      return 0;
    }
  }

  public static double distSq(Point p1, Point p2) { 
    return Math.pow(p1.x - p2.x,2) + Math.pow(p1.y - p2.y,2); 
  }

  static class Point implements Comparable<Point> {
    public double x; 
    public double y;
    public double angle;
    public Point(double x, double y) {
      this.x = x;
      this.y = y;
      angle = -1.0;
    }

    public int compareTo(Point p) {
      if (angle == -1.0) {
        // Find orientation 
        int o = orientation(p0,this,p); 
        if (o == 0) {
          double d1 = distSq(p0,this);
          double d2 = distSq(p0,p);
          if (d1 < d2) {
            return -1;
          } else {
            return 1;
          }
        } else {
          if (o==1) {
            return 1;
          } else {
            return -1;
          }
        }
      } else {
        //sort by angle
        if (angle < p.angle) {
          return -1;
        } else {
          return 1;
        }
      }
    }

    public String toString() {
      return ("(" + x + "," + y + ")");
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