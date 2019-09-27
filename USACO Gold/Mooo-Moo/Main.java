import java.util.*;
import java.math.*;
import java.io.*;

class Main {
  public static void main(String[] args) throws IOException {
    InputStream is = new FileInputStream("mooomoo.in");
    PrintWriter pw = new PrintWriter(new BufferedWriter(new FileWriter("mooomoo.out")));
    FastScanner sc = new FastScanner(is);

    int N = sc.nextInt();
    int B = sc.nextInt();
    int[] loudness = new int[B];
    for (int i = 0; i < B; i++)
      loudness[i] = sc.nextInt();
    Arrays.sort(loudness);
    int[] fields = new int[N];
    for (int i = 0; i < N; i++)
      fields[i] = sc.nextInt();
    
    int[] dp = new int[100001];
    for (int i = 1; i <= 100000; i++)
      dp[i] = Integer.MAX_VALUE;
    for (int i = 0; i <= 100000-loudness[B-1]; i++) {
      if (dp[i] < Integer.MAX_VALUE) {
        for (int j = 0; j < B; j++)
          dp[i+loudness[j]] = Math.min(dp[i+loudness[j]],dp[i]+1);
      }
    }

    int[] carry = new int[N];
    int[] realVolume = new int[N];
    boolean good = true;
    realVolume[0] = fields[0];
    int moo = fields[0];
    for (int i = 1; i < N; i++) {
      moo = Math.max(0,moo-1);
      realVolume[i] = fields[i]-moo;
      if (realVolume[i] < 0) {
        good = false;
        break;
      }
      moo += realVolume[i];
    }

    int ans = 0;
    if (good) {
      for (int i = 0; i < N; i++) {
        if (dp[realVolume[i]] == Integer.MAX_VALUE) {
          ans = -1;
          break;
        } else {
          ans += dp[realVolume[i]];
        }
      }
    } else {
      ans = -1;
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