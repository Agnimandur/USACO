import java.util.*;
import java.io.*;

class Main {
  public static void main(String[] args) throws IOException {
    InputStream is = new FileInputStream("cownomics.in");
    PrintWriter pw = new PrintWriter(new BufferedWriter(new FileWriter("cownomics.out")));
    FastScanner sc = new FastScanner(is);

    int N = sc.nextInt();
    int M = sc.nextInt();
    String[] cows = new String[2*N];
    for (int i = 0; i < 2*N; i++) {
      cows[i] = sc.next();
    }

    int ans = 0;
    for (int i = 0; i < M-2; i++) {
      for (int j = i+1; j < M-1; j++) {
        for (int k = j+1; k < M; k++) {
          HashSet<String> hs = new HashSet<String>();
          for (int n = 0; n < N; n++) {
            StringBuilder temp = new StringBuilder();
            temp.append(cows[n].charAt(i));
            temp.append(cows[n].charAt(j));
            temp.append(cows[n].charAt(k));
            hs.add(temp.toString());
          }
          int good = 1;
          for (int n = N; n < 2*N; n++) {
            StringBuilder temp = new StringBuilder();
            temp.append(cows[n].charAt(i));
            temp.append(cows[n].charAt(j));
            temp.append(cows[n].charAt(k));
            if (hs.contains(temp.toString())) {
              good = 0;
              break;
            }
          }

          ans += good;
        }
      }
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