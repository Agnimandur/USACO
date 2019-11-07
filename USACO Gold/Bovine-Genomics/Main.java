import java.util.*;
import java.io.*;

class Main {
  public static void main(String[] args) throws IOException {
    InputStream is = new FileInputStream("cownomics.in");
    PrintWriter pw = new PrintWriter(new BufferedWriter(new FileWriter("cownomics.out")));
    FastScanner sc = new FastScanner(is);

    int N = sc.nextInt();
    int M = sc.nextInt();
    StringBuilder[] cows = new StringBuilder[2*N];
    for (int i = 0; i < 2*N; i++) {
      cows[i] = new StringBuilder(sc.next());
    }
    
    int low = 1;
    int high = M;
    while (low < high) {
      int length = (low+high)/2;
      boolean solution = false;
      for (int i = 0; i <= M-length; i++) {
        HashSet<String> hs = new HashSet<String>();
        for (int n = 0; n < N; n++) {
          hs.add(cows[n].substring(i,i+length));
        }
        boolean good = true;
        for (int n = N; n < 2*N; n++) {
          if (hs.contains(cows[n].substring(i,i+length))) {
            good = false;
            break;
          }
        }
        if (good == true) {
          solution = true;
          break;
        }
      }
      //System.out.println(length + " " + solution);
      if (solution == true) {
        high = length;
      } else {
        low = length+1;
      }
    }
    pw.println(low);
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