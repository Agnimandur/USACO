import java.util.*;
import java.io.*;
import java.awt.Point;

class Main {
  public static void main(String[] args) throws IOException {
    InputStream is = new FileInputStream("lasers.in");
    PrintWriter pw = new PrintWriter(new BufferedWriter(new FileWriter("lasers.out")));
    FastScanner sc = new FastScanner(is);
    int N = sc.nextInt();
    //HashMap<Integer,ArrayList<Integer>> xs = new HashMap<Integer,ArrayList<Integer>>();
    //HashMap<Integer,ArrayList<Integer>> ys = new HashMap<Integer,ArrayList<Integer>>();

    HashMap<Integer,ArrayList<Integer>> points = new HashMap<Integer,ArrayList<Integer>>();
    Point laser = new Point(sc.nextInt(),(-1*sc.nextInt())-1);
    Point barn = new Point(sc.nextInt(),(-1*sc.nextInt())-1);
    for (int i = 0; i < N; i++) {
      int x = sc.nextInt();
      int y = (-1*sc.nextInt())-1;
      if (! points.containsKey(x)) {
        points.put(x,new ArrayList<Integer>());
      }
      points.get(x).add(y);
      if (! points.containsKey(y)) {
        points.put(y,new ArrayList<Integer>());
      }
      points.get(y).add(x);
    }
    HashMap<Integer,Integer> visited = new HashMap<Integer,Integer>();
    int ans = -1;
    //BFS Algorithm
    Queue<Integer> q = new LinkedList<Integer>();
    q.add(laser.x);
    visited.put(laser.x,0);
    q.add(laser.y);
    visited.put(laser.y,0);
    while (! q.isEmpty()) {
      int p = q.remove();
      if (p == barn.x || p == barn.y) {
        ans = visited.get(p);
        break;
      }
      if (points.containsKey(p)) {
        for (int n: points.get(p)) {
          if (! visited.containsKey(n)) {
            q.add(n);
            visited.put(n,visited.get(p)+1);
          }
        }
      }
    }

    //System.out.println(ans);
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