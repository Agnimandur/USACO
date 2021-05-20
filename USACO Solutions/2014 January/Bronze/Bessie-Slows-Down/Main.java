import java.util.*;
import java.io.*;

class Main {
  public static final String TASKNAME = "slowdown";

  public static void main(String[] args) throws IOException {
    InputStream is = new FileInputStream(TASKNAME+".in");
    PrintWriter pw = new PrintWriter(new BufferedWriter(new FileWriter(TASKNAME+".out")));
    FastScanner sc = new FastScanner(is);

    int N = sc.ni();
    PriorityQueue<Integer> time = new PriorityQueue<Integer>();
    PriorityQueue<Integer> dist = new PriorityQueue<Integer>();
    for (int i = 0; i < N; i++) {
      char c = sc.next().charAt(0);
      int v = sc.ni();
      if (c=='T')
        time.add(v);
      else
        dist.add(v);
    }
    dist.add(1000);

    double x = 0;
    double t = 0;
    int speed = 1;
    while (!dist.isEmpty()) {
      double t1 = 1000000000000.0;
      if (!time.isEmpty()) t1 = time.peek()-t;

      double t2 = (dist.peek()-x)*speed;

      if (t1 < t2) {
        x += t1/speed;
        t = time.poll();
      } else {
        x = dist.poll();
        t += t2;
      }
      speed++;
    }
    int ans = (int)Math.round(t);
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