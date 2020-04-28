import java.util.*;
import java.io.*;

class Main {
  public static void main(String[] args) throws IOException {
    InputStream is = new FileInputStream("lazy.in");
    PrintWriter pw = new PrintWriter(new BufferedWriter(new FileWriter("lazy.out")));
    FastScanner sc = new FastScanner(is);

    int N = sc.ni();
    int K = sc.ni();
    long[][] nums = new long[3*N][3*N];
    long[][] pref = new long[3*N][3*N];
    long full = 0;
    for (int i = N; i < 2*N; i++) {
      for (int j = N; j < 2*N; j++) {
        nums[i][j] = sc.nl();
        full += nums[i][j];
        pref[i][j] = pref[i][j-1]+nums[i][j];
      }
    }
    long ans = 0;
    if (K >= N) {
      pw.println(full);
      pw.close();
      return;
    }
    int negK = (-1)*K;
    for (int i = N; i < 2*N; i++) {
      for (int j = N; j < 2*N; j++) {
        long sum = 0;
        for (int k = negK; k < 0; k++) {
          sum += (pref[i+k][j+(K+k)]-pref[i+k][(j-1)-(K+k)]);
        }
        for (int k = 0; k <= K; k++) {
          sum += (pref[i+k][j+(K-k)]-pref[i+k][(j-1)-(K-k)]);
        }
        ans = Math.max(ans,sum);
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