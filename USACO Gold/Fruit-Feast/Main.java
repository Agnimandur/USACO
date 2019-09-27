import java.util.*;
import java.math.*;
import java.io.*;

class Main {
  public static void main(String[] args) throws IOException {
    InputStream is = new FileInputStream("feast.in");
    PrintWriter pw = new PrintWriter(new BufferedWriter(new FileWriter("feast.out")));
    FastScanner sc = new FastScanner(is);
    int T = sc.nextInt();
    int A = sc.nextInt();
    int B = sc.nextInt();
    boolean[] reachable = new boolean[T+1];
    reachable[0] = true;
    //Base knapsack dp
    for (int i = 1; i <= T; i++) {
      if ((i>=A && reachable[i-A]) || (i>=B && reachable[i-B])) {
        reachable[i] = true;
      }
    }
    //After water
    for (int i = 0; i <= T; i++) {
      if (reachable[i]) {
        reachable[i/2] = true;
      }
    }
    //Rerun dp with the water results
    int ans = 0;
    for (int i = 1; i <= T; i++) {
      if ((i>=A && reachable[i-A]) || (i>=B && reachable[i-B])) {
        reachable[i] = true;
        ans = i;
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