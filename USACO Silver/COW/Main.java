import java.util.*;
import java.math.*;
import java.io.*;

class Main {
  public static void main(String[] args) throws IOException {
    InputStream is = new FileInputStream("cow.in");
    PrintWriter pw = new PrintWriter(new BufferedWriter(new FileWriter("cow.out")));
    FastScanner sc = new FastScanner(is);
    int N = sc.nextInt();
    String s = sc.next();
    int[] Cs = new int[N+1];
    Cs[0] = 0;
    for (int i = 1; i <= N; i++) {
      if (s.charAt(i-1)=='C') {
        Cs[i] = Cs[i-1]+1;
      } else {
        Cs[i] = Cs[i-1];
      }
    }
    int[] Ws = new int[N+1];
    Ws[N] = 0;
    for (int i = N-1; i >= 0; i--) {
      if (s.charAt(i)=='W') {
        Ws[i] = Ws[i+1]+1;
      } else {
        Ws[i] = Ws[i+1];
      }
    }

    long ans = 0;
    for (int i = 0; i < N; i++) {
      if (s.charAt(i)=='O') {
        ans += ((long)Cs[i+1])*Ws[i];
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