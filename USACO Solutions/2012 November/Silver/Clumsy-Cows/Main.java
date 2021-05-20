import java.util.*;
import java.io.*;

class Main {
  public static void main(String[] args) throws IOException {
    InputStream is = new FileInputStream("clumsy.in");
    PrintWriter pw = new PrintWriter(new BufferedWriter(new FileWriter("clumsy.out")));
    FastScanner sc = new FastScanner(is);

    String s = sc.next();
    int cnt = 0;
    int ans = 0;
    for (int i = 0; i < s.length(); i++) {
      if (s.charAt(i) == '(')
        cnt++;
      else
        cnt--;
      if (cnt < 0) {
        cnt += 2;
        ans++;
      }
    }
    if (cnt > 0) {
      ans += cnt/2;
      cnt = 0;
    }
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