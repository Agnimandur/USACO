import java.util.*;
import java.io.*;

class Main {
  public static void main(String[] args) throws IOException {
    InputStream is = new FileInputStream("tallbarn.in");
    PrintWriter pw = new PrintWriter(new BufferedWriter(new FileWriter("tallbarn.out")));
    FastScanner sc = new FastScanner(is);
    int N = sc.nextInt();
    long K = sc.nextLong()-N;
    double[] nums = new double[N];

    for (int i = 0; i < N; i++)
      nums[i] = sc.nextDouble();
    
    double min = 0;
    double max = 1e18;

    for (int i = 0; i < 500; i++) {
      double test = (min+max)/2;
      long cows = 0;
      for (double num: nums) {
        cows += (long)Math.floor((Math.sqrt(1+4*num/test)-1)/2);
      }
      if (cows >= K) {
        min = test;
      } else {
        max = test;
      }
    }

    double ans = 0;
    long c = 0;
    for (double num: nums) {
      long cows = (long)Math.floor((Math.sqrt(1+4*num/min)-1)/2);
      c += cows;
      ans += (num/(cows+1));
    }

    pw.println(Math.round(ans-(K-c)*min));
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