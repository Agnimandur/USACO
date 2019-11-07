import java.util.*;
import java.io.*;

class Main {
  public static void main(String[] args) throws IOException {
    InputStream is = new FileInputStream("wifi.in");
    PrintWriter pw = new PrintWriter(new BufferedWriter(new FileWriter("wifi.out")));
    FastScanner sc = new FastScanner(is);

    int N = sc.nextInt();
    int A = 2*sc.nextInt();
    int B = sc.nextInt();

    ArrayList<Integer> cows = new ArrayList<Integer>(N+1);
    cows.add(0);
    for (int i = 1; i <= N; i++) {
      cows.add(2*sc.nextInt());
    }
    Collections.sort(cows);
    //System.out.println(cows);

    int[] dp = new int[N+1];
    Arrays.fill(dp,Integer.MAX_VALUE);
    dp[0] = 0;
    for (int i = 1; i <= N; i++) {
      for (int j = 0; j < i; j++) {
        int r = (cows.get(i)-cows.get(i-j))/2;
        dp[i] = Math.min(dp[i],dp[i-j-1]+(A+B*r));
      }
    }
    //System.out.println(Arrays.toString(dp));
    if (dp[N] % 2 == 0) {
      pw.println(dp[N]/2);
    } else {
      pw.println(dp[N]/2.0);
    }
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